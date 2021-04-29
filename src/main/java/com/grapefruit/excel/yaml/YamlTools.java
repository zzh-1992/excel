package com.grapefruit.excel.yaml;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
        String sourcePath = "/Users/zhihuangzhang/IdeaProjects/excel/src/main/resources/application.yaml";

        // 输出路径
        String path = "/Users/zhihuangzhang/IdeaProjects/excel/src/main/java/com/grapefruit/excel/yaml/output.yaml";

        // 输出
        yaml.dump(getSourceFromYaml(sourcePath),new FileWriter(path));
    }

    /**
     * 在给定文件夹里获取所有的yaml文件并转换为map
     *
     * @return map
     */
    public static HashMap<String,String> getFile(){
        String baseDirectory = "/Users/zhihuangzhang/IdeaProjects/excel/src/main/java/com/grapefruit/excel/file";

        List<File> yamlFile = getYamlFile(baseDirectory);

        HashMap<String,String> keyValue = new HashMap<>();
        yamlFile.stream()
                .filter(file -> file.getName().endsWith(".yaml"))
                .forEach(
                        file -> keyValue.putAll(getSourceFromYaml(file.getAbsolutePath()))
                );
        System.out.println(keyValue.size());
        return keyValue;
    }

    /**
     * 获取给定文件夹下的所有文件
     *
     * @param directoryPath 文件夹
     * @return List<File>
     */
    public static List<File> getYamlFile(String directoryPath){
        File baseFile = new File(directoryPath);
        return Arrays.asList(Objects.requireNonNull(baseFile.listFiles()));
    }

    /**
     * 从yaml文件里获取值并返回map
     *
     * @param fileName yaml文件的绝对路径
     * @return Map
     */
    private static HashMap<String,String> getSourceFromYaml(String fileName) {
        Yaml yaml = new Yaml();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return yaml.loadAs(fis, HashMap.class);
    }
}
