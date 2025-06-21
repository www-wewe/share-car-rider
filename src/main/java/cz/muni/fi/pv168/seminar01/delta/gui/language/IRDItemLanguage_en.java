package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class IRDItemLanguage_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "yes", "Yes" },
            { "no", "No" },

            { "deleteCategory?", "Are you sure you want to delete the selected category?" },
            { "deleteCategoryTitle", "Delete Category" },
            { "deleteCategoryOk", "Category Deleted" },
            { "deleteCategoryNok", "Category deletion has been cancelled" },
            { "deleteCategoryMessage", "Selected Category successfully deleted!" },

            { "deleteCar?", "Are you sure you want to delete the selected car?" },
            { "deleteCarTitle", "Delete Car" },
            { "deleteCarOk", "Car Deleted" },
            { "deleteCarNok", "Car deletion has been cancelled" },
            { "deleteCarMessage", "Selected Car successfully deleted!" },

            { "deleteDestination?", "Are you sure you want to delete the selected destination?" },
            { "deleteDestinationTitle", "Delete Destination" },
            { "deleteDestinationOk", "Destination Deleted" },
            { "deleteDestinationNok", "Destination deletion has been cancelled" },
            { "deleteDestinationMessage", "Selected Destination successfully deleted!" },

            { "insert", "Insert" },
            { "cancel", "Cancel" },

            { "categoryName?", "New category name?" },
            { "insertCategory", "Insert new category" },
            { "insertCategoryNok", "Adding a category has been cancelled" },
            { "insertCategoryOk", "Category has been added" },

            { "destinationName?", "New destination name?" },
            { "insertDestination", "Insert new destination" },
            { "insertDestinationNok", "Adding a destination has been cancelled" },
            { "insertDestinationOk", "Destination has been added" },

            { "insertCar", "Insert new car" },
            { "insertCarNok", "Adding a car has been cancelled" },
            { "insertCarOk", "Car has been added" },
            { "licencePlate", "Licence plate:" },
            { "brand", "Brand:" },
            { "fuelType", "Fuel type:" },

            { "categoryRename?", "What name do you wish to rename the category?" },
            { "categoryRename", "Rename category" },
            { "categoryRenameOk", "Category has been renamed" },
            { "categoryRenameNok", "The renaming has been cancelled" },

            { "destinationRename?", "What name do you wish to rename the destination?" },
            { "destinationRename", "Rename destination" },
            { "destinationRenameOk", "Destination has been renamed" },
            { "destinationRenameNok", "The renaming has been cancelled" },

            { "renameCar", "Rename car" },
            { "renameCarNok", "Renaming a car has been cancelled" },
            { "renameCarOk", "Car has been renamed" }
    };
}
