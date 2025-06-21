package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class CsvExporterTest extends ExporterTest {

    protected final Path exportFilePath = testDir.resolve(Instant.now().toString().replace(':', '_') + ".csv");

    protected void assertExportedContent(String expectedContent) throws IOException {
        assertEquals(Files.readString(exportFilePath), expectedContent);
    }
}
