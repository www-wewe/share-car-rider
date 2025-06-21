package cz.muni.fi.pv168.seminar01.delta.error;

public class ParseError extends RuntimeException {

    public ParseError(String userMessage) {
        super(userMessage);
    }
}
