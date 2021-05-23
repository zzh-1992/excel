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
public class XmlTools {

    public static void main(String[] args) throws DocumentException {
        InputStream stream = XmlTools.class.getClassLoader().getResourceAsStream("xml.xml");

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

            // 递归调用
            re(next,key);

            // 每遍历一个实例,将其加入到集合中
            keys.add(key);
        }
    }

    // 递归
    public static void re(Element next,Key key){
        Iterator<Element> iterator = next.elementIterator();
        while(iterator.hasNext()){
            // 若当前元素还有子元素，递归调用
            Element e = iterator.next();
            re(e,key);
        }
        // 能到这里说明已经是最底层元素，直接获取值
        if("secret".equals(next.attribute(0).getValue())){
            key.setSecret(next.getText());
        }
        if("iv".equals(next.attribute(0).getValue())){
            key.setIv(next.getText());
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

