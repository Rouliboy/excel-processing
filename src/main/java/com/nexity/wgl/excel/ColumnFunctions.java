package com.nexity.wgl.excel;

import java.lang.reflect.Field;
import java.util.function.Function;
import com.nexity.wgl.excel.annotations.ExcelColumn;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ColumnFunctions {

  public Function<Field, ColumnMetadata> toColumnMetadata() {
    return f -> {

      final ExcelColumn excelColumn = f.getAnnotation(ExcelColumn.class);

      // @formatter:off
      return ColumnMetadata.builder()
          .beanFieldName(f.getName())
          .name(excelColumn.name())
          .serializer(excelColumn.serializer())
          .build();
      // @formatter:on
    };
  }

  // private Class<?> getType(final Field f, final ExcelColumn excelColumn) {
  //
  // if (excelColumn.serializeAsString()) {
  // return String.class;
  // } else {
  // return f.getType();
  // }
  // }
}
