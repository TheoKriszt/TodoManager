package View;

import Model.Task;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Theo on 07/12/2016.
 */
public class TaskView extends JPanel {

    private Task task;
    private JLabel nameLabel, endDateLabel, progressLabel;

    public TaskView(Task t){
        super(new BorderLayout(5, 5));
        task = t;

        setBorder(BorderFactory.createLineBorder(Color.black));

        nameLabel = new JLabel(task.getName(),JLabel.CENTER);
        endDateLabel = new JLabel(task.getEcheance().toString(), JLabel.RIGHT);
        progressLabel = new JLabel(String.valueOf(task.getProgress()) + " %", JLabel.CENTER);

        add(nameLabel, BorderLayout.NORTH);
        add(endDateLabel, BorderLayout.EAST);
        add(progressLabel, BorderLayout.SOUTH);

    }
}
