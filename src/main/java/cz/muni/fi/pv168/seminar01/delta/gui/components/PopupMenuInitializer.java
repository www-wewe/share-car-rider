package cz.muni.fi.pv168.seminar01.delta.gui.components;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Class initializing JPopupMenu component with provided options.
 *
 * @author Martin Vrzon
 */
public class PopupMenuInitializer {

    private PopupMenuInitializer() {
    }

    public static JPopupMenu initializePopupMenu(Map<String, ActionListener> options) {
        var popupMenu = new JPopupMenu();

        for (var option : options.keySet()) {
            JMenuItem item = new JMenuItem(option);
            item.addActionListener(options.get(option));
            item.setActionCommand(option);
            popupMenu.add(item);
        }

        return popupMenu;
    }
}
