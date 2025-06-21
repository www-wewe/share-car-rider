package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class MainWindowLanguage_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "filter", "Filters" },
            { "statistics", "Statistics" },
            { "insert", "Add ride" },
            { "edit", "Edit ride" },
            { "delete", "Delete ride" },
            { "todayIs", "Today is" },
            { "search", "Search" },
            { "chart", "Chart" }
    };
}
