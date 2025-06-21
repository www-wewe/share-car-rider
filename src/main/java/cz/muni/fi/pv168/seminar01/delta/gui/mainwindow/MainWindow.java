package cz.muni.fi.pv168.seminar01.delta.gui.mainwindow;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.gui.*;
import cz.muni.fi.pv168.seminar01.delta.gui.language.MainWindowLanguage;
import cz.muni.fi.pv168.seminar01.delta.gui.category.CarTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.category.CategoryTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.category.DestinationTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.category.TreeModelType;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.components.TableComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.components.WeekCalendar;
import cz.muni.fi.pv168.seminar01.delta.gui.components.WeekChange;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;
import cz.muni.fi.pv168.seminar01.delta.wiring.DependencyProvider;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;


/**
 * Main Share_Car_Rider application window.
 * Contains all Swing objects and assigns them to Component classes.
 * Holds database connection.
 *
 * @author Veronika Lenkova
 */
public class MainWindow extends JFrame implements WindowListener {

    private final TableComponent tableComponent;
    private final LastCommandComponent lastCommandComponent;
    private final Filters filters;
    private final DependencyProvider dependencyProvider;
    private JPanel mainPanel;
    private JTable dataTable;
    private JButton exportButton;
    private JButton importButton;
    private JTextField searchTextField;
    private JButton viewStatisticsButton;
    private JButton insertRideButton;
    private JButton deleteRideButton;
    private JButton oKButton;
    private JTree tree;
    private JLabel introductionMessage;
    private JButton filtersButton;
    private JButton nextWeekButton;
    private JButton prevWeekButton;
    private JLabel weekLabel;
    private JLabel lastCommand;
    private JButton newTreeButton;
    private JButton deleteTreeButton;
    private JButton renameTreeButton;
    private JButton editRecordButton;
    private JButton showRidesButton;
    private final ShowRides showRides;
    private JComboBox<TreeModelType> treeModelType;
    private JLabel searchLabel;
    private JButton languageButton;
    private JButton chartButton;
    private final CategoryTreeModel categoryModel;
    private final CarTreeModel carModel;
    private final DestinationTreeModel destinationModel;
    private final Repository<Auto> autos;
    private final Repository<Category> categories;
    private final Repository<Destination> destinations;
    private final Repository<Ride> rides;
    private Locale locale;
    private ResourceBundle text;
    private TreeModelTypeListener treeModelTypeListener;
    private static final String CZECH = "";
    private static final String ENGLISH = "en";
    private DeleteRideListener deleteRideListener;
    private DeleteItemListener deleteItemListener;
    private InsertItemListener insertItemListener;
    private RenameItemListener renameItemListener;
    private TableMouseListener tableMouseListener;

    public MainWindow(DependencyProvider dependencyProvider) {
        super("Share Car Rider");
        addWindowListener(this);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.dependencyProvider = dependencyProvider;

        this.rides = dependencyProvider.getRideRepository();
        this.autos = dependencyProvider.getAutoRepository();
        this.categories = dependencyProvider.getCategoryRepository();
        this.destinations = dependencyProvider.getDestinationRepository();

        this.categoryModel = new CategoryTreeModel(categories);
        this.tree.setModel(categoryModel);
        this.carModel = new CarTreeModel(autos);
        this.destinationModel = new DestinationTreeModel(destinations);
        this.treeModelType.setModel(new DefaultComboBoxModel<>(TreeModelType.values()));


        this.lastCommandComponent = new LastCommandComponent(lastCommand);
        this.showRides = new ShowRides(showRidesButton, prevWeekButton, nextWeekButton, lastCommandComponent, weekLabel);
        this.filters = new Filters(rides, dataTable, this.showRides, lastCommandComponent);
        this.tableComponent = new TableComponent(dataTable, weekLabel, rides, filters, showRides);


        initLocale(CZECH);
        text = ResourceBundle.getBundle(MainWindowLanguage.PATH, locale);
        addListeners();
        setLanguage(locale);

        changeCategoriesActionsState(0);
        changeTableActionsState(0);
    }

    private void categoriesSelectionChanged(TreeSelectionEvent treeSelectionEvent) {
        var selectionModel = (TreeSelectionModel) treeSelectionEvent.getSource();
        var count = selectionModel.getSelectionCount();
        changeCategoriesActionsState(count);
    }

