package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class StatisticsLanguage extends ListResourceBundle {
    public static final String PATH = "cz.muni.fi.pv168.seminar01.delta.gui.language.StatisticsLanguage";

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "title", "Statistiky" },
            { "lastCommand", "Statistiky zobrazeny" },
            { "saved", "Máte uložených %d jízd! \n" },
            { "totalDistance", "Ujetá vzdálenost" },
            { "maxDistance", "Nejdelší jizda"},
            { "totalPrice", "Cena paliva"},
            { "maxPrice", "Nejdražší jízda" },

            { "chartTitle", "Graf ceny/km" },
            { "week", "V týdnu od dne" },
            { "noCars", "Žádné auto" }
    };
}
