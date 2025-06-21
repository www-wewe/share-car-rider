package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import cz.muni.fi.pv168.seminar01.delta.model.Category;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

/**
 * Class for exporting categories to csv
 */
public class CsvCategoryExporter implements Exporter<Category> {

    @Override
    public void export(Collection<Category> collection, Path filePath) throws ExporterException {
        filePath = FileSuffixValidator.checkFileSuffix(filePath, FileFormat.CSV);
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(filePath.toString()))){
            for (var category : collection){
                buffer.write(export(category));
            }
        } catch(IOException ioException){
            throw new ExporterException(ioException.getMessage(), ioException);
        }
    }

    private String export(Category category) {
        return String.format("%s,%s\n", category.getName(), category.getParentId());
    }
}
