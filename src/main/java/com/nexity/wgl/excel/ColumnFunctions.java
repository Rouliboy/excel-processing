package com.nexity.wgl.excel;

import java.lang.reflect.Field;
import java.util.function.Function;
import com.nexity.wgl.excel.annotations.ExcelColumn;

public final class ColumnFunctions {

  public static Function<Field, ColumnMetadata> toColumnMetadata() {
    return f -> {

      final ExcelColumn excelColumn = f.getAnnotation(ExcelColumn.class);

      // @formatter:off
      return ColumnMetadata.builder()
          .beanFieldName(f.getName())
          .name(excelColumn.name())
          .serializer(excelColumn.serializer())
          .style(excelColumn.style())
          .build();
      // @formatter:on
    };
  }
}
