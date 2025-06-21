package cz.muni.fi.pv168.seminar01.delta.gui.mainwindow;

import cz.muni.fi.pv168.seminar01.delta.gui.components.TableComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.language.DeleteRecordLanguage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class representing Delete Record action.
 *
 * @author Veronika Lenkova
 */
public class DeleteRideListener implements ActionListener {

    private final TableComponent tableComponent;
    private ResourceBundle text;

    public DeleteRideListener(TableComponent tableComponent, Locale locale) {
        this.text = ResourceBundle.getBundle(DeleteRecordLanguage.PATH, locale);
        this.tableComponent = tableComponent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (tableComponent.getSelectedRow() != -1) {
            String[] options = {text.getString("yes"), text.getString("no")};
            int selectedOption = JOptionPane.showOptionDialog(null,
                    text.getString("question"), text.getString("title"),
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (selectedOption == JOptionPane.YES_OPTION) {
                var record = tableComponent.getSelectedRow();
                tableComponent.delete(record);
                tableComponent.refreshTableData();
                JOptionPane.showMessageDialog(null, text.getString("message"));
            }
        }
    }

    void setLanguage(Locale locale) {
        text = ResourceBundle.getBundle(DeleteRecordLanguage.PATH, locale);
    }
}
