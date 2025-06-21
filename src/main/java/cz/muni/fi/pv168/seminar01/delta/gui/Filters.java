package cz.muni.fi.pv168.seminar01.delta.gui;

import cz.muni.fi.pv168.seminar01.delta.data.processing.FilterTools;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.language.FilterLanguage;
import cz.muni.fi.pv168.seminar01.delta.gui.mainwindow.Displayed;
import cz.muni.fi.pv168.seminar01.delta.gui.mainwindow.ShowRides;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Andrej Gafrik
 *
 * Window, which lets user set filters and apply them.
 */
public class Filters extends JDialog {
    private final LastCommandComponent lastCommand;
    private JFormattedTextField rideLengthFrom;
    private JFormattedTextField rideLengthTo;
    private JFormattedTextField passengerCountFrom;
    private JFormattedTextField passengerCountTo;
    private JComboBox<Object> destinationFrom;
    private JComboBox<Object> destinationTo;
    private JButton cancelButton;
    private JButton applyButton;
    private JPanel filterPanel;
    private JButton deleteFiltersButton;
    private JLabel mainLabel;
    private JLabel distanceLabel;
    private JLabel passengerLabel;
    private JLabel destinationLabel;
    private JLabel fromDistanceLabel;
    private JLabel toDistanceLabel;
    private JLabel fromPassengerLabel;
    private JLabel toPassengerLabel;
    private JLabel fromDestinationLabel;
    private JLabel toDestinationLabel;
    private JLabel carLabel;
    private JComboBox<Object> carComboBox;
    private final Repository<Ride> rides;
    private final JTable table;
    private int currentRideLengthFrom;
    private int currentRideLengthTo;
    private int currentPassengersCountFrom;
    private int currentPassengersCountTo;
    private int currentIndexDestinationFrom;
    private int currentIndexDestinationTo;
    private int currentCarIndex;
    private String searchWord;
    private final ShowRides showRides;
    private ResourceBundle text;

    public Filters(Repository<Ride> rides, JTable table, ShowRides showRides, LastCommandComponent lastCommand) {
        this.text = ResourceBundle.getBundle(FilterLanguage.PATH, new Locale(""));
        this.setTitle(text.getString("title"));
        setContentPane(filterPanel);
        setModal(true);

        this.rides = rides;
        this.table = table;
        this.showRides = showRides;
        this.lastCommand = lastCommand;

        setFormatter();
        initCurrentFilters();
        initDestinationComboBox();
        initButtons();
        initCarComboBox();
    }

    public void setLanguage(Locale locale) {
        text = ResourceBundle.getBundle(FilterLanguage.PATH, locale);
        this.setTitle(text.getString("title"));
        mainLabel.setText(text.getString("mainLabel"));
        distanceLabel.setText(text.getString("distance"));
        fromDistanceLabel.setText(text.getString("from"));
        toDistanceLabel.setText(text.getString("to"));
        passengerLabel.setText(text.getString("passenger"));
        fromPassengerLabel.setText(text.getString("from"));
        toPassengerLabel.setText(text.getString("to"));
        destinationLabel.setText(text.getString("destination"));
        fromDestinationLabel.setText(text.getString("fromWhere"));
        toDestinationLabel.setText(text.getString("toWhere"));
        cancelButton.setText(text.getString("cancel"));
        applyButton.setText(text.getString("apply"));
        deleteFiltersButton.setText(text.getString("deleteFilters"));
        carLabel.setText(text.getString("car"));
    }

    /**
     * Filter table rows based on filter criteria selected by user
     */
    public void applyFilters() {
        FilterTools filterTools = new FilterTools(
                table,
                currentPassengersCountFrom,
                currentPassengersCountTo,
                currentRideLengthFrom,
                currentRideLengthTo,
                (String) destinationFrom.getItemAt(currentIndexDestinationFrom),
                (String) destinationTo.getItemAt(currentIndexDestinationTo),
                (String) carComboBox.getItemAt(currentCarIndex),
                showRides.getState() == Displayed.SEARCH ? searchWord : ""
        );
        filterTools.filterTable();
    }

    /**
     * Refreshes items in comboBoxes (searches for items in table, not in repository).
     * Currently selected items remain selected, as long as they are in the table
     */
    public void refreshComboBoxes() {
        String from = (String) destinationFrom.getItemAt(currentIndexDestinationFrom);
        String to = (String) destinationTo.getItemAt(currentIndexDestinationTo);
        initDestinationComboBox();
        currentIndexDestinationFrom = setComboBoxSelected(destinationFrom, from);
        currentIndexDestinationTo = setComboBoxSelected(destinationTo, to);

        String car = (String) carComboBox.getItemAt(currentCarIndex);
        initCarComboBox();
        currentCarIndex = setComboBoxSelected(carComboBox, car);
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord == null ? "" : searchWord;
    }

