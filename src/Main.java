import Controller.TaskController;
import Model.*;
import View.MainFrame;
import View.TaskView;
import View.TestFrame;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        TodoManager tm = new TodoManager();
        SaveFileManager sfm = SaveFileManager.O();
        sfm.setTodoManager(tm);

        sfm.readFromFile();

        //TODO : jeu de test, à supprimer avant rendu prof
        Task t1 = new Task("Tache 1"),
                t2 = new Task("Tache 2"),
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

        /**
         * TODO :
         * Ajouter listeners sur les TaskView
         * Quand cliqué, ouvre une popup de Lecture/Modif/Suppression
         *
         */

        //JFrame jf = new TestFrame();
        MainFrame mainFrame = new MainFrame(tm);

    }
}
