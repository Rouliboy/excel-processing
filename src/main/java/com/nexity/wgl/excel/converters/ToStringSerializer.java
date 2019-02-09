package com.nexity.wgl.excel.converters;

public final class ToStringSerializer implements ColumnDataSerializer<String, Object> {
  @Override
  public String serialize(final Object value) {
    return value.toString();
  }

}
