package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

public class ExporterFactory {

    public static AsyncExporter createExporter(DataType dataType, FileFormat fileType) {
        if (dataType == DataType.RIDES) {
            if (fileType == FileFormat.CSV) {
                return new AsyncExporter<>(new CsvRideExporter());
            }
            return new AsyncExporter<>(new PdfRideExporter());
        }
        if (fileType == FileFormat.CSV) {
            return new AsyncExporter<>(new CsvCategoryExporter());
        }
        return new AsyncExporter<>(new PdfCategoryExporter());
    }
}
