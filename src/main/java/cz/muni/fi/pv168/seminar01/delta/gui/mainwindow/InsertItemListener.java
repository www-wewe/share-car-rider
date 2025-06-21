package cz.muni.fi.pv168.seminar01.delta.gui.mainwindow;


import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.AutoRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.CategoryRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.DestinationRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.gui.category.CarTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.category.CategoryTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.category.DestinationTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.language.IRDItemLanguage;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;
import cz.muni.fi.pv168.seminar01.delta.model.FuelType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class representing Insert item to tree action.
 *
 * @author Veronika Lenkova
 */
public class InsertItemListener extends AbstractAction {

    private final JTree tree;
    private final LastCommandComponent lastCommandComponent;
    private final CategoryRepository categories;
    private final AutoRepository cars;
    private final DestinationRepository destinations;
    private ResourceBundle text;

    public InsertItemListener(JTree tree, LastCommandComponent lastCommandComponent, Repository<Category> categories, Repository<Auto> cars, Repository<Destination> destinations, Locale locale) {
        super("Vložit");
        this.text = ResourceBundle.getBundle(IRDItemLanguage.PATH, locale);
        this.tree = tree;
        this.lastCommandComponent = lastCommandComponent;
        this.categories = (CategoryRepository) categories;
        this.cars = (AutoRepository) cars;
        this.destinations = (DestinationRepository) destinations;
        UIManager.put("OptionPane.cancelButtonText", text.getString("cancel"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath currentSelection = tree.getSelectionPath();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        if (((DefaultMutableTreeNode) model.getRoot()).getUserObject().toString().equals("Všechny kategorie")) {
            insertCategory(model, currentSelection);
        } else if (((DefaultMutableTreeNode) model.getRoot()).getUserObject().toString().equals("Všechny auta")) {
            insertAuto(model);
        } else if (((DefaultMutableTreeNode) model.getRoot()).getUserObject().toString().equals("Všechny destinace")) {
            insertDestination(model);
        }
        model.reload();
    }

    /**
     * Inserts category to tree model
     * @param model Category Tree Model
     * @param currentSelection selected category
     */
    private void insertCategory(DefaultTreeModel model, TreePath currentSelection) {
        String name = JOptionPane.showInputDialog(null,
                text.getString("categoryName?"),
                text.getString("insertCategory"),
                JOptionPane.QUESTION_MESSAGE);
        if ((name == null) || (name.length() == 0)) {
            lastCommandComponent.setText(text.getString("insertCategoryNok"));
            return;
        }
        Category newCategory = new Category(name);
        insertCategoryToTree(model, currentSelection, newCategory);
        categories.create(newCategory);
        tree.setModel(new CategoryTreeModel(categories));
        lastCommandComponent.setText(text.getString("insertCategoryOk"));
    }

    /**
     * Inserts category to Category Tree Model
     * @param model Category Tree Model
     * @param currentSelection selected path in tree
     * @param newCategory to insert
     */
    private static void insertCategoryToTree(DefaultTreeModel model, TreePath currentSelection, Category newCategory) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newCategory);
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
            model.insertNodeInto(newNode, currentNode, currentNode.getChildCount());
            newCategory.setParent(((Category)currentNode.getUserObject()).getId());
        } else {
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            root.add(newNode);
        }
    }

    /**
     * Inserts destination to model
     * @param model Destination Tree Model
     */
    private void insertDestination(DefaultTreeModel model) {
        String name = JOptionPane.showInputDialog(null,
                text.getString("destinationName?"),
                text.getString("insertDestination"),
                JOptionPane.QUESTION_MESSAGE);
        if ((name == null) || (name.length() == 0)) {
            lastCommandComponent.setText(text.getString("insertDestinationNok"));
            return;
        }
        Destination newDestination = new Destination(name);
        destinations.create(newDestination);
        insertDestinationToTree(model, newDestination);
        tree.setModel(new DestinationTreeModel(destinations));
        lastCommandComponent.setText(text.getString("insertDestinationOk"));
    }

    /**
     * Inserts destination to Destination Tree Model
     * @param model Destination Tree Model
     * @param newDestination to insert
     */
    private static void insertDestinationToTree(DefaultTreeModel model, Destination newDestination) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newDestination);
        ((DefaultMutableTreeNode) model.getRoot()).add(newNode);
    }

    /**
     * Inserts car to model
     * @param model Car Tree Model
     */
    private void insertAuto(DefaultTreeModel model) {
        JTextField licensePlate = new JTextField();
        JTextField brand = new JTextField();
        JComboBox<FuelType> fuelType = new JComboBox<>(new DefaultComboBoxModel<>(FuelType.values()));

        Object[] message = {
                text.getString("licencePlate"), licensePlate,
                text.getString("brand"), brand,
                text.getString("fuelType"), fuelType
        };
        int option = JOptionPane.showConfirmDialog(null, message,
                text.getString("insertCar"), JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if ((brand.getText().length() == 0) || (licensePlate.getText().length() == 0)) {
                lastCommandComponent.setText(text.getString("insertCarNok"));
                return;
            }
            Auto car = new Auto(licensePlate.getText(), brand.getText(), (FuelType) fuelType.getSelectedItem());
            cars.create(car);
            insertAutoToTree(model, car);
            tree.setModel(new CarTreeModel(cars));
            lastCommandComponent.setText(text.getString("insertCarOk"));
        }
    }

    public void setLanguage(Locale locale) {
        this.text = ResourceBundle.getBundle(IRDItemLanguage.PATH, locale);
        UIManager.put("OptionPane.cancelButtonText", text.getString("cancel"));
    }

    /**
     * Inserts car to Car Tree Model
     * @param model Car Tree Model
     * @param car to insert
     */
    private static void insertAutoToTree(DefaultTreeModel model, Auto car) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(car);
        newNode.add(new DefaultMutableTreeNode(car.getBrand()));
        newNode.add(new DefaultMutableTreeNode(car.getFuelType()));
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.add(newNode);
    }
}
