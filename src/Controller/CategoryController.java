package Controller;

import Model.Category;
import View.CategoryView;

import javax.swing.*;
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

        String s = (String)JOptionPane.showInputDialog(
                category.getView(),
                "Entrez un nouveau nom pour la catégorie",
                "Editer la catégorie",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                category.getName());

        if (!s.equals(category.getName())){ //ne modifier que si le nom change effectivement
            try{
                category.renameCategory(s);
                category.update();
            } catch (IllegalArgumentException | UnsupportedOperationException e){
                JOptionPane.showMessageDialog(category.getView(), "Erreur lors du renommage : " + e.getMessage(), "Erreur de renommage", JOptionPane.ERROR_MESSAGE);
            }
        }



    }

    private void removeButtonPressed() {

        Object[] options = {"Confirmer", "Annuler"};
        int n = JOptionPane.showOptionDialog(category.getView(),
                "Confirmer la suppression de la catégorie ?",
                "Suppression de catégorie",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);

        if (n == 0){ //Suppression confirmée par l'utilisateur
            try{
                category.removeCategory();
                category.update();
            }catch (UnsupportedOperationException e){
                JOptionPane.showMessageDialog(category.getView(), "Erreur lors de la suppression : " + e.getMessage(), "Erreur de suppression", JOptionPane.ERROR_MESSAGE);
            }

        }



    }
}
