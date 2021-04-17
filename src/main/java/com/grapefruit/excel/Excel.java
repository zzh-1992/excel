package com.grapefruit.excel;

import com.grapefruit.excel.yaml.YamlTools;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Grapefruit
 * @version 1.0
 * @date 2021/4/15
 */
@Component
public class Excel implements ApplicationContextAware {
    @Autowired
    private Environment env;
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        getProperty();
    }

    // https://www.cnblogs.com/LiZhiW/p/4313789.html?utm_source=tuicool&utm_medium=referral
    // 网友博客 https://blog.csdn.net/fukaiit/article/details/82724545

    public static void main(String[] args) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("柚子苦瓜茶");

        String fileName = "/Users/zhihuangzhang/IdeaProjects/excel/src/main/java/com/grapefruit/excel/file/properties.yaml";
        HashMap map = YamlTools.getSource(fileName);
        // 行
        int r = map.size();
        // 列
        int c = 2;
        ArrayList<Map.Entry> list = new ArrayList<>(map.entrySet());

        for (int i = 0;i<r ;i++){
            // 创建行
            XSSFRow row = sheet.createRow(i);
            // 创建列
            createCell(row,c,list.get(i));
        }

        OutputStream os = new FileOutputStream("/Users/zhihuangzhang/Desktop/excel.xls");
        workbook.write(os);
        os.flush();
        os.close();
        workbook.close();
    }

    private void getProperty(){
        System.out.println(env.getProperty("name"));
    }

    /**
     * 在给定的行创建列(单元格)
     * @param row 行
     * @param count 该行的列数
     */
    private static void createCell(XSSFRow row, int count, Map.Entry entry){
        for (int i = 0;i<count ;i++){
            // 创建列
            XSSFCell c1 = row.createCell(0);
            c1.setCellValue(String.valueOf(entry.getKey()));
            XSSFCell c2 = row.createCell(1);
            c2.setCellValue(String.valueOf(entry.getValue()));
        }
    }
}
