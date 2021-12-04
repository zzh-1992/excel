/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.excel.quickguide;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;

import java.io.FileOutputStream;
import java.io.OutputStream;


/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-12-04 2:04 下午
 */
public class UsingTheConvenienceFunctions {
    @SneakyThrows
    public static void main(String[] args) {
        Workbook wb = new HSSFWorkbook();  // or new XSSFWorkbook()
        Sheet sheet1 = wb.createSheet("new sheet");
        // Create a merged region
        Row row = sheet1.createRow(1);
        Row row2 = sheet1.createRow(2);
        Cell cell = row.createCell(1);
        cell.setCellValue("This is a test of merging");
        CellRangeAddress region = CellRangeAddress.valueOf("B2:E5");
        sheet1.addMergedRegion(region);
        // Set the border and border colors.
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM_DASHED, region, sheet1);
        RegionUtil.setBorderTop(BorderStyle.MEDIUM_DASHED, region, sheet1);
        RegionUtil.setBorderLeft(BorderStyle.MEDIUM_DASHED, region, sheet1);
        RegionUtil.setBorderRight(BorderStyle.MEDIUM_DASHED, region, sheet1);
        RegionUtil.setBottomBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1);
        RegionUtil.setTopBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1);
        RegionUtil.setLeftBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1);
        RegionUtil.setRightBorderColor(IndexedColors.AQUA.getIndex(), region, sheet1);
        // Shows some usages of HSSFCellUtil
        CellStyle style = wb.createCellStyle();
        style.setIndention((short) 4);
        CellUtil.createCell(row, 8, "This is the value of the cell", style);
        Cell cell2 = CellUtil.createCell(row2, 8, "This is the value of the cell");
        CellUtil.setAlignment(cell2, HorizontalAlignment.CENTER);

        // Write out the workbook
        try (OutputStream fileOut = new FileOutputStream("SetPrintArea.xls")) {
            wb.write(fileOut);
        }
        wb.close();
    }
}
