package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class ShowRidesLanguage_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "allRides", "All rides" },
            { "weekRides", "Show week" },
            { "searchRides", "Search" }
    };
}
