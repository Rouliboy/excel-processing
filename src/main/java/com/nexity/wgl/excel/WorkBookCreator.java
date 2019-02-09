package com.nexity.wgl.excel;

import static org.reflections.ReflectionUtils.withName;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.reflections.ReflectionUtils;
import com.nexity.wgl.excel.converters.ColumnDataSerializer;

public class WorkBookCreator {

  /** BETTER CREATE THIS METHOD FOR A SINGLE SHEET TO ENABLE MULTI SHEETS WORKBOOKS */
  public <T> void createFrom(final String sheetName, final List<T> data, final Class<T> model)
      throws IOException {
    final Workbook workbook = new XSSFWorkbook();

    /*
     * CreationHelper helps us create instances of various things like DataFormat, Hyperlink,
     * RichTextString etc, in a format (HSSF, XSSF) independent way
     */
    final CreationHelper createHelper = workbook.getCreationHelper();

    final ColumnMetadataExtractor columnExtractor = new ColumnMetadataExtractor();
    final List<ColumnMetadata> columns = columnExtractor.extract(model);

    // Create a Sheet
    final Sheet sheet = workbook.createSheet(sheetName);

    // create headers
    createHeaders(sheet, columns);

    // Append Data
    addBeanData(sheet, columns, data);

    // Write the output to a file
    final FileOutputStream fileOut = new FileOutputStream("poi-generated-file.xlsx");
    workbook.write(fileOut);
    fileOut.close();

    // Closing the workbook
    workbook.close();
  }

  private void createHeaders(final Sheet sheet, final List<ColumnMetadata> columns) {
    // Create a Font for styling header cells
    final Font headerFont = sheet.getWorkbook().createFont();
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints((short) 10);
    headerFont.setColor(IndexedColors.BLACK.getIndex());

    // Create a CellStyle with the font
    final CellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
    headerCellStyle.setFont(headerFont);

    // Create a Row
    final Row headerRow = sheet.createRow(0);

    // Create header
    for (int i = 0; i < columns.size(); i++) {
      final Cell cell = headerRow.createCell(i);
      cell.setCellValue(columns.get(i).getName());
      cell.setCellStyle(headerCellStyle);
    }
  }

  private <T> void addBeanData(final Sheet sheet, final List<ColumnMetadata> columns,
      final List<T> datas) {
    // TODO Auto-generated method stub

    int rowNum = 0;
    for (final T data : datas) {
      final Row row = sheet.createRow(++rowNum);
      int columnIndex = 0;
      for (final ColumnMetadata metadata : columns) {
        final Field field = ReflectionUtils
            .getAllFields(data.getClass(), withName(metadata.getBeanFieldName())).stream()
            .findFirst().orElseThrow(() -> new RuntimeException("Erreur à customiser"));
        field.setAccessible(true);
        try {
          final Object fieldValue = field.get(data);
          final Cell cell = row.createCell(columnIndex++);
          writeToCell(cell, metadata, fieldValue);

        } catch (IllegalArgumentException | IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }

  }

  private void writeToCell(final Cell cell, final ColumnMetadata metadata, Object fieldValue) {
    if (fieldValue == null) {
      cell.setCellValue((String) null);
      return;
    }
    if (metadata.getSerializer() != null) {
      try {
        @SuppressWarnings("unchecked")
        final ColumnDataSerializer<?, Object> converter =
            (ColumnDataSerializer<?, Object>) metadata.getSerializer().newInstance();
        fieldValue = converter.serialize(fieldValue);
        writeToCellWithType(cell, fieldValue);
      } catch (final InstantiationException e) {
        throw new RuntimeException(e);
      } catch (final IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

  protected void writeToCellWithType(final Cell cell, final Object fieldValueObj) {

    final Class<?> type = fieldValueObj.getClass();

    if (type == Date.class) {
      cell.setCellValue((Date) fieldValueObj);
    } else if (type == Boolean.class) {
      cell.setCellValue((Boolean) fieldValueObj);
    } else if (type == Double.class || type == double.class || type == Integer.class
        || type == int.class || type == Long.class || type == long.class || type == Float.class
        || type == float.class || type == Short.class || type == short.class) {
      cell.setCellType(CellType.NUMERIC);
      cell.setCellValue(Double.valueOf(fieldValueObj.toString()));
    } else {
      cell.setCellType(CellType.STRING);
      cell.setCellValue(fieldValueObj.toString());
    }
  }
}
