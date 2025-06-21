package cz.muni.fi.pv168.seminar01.delta.gui;

import cz.muni.fi.pv168.seminar01.delta.data.processing.StatisticsTools;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.language.StatisticsLanguage;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *  Window, which displays statistics.
 *
 * @author Andrej Gafrik
 * @author Veronika Lenkova
 */
public class Statistics extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel distance;
    private JLabel moneySpent;
    private JLabel mainInfo;
    private JLabel longestRide;
    private JLabel expensiveRide;
    private JLabel totalDistance;
    private JLabel maxDistance;
    private JLabel totalPrice;
    private JLabel maxPrice;
    private final StatisticsTools tools;
    private final ResourceBundle text;

    public Statistics(List<Ride> rides, LastCommandComponent lastCommand, Locale locale) {
        this.text = ResourceBundle.getBundle(StatisticsLanguage.PATH, locale);
        this.setTitle(text.getString("title"));
        totalDistance.setText(text.getString("totalDistance"));
        maxDistance.setText(text.getString("maxDistance"));
        totalPrice.setText(text.getString("totalPrice"));
        maxPrice.setText(text.getString("maxPrice"));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(this::onOK);

        tools = new StatisticsTools(rides);
        setStatistics();
        lastCommand.setText(text.getString("lastCommand"));
    }

    private void onOK(ActionEvent e) {
        dispose();
    }

    private void setStatistics() {
        mainInfo.setText(String.format(text.getString("saved"), tools.getRideCount()));
        distance.setText(tools.totalDistance() + " km");
        moneySpent.setText(tools.totalPrice() + " kč");
        expensiveRide.setText(tools.maxPrice() + " kč");
        longestRide.setText(tools.maxDistance() + " km");
    }
}
