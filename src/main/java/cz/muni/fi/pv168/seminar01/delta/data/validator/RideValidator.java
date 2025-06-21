package cz.muni.fi.pv168.seminar01.delta.data.validator;

import cz.muni.fi.pv168.seminar01.delta.gui.components.Column;
import cz.muni.fi.pv168.seminar01.delta.gui.language.InsertRecordLanguage;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class RideValidator extends Validator {
    public static String validate(String name, Destination from, Destination to, String countOfPassengers, String cost, String distance, List<String> categories, Auto auto, Locale locale) {
        String message = null;
        ResourceBundle text = ResourceBundle.getBundle(InsertRecordLanguage.PATH, locale == null ? new Locale("") : locale);
        if (name.isEmpty()) {
            message = text.getString("invalidNameMsg");
        } else if (from == null) {
            message = text.getString("invalidFromMsg");
        } else if (to == null) {
            message = text.getString("invalidToMsg");;
        } else if (countOfPassengers.isEmpty() || !Validator.isParsingInteger(countOfPassengers)) {
            message = text.getString("invalidPersonsMsg");
        } else if (distance.isEmpty() || !Validator.isParsingDouble(distance)) {
            message = text.getString("invalidDistanceMsg");
        } else if (cost.isEmpty() || !Validator.isParsingDouble(cost)) {
            message = text.getString("invalidPriceMsg");
        } else if (categories == null || categories.isEmpty()) {
            message = text.getString("invalidCategoryMsg");
        } else if (auto == null) {
            message = text.getString("invalidCarMsg");
        }

        return message;
    }
}
