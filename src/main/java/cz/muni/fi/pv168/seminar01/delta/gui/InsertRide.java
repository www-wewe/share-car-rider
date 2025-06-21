package cz.muni.fi.pv168.seminar01.delta.gui;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.AutoRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.CategoryRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.DestinationRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.data.validator.RideValidator;
import cz.muni.fi.pv168.seminar01.delta.gui.category.CategoryTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.components.TableComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.language.InsertRecordLanguage;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;
import org.jdatepicker.JDatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Class representing dialog window for inserting record to the table component.
 *
 * @author Veronika Lenková
 */
public class InsertRide extends JDialog {
    protected final LastCommandComponent lastCommandComponent;
    protected final DestinationRepository destinationRepository;
    protected final CategoryRepository categoryRepository;
    private final AutoRepository autoRepository;
    protected JTextField nameTextField;
    protected JTextField costTextField;
    protected JTextField distanceTextField;
    protected JTextField countOfPassengersTextField;
    protected JButton chooseCategoriesButton;
    protected JPanel datePanel;
    protected JComboBox<Object> fromComboBox;
    protected JComboBox<Object> toComboBox;
    private JLabel nameLabel;
    private JLabel fromLabel;
    private JLabel whereLabel;
    private JLabel whenLabel;
    private JLabel passengerLabel;
    private JLabel distanceLabel;
    private JLabel priceLabel;
    private JLabel categoryLabel;
    protected JComboBox autoComboBox;
    protected JDatePicker datePicker;
    protected TableComponent ridesTable;
    protected ManageCategoriesDialog categoriesDialog;
    private JPanel contentPane;
    private JButton okButton;
    private JButton cancelButton;

    protected Locale locale;
    protected ResourceBundle text;

    public InsertRide(TableComponent ridesTable, CategoryTreeModel categoryModel, LastCommandComponent lastCommand, Repository<Destination> destinations, Repository<Category> categories, Repository<Auto> autos, Locale locale) {
        this.locale = locale;
        this.text = ResourceBundle.getBundle(InsertRecordLanguage.PATH, locale);
        this.lastCommandComponent = lastCommand;
        this.ridesTable = ridesTable;
        this.destinationRepository = (DestinationRepository) destinations;
        this.categoryRepository = (CategoryRepository) categories;
        this.setTitle(text.getString("title"));
        this.autoRepository = (AutoRepository) autos;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        fromComboBox.setModel(new DefaultComboBoxModel(destinations.findAll().toArray()));
        toComboBox.setModel(new DefaultComboBoxModel(destinations.findAll().toArray()));
        autoComboBox.setModel(new DefaultComboBoxModel(autos.findAll().toArray()));
        categoriesDialog = new ManageCategoriesDialog(categoryModel, locale);
        chooseCategoriesButton.addActionListener(categoriesDialog);

        okButton.addActionListener(this::onOK);
        cancelButton.addActionListener(this::onCancel);

        setText();
    }

    protected void onOK(ActionEvent e) {
        Ride record = createRide();
        if (record != null) {
            addRecordToDatabase(record);
            lastCommandComponent.setText(text.getString("lastCommandInsertOk"));
        }
    }

    protected Ride createRide() {
        return createRide(null);
    }


    protected Ride createRide(Long rideId) {
        String name = nameTextField.getText();
        Destination from = (Destination) fromComboBox.getSelectedItem();
        Destination to = (Destination) toComboBox.getSelectedItem();
        var date = datePicker.getModel().getValue();
        String countOfPassengers = countOfPassengersTextField.getText();
        String distance = distanceTextField.getText();
        String cost = costTextField.getText();
        List<String> categories = categoriesDialog.getCheckedCategories();
        var auto = (Auto) autoComboBox.getSelectedItem();

        var errorMessage = RideValidator.validate(name, from, to, countOfPassengers, cost, distance, categories, auto, locale);
        if (errorMessage != null) {
            JOptionPane.showMessageDialog(null, errorMessage);
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.M.yyyy");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        Destination fromDestination = this.destinationRepository.findByName(from.getName());
        Destination toDestination = this.destinationRepository.findByName(to.getName());
        List<Category> categoryList = new ArrayList<>();
        for (var categoryName : categories) {
            categoryList.add(this.categoryRepository.findByName(categoryName));
        }

        return new Ride(rideId, name, fromDestination, toDestination, LocalDate.parse(simpleDateFormat.format(date), dateFormatter),
                Integer.parseInt(countOfPassengers), Double.parseDouble(distance),
                new BigDecimal(cost), categoryList, auto);
    }

    protected void addRecordToDatabase(Ride record) {
        ridesTable.add(record);
        ridesTable.refreshTableData();
        dispose();
    }

    private void createUIComponents() {
        datePicker = new JDatePicker(new Date());
        datePanel = new JPanel();
        datePanel.add(datePicker);
    }
    

    protected void onCancel(ActionEvent e) {
        lastCommandComponent.setText(text.getString("lastCommandInsertNok"));
        dispose();
    }

    private void setText() {
        cancelButton.setText(text.getString("cancel"));
        chooseCategoriesButton.setText(text.getString("categoryButton"));
        nameLabel.setText(text.getString("name"));
        fromLabel.setText(text.getString("from"));
        whereLabel.setText(text.getString("where"));
        whenLabel.setText(text.getString("when"));
        passengerLabel.setText(text.getString("passenger"));
        distanceLabel.setText(text.getString("distance"));
        priceLabel.setText(text.getString("price"));
        categoryLabel.setText(text.getString("category"));
    }

    protected void setValues(Ride selectedRide) {
        nameTextField.setText(selectedRide.getName());
        fromComboBox.setSelectedItem(selectedRide.getFrom());
        toComboBox.setSelectedItem(selectedRide.getTo());
        countOfPassengersTextField.setText(String.valueOf(selectedRide.getPassengerCount()));
        distanceTextField.setText(String.valueOf(selectedRide.getDistance()));
        costTextField.setText(String.valueOf(selectedRide.getPrice()));
        categoriesDialog.setCheckedCategories(selectedRide.getCategories().stream().map(Category::getName).toList());
        datePicker.getModel().setDate(
                selectedRide.getDate().getYear(), selectedRide.getDate().getMonthValue() - 1, selectedRide.getDate().getDayOfMonth());
    }
}
