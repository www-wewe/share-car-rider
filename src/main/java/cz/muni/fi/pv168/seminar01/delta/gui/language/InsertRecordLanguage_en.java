package cz.muni.fi.pv168.seminar01.delta.gui.language;

import cz.muni.fi.pv168.seminar01.delta.gui.components.Column;

import java.util.ListResourceBundle;

public class InsertRecordLanguage_en extends ListResourceBundle {
    private static final String NOT_FILLED = " isn`t filled!";
    private static final String FILLED_INCORRECTLY = " is filled incorrectly!";
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "title", "Add ride" },
            { "lastCommandInsertOk", "Ride added" },
            { "lastCommandInsertNok", "Ride adding canceled" },
            { "cancel", "Cancel" },
            { "categoryButton", "Select categories" },
            { "name", "Name" },
            { "from", "From?" },
            { "where", "To?" },
            { "when", "When?" },
            { "passenger", "Number of passengers" },
            { "distance", "Distance (km)" },
            { "price", "Fuel price (KČ)" },
            { "category", "Categories" },
            { "titleUpdate", "Update ride" },
            { "lastCommandUpdateOk", "Ride modified" },
            { "lastCommandUpdateNok", "Ride modifying canceled" },
            { "invalidNameMsg", Column.NAME.english + NOT_FILLED },
            { "invalidFromMsg", Column.FROM.english + NOT_FILLED },
            { "invalidToMsg", Column.TO.english + NOT_FILLED },
            { "invalidPersonsMsg", Column.PERSONS.english + FILLED_INCORRECTLY },
            { "invalidDistanceMsg", Column.DISTANCE.english + FILLED_INCORRECTLY },
            { "invalidPriceMsg", Column.PRICE.english + FILLED_INCORRECTLY },
            { "invalidCategoryMsg", "No categories selected!" },
            { "invalidCarMsg", Column.AUTO.english + NOT_FILLED },
            { "useAsTemplate", "Use as template" },
            { "categoryManagement", "Category management" },
    };
}
