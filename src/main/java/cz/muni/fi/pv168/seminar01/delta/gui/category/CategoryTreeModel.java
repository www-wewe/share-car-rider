package cz.muni.fi.pv168.seminar01.delta.gui.category;

import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.CategoryRepository;
import cz.muni.fi.pv168.seminar01.delta.data.storage.repository.Repository;
import cz.muni.fi.pv168.seminar01.delta.model.Category;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.*;

/**
 * Class representing Tree Model of Categories
 * @author Veronika Lenkova
 */
public class CategoryTreeModel extends DefaultTreeModel {

    private final DefaultMutableTreeNode root;
    private final CategoryRepository categories;
    public CategoryTreeModel(Repository<Category> categories) {
        super(new DefaultMutableTreeNode(new Category("Všechny kategorie")));
        this.categories = (CategoryRepository) categories;
        this.root = (DefaultMutableTreeNode) getRoot();
        initCategoryTree();
    }

    private void initCategoryTree() {
        List<Category> categoryList = categories.findAll();
        List<DefaultMutableTreeNode> nodeList = categoryList.stream().map(DefaultMutableTreeNode::new).toList();

        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getParentId() != 0) {
                int index = getParentIndex(categoryList, i);
                DefaultMutableTreeNode parentNode = nodeList.get(index);
                parentNode.add(nodeList.get(i));
            }
            else {
                root.add(nodeList.get(i));
            }
        }
    }

    /**
     * Returns parent index of child in categoryList
     * @param categoryList list of all categories
     * @param child index of child in categoryList
     * @return index of parent in categoryList
     */
    private int getParentIndex(List<Category> categoryList, int child) {
        int index = -1;
        Long parentId = categoryList.get(child).getParentId();
        for (int j = 0; j < categoryList.size(); j++) {
            if (categoryList.get(j).getId() == parentId) {
                index = j;
                break;
            }
        }
        return index;
    }

    /**
     * @return list of all categories
     */
    public List<Category> getAllCategories() {
        return categories.findAll();
    }

}
