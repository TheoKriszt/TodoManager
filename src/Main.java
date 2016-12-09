import Controller.MainFrameController;
import Model.SaveFileManager;
import Model.TodoManager;
import View.MainFrame;
import java.io.IOException;
public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        TodoManager tm = new TodoManager();
        MainFrameController mainFrameController = new MainFrameController(tm);
        MainFrame mainFrame = new MainFrame(mainFrameController);
        SaveFileManager sfm = SaveFileManager.O();
        sfm.setTodoManager(tm);
        sfm.readFromFile();
    }
}
