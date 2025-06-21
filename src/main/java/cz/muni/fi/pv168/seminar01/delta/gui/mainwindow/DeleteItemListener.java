package cz.muni.fi.pv168.seminar01.delta.gui.mainwindow;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.AutoRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.CategoryRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.DestinationRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.components.TableComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.language.IRDItemLanguage;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class representing Delete item action.
 *
 * @author Veronika Lenkova
 */
public class DeleteItemListener extends AbstractAction {

    private final JTree tree;
    private final TableComponent tableComponent;
    private final LastCommandComponent lastCommandComponent;
    private final CategoryRepository categories;
    private final AutoRepository cars;
    private final DestinationRepository destinations;
    private ResourceBundle text;

    public DeleteItemListener(JTree tree, TableComponent tableComponent, LastCommandComponent lastCommandComponent, Repository<Category> categories, Repository<Auto> cars, Repository<Destination> destinations, Locale locale) {
        super("Smazat");
        text = ResourceBundle.getBundle(IRDItemLanguage.PATH, locale);
        this.tree = tree;
        this.tableComponent = tableComponent;
        this.lastCommandComponent = lastCommandComponent;
        this.categories = (CategoryRepository) categories;
        this.cars = (AutoRepository) cars;
        this.destinations = (DestinationRepository) destinations;
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
            deleteCategory(model, currentNode);
        } else if (((DefaultMutableTreeNode) model.getRoot()).getUserObject().toString().equals("Všechny auta")) {
            deleteAuto(model, currentNode);
        } else if (((DefaultMutableTreeNode) model.getRoot()).getUserObject().toString().equals("Všechny destinace")) {
            deleteDestination(model, currentNode);
        }
        model.reload();
    }

    /**
     * Deletes selected category
     * @param model Category Tree Model
     * @param currentNode selected category
     */
    private void deleteCategory(DefaultTreeModel model, DefaultMutableTreeNode currentNode) {
        String[] options = {text.getString("yes"), text.getString("no")};
        int selectedOption = JOptionPane.showOptionDialog(null,
                text.getString("deleteCategory?"), text.getString("deleteCategoryTitle"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selectedOption == JOptionPane.YES_OPTION) {
            Category category = (Category) currentNode.getUserObject();
            categories.deleteById(category.getId());
            tableComponent.refreshTableDataWithFetch();
            model.removeNodeFromParent(currentNode);
            lastCommandComponent.setText(text.getString("deleteCategoryOk"));
            JOptionPane.showMessageDialog(null, text.getString("deleteCategoryMessage"));
        } else {
            lastCommandComponent.setText(text.getString("deleteCategoryNok"));
        }
    }

    /**
     * Deletes selected car
     * @param model Car Tree Model
     * @param currentNode selected car
     */
    private void deleteAuto(DefaultTreeModel model, DefaultMutableTreeNode currentNode) {
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) currentNode.getParent();
        String[] options = {text.getString("yes"), text.getString("no")};
        int selectedOption = JOptionPane.showOptionDialog(null,
                text.getString("deleteCar?"), text.getString("deleteCarTitle"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selectedOption == JOptionPane.YES_OPTION) {
            Auto car = currentNode.isLeaf() ? (Auto) parent.getUserObject() : (Auto) currentNode.getUserObject();
            cars.deleteById(car.getId());
            model.removeNodeFromParent(currentNode.isLeaf() ? parent : currentNode);
            tableComponent.refreshTableDataWithFetch();
            lastCommandComponent.setText(text.getString("deleteCarOk"));
            JOptionPane.showMessageDialog(null, text.getString("deleteCarMessage"));
        } else {
            lastCommandComponent.setText(text.getString("deleteCarNok"));
        }
    }

    /**
     * Deletes selected destination
     * @param model Destination Tree Model
     * @param currentNode selected destination
     */
    private void deleteDestination(DefaultTreeModel model, DefaultMutableTreeNode currentNode) {
        String[] options = {text.getString("yes"), text.getString("no")};
        int selectedOption = JOptionPane.showOptionDialog(null,
                text.getString("deleteDestination?"), text.getString("deleteDestinationTitle"),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (selectedOption == JOptionPane.YES_OPTION) {
            Destination destination = (Destination) currentNode.getUserObject();
            destinations.deleteById(destination.getId());
            model.removeNodeFromParent(currentNode);
            tableComponent.refreshTableDataWithFetch();
            lastCommandComponent.setText(text.getString("deleteDestinationOk"));
            JOptionPane.showMessageDialog(null, text.getString("deleteDestinationMessage"));
        } else {
            lastCommandComponent.setText(text.getString("deleteDestinationNok"));
        }
    }

    public void setLanguage(Locale locale) {
        this.text = ResourceBundle.getBundle(IRDItemLanguage.PATH, locale);
    }
}
