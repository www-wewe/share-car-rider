package cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter;

/**
 * Exception that is thrown if there is some problem with exporter
 */
public class ExporterException extends RuntimeException {

    public ExporterException(String message, Throwable cause) {
        super("Exporter error: " +  message, cause);
    }
}
