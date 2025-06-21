package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class ShowRidesLanguage extends ListResourceBundle {
    public static final String PATH = "cz.muni.fi.pv168.seminar01.delta.gui.language.ShowRidesLanguage";

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "allRides", "Všechny jízdy" },
            { "weekRides", "Zobrazit týden" },
            { "searchRides", "Hledání" }
    };
}
