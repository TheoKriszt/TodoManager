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

    }

    @Override
    public void update(Observable o, Object arg) {
        removeAll();
        ArrayList<Task> tasks = Task.allTasks();
        Task.sortByDueDate(tasks);
        for (Task t : tasks){
            t.update();
            TaskView tv = t.getView();
            try{
                add(tv);
            }catch (NullPointerException e){
                System.err.println(e.getMessage());
            }
        }
        repaint();
        revalidate();
    }
}
