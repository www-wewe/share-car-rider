package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class MainWindowLanguage extends ListResourceBundle {
    public static final String PATH = "cz.muni.fi.pv168.seminar01.delta.gui.language.MainWindowLanguage";

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "filter", "Filtry" },
            { "statistics", "Statistika" },
            { "insert", "Přidat jízdu" },
            { "edit", "Upravit jízdu" },
            { "delete", "Smazat jízdu" },
            { "todayIs", "Dnes je" },
            { "search", "Hledání" },
            { "chart", "Graf" }
    };
}
