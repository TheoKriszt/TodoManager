import Controller.MainFrameController;
import Model.SaveFileManager;
import Model.TodoManager;
import View.MainFrame;

import javax.swing.*;
import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        TodoManager tm = new TodoManager(); // L'instance du gestionnaire de TodoList

        MainFrameController mainFrameController = new MainFrameController(tm); //La fenêtre de l'appi et son contrôleur
        MainFrame mainFrame = new MainFrame(mainFrameController);

        SaveFileManager sfm = SaveFileManager.O(); //Le gestionnaire de sauvegarde (singleton)
        sfm.setTodoManager(tm);
        sfm.readFromFile(); //Chargement / création du fichier de sauvegarde et import des Tâches
    }
}
