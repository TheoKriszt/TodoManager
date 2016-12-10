package Model;

import View.MainFrame;

import java.io.IOException;
import java.util.Observable;

/**
 * Gestionnaire de TodoManager
 * Cette classe représente l'instance de la liste des tâches de l'application
 *
 */
public class TodoManager extends Observable {

    private SaveFileManager saveFileManager;
    private MainFrame frame;

    public TodoManager(){

        Category.setTodoManager(this);

    }

    public void setSaveFileManager(SaveFileManager s){
        saveFileManager = s;
    }

    /**
     * Assure la persistence des données puis ferme le programme
     */
    public void close() {
        int status = 0;
        try{
            saveFileManager.saveToFile();
        } catch (IOException e) {
            e.printStackTrace();
            status = -1;
        }

        System.exit(status);
    }

    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }

    public MainFrame getFrame() {
        return frame;
    }
}
