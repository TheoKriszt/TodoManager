package Controller;

import Model.Category;
import Model.Task;
import Model.TodoManager;
import View.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Classe de contrôle pour la fenêtre principale
 * @see MainFrame
 */
public class MainFrameController {

    private TodoManager todoManager;

    /**
     * Constructeur du controller
     * @param tm instance de TodoManager
     */
    public MainFrameController(TodoManager tm){
        todoManager = tm;
    }

    /**
     * Définie les différents listeners nécessaire au composants de la Frame il contient :
     *          - le listener pour quitter l'application depuis le menu
     *          - le listener pour créer une nouvelle catégorie depuis le menu
     *          - le listener pour créer une nouvelle tâche depuis le menu
     *
     * @param mf fenêtre principale
     */
    public void setListeners(MainFrame mf) {
        todoManager.setFrame(mf);

        //Ajouter des garde-fous à la fermeture du programme : sauvegarder la todoList au préalable
        mf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                todoManager.close();
            }
        });

        mf.getMenuItemQuitter().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                todoManager.close();

            }
        });

        mf.getMenuItemNewCategory().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Category c;
                CategoryController cc;
                CategoryView cv;

                String s = (String) JOptionPane.showInputDialog(
                        null,
                        "Donner un nom à la nouvelle catégorie",
                        "Nouvelle catégorie",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        "Nouvelle catégorie");

                try {
                    c = new Category(s);
                    cc = new CategoryController(c);
                    cv = new CategoryView(cc);
                    c.setView(cv);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la création de la catégorie: " + ex.getMessage(), "Erreur de création", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mf.getMenuItemNewTask().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Task t = null;
                TaskPropertiesController taskPropertiesController = new TaskPropertiesController(null);
                TaskPropertiesDialogPanel tpdp = new TaskPropertiesDialogPanel();
                taskPropertiesController.setListeners(tpdp);

                tpdp.loadFrom(t);
                tpdp.setVisible(true);


                /*
                String s = (String) JOptionPane.showInputDialog(
                        null,
                        "Donner un nom à la nouvelle tâche",
                        "Nouvelle tâche",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        "Nouvelle tâche");

                /*
                try {
                    t = new Task(s);
                    tc = new TaskController(t);
                    tv = new TaskView(tc);
                    t.setView(tv);
                    mf.update(null, null);

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la création de la tâche: " + ex.getMessage(), "Erreur de création", JOptionPane.ERROR_MESSAGE);
                }
                */
            }
        });

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println("Calling global update");
                ((ObserverPanel)sourceTabbedPane.getComponentAt(index)).update(null, null);

            }
        };

        mf.getTabbedPane().addChangeListener(changeListener);
    }

}
