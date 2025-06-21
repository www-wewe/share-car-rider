package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Class for exporting rides to csv
 */
public class CsvRideExporter implements Exporter<Ride> {

    @Override
    public void export(Collection<Ride> collection, Path filePath) throws ExporterException {
        filePath = FileSuffixValidator.checkFileSuffix(filePath, FileFormat.CSV);
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(filePath.toString()))) {
            for (var record : collection) {
                buffer.write(export(record));
            }
        } catch (IOException ioException) {
            throw new ExporterException(ioException.getMessage(), ioException);
        }
    }

    private String export(Ride ride) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                ride.getName(),
                ride.getFrom(),
                ride.getTo(),
                ride.getDate(),
                ride.getPassengerCount(),
                ride.getDistance(),
                ride.getPrice(),
                ride.getCategories().stream().map(Category::toString).collect(Collectors.joining(";")),
                ride.getAuto().getId()
        );
    }
}
