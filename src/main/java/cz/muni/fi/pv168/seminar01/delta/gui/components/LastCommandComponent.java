package cz.muni.fi.pv168.seminar01.delta.gui.components;

import javax.swing.*;

/**
 * Class grouping Swing elements on the bottom-left of Share_Car_Rider application window.
 * Contains methods for working with these elements.
 *
 * @author Jiri Zak
 */
public class LastCommandComponent {

    private final JLabel lastCommand;

    public LastCommandComponent(JLabel lastCommand) {
        this.lastCommand = lastCommand;
    }

    public void setText(String text) {
        this.lastCommand.setText(text);
    }
}
