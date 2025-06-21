package cz.muni.fi.pv168.seminar01.delta.gui.mainwindow;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.AutoRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.CategoryRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.DestinationRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.gui.category.CarTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.category.CategoryTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.category.DestinationTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.components.TableComponent;
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
 * Class representing Rename item action.
 *
 * @author Veronika Lenkova
 */
public class RenameItemListener extends AbstractAction {
    private final JTree tree;
    private final TableComponent tableComponent;
    private final LastCommandComponent lastCommandComponent;
    private final CategoryRepository categories;
    private final AutoRepository cars;
    private final DestinationRepository destinations;
    private ResourceBundle text;

    public RenameItemListener(JTree tree, TableComponent tableComponent, LastCommandComponent lastCommandComponent, Repository<Category> categories, Repository<Auto> cars, Repository<Destination> destinations, Locale locale) {
        super("Přejmenovat");
        this.text = ResourceBundle.getBundle(IRDItemLanguage.PATH, locale);
        this.tree = tree;
        this.tableComponent = tableComponent;
        this.lastCommandComponent = lastCommandComponent;
        this.categories = (CategoryRepository) categories;
        this.cars = (AutoRepository) cars;
        this.destinations = (DestinationRepository) destinations;
        UIManager.put("OptionPane.cancelButtonText", text.getString("cancel"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreePath currentSelection = tree.getSelectionPath();
        assert currentSelection != null;
        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        if (currentNode.getParent() == null) {
            return;
        }
        if (((DefaultMutableTreeNode) model.getRoot()).getUserObject().toString().equals("Všechny kategorie")) {
            renameCategory(currentNode);
        } else if (((DefaultMutableTreeNode) model.getRoot()).getUserObject().toString().equals("Všechny auta")) {
            renameAuto(currentNode);
        } else if (((DefaultMutableTreeNode) model.getRoot()).getUserObject().toString().equals("Všechny destinace")) {
            renameDestination(currentNode);
        }
        model.nodeChanged(currentNode);
    }

    /**
     * Renames selected category
     * @param currentNode selected category
     */
    private void renameCategory(DefaultMutableTreeNode currentNode) {
        String newName = JOptionPane.showInputDialog(null,
                text.getString("categoryRename?"),
                text.getString("categoryRename"),
                JOptionPane.QUESTION_MESSAGE);
        if ((newName != null) && (newName.length() > 0)) {
            Category category = (Category) currentNode.getUserObject();
            category.setName(newName);
            categories.update(category);
            tree.setModel(new CategoryTreeModel(categories));
            tableComponent.refreshTableDataWithFetch();
            lastCommandComponent.setText(text.getString("categoryRenameOk"));
        } else {
            lastCommandComponent.setText(text.getString("categoryRenameNok"));
        }
    }

    /**
     * Renames selected destination
     * @param currentNode selected destination
     */
    private void renameDestination(DefaultMutableTreeNode currentNode) {
        String newName = JOptionPane.showInputDialog(null,
                text.getString("destinationRename?"),
                text.getString("destinationRename"),
                JOptionPane.QUESTION_MESSAGE);
        if ((newName != null) && (newName.length() > 0)) {
            Destination destination = (Destination) currentNode.getUserObject();
            destination.setName(newName);
            destinations.update(destination);
            tree.setModel(new DestinationTreeModel(destinations));
            tableComponent.refreshTableDataWithFetch();
            lastCommandComponent.setText(text.getString("destinationRenameOk"));
        } else {
            lastCommandComponent.setText(text.getString("destinationRenameNok"));
        }
    }

    public void setLanguage(Locale locale) {
        this.text = ResourceBundle.getBundle(IRDItemLanguage.PATH, locale);
        UIManager.put("OptionPane.cancelButtonText", text.getString("cancel"));
    }

    /**
     * Renames selected car
     * @param currentNode selected node in tree
     */
    private void renameAuto(DefaultMutableTreeNode currentNode) {
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) currentNode.getParent();
        DefaultMutableTreeNode carNode = currentNode.isLeaf() ? parent : currentNode;
        Auto car = (Auto) carNode.getUserObject();

        JTextField licensePlate = new JTextField();
        JTextField brand = new JTextField();
        JComboBox<FuelType> fuelType = new JComboBox<>(new DefaultComboBoxModel<>(FuelType.values()));
        licensePlate.setText(car.getLicensePlate());
        brand.setText(car.getBrand());
        fuelType.setSelectedItem(car.getFuelType());

        Object[] message = {
                text.getString("licencePlate"), licensePlate,
                text.getString("brand"), brand,
                text.getString("fuelType"), fuelType
        };
        int option = JOptionPane.showConfirmDialog(null, message,
                text.getString("renameCar"), JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if ((brand.getText().length() == 0) || (licensePlate.getText().length() == 0)) {
                lastCommandComponent.setText(text.getString("renameCarNok"));
                return;
            }
            car.setLicensePlate(licensePlate.getText());
            car.setBrand(brand.getText());
            car.setFuelType((FuelType) fuelType.getSelectedItem());
            updateCarInTree(carNode, car);
            cars.update(car);
            tree.setModel(new CarTreeModel(cars));
            tableComponent.refreshTableDataWithFetch();
            lastCommandComponent.setText(text.getString("renameCarOk"));
        } else {
            lastCommandComponent.setText(text.getString("renameCarNok"));
        }
    }

    /**
     * Updates renamed car in Car Tree Model
     * @param currentNode selected Node
     * @param car renamed
     */
    private static void updateCarInTree(DefaultMutableTreeNode currentNode, Auto car) {
        currentNode.removeAllChildren();
        currentNode.add(new DefaultMutableTreeNode(car.getBrand()));
        currentNode.add(new DefaultMutableTreeNode(car.getFuelType()));
    }
}
