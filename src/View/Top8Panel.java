package View;

import Model.Task;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by Theo on 07/12/2016.
 */
public class Top8Panel extends ObserverPanel {

    public Top8Panel(){

    }


    @Override
    public void update(Observable o, Object arg) {
        removeAll();
        ArrayList<Task> tasks = Task.allTasks();
        tasks = Task.findTop8Tasks(tasks); //filtrage : ne garder que les plus importants
                                            //Au maximum : 1+3+5 = 9 tâches, les critères peuvent en faire apparaitre moins
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
