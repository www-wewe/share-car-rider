package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class DeleteRecordLanguage extends ListResourceBundle {
    public static final String PATH = "cz.muni.fi.pv168.seminar01.delta.gui.language.DeleteRecordLanguage";

    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "yes", "Ano" },
            { "no", "Ne" },
            { "title", "Smazat" },
            { "question", "Jste si jisti, že chcete smazat vybraný záznam?" },
            { "message", "Vybraný řádek byl úspěšně smazán" }
    };
}
