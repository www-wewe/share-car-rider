package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import cz.muni.fi.pv168.seminar01.delta.gui.components.Column;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

/**
 * Class for exporting rides to pdf
 */
public class PdfRideExporter implements Exporter<Ride> {
    @Override
    public void export(Collection<Ride> collection, Path filePath) throws ExporterException {
        filePath = FileSuffixValidator.checkFileSuffix(filePath, FileFormat.PDF);
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath.toString()));
            document.open();
            var numberOfColumns = Column.values().length;
            PdfPTable table = new PdfPTable(numberOfColumns);
            addTableHeader(table);
            addRows(table, collection);
            document.add(new Phrase("Jízdy:", FontFactory.getFont(FontFactory.COURIER, 26, Font.NORMAL)));
            document.add(table);
            document.close();
        } catch (Exception docException) {
            document.close();
            throw new ExporterException(docException.getMessage(), docException);
        }
    }

    private void addTableHeader(PdfPTable table) {
        Arrays.stream(Column.values())
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(3);
                    header.setPhrase(new Phrase(columnTitle.czech));
                    table.addCell(header);
        });
    }

    private void addRows(PdfPTable table, Collection<Ride> collection) {
        for (var ride : collection){
            table.addCell(ride.getName());
            table.addCell(ride.getFrom().getName());
            table.addCell(ride.getTo().getName());
            table.addCell(ride.getDate().toString());
            table.addCell(String.valueOf(ride.getPassengerCount()));
            table.addCell(String.valueOf(ride.getDistance()));
            table.addCell(String.valueOf(ride.getPrice()));
            table.addCell(ride.getCategories().toString());
            table.addCell(ride.getAuto().toString());
        }
    }

}
