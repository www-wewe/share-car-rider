package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class TreeModelLanguage extends ListResourceBundle {
    public static final String PATH = "cz.muni.fi.pv168.seminar01.delta.gui.language.TreeModelLanguage";

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "renameDestination", "Přejmenovat destinace" },
            { "newDestination", "Nová destinace" },
            { "deleteDestination", "Smazat destinace" },
            { "newCar", "Nové auto" },
            { "deleteCar", "Smazat auto" },
            { "renameCategory", "Přejmenovat kategorii" },
            { "newCategory", "Nová kategorie" },
            { "deleteCategory", "Smazat kategorii" },
            { "modifyCar", "Upravit auto" }
    };
}
