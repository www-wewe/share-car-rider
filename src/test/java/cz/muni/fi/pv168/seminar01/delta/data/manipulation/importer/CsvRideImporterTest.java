package cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer;

import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CsvRideImporterTest extends CsvImporterTest {

    private RideImporter importer;

    @BeforeEach
    void setUp(){
        importer = new RideImporter();
    }

    @Test
    void noRide() throws IOException {
        Path importFilePath = TEST_RESOURCES.resolve("empty.csv");
        Collection<Ride> rides = importer.importData(importFilePath);
        assertThat(rides).isEmpty();
    }

    @Test
    void singleRide() throws IOException {
        Path importFilePath = TEST_RESOURCES.resolve("single-ride.csv");
        Collection<Ride> rides = importer.importData(importFilePath);
        assertThat(rides).containsExactlyInAnyOrder(
            new Ride("test",
                    new Destination("Madrid"),
                    new Destination("Praha"),
                    LocalDate.of(2022, 12, 01),
                    12,
                    234.0,
                    new BigDecimal("2342.0"),
                    List.of(new Category("Test2"), new Category("Test3")),
                    new Auto(Long.parseLong("1"), null, null, null))
        );
    }

    @Test
    void multiRide() throws IOException {
        Path importFilePath = TEST_RESOURCES.resolve("multi-rides.csv");
        Collection<Ride> rides = importer.importData(importFilePath);
        assertThat(rides).containsExactlyInAnyOrder(
                new Ride("test",
                        new Destination("Madrid"),
                        new Destination("Praha"),
                        LocalDate.of(2022, 12, 1),
                        12,
                        234.0,
                        new BigDecimal("2342.0"),
                        List.of(new Category("Test2"), new Category("Test3")),
                        new Auto(Long.parseLong("1"), null, null, null)),
                new Ride("jizda",
                        new Destination("Praha"),
                        new Destination("Brno"),
                        LocalDate.of(2022, 12, 7),
                        1,
                        123.0,
                        new BigDecimal("456.0"),
                        List.of(new Category("Test1")),
                        new Auto(Long.parseLong("2"), null, null, null)),
                new Ride("lalal",
                        new Destination("Brno"),
                        new Destination("Barcelona"),
                        LocalDate.of(2022, 11, 23),
                        3,
                        1234.0,
                        new BigDecimal("4567.0"),
                        List.of(new Category("Test2")),
                        new Auto(Long.parseLong("1"), null, null, null))
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
