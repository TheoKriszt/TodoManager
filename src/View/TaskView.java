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

    private TaskController taskController;
    private JLabel nameLabel, endDateLabel, progressLabel;
    private JButton removeButton, editButton, moveButton;

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public JLabel getEndDateLabel() {
        return endDateLabel;
    }

    public JLabel getProgressLabel() {
        return progressLabel;
    }



    public TaskView(TaskController tc){
        super(new BorderLayout(5, 5));
        taskController = tc;

        setBorder(BorderFactory.createLineBorder(Color.black));

        nameLabel = new JLabel("",JLabel.CENTER);
        endDateLabel = new JLabel("", JLabel.RIGHT);
        progressLabel = new JLabel("%", JLabel.CENTER);

        editButton = new JButton("Modifier");
        removeButton = new JButton("Supprimer");
        moveButton = new JButton("Déplacer");

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
