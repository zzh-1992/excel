package com.grapefruit.excel;

import com.grapefruit.excel.yaml.YamlTools;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
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
import java.util.List;
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

    int i = 1;

    int j = 2;

    int k = 3;

    public static void main(String[] args) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("柚子苦瓜茶-2021-04-29");

        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();

        //String fileName = "/Users/user/hahaha/excel/src/main/java/com/grapefruit/excel/file
        // /properties.yaml";
        //HashMap map = YamlTools.getSource(fileName);
        HashMap<String, Object> map = YamlTools.getFile();
        // 行
        int r = map.size();
        // 列
        int c = 2;
        ArrayList<Map.Entry> list = new ArrayList<>(map.entrySet());

        for (int i = 0; i < r; i++) {
            // 创建行
            XSSFRow row = sheet.createRow(i);
            // 创建列
            createCell( workbook,row, c, list.get(i));
        }

        OutputStream os = new FileOutputStream(Path.EXCEL_PATH);
        workbook.write(os);
        os.flush();
        os.close();
        workbook.close();
    }

    // https://www.cnblogs.com/LiZhiW/p/4313789.html?utm_source=tuicool&utm_medium=referral
    // 网友博客 https://blog.csdn.net/fukaiit/article/details/82724545

    /**
     * 在给定的行创建列(单元格)
     *
     * @param row   row
     * @param count count
     * @param entry entry
     */
    private static void createCell(XSSFWorkbook workbook,XSSFRow row, int count, Map.Entry entry) {
        Font font=workbook.createFont();
        font.setUnderline((byte)1);
        font.setColor(IndexedColors.BLUE.getIndex());

        for (int i = 0; i < count; i++) {
            // 1 创建列
            XSSFCell c1 = row.createCell(0);

            // 2 创建单元格
            String value = String.valueOf(entry.getKey());
            // 3 设置单元格内容
            c1.setCellValue(value);

            // 4.1 定义超链接
            Hyperlink link = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
            // 4.2 设置超链接地址
            link.setAddress("http://www.baidu.com/" + i);
            // 4.3 单元格设置超链接
            c1.setHyperlink(link);

            // 5.1 给样式设置字体
            CellStyle style=workbook.createCellStyle();
            style.setFont(font);

            // 5.2 单元格设置样式
            c1.setCellStyle(style);

            //========================================================
            XSSFCell c2 = row.createCell(1);
            c2.setCellValue(String.valueOf(entry.getValue()));

            Hyperlink link2 = getSuperLink(workbook);
            link2.setAddress("http://www.baidu.com/" + i);
            c2.setHyperlink(link2);
        }
    }

    /**
     * 设置单元格内容的超链接
     *
     * @param workbook workbook
     * @return Hyperlink
     */
    public static Hyperlink getSuperLink(XSSFWorkbook workbook) {
        CreationHelper creationHelper = workbook.getCreationHelper();
        //cell.setHyperlink(link);
        return creationHelper.createHyperlink(HyperlinkType.FILE);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        getProperty();
    }

    private void getProperty() {
        System.out.println(env.getProperty("name"));
    }
}
