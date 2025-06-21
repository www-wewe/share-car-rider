package cz.muni.fi.pv168.seminar01.delta.gui.language;

import java.util.ListResourceBundle;

public class DeleteRecordLanguage_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return contents;
    }

    private final Object[][] contents = {
            { "yes", "yes" },
            { "no", "no" },
            { "title", "Delete" },
            { "question", "Are you sure you want to delete the selected entry?" },
            { "message", "The selected row was successfully deleted" }
    };
}
