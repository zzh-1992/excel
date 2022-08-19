/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.excel.quickguide;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 官方链接:https://poi.apache.org/components/spreadsheet/quick-guide.html#NewLinesInCells
 * Using newlines in cells(在单元格里做空行处理)
 *
 * @Author ZhangZhihuang
 * @Date 2022/8/19 08:28
 * @Version 1.0
 */
public class UsingNewlinesInCells {
    @SneakyThrows
    public static void main(String[] args) {
        Workbook wb = new XSSFWorkbook();   //or new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(2);
        Cell cell = row.createCell(2);
        cell.setCellValue("Use \n with word wrap on to create a new line");
        //to enable newlines you need set a cell styles with wrap=true
        CellStyle cs = wb.createCellStyle();
        cs.setWrapText(true);
        cell.setCellStyle(cs);
        //increase row height to accommodate two lines of text
        row.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
        //adjust column width to fit the content
        sheet.autoSizeColumn(2);
        try (OutputStream fileOut = Files.newOutputStream(Paths.get("ooxml-newlines.xlsx"))) {
            wb.write(fileOut);
        }
        wb.close();
    }
}
