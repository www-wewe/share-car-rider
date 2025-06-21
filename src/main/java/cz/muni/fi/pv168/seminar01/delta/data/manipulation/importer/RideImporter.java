package cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer;

import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for importing rides
 */
public class RideImporter implements Importer<Ride> {

    @Override
    public Collection<Ride> importData(Path filePath) throws ImporterException {
        try (BufferedReader buffer = new BufferedReader(new FileReader(filePath.toString()))) {
            return buffer.lines().map(this::parseLine).toList();
        } catch (IOException ioException) {
            throw new ImporterException("Unable to import data");
        }
    }

    private Ride parseLine(String line) {
        String[] values = line.split(",");
        Destination fromDestination = new Destination(values[1]);
        Destination toDestination = new Destination(values[2]);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        List<String> categoryString = List.of(values[7].split(";"));
        List<Category> categoryList = new ArrayList<>();
        for (var categoryName : categoryString) {
            categoryList.add(new Category(categoryName));
        }
        Auto car = new Auto(Long.parseLong(values[8]), null, null, null);
        return new Ride(values[0],
                fromDestination,
                toDestination,
                LocalDate.parse(values[3], dateFormatter),
                Integer.parseInt(values[4]),
                Double.parseDouble(values[5]),
                new BigDecimal(values[6]),
                categoryList,
                car
        );
    }
}
