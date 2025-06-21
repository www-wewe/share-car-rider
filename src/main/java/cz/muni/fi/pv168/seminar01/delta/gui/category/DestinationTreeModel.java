package cz.muni.fi.pv168.seminar01.delta.gui.category;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.DestinationRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.model.Destination;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Class representing Tree Model of Destinations
 * @author Veronika Lenkova
 */
public class DestinationTreeModel extends DefaultTreeModel {

    DefaultMutableTreeNode root;
    private final DestinationRepository destinations;

    public DestinationTreeModel(Repository<Destination> destinations) {
        super(new DefaultMutableTreeNode(new Destination("Všechny destinace")));
        this.destinations = (DestinationRepository) destinations;
        this.root = (DefaultMutableTreeNode) getRoot();
        initDestinationsTree();
    }

    private void initDestinationsTree() {
        for (Destination destination : destinations.findAll())
            root.add(new DefaultMutableTreeNode(destination));
    }

    public List<Destination> getAllDestinations() {
        List<Destination> destinations = new ArrayList<>();
        Enumeration<TreeNode> children = root.children();
        while(children.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
            destinations.add((Destination) node.getUserObject());
        }
        return destinations;
    }
}
