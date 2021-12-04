/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.excel.quickguide;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 图片
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-12-04 2:19 下午
 */
public class ExcelImages {

    @SneakyThrows
    public static void main(String[] args) {
        //create a new workbook
        Workbook wb = new XSSFWorkbook(); //or new HSSFWorkbook();
        //add picture data to this workbook.
        InputStream is = new FileInputStream("/Users/zhihuangzhang/IdeaProjects/image/imagevue/imagevue/img/po.jpg");
        byte[] bytes = IOUtils.toByteArray(is);
        int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        is.close();
        CreationHelper helper = wb.getCreationHelper();
        //create sheet
        Sheet sheet = wb.createSheet();
        // Create the drawing patriarch.  This is the top level container for all shapes.
        Drawing drawing = sheet.createDrawingPatriarch();
        //add a picture shape
        ClientAnchor anchor = helper.createClientAnchor();
        //set top-left corner of the picture,
        //subsequent call of Picture#resize() will operate relative to it
        anchor.setCol1(3);
        anchor.setRow1(2);
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        //auto-size picture relative to its top-left corner
        pict.resize();
        //save workbook
        String file = "picture.xls";
        if (wb instanceof XSSFWorkbook) file += "x";
        try (OutputStream fileOut = new FileOutputStream(file)) {
            wb.write(fileOut);
        }
    }
}
