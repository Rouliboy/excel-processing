package com.nexity.wgl.excel;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import com.nexity.wgl.excel.annotations.ExcelColumn;
import com.nexity.wgl.excel.annotations.ExcelColumn.Style;
import com.nexity.wgl.excel.converters.CustomListToStringSerializer;
import com.nexity.wgl.excel.converters.InstantStringSerializer;
import com.nexity.wgl.excel.converters.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestPojoWithColumns {

  @ExcelColumn(name = "Nom")
  private String name;

  @ExcelColumn(name = "Prenom")
  private String surname;

  @ExcelColumn(name = "Age de la personne")
  private int age;

  @ExcelColumn(name = "Date de naissance", serializer = InstantStringSerializer.class)
  private Instant dateOfBirth;

  @ExcelColumn(name = "date")
  private Date date;

  @ExcelColumn(name = "dateAsString", serializer = ToStringSerializer.class)
  private Date dateAsString;

  @ExcelColumn(name = "list of string wrapped", style = Style.WRAP,
      serializer = CustomListToStringSerializer.class)
  private List<String> listStringWrapped;

}
