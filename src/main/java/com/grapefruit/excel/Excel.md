# Excel下拉框

[http://poi.apache.org/](http://poi.apache.org/components/spreadsheet/quick-guide.html)

```java
XSSFWorkbook workbook = new XSSFWorkbook();
XSSFSheet sheet = workbook.createSheet("Data Validation");
XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)
  dvHelper.createExplicitListConstraint(new String[]{"11", "21", "31"});
CellRangeAddressList addressList = new CellRangeAddressList(0, 0, 0, 0);
XSSFDataValidation validation = (XSSFDataValidation)dvHelper.createValidation(
  dvConstraint, addressList);
validation.setShowErrorBox(true);
sheet.addValidationData(validation);
```
