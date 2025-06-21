package cz.muni.fi.pv168.seminar01.delta.gui.language;

import cz.muni.fi.pv168.seminar01.delta.gui.components.Column;

import java.util.ListResourceBundle;

public class InsertRecordLanguage extends ListResourceBundle {
    public static final String PATH = "cz.muni.fi.pv168.seminar01.delta.gui.language.InsertRecordLanguage";
    private static final String NOT_FILLED = " není vyplněno!";
    private static final String FILLED_INCORRECTLY = " je vyplněná špatně!";
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "title", "Přidat jízdu" },
            { "lastCommandInsertOk", "Jízda byla přidána" },
            { "lastCommandInsertNok", "Přidávání bylo zrušeno" },
            { "cancel", "Zrušit" },
            { "categoryButton", "Vybrat kategorie" },
            { "name", "Jméno" },
            { "from", "Odkud?" },
            { "where", "Kam?" },
            { "when", "Kdy?" },
            { "passenger", "Počet pasažérů" },
            { "distance", "Vzdálenost (km)" },
            { "price", "Cena paliva (KČ)" },
            { "category", "Kategorie" },
            { "titleUpdate", "Upravit jízdu" },
            { "lastCommandUpdateOk", "Jízda byla upravena" },
            { "lastCommandUpdateNok", "Upravování bylo zrušeno" },
            { "invalidNameMsg", Column.NAME.czech + NOT_FILLED },
            { "invalidFromMsg", Column.FROM.czech + NOT_FILLED },
            { "invalidToMsg", Column.TO.czech + NOT_FILLED },
            { "invalidPersonsMsg", Column.PERSONS.czech + " je vyplněný špatně!" },
            { "invalidDistanceMsg", Column.DISTANCE.czech + FILLED_INCORRECTLY },
            { "invalidPriceMsg", Column.PRICE.czech + FILLED_INCORRECTLY },
            { "invalidCategoryMsg", "Nejsou vybrány žádné kategorie!" },
            { "invalidCarMsg", Column.AUTO.czech + NOT_FILLED },
            { "useAsTemplate", "Použít jako vzor" },
            { "categoryManagement", "Správa kategorií" },
    };
}
