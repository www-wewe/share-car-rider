package cz.muni.fi.pv168.seminar01.delta.gui;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.gui.category.CategoryTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.components.TableComponent;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import java.awt.event.ActionEvent;
import java.util.Locale;

/**
 * Class representing dialog window for updating selectedRecord to the table component.
 *
 * @author Jiri Zak
 */
public class UpdateRide extends InsertRide {
    private final Ride selectedRide;

    public UpdateRide(TableComponent recordsTable, CategoryTreeModel categoryModel, LastCommandComponent lastCommand,
                      Repository<Destination> destinations, Repository<Category> categories, Repository<Auto> autos, Locale locale) {
        super(recordsTable, categoryModel, lastCommand, destinations, categories, autos, locale);
        this.setTitle(text.getString("titleUpdate"));
        this.selectedRide = recordsTable.getSelectedRecord();
        setValues(selectedRide);
    }

    @Override
    protected void onOK(ActionEvent e) {
        var updatedRide = createRide(this.selectedRide.getId());
        if (updatedRide != null) {
            updateRideInDatabase(updatedRide);
            lastCommandComponent.setText(text.getString("lastCommandUpdateOk"));
        }
    }

    @Override
    protected void onCancel(ActionEvent e) {
        lastCommandComponent.setText(text.getString("lastCommandUpdateNok"));
        dispose();
    }

    private void updateRideInDatabase(Ride record) {
        ridesTable.update(record);
        ridesTable.refreshTableData();
        dispose();
    }
}
