package cz.muni.fi.pv168.seminar01.delta.gui;

import cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter.*;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.language.ImportExportLanguage;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Export data window.
 * Contains all Swing objects and working logic.
 *
 * @author Jiří Žák
 */
public class ExportData extends JDialog {
    private final ResourceBundle text;
    private JPanel contentPane;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField exportPathTextField;
    private JButton browseButton;
    private JComboBox<DataType> exportComboBox;
    private JComboBox<FileFormat> fileTypeComboBox;
    private JLabel mainMessage;
    private final LastCommandComponent lastCommandComponent;
    private final List<Ride> rides;
    private final List<Category> categories;

    public ExportData(List<Ride> rides, List<Category> categories, LastCommandComponent lastCommand, Locale locale) {
        this.text = ResourceBundle.getBundle(ImportExportLanguage.PATH, locale);
        this.lastCommandComponent = lastCommand;
        this.rides = rides;
        this.categories = categories;
        this.setTitle("Export");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(okButton);
        initComponents();

        setText();
    }

    private void initComponents(){
        exportPathTextField.setEditable(false);
        okButton.addActionListener(this::onOK);
        cancelButton.addActionListener(this::onCancel);
        browseButton.addActionListener(this::onBrowse);
        exportComboBox.addItem(DataType.RIDES);
        exportComboBox.addItem(DataType.CATEGORIES);
        fileTypeComboBox.addItem(FileFormat.CSV);
        fileTypeComboBox.addItem(FileFormat.PDF);
    }

    private void onOK(ActionEvent e) {
        if (exportPathTextField.getText().isEmpty()){
            lastCommandComponent.setText(text.getString("lastCommandExportNokPath"));
            dispose();
            return;
        }
        DataType dataType = (DataType)exportComboBox.getSelectedItem();
        FileFormat fileType = (FileFormat)fileTypeComboBox.getSelectedItem();
        AsyncExporter exporter = ExporterFactory.createExporter(dataType, fileType);
        Path path = Paths.get(exportPathTextField.getText());
        try {
            if (dataType == DataType.RIDES) {
                exporter.export(rides, path);
                lastCommandComponent.setText(text.getString("lastCommandExportRideOk"));
            } else {
                exporter.export(categories, path);
                lastCommandComponent.setText(text.getString("lastCommandExportCategoryOk"));
            }
        } catch(ExporterException exception){
            lastCommandComponent.setText(text.getString("lastCommandExportNok"));
        }
        dispose();
    }

    private void onCancel(ActionEvent e) {
        lastCommandComponent.setText(text.getString("lastCommandExportNokCancel"));
        dispose();
    }

    private void onBrowse(ActionEvent e){
        JFileChooser fileChooser = new JFileChooser(".");
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            exportPathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private void setText() {
        cancelButton.setText(text.getString("cancel"));
        mainMessage.setText(text.getString("selectPath"));
        browseButton.setText(text.getString("browse"));
    }
}
