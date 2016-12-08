package Controller;

import Model.Category;
import View.CategoryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Theo on 08/12/2016.
 */
public class CategoryController {
    private Category category;

    public CategoryController(Category c){
        //add listeners
        category = c;
    }

    public void setListeners(CategoryView v){
        v.getRemoveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeButtonPressed();
            }
        });

        v.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editButtonPressed();
            }
        });

    }

    private void editButtonPressed() {

    }

    private void removeButtonPressed() {


    }
}
