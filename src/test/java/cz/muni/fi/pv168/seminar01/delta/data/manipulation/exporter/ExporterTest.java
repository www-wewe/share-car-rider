package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

abstract class ExporterTest {

    @TempDir
    protected static Path testDir;

    abstract protected void assertExportedContent(String expectedContent) throws IOException;
}
