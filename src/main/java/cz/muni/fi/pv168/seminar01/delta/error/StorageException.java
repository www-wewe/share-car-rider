package cz.muni.fi.pv168.seminar01.delta.error;

/**
 * Exception that is thrown if there is some problem with data storage
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        this(message, null);
    }

    public StorageException(String message, Throwable cause) {
        super("Storage error: " +  message, cause);
    }

}
