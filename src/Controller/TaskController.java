package Controller;

import Model.Category;
import Model.Task;
import View.TaskPropertiesDialogPanel;
import View.TaskView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Classe de contrôle pour les tâches
 */
public class TaskController {
    private Task task;

    /**
     * Constructeur de TaskController
     * @param t La tâche à contrôler
     */
    public TaskController(Task t){
        //init listeners
        task = t;
    }

    /**
     * Prépare les listeners pour écouter les actions utilisateur de la vue
     * @param v La vue de la tâche sur laquel sera appliqué les listeners
     */
    public void setListeners(TaskView v) {
        JButton removeButton, editButton, moveButton;

        editButton = v.getEditButton();
        removeButton = v.getRemoveButton();
        moveButton = v.getMoveButton();

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onEditButtonPressed();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onRemoveButtonPressed();
                //v.remove(removeButton); // dafuq ?
            }
        });

        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onMoveButtonPressed();
            }
        });


    }

    /**
     * Méthode définissant le comportement du listener pour le editButton
     */
    private void onEditButtonPressed() {

        TaskPropertiesController tpc = new TaskPropertiesController(task);
        TaskPropertiesDialogPanel tpdp = new TaskPropertiesDialogPanel();

        tpc.setListeners(tpdp);

        tpdp.loadFrom(task);
        tpdp.setVisible(true);

    }

    /**
     * Méthode définissant le comportement du listener pour le MoveButton
     */
    private void onMoveButtonPressed(){
        ArrayList<Category> cats = Category.getCategories();
        String[] possibilities = new String[cats.size()];
        for (int i=0; i<cats.size(); i++){
            possibilities[i] = cats.get(i).getName();
        }

        String s = (String)
        JOptionPane.showInputDialog(
                task.getView(),
                "Vers quelle catégorie déplacer la tâche ?",
                "Changement de catégorie",
                JOptionPane.QUESTION_MESSAGE, //PLAIN_MESSAGE,
                null,
                possibilities,
                "Aucune"
        );
        Category destination = Category.findByName(s);
        if (!destination.equals(task.findContainer())){
            task.moveToCategory(destination);
        }


    }

    /**
     * Méthode définissant le comportement du listener pour le RemoveButton
     */
    private void onRemoveButtonPressed(){

        Object[] options = {"Confirmer", "Annuler"};
        int n = JOptionPane.showOptionDialog(task.getView(),
                "Confirmer la suppression de la tâche ?",
                "Suppression de tâche",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);

        if (n == 0){ //Suppression confirmée par l'utilisateur
            Category c = task.findContainer();
            task.eraseTask();
            c.update();
        }

    }
}
