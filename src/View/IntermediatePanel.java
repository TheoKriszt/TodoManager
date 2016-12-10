package View;

import Model.Task;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Theo on 07/12/2016.
 */
public class IntermediatePanel extends ObserverPanel {


    @Override
    public void update(Observable o, Object arg) {
        removeAll();
        ArrayList<Task> tasks = Task.allTasks();
        Task.sortByIntermediateDueDate(tasks);
        for (Task t : tasks){
            if (t.getDoneDate() == null){
                t.update();
                TaskView tv = t.getView();
                try{
                    add(tv);
                }catch (NullPointerException e){
                    System.err.println(e.getMessage());
                }
            }

        }
        repaint();
        revalidate();
    }
}
