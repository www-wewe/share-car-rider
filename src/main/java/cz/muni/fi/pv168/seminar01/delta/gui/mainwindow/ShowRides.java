package cz.muni.fi.pv168.seminar01.delta.gui.mainwindow;

import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.language.ShowRidesLanguage;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Andrej Gafrik
 * This class holds information about what is displayed in the table (all rides, rides withing one week, ...)
 * and changes label of button used for state switching
 */

public class ShowRides {
    private final JButton showButton;
    private final JButton prevWeek;
    private final JButton nextWeek;
    private final LastCommandComponent lastCommand;
    private Displayed state;
    private Locale locale;
    private ResourceBundle text;
    private JLabel weekLabel;

    /**
     * @param showButton used for switching state
     * @param prevWeek (sets this button enabled/disabled based on state)
     * @param nextWeek  (sets this button enabled/disabled base on state)
     */
    ShowRides(JButton showButton, JButton prevWeek, JButton nextWeek, LastCommandComponent lastCommand, JLabel weekLabel) {
        this.locale = new Locale("");
        this.text = ResourceBundle.getBundle(ShowRidesLanguage.PATH, locale);

        this.weekLabel = weekLabel;
        this.showButton = showButton;
        this.prevWeek = prevWeek;
        this.nextWeek = nextWeek;
        setState(Displayed.WEEK);
        this.showButton.setText(text.getString("allRides"));
        this.lastCommand = lastCommand;
    }

    private void setState(Displayed newState) {
        if (newState == Displayed.ALL) {
            showButton.setText(text.getString("weekRides"));
            state = Displayed.ALL;
            prevWeek.setEnabled(false);
            nextWeek.setEnabled(false);
            weekLabel.setText(text.getString("allRides"));
        } else if (newState == Displayed.SEARCH) {
            showButton.setText(text.getString("weekRides"));
            state = Displayed.SEARCH;
            prevWeek.setEnabled(false);
            nextWeek.setEnabled(false);
            weekLabel.setText(text.getString("searchRides"));
        } else {
            showButton.setText(text.getString("allRides"));
            state = Displayed.WEEK;
            prevWeek.setEnabled(true);
            nextWeek.setEnabled(true);
        }
    }

    public Displayed getState() {
        return state;
    }

    /**
     * switch state between week and all rides
     */
    public void switchState() {
        if (state == Displayed.WEEK) {
            setState(Displayed.ALL);
            lastCommand.setText(text.getString("allRides"));
        } else {
            setState(Displayed.WEEK);
            lastCommand.setText(text.getString("weekRides"));
        }
    }

    /**
     * set "search" state
     */
    public void searchState() {
        setState(Displayed.SEARCH);
        lastCommand.setText(text.getString("searchRides"));
    }

    public void setLanguage(Locale locale) {
        this.text = ResourceBundle.getBundle(ShowRidesLanguage.PATH, locale);
        setState(state);
    }
}
