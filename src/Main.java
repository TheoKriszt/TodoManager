import Model.*;
import View.MainFrame;
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

        Task t1 = new SimpleTask("Tache 1"),
                t2 = new SimpleTask("Tache 2"),
                t3 = new SimpleTask("Tache 3");

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
