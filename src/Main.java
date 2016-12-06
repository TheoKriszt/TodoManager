import Model.SaveFileManager;
import Model.TodoManager;

public class Main {

    public static void main(String[] args) {

        TodoManager tm = new TodoManager();
        SaveFileManager sfm = SaveFileManager.O();
        sfm.setTodoManager(tm);

        sfm.readFromFile();

        //Todo : afficher le TodoManager

    }
}
