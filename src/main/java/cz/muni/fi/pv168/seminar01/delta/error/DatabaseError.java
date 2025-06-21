package cz.muni.fi.pv168.seminar01.delta.error;

public class DatabaseError extends RuntimeException {
    String userMessage;

    public DatabaseError(String userMessage, Throwable cause) {
        super(cause);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
