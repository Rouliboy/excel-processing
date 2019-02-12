package com.nexity.wgl.excel.converters;

import java.util.List;
import java.util.stream.Collectors;

public final class CustomListToStringSerializer
    implements ColumnDataSerializer<String, List<String>> {

  @Override
  public String serialize(final List<String> value) {
    return value.stream().collect(Collectors.joining("\n"));
  }

}
