package com.nexity.wgl.excel.converters;

public interface ColumnDataSerializer<T, V> {

  T serialize(V value);
}
