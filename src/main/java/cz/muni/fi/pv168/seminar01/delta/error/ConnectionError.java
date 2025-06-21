package cz.muni.fi.pv168.seminar01.delta.error;

public class ConnectionError extends DatabaseError {

    public ConnectionError(String userMessage, Throwable cause) {
        super(userMessage, cause);
    }
}
