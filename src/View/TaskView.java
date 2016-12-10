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
 *  Classe vue des tâches, gérant le rendu visuel à l'affichage d'une tâche.
 *  organisation : - Un TitlesPanel(NORTH) contenant nameLabel et categoryLabel
 *                 - Un controlPanel(SOUTH) contenant les boutons
 *                 - Un centerPanel(CENTER) contenant remainingLabel et progressLabel
 *                 - StartDateLabel(WEST) et endDateLabel(EAST) sont directement ajouter a la vue
 */
public class TaskView extends ObserverPanel{

    protected TaskController taskController;
    protected JLabel nameLabel, categoryLabel, startDateLabel, endDateLabel, progressLabel, remainingLabel ;
    protected JButton removeButton, editButton, moveButton;
    protected JPanel controlPanel, titlesPanel, centerPanel;

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

    /**
     * Constructeur de TaskView.
     * Le controller passé en paramètre permet la mise en place des listener nécessaire a la view
     * @param tc Controller de la view
     */
    public TaskView(TaskController tc){
        setLayout(new BorderLayout(5, 5));
        setMinimumSize(new Dimension(200, 300));


        taskController = tc;

        nameLabel = new JLabel("",JLabel.CENTER);
        categoryLabel = new JLabel("",JLabel.CENTER);
        endDateLabel = new JLabel("", JLabel.RIGHT);
        startDateLabel = new JLabel("", JLabel.LEFT);
        progressLabel = new JLabel("%", JLabel.CENTER);
        remainingLabel = new JLabel("", JLabel.CENTER);

        editButton = new JButton("Modifier");
        removeButton = new JButton("Supprimer");
        moveButton = new JButton("Déplacer");

        controlPanel = new JPanel();
        titlesPanel = new JPanel();
        centerPanel  = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));



        titlesPanel.add(nameLabel);
        titlesPanel.add(categoryLabel);
        add(endDateLabel, BorderLayout.EAST);
        add(startDateLabel, BorderLayout.WEST);

        centerPanel.add(remainingLabel);
        centerPanel.add(progressLabel);

        controlPanel.add(editButton);
        controlPanel.add(moveButton);
        controlPanel.add(removeButton);

        add(controlPanel, BorderLayout.SOUTH);
        add(titlesPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        taskController.setListeners(this);

    }


    /**
     * Met à jour la vue avec les nouvelles données contenu dans la tâche
     * @param o objet mis à jour (une tâche ici)
     * @param arg
     */
    public void update(Observable o, Object arg) {
        if (o instanceof Task){
            Task t = (Task)o;
            categoryLabel.setText("["+t.findContainer().getName()+"]");
            nameLabel.setText(t.getName());
            endDateLabel.setText(t.getEcheance().toString());
            progressLabel.setText(t.getProgress()+" % effectués");
            remainingLabel.setText(t.getTimeLeftMessage());

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