    private void changeCategoriesActionsState(int selectedItemsCount) {
        renameTreeButton.setEnabled(selectedItemsCount == 1);
        deleteTreeButton.setEnabled(selectedItemsCount == 1);

        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
            if (currentNode.getUserObject().toString().equals("Všechny kategorie") ||
                    currentNode.getUserObject().toString().equals("Všechny destinace") ||
                    currentNode.getUserObject().toString().equals("Všechny auta")) {
                renameTreeButton.setEnabled(false);
                deleteTreeButton.setEnabled(false);
            }
        }
    }

    private void tableSelectionChanged(ListSelectionEvent listSelectionEvent) {
        var selectionModel = (ListSelectionModel) listSelectionEvent.getSource();
        var count = selectionModel.getSelectedItemsCount();
        changeTableActionsState(count);
    }

    private void changeTableActionsState(int selectedItemsCount) {
        editRecordButton.setEnabled(selectedItemsCount == 1);
        deleteRideButton.setEnabled(selectedItemsCount == 1);
    }

    private void exportListener(ActionEvent e) {
        ExportData frame = new ExportData(rides.findAll(), categoryModel.getAllCategories(), lastCommandComponent, locale);
        frameInit(frame);
    }

    private void importListener(ActionEvent e){
        ImportData frame = new ImportData(tableComponent, lastCommandComponent, destinations, categories, autos, locale);
        frameInit(frame);
    }

    private void insertRecordListener(ActionEvent e){
        InsertRide frame = new InsertRide(tableComponent, categoryModel, lastCommandComponent, destinations, categories, autos, locale);
        frameInit(frame);
    }

    private void editRecordListener(ActionEvent e){
        InsertRide frame = new UpdateRide(tableComponent, categoryModel, lastCommandComponent, destinations, categories, autos, locale);
        frameInit(frame);
    }

    private void statisticsListener(ActionEvent e) {
        Statistics frame = new Statistics(rides.findAll(), lastCommandComponent, locale);
        frameInit(frame);
    }

    private void chartListener(ActionEvent e) {
        Chart frame = new Chart(rides, autos, locale);
        frameInit(frame);
    }

    private void filtersListener(ActionEvent e) {
        filters.refreshComboBoxes();
        frameInit(filters);
    }

    private void changeWeekForwardListener(ActionEvent e){
        changeWeekListener(WeekChange.NEXT);
    }

    private void changeWeekBackwardListener(ActionEvent e){
        changeWeekListener(WeekChange.PREV);
    }

    private void changeWeekListener(WeekChange week){
        tableComponent.shiftWeek(week);
        tableComponent.refreshTableData();
    }

    private void showRidesListener(ActionEvent e) {
        showRides.switchState();
        tableComponent.refreshTableData();
    }

    private void okSearchListener(ActionEvent e) {
        if (!Objects.equals(searchTextField.getText(), "")) {
            filters.setSearchWord(searchTextField.getText());
            showRides.searchState();
            tableComponent.refreshTableData();
        } else if (showRides.getState() == Displayed.SEARCH) {
            showRides.switchState();
            tableComponent.refreshTableData();
        }
    }

    private void frameInit(JDialog frame){
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setLanguage(Locale locale) {
        this.locale = locale;
        filtersButton.setText(text.getString("filter"));
        viewStatisticsButton.setText(text.getString("statistics"));
        insertRideButton.setText(text.getString("insert"));
        editRecordButton.setText(text.getString("edit"));
        deleteRideButton.setText(text.getString("delete"));
        introductionMessage.setText(text.getString("todayIs") + " " + WeekCalendar.formatDate(WeekCalendar.getTodayDate()));
        searchLabel.setText(text.getString("search"));
        chartButton.setText(text.getString("chart"));
        filters.setLanguage(locale);
        showRides.setLanguage(locale);
        treeModelTypeListener.setLanguage(locale, treeModelType);
        deleteRideListener.setLanguage(locale);
        deleteItemListener.setLanguage(locale);
        insertItemListener.setLanguage(locale);
        renameItemListener.setLanguage(locale);
        tableMouseListener.setLanguage(locale);
    }

    private void languageButtonListener(ActionEvent e) {
        if (Objects.equals(locale.getLanguage(), ENGLISH)) {
            languageButton.setText("English");
            locale = new Locale(CZECH);
        } else {
            languageButton.setText("Česky");
            locale = new Locale(ENGLISH);
        }
        text = ResourceBundle.getBundle(MainWindowLanguage.PATH, locale);
        setLanguage(locale);
    }

    private void initLocale(String language) {
        locale = new Locale(language);
        languageButton.setText("English");
    }

    private void addListeners() {
        importButton.addActionListener(this::importListener);
        exportButton.addActionListener(this::exportListener);
        insertRideButton.addActionListener(this::insertRecordListener);
        editRecordButton.addActionListener(this::editRecordListener);
        deleteRideListener = new DeleteRideListener(tableComponent, locale);
        deleteRideButton.addActionListener(deleteRideListener);
        viewStatisticsButton.addActionListener(this::statisticsListener);
        filtersButton.addActionListener(this::filtersListener);
        prevWeekButton.addActionListener(this::changeWeekBackwardListener);
        nextWeekButton.addActionListener(this::changeWeekForwardListener);
        showRidesButton.addActionListener(this::showRidesListener);
        oKButton.addActionListener(this::okSearchListener);
        languageButton.addActionListener(this::languageButtonListener);

        insertItemListener = new InsertItemListener(tree, lastCommandComponent, categories, autos, destinations, locale);
        newTreeButton.addActionListener(insertItemListener);
        renameItemListener = new RenameItemListener(tree, tableComponent, lastCommandComponent, categories, autos, destinations, locale);
        renameTreeButton.addActionListener(renameItemListener);
        deleteItemListener = new DeleteItemListener(tree, tableComponent, lastCommandComponent, categories, autos, destinations, locale);
        deleteTreeButton.addActionListener(deleteItemListener);
        treeModelTypeListener = new TreeModelTypeListener(tree,
                categoryModel, carModel, destinationModel,
                newTreeButton, renameTreeButton, deleteTreeButton);
        treeModelType.addActionListener(treeModelTypeListener);
        tree.getSelectionModel().addTreeSelectionListener(this::categoriesSelectionChanged);
        dataTable.getSelectionModel().addListSelectionListener(this::tableSelectionChanged);
        tableMouseListener = new TableMouseListener(tableComponent, categoryModel, lastCommandComponent,
                destinations, categories, autos, locale);
        dataTable.addMouseListener(tableMouseListener);
        chartButton.addActionListener(this::chartListener);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        this.dependencyProvider.closeConnection();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
