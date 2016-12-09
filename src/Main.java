import Controller.CategoryController;
import Controller.TaskController;
import Model.*;
import View.CategoryView;
import View.MainFrame;
import View.TaskView;
import java.io.IOException;
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        TodoManager tm = new TodoManager();
        MainFrame mainFrame = new MainFrame(tm);
        SaveFileManager sfm = SaveFileManager.O();
        sfm.setTodoManager(tm);
        sfm.readFromFile();

    }
}
