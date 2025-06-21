package cz.muni.fi.pv168.seminar01.delta.gui;

import cz.muni.fi.pv168.seminar01.delta.data.manipulation.exporter.DataType;
import cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer.AsyncImporter;
import cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer.CategoryImporter;
import cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer.ImporterException;
import cz.muni.fi.pv168.seminar01.delta.data.manipulation.importer.RideImporter;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.CategoryRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.DestinationRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.error.ParseError;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.components.TableComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.language.ImportExportLanguage;
import cz.muni.fi.pv168.seminar01.delta.gui.language.MainWindowLanguage;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Import data window.
 * Contains all Swing objects and working logic.
 *
 * @author Jiří Žák
 */
public class ImportData extends JDialog {
    private final ResourceBundle text;
    private JPanel contentPane;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField importPathTextField;
    private JButton browseButton;
    private JComboBox<DataType> importComboBox;
    private JLabel mainMessage;
    private final LastCommandComponent lastCommandComponent;
    private final TableComponent tableComponent;
    private final DestinationRepository destinationRepository;
    private final CategoryRepository categoryRepository;
    private final Repository<Auto> autoRepository;

    public ImportData(TableComponent tableComponent, LastCommandComponent lastCommand, Repository<Destination> destinationRepository, Repository<Category> categoryRepository, Repository<Auto> autoRepository, Locale locale) {
        this.text = ResourceBundle.getBundle(ImportExportLanguage.PATH, locale);
        this.lastCommandComponent = lastCommand;
        this.tableComponent = tableComponent;
        this.destinationRepository = (DestinationRepository) destinationRepository;
        this.categoryRepository = (CategoryRepository) categoryRepository;
        this.autoRepository = autoRepository;
        this.setTitle(text.getString("title"));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(okButton);

        importPathTextField.setEditable(false);
        okButton.addActionListener(this::onOk);
        cancelButton.addActionListener(this::onCancel);
        browseButton.addActionListener(this::onBrowse);
        importComboBox.addItem(DataType.RIDES);
        importComboBox.addItem(DataType.CATEGORIES);

        setText();
    }

    private void onOk(ActionEvent e) {
        DataType dataType = (DataType) importComboBox.getSelectedItem();
        Path path = Paths.get(importPathTextField.getText());
        try {
            if (dataType == DataType.RIDES) {
                AsyncImporter<Ride> asyncImporter = new AsyncImporter<>(new RideImporter(), this::processRidesAfterImport);
                asyncImporter.importData(path);
                lastCommandComponent.setText(text.getString("lastCommandImportOk"));
            } else {
                AsyncImporter<Category> asyncImporter = new AsyncImporter<>(new CategoryImporter(), this::processCategoriesAfterImport);
                asyncImporter.importData(path);
                lastCommandComponent.setText(text.getString("lastCommandImportCategoriesOk"));
            }
        } catch (ParseError parseError) {
            lastCommandComponent.setText(parseError.getMessage());
        } catch (ImporterException ioException) {
            lastCommandComponent.setText(text.getString("lastCommandImportNok"));
        }
        dispose();
    }

    private Void processRidesAfterImport(Collection<Ride> importedRides){
        Collection<Ride> rides = checkRides(importedRides);
        rides.forEach(tableComponent::add);
        tableComponent.refreshTableData();
        return null;
    }

    private Void processCategoriesAfterImport(Collection<Category> categories){
        checkCategories(categories);
        categories.forEach(categoryRepository::create);
        return null;
    }
    private void onCancel(ActionEvent e) {
        lastCommandComponent.setText(text.getString("lastCommandCancel"));
        dispose();
    }

    private void onBrowse(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser(".");
        int response = fileChooser.showOpenDialog(null);
        if (response == JFileChooser.APPROVE_OPTION) {
            importPathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    private Collection<Ride> checkRides(Collection<Ride> importedRides) throws ParseError {
        List<Ride> rides = new ArrayList<>();
        for (Ride ride : importedRides) {
            Destination from = destinationRepository.findByName(ride.getFrom().getName());
            if (from == null)
                throw new ParseError(String.format(text.getString("invalidDestination"), ride.getFrom().getName()));
            Destination to = destinationRepository.findByName(ride.getTo().getName());
            if (to == null)
                throw new ParseError(String.format(text.getString("invalidDestination"), ride.getTo().getName()));
            List<Category> categories = new ArrayList<>();
            for (Category category : ride.getCategories()) {
                Category foundCategory = categoryRepository.findByName(category.getName());
                if (foundCategory == null)
                    throw new ParseError(String.format(text.getString("invalidCategory"), category.getName()));
                categories.add(foundCategory);
            }
            Optional<Auto> car = autoRepository.findByPK(ride.getAuto().getId());
            if (car.isEmpty())
                throw new ParseError(text.getString("invalidCar"));
            rides.add(new Ride(
                    ride.getName(),
                    from,
                    to,
                    ride.getDate(),
                    ride.getPassengerCount(),
                    ride.getDistance(),
                    ride.getPrice(),
                    categories,
                    car.get()
            ));
        }
        return rides;
    }

    private void checkCategories(Collection<Category> categories){
        for (Category category : categories){
            Optional<Category> parentCategory = categoryRepository.findByPK(category.getParentId());
            if (parentCategory.isEmpty())
                category.setParent(null);
        }
    }

    private void setText() {
        cancelButton.setText(text.getString("cancel"));
        mainMessage.setText(text.getString("selectCSV"));
        browseButton.setText(text.getString("browse"));
    }
}
