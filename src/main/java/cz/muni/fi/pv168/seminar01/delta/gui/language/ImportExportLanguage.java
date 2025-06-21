package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class ImportExportLanguage extends ListResourceBundle {
    public static final String PATH = "cz.muni.fi.pv168.seminar01.delta.gui.language.ImportExportLanguage";

    private static final String NOT_VALID = "je neplatná";

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "cancel", "Zrušit" },
            { "browse", "Prohledávat..." },

            { "title", "Import" },
            { "lastCommandImportOk", "Jízdy byly importovány" },
            { "lastCommandImportNok", "Import se nezdařil" },
            { "lastCommandImportCategoriesOk", "Kategorie byly importovány" },
            { "lastCommandCancel", "Import byl zrušen" },
            { "invalidDestination", "Destinace %s " + NOT_VALID},
            { "invalidCategory", "Kategorie %s " + NOT_VALID },
            { "invalidCar", "Auto je neplatné" },
            { "selectCSV", "Vybrat CSV soubor" },

            { "selectPath", "Kam exportovat?" },
            { "lastCommandExportNokPath", "Export se nezdařil - nebyl vybrán výstupní soubor"},
            { "lastCommandExportRideOk", "Jízdy byly exportovány"},
            { "lastCommandExportCategoryOk", "Kategorie byly exportovány"},
            { "lastCommandExportNok", "Export se nezdařil"},
            { "lastCommandExportNokCancel", "Export byl zrušen"},
    };
}