    private void onCancel() {
        displayCurrentFilters();
        dispose();
    }

    private void onApply() {
        updateCurrentFilters();
        applyFilters();
        lastCommand.setText(text.getString("lastCommand"));
        dispose();
    }

    private void setVisualToInitState() {
        destinationTo.setSelectedIndex(0);
        destinationFrom.setSelectedIndex(0);
        carComboBox.setSelectedIndex(0);
        passengerCountFrom.setValue(null);
        passengerCountTo.setValue(null);
        rideLengthFrom.setValue(null);
        rideLengthTo.setValue(null);
    }

    private void displayCurrentFilters() {
        rideLengthFrom.setValue(currentRideLengthFrom == 0 ? null : currentRideLengthFrom);
        rideLengthTo.setValue(currentRideLengthTo == 0 ? null : currentRideLengthTo);
        passengerCountFrom.setValue(currentPassengersCountFrom == 0 ? null : currentPassengersCountFrom);
        passengerCountTo.setValue(currentPassengersCountTo == 0 ? null : currentPassengersCountTo);
        destinationFrom.setSelectedIndex(currentIndexDestinationFrom);
        destinationTo.setSelectedIndex(currentIndexDestinationTo);
        carComboBox.setSelectedIndex(currentCarIndex);
    }

    private void updateCurrentFilters() {
        currentRideLengthFrom = stringToInt(rideLengthFrom.getText());
        currentRideLengthTo = stringToInt(rideLengthTo.getText());
        currentPassengersCountFrom = stringToInt(passengerCountFrom.getText());
        currentPassengersCountTo = stringToInt(passengerCountTo.getText());
        currentIndexDestinationFrom = destinationFrom.getSelectedIndex();
        currentIndexDestinationTo = destinationTo.getSelectedIndex();
        currentCarIndex = carComboBox.getSelectedIndex();
    }

    private int stringToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int setComboBoxSelected(JComboBox<Object> comboBox, String selectTo) {
        for (int i = 1; i < comboBox.getModel().getSize(); i++) {
            if (Objects.equals(comboBox.getItemAt(i), selectTo)) {
                comboBox.setSelectedIndex(i);
                return i;
            }
        }
        return 0;
    }

    private void setFormatter() {
        NumberFormat nf = NumberFormat.getIntegerInstance();
        NumberFormatter nff = new NumberFormatter(nf);
        nff.setAllowsInvalid(false);
        DefaultFormatterFactory factory = new DefaultFormatterFactory(nff);

        rideLengthFrom.setFormatterFactory(factory);
        rideLengthTo.setFormatterFactory(factory);
        passengerCountFrom.setFormatterFactory(factory);
        passengerCountTo.setFormatterFactory(factory);
    }

    private void initDestinationComboBox() {

        destinationFrom.setModel(new DefaultComboBoxModel<>(
                rides.findAll().stream().map(ride -> ride.getFrom().toString()).distinct().toArray()));
        destinationTo.setModel(new DefaultComboBoxModel<>(
                rides.findAll().stream().map(ride -> ride.getTo().toString()).distinct().toArray()));
        destinationFrom.insertItemAt("", 0);
        destinationTo.insertItemAt("", 0);

        destinationTo.setSelectedIndex(0);
        destinationFrom.setSelectedIndex(0);
        currentIndexDestinationFrom = 0;
        currentIndexDestinationTo = 0;
    }

    private void initCarComboBox() {
        carComboBox.setModel(new DefaultComboBoxModel<>(
                rides.findAll().stream().map(ride -> ride.getAuto().toString()).distinct().toArray()));
        carComboBox.insertItemAt("", 0);
        carComboBox.setSelectedIndex(0);

        currentCarIndex = 0;
    }

    private void initCurrentFilters() {
        currentRideLengthFrom = 0;
        currentRideLengthTo = 0;
        currentPassengersCountFrom = 0;
        currentPassengersCountTo = 0;
        currentIndexDestinationFrom = 0;
        currentIndexDestinationTo = 0;
        currentCarIndex = 0;
        searchWord = "";
    }

    private void initButtons() {
        cancelButton.addActionListener(e -> onCancel());
        applyButton.addActionListener(e -> onApply());
        deleteFiltersButton.addActionListener(e -> setVisualToInitState());
    }
}
