package cz.muni.fi.pv168.seminar01.delta.gui.mainwindow;

import cz.muni.fi.pv168.seminar01.delta.gui.category.TreeModelType;
import cz.muni.fi.pv168.seminar01.delta.gui.language.TreeModelLanguage;

import javax.swing.*;
import javax.swing.tree.TreeModel;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class changes model of tree to Category, Car or Destination model
 */
public class TreeModelTypeListener extends AbstractAction {

    private final JTree categoriesTree;
    private final TreeModel categoriesModel;
    private final TreeModel carsModel;
    private final TreeModel destinationsModel;
    private ResourceBundle text;
    private Locale locale;
    private final JButton newTreeButton;
    private final JButton renameTreeButton;
    private final JButton deleteTreeButton;

    TreeModelTypeListener(JTree categoriesTree,
                          TreeModel categoriesModel, TreeModel carsModel, TreeModel destinationModel,
                          JButton newTreeButton, JButton renameTreeButton, JButton deleteTreeButton) {
        this.categoriesTree = categoriesTree;
        this.categoriesModel = categoriesModel;
        this.carsModel = carsModel;
        this.destinationsModel = destinationModel;
        this.locale = new Locale("");
        this.text = ResourceBundle.getBundle(TreeModelLanguage.PATH, locale);
        this.newTreeButton = newTreeButton;
        this.renameTreeButton = renameTreeButton;
        this.deleteTreeButton = deleteTreeButton;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<TreeModelType> treeModelTypeComboBox = (JComboBox) e.getSource();
        TreeModelType selected = (TreeModelType) treeModelTypeComboBox.getSelectedItem();
        switch (Objects.requireNonNull(selected)) {
            case DESTINATION -> {
                categoriesTree.setModel(destinationsModel);
                renameTreeButton.setText(text.getString("renameDestination"));
                newTreeButton.setText(text.getString("newDestination"));
                deleteTreeButton.setText(text.getString("deleteDestination"));
                renameTreeButton.setEnabled(false);
                deleteTreeButton.setEnabled(false);
            }
            case CAR -> {
                categoriesTree.setModel(carsModel);
                renameTreeButton.setText(text.getString("modifyCar"));
                newTreeButton.setText(text.getString("newCar"));
                deleteTreeButton.setText(text.getString("deleteCar"));
                renameTreeButton.setEnabled(false);
                deleteTreeButton.setEnabled(false);
            }
            default -> {
                categoriesTree.setModel(categoriesModel);
                renameTreeButton.setText(text.getString("renameCategory"));
                newTreeButton.setText(text.getString("newCategory"));
                deleteTreeButton.setText(text.getString("deleteCategory"));
                renameTreeButton.setEnabled(false);
                deleteTreeButton.setEnabled(false);
            }
        }
    }

    public void setLanguage(Locale locale, JComboBox<TreeModelType> cb) {
        this.text = ResourceBundle.getBundle(TreeModelLanguage.PATH, locale);
        cb.setSelectedIndex(cb.getSelectedIndex());
    }
}
