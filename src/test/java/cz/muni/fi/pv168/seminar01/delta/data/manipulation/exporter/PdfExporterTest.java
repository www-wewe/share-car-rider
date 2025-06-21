package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class PdfExporterTest extends ExporterTest {

    protected final Path exportFilePath = testDir.resolve(Instant.now().toString().replace(':', '_') + ".pdf");

    protected void assertExportedContent(String expectedContent) throws IOException {
        PdfReader pdfReader = new PdfReader(exportFilePath.toString());
        String content = PdfTextExtractor.getTextFromPage(pdfReader, 1);
        assertEquals(content, expectedContent);
        pdfReader.close();
    }
}
