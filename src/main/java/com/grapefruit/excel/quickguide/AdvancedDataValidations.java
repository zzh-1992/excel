/*
 *Copyright @2022 Grapefruit. All rights reserved.
 */

package com.grapefruit.excel.quickguide;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static void main(String[] args) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();

        // 创建sheet
        Sheet sheet = workbook.createSheet("Data Validation");

        // 预制数据
        Sheet dbSheet = workbook.createSheet("db");
        Map<String, List<String>> map = new HashMap<>();
        List<String> gd = Arrays.asList("广州", "梅州", "深圳");
        List<String> jx = Arrays.asList("九江", "赣州", "南昌");
        List<String> fj = Arrays.asList("龙岩", "福州", "厦门");
        map.put("广东", gd);
        map.put("江西", jx);
        map.put("福建", fj);


        // 起始行
        AtomicInteger rowIndex = new AtomicInteger(1);
        map.forEach((key, strList) -> {
            Row row = dbSheet.createRow(rowIndex.get() - 1);
            row.createCell(0).setCellValue(key);
            for (int i = 1; i <= gd.size(); i++) {
                row.createCell(i).setCellValue(strList.get(i - 1));
            }

            // 构造名称管理器
            String range = buildRange(rowIndex, strList.size());
            Name gzName = workbook.createName();
            gzName.setNameName(key);
            String formula = "db" + "!" + range;
            gzName.setRefersToFormula(formula);
            rowIndex.getAndIncrement();
        });

        DataValidationHelper dvHelper = sheet.getDataValidationHelper();

        // 下拉选项里的可选值
        String[] china = map.keySet().toArray(new String[0]);

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
        String file = "AdvancedDataValidations.xlsx";

        try (OutputStream fileOut = Files.newOutputStream(Paths.get(file))) {
            workbook.write(fileOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        workbook.close();
    }

    private static String buildRange(AtomicInteger index, int size) {
        int rowPosition = index.get();
        char offest = (char) ('A' + size);
        return "$" + "B" + "$" + rowPosition + ":$" + offest + "$" + rowPosition;
    }

    private static String buildFormulaString(String offset, int rowNum) {
        return "INDIRECT($" + offset + (rowNum) + ")";
    }
}
