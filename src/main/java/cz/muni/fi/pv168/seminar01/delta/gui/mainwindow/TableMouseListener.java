package cz.muni.fi.pv168.seminar01.delta.gui.mainwindow;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.gui.InsertRideFromTemplate;
import cz.muni.fi.pv168.seminar01.delta.gui.category.CategoryTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.components.PopupMenuInitializer;
import cz.muni.fi.pv168.seminar01.delta.gui.components.TableComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.language.InsertRecordLanguage;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Class representing using PopupMenu in Table Component to select ride as template
 *
 * @author Andrej Gafrik, Veronika Lenkova
 */
public class TableMouseListener implements MouseListener {
    private JPopupMenu popupMenu;
    private final TableComponent recordsTable;
    private final LastCommandComponent lastCommand;
    CategoryTreeModel categoryModel;
    Repository<Destination> destinations;
    Repository<Category> categories;
    Repository<Auto> autos;
    Locale locale;
    private ResourceBundle text;


    public TableMouseListener(TableComponent recordsTable, CategoryTreeModel categoryModel, LastCommandComponent lastCommand,
                              Repository<Destination> destinations, Repository<Category> categories, Repository<Auto> autos, Locale locale) {
        this.locale = locale;
        this.text = setText(locale);
        this.recordsTable = recordsTable;
        this.categoryModel = categoryModel;
        this.popupMenu = PopupMenuInitializer.initializePopupMenu(popupMenuOptions());
        this.lastCommand = lastCommand;
        this.destinations = destinations;
        this.categories = categories;
        this.autos = autos;
    }

    private Map<String, ActionListener> popupMenuOptions() {
        var popupMenuOptions = new HashMap<String, ActionListener>();
        popupMenuOptions.put(text.getString("useAsTemplate"),  e -> {
            InsertRideFromTemplate frame = new InsertRideFromTemplate(recordsTable, categoryModel, lastCommand,
                    destinations, categories, autos, locale);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
        return popupMenuOptions;
    }

    public void setLanguage(Locale locale) {
        this.locale = locale;
        this.text = setText(locale);
        this.popupMenu = PopupMenuInitializer.initializePopupMenu(popupMenuOptions());
    }

    private ResourceBundle setText(Locale locale) {
        return ResourceBundle.getBundle(InsertRecordLanguage.PATH, locale);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            if (recordsTable.getSelectedRecord()!= null) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
