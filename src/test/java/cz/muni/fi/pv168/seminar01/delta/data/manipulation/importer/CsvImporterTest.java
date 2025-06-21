package cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer;

import java.nio.file.Path;
import java.nio.file.Paths;

abstract class CsvImporterTest {
    private static final Path PROJECT_ROOT = Paths.get(System.getProperty("project.basedir", "")).toAbsolutePath();
    protected static final Path TEST_RESOURCES = PROJECT_ROOT.resolve(Path.of("src", "test", "resources"));

}
