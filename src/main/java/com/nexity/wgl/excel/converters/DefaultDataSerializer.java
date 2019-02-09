package com.nexity.wgl.excel.converters;

public final class DefaultDataSerializer implements ColumnDataSerializer<Object, Object> {

  @Override
  public Object serialize(final Object value) {
    return value;
  }

}
