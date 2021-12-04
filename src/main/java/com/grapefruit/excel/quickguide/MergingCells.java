/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.excel.quickguide;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 单元格合并
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-12-04 1:56 下午
 */
public class MergingCells {
    @SneakyThrows
    public static void main(String[] args) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("new sheet");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("This is a test of merging XSSF");
        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                2, //last row  (0-based)
                0, //first column (0-based)
                2  //last column  (0-based)
        ));
        // Write the output to a file
        try (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
            wb.write(fileOut);
        }
        wb.close();
    }
}
