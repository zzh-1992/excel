package com.grapefruit.excel.yaml;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * @author Grapefruit
 * @version 1.0
 * @date 2021/4/17
 */
@Component
public class YamlTools implements ApplicationContextAware {
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        //getSource();
    }

    public static void main(String[] args) {
        getFile();
    }

    public static void getFile(){
        File baseFile = new File("/Users/zhihuangzhang/IdeaProjects/excel/src/main/java/com/grapefruit/excel/file");
        File[] files = baseFile.listFiles();
        HashMap keyValue = new HashMap<>();
        for (File file:files) {
            if(file.getName().endsWith(".yaml")){
                keyValue.putAll(getSource(file.getAbsolutePath()));
            }
        }
        System.out.println(keyValue.size());
    }

    public static HashMap  getSource(String fileName) {
        Yaml yaml = new Yaml();
        FileInputStream fis = null;
        try {
            //fis = new FileInputStream("/Users/zhihuangzhang/IdeaProjects/excel/src/main/java/com/grapefruit/excel/file/properties.yaml");
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return yaml.loadAs(fis, HashMap.class);
    }
}
