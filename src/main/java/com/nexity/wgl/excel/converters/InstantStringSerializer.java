package com.nexity.wgl.excel.converters;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class InstantStringSerializer implements ColumnDataSerializer<String, Instant> {

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

  @Override
  public String serialize(final Instant value) {
    return DATE_TIME_FORMATTER.format(value);
  }

}
