package View;

import Model.Category;
import Model.Task;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Theo on 08/12/2016.
 */
public class CategoriesPanel extends ObserverPanel{
    @Override
    public void update(Observable o, Object arg) {
        removeAll();

        ArrayList<Category> cats = Category.getCategories();
        for (Category c : cats){
            try{
                CategoryView cv = c.getView();
                for (Task t : c.getTasks()){
                    if (t.getDoneDate() == null){
                        cv.getTasksPanel().add(t.getView());
                    }

                }
                add(cv);
                cv.revalidate();
            }catch(NullPointerException e){
                System.err.println("CatView not found");
            }

        }
        repaint(); //Appel explicite à repaint() : sinon reliquats d'affichage de catégories supprimées
        revalidate();

    }
}
