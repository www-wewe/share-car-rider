package cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer;

import cz.muni.fi.pv168.seminar01.delta.model.Category;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

/**
 * Class for importing categories
 */
public class CategoryImporter implements Importer<Category> {
    @Override
    public Collection<Category> importData(Path filePath) throws ImporterException {
        try (BufferedReader buffer = new BufferedReader(new FileReader(filePath.toString()))){
            return buffer.lines().map(this::parseLine).toList();
        } catch(IOException ioException){
            throw new ImporterException("Unable to import data");
        }
    }

    private Category parseLine(String line){
        String[] values = line.split(",");
        return new Category(null, values[0], Long.parseLong(values[1]));
    }
}
