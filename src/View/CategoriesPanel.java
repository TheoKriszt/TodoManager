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
        System.out.println("CategoriesPanel::update");
        removeAll();

        for (Category c : Category.getCategories()){
            try{
                CategoryView cv = c.getView();
                cv.getTasksPanel().removeAll();
                for (Task t : c.getTasks()){
                    if (t.getDoneDate() == null){
                        cv.getTasksPanel().add(t.getView());
                    }
                }


                cv.revalidate();
                cv.repaint();
                add(cv);

            }catch(NullPointerException e){
                System.err.println("CatView not found");
            }

        }
        repaint(); //Appel explicite à repaint() : sinon reliquats d'affichage de catégories supprimées
        revalidate();

    }
}
