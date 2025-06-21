package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import cz.muni.fi.pv168.seminar01.delta.model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class PdfRideExporterTest extends PdfExporterTest {

    private final PdfRideExporter exporter = new PdfRideExporter();

    @Test
    void noRide() throws IOException {
        exporter.export(List.of(), exportFilePath);
        assertExportedContent("Jízdy:\n" +
                "Jméno Odkud Kam Datum Poet Vzdále Cena Kategor Auto\n" +
                "pasažé nost paliva ie\n" +
                "r (km) (K)");
    }

    @Test
    void oneRide() throws IOException {
        Ride ride = new Ride("name", new Destination("from"), new Destination("to"),
                LocalDate.of(2022, 11, 14), 3, 1.2, new BigDecimal(3),
                List.of(new Category("category")), new Auto("spz1234", "audi", FuelType.DIESEL));
        exporter.export(List.of(ride), exportFilePath);
        assertExportedContent("Jízdy:\n" +
                "Jméno Odkud Kam Datum Poet Vzdále Cena Kategor Auto\n" +
                "pasažé nost paliva ie\n" +
                "r (km) (K)\n" +
                "name from to 2022-1 3 1.2 3 [catego spz123\n" +
                "1-14 ry] 4");
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
        assertExportedContent("Jízdy:\n" +
                "Jméno Odkud Kam Datum Poet Vzdále Cena Kategor Auto\n" +
                "pasažé nost paliva ie\n" +
                "r (km) (K)\n" +
                "name from to 2022-1 3 1.2 3 [catego spz123\n" +
                "1-14 ry] 4\n" +
                "nazev Madrid Brno 2022-1 5 2.2 8 [katego qwer12\n" +
                "0-14 rie] 3\n" +
                "test Praha Ostrava 2019-1 8 111.2 300 [nakup] lklk888\n" +
                "1-10");
    }
}
