/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.excel.quickguide;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 颜色填充
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-12-04 1:41 下午
 */
public class FillsAndColors {
    @SneakyThrows
    public static void main(String[] args) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");
        // Create a row and put some cells in it. Rows are 0 based.
        Row row = sheet.createRow(1);
        // Aqua background
        CellStyle style = wb.createCellStyle();
        style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(FillPatternType.BIG_SPOTS);
        Cell cell = row.createCell(1);
        cell.setCellValue("X");
        cell.setCellStyle(style);
        // Orange "foreground", foreground being the fill foreground not the font color.
        style = wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell = row.createCell(2);
        cell.setCellValue("X");
        cell.setCellStyle(style);
        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
            wb.write(fileOut);
        }
        wb.close();
    }
}
