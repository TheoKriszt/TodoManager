package View;

import Controller.CategoryController;
import Model.Category;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Theo on 08/12/2016.
 */
public class CategoryView extends ObserverPanel{



    private JPanel tasksPanel, controlPanel;
    private JLabel nameLabel;
    private JButton editButton, removeButton;
    private CategoryController categoryController;

    public CategoryView(CategoryController cc){
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createLineBorder(Color.blue));

        categoryController = cc;

        nameLabel = new JLabel("", JLabel.CENTER);
        tasksPanel = new JPanel();
        tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
        controlPanel = new JPanel();

        editButton = new JButton("Edit");
        removeButton = new JButton("Supprimer");

        controlPanel.add(editButton);
        controlPanel.add(removeButton);

        add(tasksPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.CENTER);
        add(nameLabel, BorderLayout.NORTH);

        categoryController.setListeners(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        nameLabel.setText(((Category)o).getName());
        tasksPanel.removeAll(); //wipe old content
        ArrayList<Task> tasks = ((Category)o).getTasks();
        Task.sortByDueDate(tasks);
        for (Task t : tasks){
            if (t.getDoneDate() == null){
                t.update();
                tasksPanel.add(t.getView());
            }

        }

        revalidate();
        repaint();
    }

    public JPanel getTasksPanel() {
        return tasksPanel;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }
}
