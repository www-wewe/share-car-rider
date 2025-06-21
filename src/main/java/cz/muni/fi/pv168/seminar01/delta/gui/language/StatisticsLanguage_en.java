package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class StatisticsLanguage_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "title", "Statistics" },
            { "lastCommand", "Statistics shown" },
            { "saved", "You have %d rides! \n" },
            { "totalDistance", "Total distance" },
            { "maxDistance", "Longest distance"},
            { "totalPrice", "Total fuel price"},
            { "maxPrice", "Max fuel price" },

            { "chartTitle", "Chart price/km" },
            { "week", "In a week from the day" },
            { "noCars", "No car" }
    };
}
