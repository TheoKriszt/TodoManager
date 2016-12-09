package View;

import Model.Category;
import Model.Task;
import org.joda.time.DateTime;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Theo on 07/12/2016.
 */
public class GeneralPanel extends ObserverPanel {

    public GeneralPanel(){
        /*
        Category none = Category.getAucune();
        System.out.println(Category.getCategories().size() + " categories found");
        System.out.println(none.getTasks().size() + " tasks in noneCat");
        System.out.println("First name found" + none.getTasks().get(0).getName());
        for (Task t : Task.allTasks()){
            System.out.println("Inserting view from " + t.getName());
            add(t.getView());
        }
        */
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("GeneralPanel::update()");
        removeAll();
        ArrayList<Task> tasks = Task.allTasks();
        Task.sortByDueDate(tasks);
        for (Task t : tasks){
            t.update();
            TaskView tv = t.getView();
            System.out.println("Displaying task " + t.getName() + " [ " + t.findContainer().getName() + " ]");
            try{
                add(tv);
            }catch (NullPointerException e){
                System.err.println(e.getMessage());
            }

            //t.update();
        }
        repaint();
        revalidate();
        System.out.println("Fin GeneralPanel::Update()");
    }
}
