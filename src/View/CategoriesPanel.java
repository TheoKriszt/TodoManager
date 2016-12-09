package View;

import Model.Category;

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
        System.out.println("CategoriesPanel::update()");
        removeAll();

        ArrayList<Category> cats = Category.getCategories();
        for (Category c : cats){
            try{
                add(c.getView());
            }catch(NullPointerException e){
                System.err.println("CatView not found");
            }

        }
        repaint(); //Appel explicite à repaint() : sinon reliquats d'affichage de catégories supprimées
        revalidate();

    }
}
