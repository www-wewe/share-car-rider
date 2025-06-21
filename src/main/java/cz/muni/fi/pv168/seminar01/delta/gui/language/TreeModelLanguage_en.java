package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class TreeModelLanguage_en extends ListResourceBundle {
    public static final String PATH = "cz.muni.fi.pv168.seminar01.delta.gui.language.TreeModelLanguage";

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "renameDestination", "Rename destination" },
            { "newDestination", "New destination" },
            { "deleteDestination", "Delete destination" },
            { "newCar", "New car" },
            { "deleteCar", "Delete car" },
            { "renameCategory", "Rename category" },
            { "newCategory", "New category" },
            { "deleteCategory", "Delete category" },
            { "modifyCar", "Modify car" }
    };
}
