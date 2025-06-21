package cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer;

import java.nio.file.Path;
import java.util.Collection;

/**
 * Generic mechanism, allowing to import a {@link Collection} of
 * objects from a file.
 */
public interface Importer<T> {

    /**
     * Imports employees from a file to an ordered {@link Collection}.
     *
     * @param filePath absolute path of the file to import
     *
     * @return imported employees (in the same order as in the file)
     *
     * @throws ImporterException if the export file cannot be opened or written
     */
    Collection<T> importData(Path filePath) throws ImporterException;
}
