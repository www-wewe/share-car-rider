package cz.muni.fi.pv168.seminar01.delta.gui.category;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.AutoRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.model.Auto;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Class representing Tree Model of Cars
 * @author Veronika Lenkova
 */
public class CarTreeModel extends DefaultTreeModel {
    private final DefaultMutableTreeNode root;
    private final AutoRepository cars;

    public CarTreeModel(Repository<Auto> cars) {
        super(new DefaultMutableTreeNode("Všechny auta"));
        this.cars = (AutoRepository) cars;
        this.root = (DefaultMutableTreeNode) getRoot();
        initCarsTree();
    }

    private void initCarsTree() {
        for (Auto car : cars.findAll()) {
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(car);
            root.add(newNode);
            newNode.add(new DefaultMutableTreeNode(car.getBrand()));
            newNode.add(new DefaultMutableTreeNode(car.getFuelType()));
        }
    }

}
