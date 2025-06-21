package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import cz.muni.fi.pv168.seminar01.delta.model.Category;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class CsvCategoryExporterTest extends CsvExporterTest {

    private final CsvCategoryExporter exporter = new CsvCategoryExporter();

    @Test
    void noCategory() throws IOException {
        exporter.export(List.of(), exportFilePath);
        assertExportedContent("");
    }

    @Test
    void oneCategory() throws IOException {
        Category category = new Category("name");
        exporter.export(List.of(category), exportFilePath);
        assertExportedContent("name,null\n");
    }

    @Test
    void multiCategory() throws IOException {
        List<Category> categories = List.of(
                new Category("one"),
                new Category("two"),
                new Category(3L, "three", 2L),
                new Category(null, "four", 1L)
        );
        exporter.export(categories, exportFilePath);
        assertExportedContent("""
                one,null
                two,null
                three,2
                four,1
                """);
    }
}
