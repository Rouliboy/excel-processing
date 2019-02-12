package com.nexity.wgl.excel.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import com.nexity.wgl.excel.converters.ColumnDataSerializer;
import com.nexity.wgl.excel.converters.DefaultDataSerializer;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ExcelColumn {

  /** Name of the column header in excel sheet */
  String name();

  /** Optional serializer */
  Class<? extends ColumnDataSerializer<?, ?>> serializer() default DefaultDataSerializer.class;

  Style style() default Style.NONE;

  public static enum Style {
    NONE, WRAP;
  }
}
