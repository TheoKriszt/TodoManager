package Controller;

import Model.Category;
import Model.Task;
import View.TaskView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Theo on 08/12/2016.
 */
public class TaskController {
    private Task task;

    public TaskController(Task t){
        //init listeners
        task = t;
    }

    /**
     * Prépare les listeners pour écouter les actions utilisateur de la vue
     * @param v
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
                v.remove(removeButton);
            }
        });

        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onMoveButtonPressed();
            }
        });


    }

    //Todo : Créer genre un popup pour chaque action
    //Todo : Le popup demandant respectivement de
    // - Entrer un texte et confirmer
    // - Choisir dans une drop down la destination et confirmer
    // - Confirmer la suppression
    // Les popups peuvent afficher des messages d'erreur ? Déléguer à d'autres popups ?

    private void onEditButtonPressed(){

    }

    private void onMoveButtonPressed(){
        System.out.println("Moving task " + task.getName());
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
        task.moveToCategory(destination);
    }

    private void onRemoveButtonPressed(){

    }
}
