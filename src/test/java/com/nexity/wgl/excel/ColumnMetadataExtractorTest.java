package com.nexity.wgl.excel;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ColumnMetadataExtractorTest {

  private ColumnMetadataExtractor instance = new ColumnMetadataExtractor();

  @Test
  public void test() throws IllegalArgumentException, IllegalAccessException, IOException {

    final List<TestPojoWithColumns> values = new ArrayList<>();
    values.add(new TestPojoWithColumns("name1", "surname1", 35, Instant.now(),
        Date.from(Instant.now()), Date.from(Instant.now())));
    final List<ColumnMetadata> columns = instance.extract(TestPojoWithColumns.class);

    System.out.println(columns);

    final ExcelCreator wbc = new ExcelCreator();
    wbc.createFrom("Test", values, TestPojoWithColumns.class);
  }

}
