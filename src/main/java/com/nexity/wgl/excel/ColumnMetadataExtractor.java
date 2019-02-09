package com.nexity.wgl.excel;

import static com.nexity.wgl.excel.ColumnFunctions.toColumnMetadata;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import com.nexity.wgl.excel.annotations.ExcelColumn;

public class ColumnMetadataExtractor {

  public <T> List<ColumnMetadata> extract(final Class<T> model) {

    Validate.notNull(model, "Model can not be null");

    // @formatter:off
    return Arrays.asList(model.getDeclaredFields()).stream()
        .filter(f -> Modifier.isPrivate(f.getModifiers()))
        .filter(f -> null != f.getAnnotation(ExcelColumn.class))
        .map(toColumnMetadata())
        .collect(Collectors.toCollection(LinkedList::new));
    // @formatter:on
  }

}
