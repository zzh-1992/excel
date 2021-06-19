/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.excel.xml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * xml解析工具
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-05-23 4:37 下午
 */
public class XmlTools2 {

    private static final String SECRET = "secret";
    private static final String IV = "iv";
    public static void main(String[] args) throws DocumentException {
        InputStream stream = XmlTools2.class.getClassLoader().getResourceAsStream("xml.xml");

        SAXReader reader = new SAXReader();
        Document document = reader.read(stream);
        Element element = document.getRootElement();

        List<Key> keys = new ArrayList<>();

        getData(element,keys);
        System.out.println("keys:" + keys);
    }
    public static void getData(Element element,List<Key> keys){
        Iterator<Element> iterator = element.elementIterator();
        while(iterator.hasNext()){
            Element next = iterator.next();

            Key key = new Key();

            List<Attribute> attributes = next.attributes();
            attributes.forEach(o->{
                if("id".equals(o.getName())){
                    key.setId(o.getValue());
                }
                if("part".equals(o.getName())){
                    key.setPart(o.getValue());
                }
            });

            List<Element> elements = next.elements();
            elements.forEach(e->{
                Attribute attribute = e.attribute(0);
                if(SECRET.equals(attribute.getValue())){
                    key.setSecret(e.getText());
                }
                if(IV.equals(attribute.getValue())){
                    key.setIv(e.getText());
                }
            });
            // 每遍历一个实例,将其加入到集合中
            keys.add(key);
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Key{
        private String id;
        private String part;
        // 密钥
        private String secret;
        // 向量
        private String iv;
    }
}

