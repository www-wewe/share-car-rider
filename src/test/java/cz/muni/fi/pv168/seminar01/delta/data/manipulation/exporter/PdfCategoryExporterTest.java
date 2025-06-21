package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import cz.muni.fi.pv168.seminar01.delta.model.Category;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class PdfCategoryExporterTest extends PdfExporterTest {

    private final PdfCategoryExporter exporter = new PdfCategoryExporter();

    @Test
    void noCategory() throws IOException {
        exporter.export(List.of(), exportFilePath);
        assertExportedContent("Kategorie:");
    }

    @Test
    void oneCategory() throws IOException {
        Category category = new Category("name");
        exporter.export(List.of(category), exportFilePath);
        assertExportedContent("Kategorie:\nname");
    }

    @Test
    void multiCategory() throws IOException {
        List<Category> categories = List.of(
                new Category("one"),
                new Category("two"),
                new Category("three")
        );
        exporter.export(categories, exportFilePath);
        assertExportedContent("""
                Kategorie:
                one
                two
                three""");
    }
}
