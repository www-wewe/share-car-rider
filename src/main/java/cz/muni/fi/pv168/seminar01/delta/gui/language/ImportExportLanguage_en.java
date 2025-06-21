package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class ImportExportLanguage_en extends ListResourceBundle {
    private static final String NOT_VALID = "is invalid";

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "cancel", "Cancel" },
            { "browse", "Browse..." },

            { "title", "Import" },
            { "lastCommandImportOk", "Rides have been imported" },
            { "lastCommandImportNok", "Import failed" },
            { "lastCommandImportCategoriesOk", "Categories have been imported" },
            { "lastCommandCancel", "Import was cancelled" },
            { "invalidDestination", "Destination %s " + NOT_VALID},
            { "invalidCategory", "Category %s " + NOT_VALID },
            { "invalidCar", "Car is invalid" },
            { "selectCSV", "Select CSV file" },

            { "selectPath", "Select Path" },
            { "lastCommandExportNokPath", "Export failed - no output file selected"},
            { "lastCommandExportRideOk", "Rides have been exported"},
            { "lastCommandExportCategoryOk", "Categories have been exported"},
            { "lastCommandExportNok", "Export failed"},
            { "lastCommandExportNokCancel", "Export has been cancelled"},
    };
}
