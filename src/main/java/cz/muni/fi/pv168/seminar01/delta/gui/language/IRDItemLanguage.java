package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class IRDItemLanguage extends ListResourceBundle {
    public static final String PATH = "cz.muni.fi.pv168.seminar01.delta.gui.language.IRDItemLanguage";

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "yes", "Ano" },
            { "no", "Ne" },

            { "deleteCategory?", "Jste si jistí, že chcete smazat vybranou kategorii?" },
            { "deleteCategoryTitle", "Smazat kategorii" },
            { "deleteCategoryOk", "Kategorie byla smazána" },
            { "deleteCategoryNok", "Mazání kategorii bylo zrušeno" },
            { "deleteCategoryMessage", "Vybraná kategorie úspěšně smazána!" },

            { "deleteCar?", "Jste si jistí, že chcete smazat vybrane auto?" },
            { "deleteCarTitle", "Smazat auto" },
            { "deleteCarOk", "Auto bylo smazáno" },
            { "deleteCarNok", "Mazání auta bylo zrušeno" },
            { "deleteCarMessage", "Vybrane auto úspěšně smazáno!" },

            { "deleteDestination?", "Jste si jistí, že chcete smazat vybranou destinaci?" },
            { "deleteDestinationTitle", "Smazat destinaci" },
            { "deleteDestinationOk", "Destinace byla smazána" },
            { "deleteDestinationNok", "Mazání destinace bylo zrušeno" },
            { "deleteDestinationMessage", "Vybraná destinace úspěšně smazána!" },

            { "insert", "Vložit" },
            { "cancel", "Zrušit" },

            { "categoryName?", "Jméno nové kategorie?" },
            { "insertCategory", "Vložit novou kategorii" },
            { "insertCategoryNok", "Přidávání kategorie bylo zrušeno" },
            { "insertCategoryOk", "Kategorie byla přídána" },

            { "destinationName?", "Jméno nové destinace?" },
            { "insertDestination", "Vložit novou destinaci" },
            { "insertDestinationNok", "Přidávání destinace bylo zrušeno" },
            { "insertDestinationOk", "Destinace byla přídána" },

            { "insertCar", "Vložit nové auto" },
            { "insertCarNok", "Přidávání auta bylo zrušeno" },
            { "insertCarOk", "Auto bylo přídáno" },
            { "licencePlate", "Poznávací značka:" },
            { "brand", "Značka:" },
            { "fuelType", "Typ paliva:" },

            { "categoryRename?", "Na jaký název si přejete kategorii přejmenovat?" },
            { "categoryRename", "Přejmenovat kategorii" },
            { "categoryRenameOk", "Kategorie byla přejmenována" },
            { "categoryRenameNok", "Přejmenování bylo zrušeno" },

            { "destinationRename?", "Na jaký název si přejete destinace přejmenovat?" },
            { "destinationRename", "Přejmenovat destinace" },
            { "destinationRenameOk", "Destinace byla přejmenována" },
            { "destinationRenameNok", "Přejmenování bylo zrušeno" },

            { "renameCar", "Přejmenovat auto" },
            { "renameCarNok", "Přejmenování auta bylo zrušeno" },
            { "renameCarOk", "Auto bylo přejmenováno" }
    };
}
