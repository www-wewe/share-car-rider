package cz.muni.fi.pv168.seminar01.delta.data.validator;

public abstract class Validator {

    /**
     * @param string to check
     * @return {@code true} if string is parsing integer, {@code null} otherwise
     */
    public static boolean isParsingInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * @param string to check
     * @return {@code true} if string is parsing double, {@code null} otherwise
     */
    public static boolean isParsingDouble(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
