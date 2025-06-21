package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import java.nio.file.Path;
import java.util.Collection;

/**
 * Generic mechanism, allowing to export a {@link Collection} of
 * objects to a file.
 */
public interface Exporter<T> {

    /**
     * Exports collection to a file.
     *
     * @param collection employees to be exported (in the collection iteration order)
     * @param filePath absolute path of the export file (to be created or overwritten)
     *
     * @throws ExporterException if there is a problem with exporter
     */
    void export(Collection<T> collection, Path filePath) throws ExporterException;

}
