package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class FilterLanguage_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "title", "Filters" },
            { "mainLabel", "Filter settings" },
            { "distance", "Distance" },
            { "from", "From" },
            { "to", "To" },
            { "passenger", "Passenger count" },
            { "destination", "Destinations" },
            { "fromWhere", "From" },
            { "toWhere", "To" },
            { "apply", "Apply" },
            { "cancel", "Cancel" },
            { "deleteFilters", "Erase filters" },
            { "lastCommand", "Filters set" },
            { "car", "Car (license plate)" }
    };
}
