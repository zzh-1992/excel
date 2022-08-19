package com.grapefruit.excel.quickguide;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 官方链接:https://poi.apache.org/components/spreadsheet/quick-guide.html#WorkingWithFonts
 * Cells with multiple styles (Rich Text Strings)
 * 一个单元格里有多个样式
 *
 * @Author ZhangZhihuang
 * @Date 2022/8/19 07:08
 * @Version 1.0
 */
public class CellsWithMultipleStyles {

    @SneakyThrows
    public static void main(String[] args) {
        XSSFWorkbook wb = new XSSFWorkbook();  // or new HSSFWorkboo

        // 创建sheet
        Sheet sheet = wb.createSheet("Cells with multiple styles");

        // 创建表头
        Row row = sheet.createRow(0);

        // XSSF Example
        Cell cell = row.createCell(1);

        // 单元格的文字/内容
        String excelValue = "*The quick brown fox";

        // 创建富文本对象
        XSSFRichTextString rt = new XSSFRichTextString(excelValue);

        if (excelValue.startsWith("*")) {
            XSSFFont redFont = wb.createFont();
            //redFont.setBold(true);
            redFont.setColor(IndexedColors.RED.getIndex());
            rt.applyFont(0, 1, redFont);
        }

        Font font2 = wb.createFont();
        font2.setItalic(true);
        // 设置双下划线
        //font2.setUnderline(XSSFFont.U_DOUBLE);
        // 设置绿色字体
        font2.setColor(IndexedColors.BLACK.getIndex());
        // 设置字体样式
        //rt.applyFont(1, 5, font2);

        XSSFFont font3 = wb.createFont();
        font3.setColor(IndexedColors.ORANGE.getIndex());
        rt.append(" Jumped over the lazy dog", font3);
        cell.setCellValue(rt);

        // 创建样式
        CellStyle style = wb.createCellStyle();
        // 设置绿色填充
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 单元格设置样式
        cell.setCellStyle(style);

        // 定义输出的excel文件名
        String file = "CellsWithMultipleStyles.xlsx";
        try (OutputStream fileOut = Files.newOutputStream(Paths.get(file))) {
            wb.write(fileOut);
        }
    }
}
