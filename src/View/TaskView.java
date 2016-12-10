package View;

import Controller.TaskController;
import Model.LongTask;
import Model.Task;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Theo on 07/12/2016.
 */
public class TaskView extends ObserverPanel{

    protected TaskController taskController;
    protected JLabel nameLabel, categoryLabel, startDateLabel, endDateLabel, progressLabel;
    protected JButton removeButton, editButton, moveButton;
    protected JPanel controlPanel, titlesPanel;

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
        setLayout(new BorderLayout(5, 5));
        setMinimumSize(new Dimension(200, 300));


        taskController = tc;

        nameLabel = new JLabel("",JLabel.CENTER);
        categoryLabel = new JLabel("",JLabel.CENTER);
        endDateLabel = new JLabel("", JLabel.RIGHT);
        startDateLabel = new JLabel("", JLabel.LEFT);
        progressLabel = new JLabel("%", JLabel.CENTER);

        editButton = new JButton("Modifier");
        removeButton = new JButton("Supprimer");
        moveButton = new JButton("Déplacer");

        controlPanel = new JPanel();
        titlesPanel = new JPanel();
        titlesPanel.setLayout(new BoxLayout(titlesPanel, BoxLayout.Y_AXIS));



        titlesPanel.add(nameLabel);
        titlesPanel.add(categoryLabel);
        add(endDateLabel, BorderLayout.EAST);
        add(startDateLabel, BorderLayout.WEST);
        add(progressLabel, BorderLayout.SOUTH);

        controlPanel.add(editButton);
        controlPanel.add(moveButton);
        controlPanel.add(removeButton);

        add(controlPanel, BorderLayout.SOUTH);
        add(titlesPanel, BorderLayout.NORTH);

        taskController.setListeners(this);

    }



    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Task){
            Task t = (Task)o;
            categoryLabel.setText("["+t.findContainer().getName()+"]");
            nameLabel.setText(t.getName());
            endDateLabel.setText(t.getEcheance().toString());
            progressLabel.setText(t.getProgress()+" %");

            if (t instanceof LongTask){
                startDateLabel.setText(((LongTask) t).getStartDate().toString());
            }

            setBorderStyle(t);
        }
        revalidate();

    }

    /**
     * Met à jour la bordure de la TaskView
     * La bordure prend un marquage rouge si la tâche est en retard
     * @param t la tâche modèle
     */
    private void setBorderStyle(Task t) {
        Border border;
        if (t.isLate()){
            border = BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(255, 0, 0, 192));
        }else {
            border = BorderFactory.createLineBorder(Color.black);
        }
        setBorder(border);
    }
}
