package Model;

import java.io.IOException;
import java.util.Observable;

/**
 * Created by achaillot on 06/12/16.
 */
public class TodoManager extends Observable {

    private SaveFileManager saveFileManager;

    public TodoManager(){


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
}
