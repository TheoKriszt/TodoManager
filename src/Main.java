import Model.SaveFileManager;
import Model.TodoManager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        TodoManager tm = new TodoManager();
        SaveFileManager sfm = SaveFileManager.O();
        sfm.setTodoManager(tm);

        sfm.readFromFile();

        //Todo : afficher le TodoManager

    }
}
