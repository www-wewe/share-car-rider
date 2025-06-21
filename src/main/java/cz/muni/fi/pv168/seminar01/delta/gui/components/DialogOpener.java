package cz.muni.fi.pv168.seminar01.delta.gui.components;

import javax.swing.*;

/**
 * Class for opening JDialog components.
 *
 * @author Martin Vrzon
 */
public class DialogOpener {

    private DialogOpener() {
    }

    public static void openDialog(JDialog dialog) {
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
