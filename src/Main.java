import Model.Category;
import Model.SaveFileManager;
import Model.TodoManager;
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

        //Fausse init
        Category none = new Category("Aucune");
        Category perso = new Category("Personnel");
        Category travail = new Category("Travail");
        ArrayList<Category> cats = new ArrayList<>();
        cats.add(none);
        cats.add(perso);
        cats.add(travail);
        Category.setCategories(cats);

        JFrame jf = new TestFrame();




        //Todo : afficher le TodoManager

    }
}
