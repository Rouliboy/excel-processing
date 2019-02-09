package com.nexity.wgl.excel;

import com.nexity.wgl.excel.converters.ColumnDataSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Metadata of Excel columns from Bean
 *
 * @author jvienne
 *
 */
@AllArgsConstructor
@Data
@Builder
public class ColumnMetadata {

  private String name;
  private String beanFieldName;
  private Class<? extends ColumnDataSerializer<?, ?>> serializer;

}
