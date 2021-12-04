/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.excel.quickguide;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * 下拉框
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-12-04 2:52 下午
 */
public class DropDownLists {

    @SneakyThrows
    public static void main(String[] args) {
        Workbook workbook = new XSSFWorkbook();  // or new HSSFWorkbook
        Sheet sheet = workbook.createSheet("Data Validation");
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        DataValidationConstraint dvConstraint = dvHelper.createExplicitListConstraint(
                new String[]{"13", "23", "33"});
        CellRangeAddressList addressList = new CellRangeAddressList(0, 5, 0, 0);
        DataValidation validation = dvHelper.createValidation(
                dvConstraint, addressList);
        // Note the check on the actual type of the DataValidation object.
        // If it is an instance of the XSSFDataValidation class then the
        // boolean value 'false' must be passed to the setSuppressDropDownArrow()
        // method and an explicit call made to the setShowErrorBox() method.
        if (validation instanceof XSSFDataValidation) {
            validation.setSuppressDropDownArrow(true);
            validation.setShowErrorBox(true);
        } else {
            // If the Datavalidation contains an instance of the HSSFDataValidation
            // class then 'true' should be passed to the setSuppressDropDownArrow()
            // method and the call to setShowErrorBox() is not necessary.
            validation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(validation);

        String file = "DropDownLists.xls";
        if (workbook instanceof XSSFWorkbook) file += "x";
        try (OutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        }
    }
}
