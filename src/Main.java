import Controller.CategoryController;
import Controller.TaskController;
import Model.*;
import View.CategoryView;
import View.MainFrame;
import View.TaskView;
import View.TestFrame;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        TodoManager tm = new TodoManager();
        MainFrame mainFrame = new MainFrame(tm);
        SaveFileManager sfm = SaveFileManager.O();
        sfm.setTodoManager(tm);

        //sfm.readFromFile();


        //TODO : jeu de test, à supprimer avant rendu prof

        Category c1 = new Category("Aucune");
        Category c2 = new Category("Perso");
        Category c3 = new Category("Travail");

        CategoryController cc1 = new CategoryController(c1);
        CategoryController cc2 = new CategoryController(c2);
        CategoryController cc3 = new CategoryController(c3);

        CategoryView cv1 = new CategoryView(cc1);
        CategoryView cv2 = new CategoryView(cc2);
        CategoryView cv3 = new CategoryView(cc3);

        c1.setView(cv1);
        c2.setView(cv2);
        c3.setView(cv3);

        System.err.println("CatViews Setted");

        Task t1, t2, t3;
        t1 = new Task("Tache 1");
        t2 = new Task("Tache 2");
        t3 = new Task("Tache 3");

        TaskController tc1 = new TaskController(t1),
                tc2 = new TaskController(t2),
                tc3 = new TaskController(t3);

        TaskView tv1 = new TaskView(tc1),
                tv2 = new TaskView(tc2),
                tv3 = new TaskView(tc3);

        t1.setView(tv1);
        t2.setView(tv2);
        t3.setView(tv3);

        Category.getAucune().notifyObservers(); //met à jour la vue

        /**
         * TODO :
         * Ajouter listeners sur les TaskView
         * Quand cliqué, ouvre une popup de Lecture/Modif/Suppression
         *
         */


    }
}
