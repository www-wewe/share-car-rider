package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import cz.muni.fi.pv168.seminar01.delta.model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class CsvRideExporterTest extends CsvExporterTest {

    private final CsvRideExporter exporter = new CsvRideExporter();

    @Test
    void noRide() throws IOException {
        exporter.export(List.of(), exportFilePath);
        assertExportedContent("");
    }
    @Test
    void oneRide() throws IOException {
        Ride ride = new Ride("name", new Destination("from"), new Destination("to"),
                LocalDate.of(2022, 11, 14), 3, 1.2, new BigDecimal(3),
                List.of(new Category("category")), new Auto("spz1234", "audi", FuelType.DIESEL));
        exporter.export(List.of(ride), exportFilePath);
        assertExportedContent("name,from,to,2022-11-14,3,1.2,3,category,null\n");
    }

    @Test
    void multiRide() throws IOException {
        List<Ride> rides = List.of(
                new Ride("name", new Destination("from"), new Destination("to"),
                        LocalDate.of(2022, 11, 14), 3, 1.2, new BigDecimal(3),
                        List.of(new Category("category")), new Auto("spz1234", "audi", FuelType.DIESEL)),
                new Ride("nazev", new Destination("Madrid"), new Destination("Brno"),
                        LocalDate.of(2022, 10, 14), 5, 2.2, new BigDecimal(8),
                        List.of(new Category("kategorie")), new Auto("qwer123", "skoda", FuelType.PETROL)),
                new Ride("test", new Destination("Praha"), new Destination("Ostrava"),
                        LocalDate.of(2019, 11, 10), 8, 111.2, new BigDecimal(300),
                        List.of(new Category("nakup")), new Auto("lklk888", "porsche", FuelType.ELECTRIC))
        );
        exporter.export(rides, exportFilePath);
        assertExportedContent("name,from,to,2022-11-14,3,1.2,3,category,null\n" +
                "nazev,Madrid,Brno,2022-10-14,5,2.2,8,kategorie,null\n" +
                "test,Praha,Ostrava,2019-11-10,8,111.2,300,nakup,null\n");
    }
}
