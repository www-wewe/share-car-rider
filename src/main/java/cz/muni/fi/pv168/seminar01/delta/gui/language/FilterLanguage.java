package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class FilterLanguage extends ListResourceBundle {
    public static final String PATH = "cz.muni.fi.pv168.seminar01.delta.gui.language.FilterLanguage";

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "title", "Filtry" },
            { "mainLabel", "Nastavení filtrů" },
            { "distance", "Vzdálenost" },
            { "from", "Od" },
            { "to", "Do" },
            { "passenger", "Počet pasažérů" },
            { "destination", "Destinace" },
            { "fromWhere", "Odkud" },
            { "toWhere", "Kam" },
            { "apply", "Použít" },
            { "cancel", "Zrušit" },
            { "deleteFilters", "Vynulovat filtry" },
            { "lastCommand", "Filtry nastaveny" },
            { "car", "Auto (SPZ)" }
    };
}
