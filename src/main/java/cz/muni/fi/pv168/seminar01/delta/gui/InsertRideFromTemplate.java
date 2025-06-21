package cz.muni.fi.pv168.seminar01.delta.gui;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.gui.category.CategoryTreeModel;
import cz.muni.fi.pv168.seminar01.delta.gui.components.LastCommandComponent;
import cz.muni.fi.pv168.seminar01.delta.gui.components.TableComponent;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;
import cz.muni.fi.pv168.seminar01.delta.model.Category;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;
import cz.muni.fi.pv168.seminar01.delta.model.Ride;

import java.util.Calendar;
import java.util.Locale;

/**
 * Class representing dialog window for insert Ride  to the table component by template (selected ride).
 *
 * @author Veronika Lenkova
 */
public class InsertRideFromTemplate extends InsertRide {
    Ride template;
    public InsertRideFromTemplate(TableComponent recordsTable, CategoryTreeModel categoryModel, LastCommandComponent lastCommand, Repository<Destination> destinations, Repository<Category> categories, Repository<Auto> autos, Locale locale) {
        super(recordsTable, categoryModel, lastCommand, destinations, categories, autos, locale);
        this.template = recordsTable.getSelectedRecord();
        setValues(template);
        setCurrentDate();
    }

    private void setCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        datePicker.getModel().setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

}
