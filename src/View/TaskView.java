package View;

import Controller.TaskController;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Theo on 07/12/2016.
 */
public class TaskView extends JPanel implements Observer{

    protected TaskController taskController;
    protected JLabel nameLabel, endDateLabel, progressLabel;
    protected JButton removeButton, editButton, moveButton;

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JLabel getEndDateLabel() {
        return endDateLabel;
    }

    public JLabel getProgressLabel() {
        return progressLabel;
    }


    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getMoveButton() {
        return moveButton;
    }

    public TaskView(TaskController tc){
        super(new BorderLayout(5, 5));

        taskController = tc;
        taskController.setListeners(this);

        setBorder(BorderFactory.createLineBorder(Color.black));

        nameLabel = new JLabel("",JLabel.CENTER);
        endDateLabel = new JLabel("", JLabel.RIGHT);
        progressLabel = new JLabel("%", JLabel.CENTER);

        editButton = new JButton("Mod");
        removeButton = new JButton("Sup");
        moveButton = new JButton("Dép");

        add(nameLabel, BorderLayout.NORTH);
        add(endDateLabel, BorderLayout.EAST);
        add(progressLabel, BorderLayout.SOUTH);
        add(editButton, BorderLayout.SOUTH);
        add(moveButton, BorderLayout.SOUTH);
        add(removeButton, BorderLayout.SOUTH);

    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Task){
            Task t = (Task)o;
            nameLabel.setText(t.getName());
            endDateLabel.setText(t.getEcheance().toString());
            progressLabel.setText(t.getProgress()+" %");
        }

    }
}
