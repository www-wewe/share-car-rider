package cz.muni.fi.pv168.seminar01.delta.gui;

import cz.muni.fi.pv168.seminar01.delta.gui.category.CategoryTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.components.DialogOpener;
import cz.muni.fi.pv168.seminar01.delta.gui.language.InsertRecordLanguage;
import cz.muni.fi.pv168.seminar01.delta.model.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class representing window in which you can manage categories
 *
 * @author Andrej Gafrik, Veronika Lenkova
 */
public class ManageCategoriesDialog extends JDialog implements ActionListener {
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane checkBoxArea;
    private JLabel recordName;
    private JPanel checkBoxPanel;
    private JPanel contentPane;
    CategoryTreeModel categoryModel;
    List<JCheckBox> categoryCB = new ArrayList<>();
    List<String> checkedCategories = new ArrayList<>();

    public ManageCategoriesDialog(CategoryTreeModel categoryModel, Locale locale) {
        ResourceBundle text = ResourceBundle.getBundle(InsertRecordLanguage.PATH, locale);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setTitle(text.getString("categoryManagement"));
        this.recordName.setText(text.getString("categoryManagement") + " :");
        this.categoryModel = categoryModel;

        initCategoryCB();
        checkBoxArea.getViewport().add(checkBoxPanel);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
    }

    private void initCategoryCB() {
        List<Category> categories = categoryModel.getAllCategories();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.LINE_START;

        for (int i = 0; i < categories.size(); i++) {
            categoryCB.add(new JCheckBox(categories.get(i).toString()));
            gbc.gridy = i;
            checkBoxPanel.add(categoryCB.get(categoryCB.size() - 1), gbc);
        }
    }
    private void onOK() {
        checkedCategories = new ArrayList<>();
        for (JCheckBox checkBox: categoryCB) {
            if (checkBox.isSelected()) {
                checkedCategories.add(checkBox.getText());
            }
        }
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DialogOpener.openDialog(this);
    }

    public List<String> getCheckedCategories() {
        return checkedCategories;
    }

    public void setCheckedCategories(List<String> checkedCategories) {
        this.checkedCategories = checkedCategories;
        for (JCheckBox checkBox: categoryCB) {
            checkBox.setSelected(checkedCategories.contains(checkBox.getText()));
        }
    }
}
