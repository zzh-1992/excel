/*
 *Copyright @2022 Grapefruit. All rights reserved.
 */

package com.grapefruit.excel.quickguide;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * 二级下拉框
 * gitbug LinkedDropDownLists https://github.com/i5possible/excel-mongodb-data-exchange/commit/78228a4adb7febef6a9f98881929b189ae8f0ce2
 * 热心网友: https://www.cnblogs.com/NaughtyCat/p/how-to-generate-excel-dependent-dropdown-list-by-poi.html
 *
 * @Author ZhangZhihuang
 * @Date 2022/9/6 07:13
 * @Version 1.0
 */
public class AdvancedDataValidations {

    public static void main(String[] args) throws FileNotFoundException {
        Workbook workbook = new XSSFWorkbook();  // or new HSSFWorkbook

        // 创建sheet
        Sheet sheet = workbook.createSheet("Data Validation");

        Sheet dbSheet = workbook.createSheet("db");
        List<String> gd = Arrays.asList("gz", "mz", "sz");
        List<String> jx = Arrays.asList("jj", "gz", "nc");
        List<String> fj = Arrays.asList("ly", "fz", "xm");

        Row row0 = dbSheet.createRow(0);
        Row row1 = dbSheet.createRow(1);
        Row row2 = dbSheet.createRow(2);

        row0.createCell(0).setCellValue(gd.get(0));
        row1.createCell(0).setCellValue(gd.get(1));
        row2.createCell(0).setCellValue(gd.get(2));

        // 构造名称管理器
        String range = buildRange(0, 1, 3);
        Name gzName = workbook.createName();
        gzName.setNameName("gz");
        String formula = "db" + "!" + range;
        gzName.setRefersToFormula(formula);

        row0.createCell(1).setCellValue(jx.get(0));
        row1.createCell(1).setCellValue(jx.get(1));
        row2.createCell(1).setCellValue(jx.get(2));

        // 构造名称管理器
        range = buildRange(1, 1, 3);
        Name jxName = workbook.createName();
        jxName.setNameName("jx");
        formula = "db" + "!" + range;
        jxName.setRefersToFormula(formula);

        row0.createCell(2).setCellValue(fj.get(0));
        row1.createCell(2).setCellValue(fj.get(1));
        row2.createCell(2).setCellValue(fj.get(2));
        // 构造名称管理器
        range = buildRange(2, 1, 3);
        Name fjName = workbook.createName();
        fjName.setNameName("fj");
        formula = "db" + "!" + range;
        fjName.setRefersToFormula(formula);

        DataValidationHelper dvHelper = sheet.getDataValidationHelper();

        // 下拉选项里的可选值
        String[] china = new String[]{"gz", "jx", "fj"};
        DataValidationConstraint dvConstraint = dvHelper.createExplicitListConstraint(china);

        // 一级下拉
        CellRangeAddressList addressList = new CellRangeAddressList(0, 5, 0, 0);

        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
        if (validation instanceof XSSFDataValidation) {
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
        } else {
            validation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(validation);

        //===================
        // 剩下的层级设置DataValidation
        for (int i = 0; i < 3; i++) {
            char[] offset = new char[1];
            offset[0] = (char) ('A');
            String formulaString = buildFormulaString(new String(offset), i + 1);
            DataValidationConstraint dvConstraint2 = dvHelper.createFormulaListConstraint(formulaString);
            CellRangeAddressList regions = new CellRangeAddressList(i, i, 1, 1);
            DataValidation dataValidationList = dvHelper.createValidation(dvConstraint2, regions);
            // 设置下拉箭头
            dataValidationList.setSuppressDropDownArrow(true);
            dataValidationList.setEmptyCellAllowed(false);
            dataValidationList.setShowErrorBox(true);
            dataValidationList.setErrorStyle(DataValidation.ErrorStyle.WARNING);
            dataValidationList.setShowPromptBox(true);
            dataValidationList.createErrorBox("error", "error input");

            sheet.addValidationData(dataValidationList);
        }

        // 定义输出的excel文件名
        String file = "DropDownLists.xlsx";

        try (OutputStream fileOut = Files.newOutputStream(Paths.get(file))) {
            workbook.write(fileOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildRange(int offset, int startRow, int rowCount) {
        char start = (char) ('A' + offset);
        return "$" + start + "$" + startRow + ":$" + start + "$" + (startRow + rowCount - 1);
    }

    private static String buildFormulaString(String offset, int rowNum) {
        // return "INDIRECT($" + offset + (rowNum) + ")";
        //return "INDIRECT($" + offset + "$" +  (rowNum) + ")";
        return "INDIRECT($" + offset + (rowNum) + ")";
    }
}
