package Controller;

import Model.Category;
import Model.Task;
import Model.TodoManager;
import View.CategoryView;
import View.MainFrame;
import View.ObserverPanel;
import View.TaskView;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Theo on 09/12/2016.
 */
public class MainFrameController {

    private TodoManager todoManager;

    public MainFrameController(TodoManager tm){
        todoManager = tm;
    }

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
                Task t;
                TaskController tc;
                TaskView tv;

                String s = (String) JOptionPane.showInputDialog(
                        null,
                        "Donner un nom à la nouvelle tâche",
                        "Nouvelle tâche",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        "Nouvelle tâche");

                try {
                    t = new Task(s);
                    tc = new TaskController(t);
                    tv = new TaskView(tc);
                    t.setView(tv);
                    mf.update(null, null); //todo : qu'est-ce qui oblige les onglets à reste des Observers ?

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la création de la tâche: " + ex.getMessage(), "Erreur de création", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
                System.out.println("new Tab is " + sourceTabbedPane.getComponentAt(index).getClass().getName());
                ((ObserverPanel)sourceTabbedPane.getComponentAt(index)).update(null, null);

            }
        };

        mf.getTabbedPane().addChangeListener(changeListener);
    }

}
