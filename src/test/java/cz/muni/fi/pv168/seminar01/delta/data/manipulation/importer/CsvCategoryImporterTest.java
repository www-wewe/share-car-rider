package cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer;

import cz.muni.fi.pv168.seminar01.delta.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CsvCategoryImporterTest extends CsvImporterTest {

    CategoryImporter importer;

    @BeforeEach
    void setUp() {
        importer = new CategoryImporter();
    }

    @Test
    void noCategory() throws IOException {
        Path importFilePath = TEST_RESOURCES.resolve("empty.csv");
        Collection<Category> categories = importer.importData(importFilePath);
        assertThat(categories).isEmpty();
    }

    @Test
    void singleCategory() throws IOException {
        Path importFilePath = TEST_RESOURCES.resolve("single-category.csv");
        Collection<Category> categories = importer.importData(importFilePath);
        assertThat(categories).containsExactlyInAnyOrder(
                new Category(null, "Test1", 0L)
        );
    }

    @Test
    void multiCategories() throws IOException {
        Path importFilePath = TEST_RESOURCES.resolve("multi-categories.csv");
        Collection<Category> categories = importer.importData(importFilePath);
        assertThat(categories).containsExactlyInAnyOrder(
                new Category(null, "Test1", 0L),
                new Category(null, "Test3", 0L),
                new Category(null, "Test2", 1L)
        );
    }

    @Test
    void nonExistingFile() {
        Path importFilePath = TEST_RESOURCES.resolve("non-existing-file.csv");
        assertThatExceptionOfType(ImporterException.class)
                .isThrownBy(() -> importer.importData(importFilePath))
                .withMessage("Unable to import data");
    }
}
