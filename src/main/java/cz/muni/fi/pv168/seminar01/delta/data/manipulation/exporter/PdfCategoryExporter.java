package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import cz.muni.fi.pv168.seminar01.delta.model.Category;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Collection;

/**
 * Class for exporting categories to pdf
 */
public class PdfCategoryExporter implements Exporter<Category> {
    @Override
    public void export(Collection<Category> collection, Path filePath) throws ExporterException {
        filePath = FileSuffixValidator.checkFileSuffix(filePath, FileFormat.PDF);
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath.toString()));
            document.open();
            document.add(new Phrase("Kategorie: \n", FontFactory.getFont(FontFactory.COURIER, 26, Font.NORMAL)));
            addCategories(document, collection);
            document.close();
        } catch (Exception exception) {
            document.close();
            throw new ExporterException(exception.getMessage(), exception);
        }
    }

    private void addCategories(Document document, Collection<Category> collection) throws DocumentException{
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        for (var category : collection){
            document.add(new Phrase(category.getName() + '\n', font));
        }
    }
}
