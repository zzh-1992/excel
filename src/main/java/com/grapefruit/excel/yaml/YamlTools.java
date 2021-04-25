package com.grapefruit.excel.yaml;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
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

    public static void main(String[] args) throws IOException {
        // 获取yaml数据
        //getFile();

        // 把数据写入yaml
        dataToYaml();
    }
    public static void dataToYaml() throws IOException {
        DumperOptions options = new DumperOptions();
        // 保证yaml写入后是层级-块状结构
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);

        // 原文件
        String sourcePath = "C:\\Users\\zhangzhihuang\\Desktop\\application.yml";

        // 输出路径
        String path = "C:\\Users\\zhangzhihuang\\Desktop\\a.yml";

        // 输出
        yaml.dump(getSource(sourcePath),new FileWriter(path));
    }

    public static HashMap getFile(){
        File baseFile = new File("/Users/zhihuangzhang/IdeaProjects/excel/src/main/java/com/grapefruit/excel/file");
        File[] files = baseFile.listFiles();
        HashMap keyValue = new HashMap<>();
        for (File file:files) {
            if(file.getName().endsWith(".yaml")){
                keyValue.putAll(getSource(file.getAbsolutePath()));
            }
        }
        System.out.println(keyValue.size());
        return keyValue;
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
