package View;


import Model.Task;
import java.util.ArrayList;
import java.util.Observable;


/**
 *sur classe vue, affichage générale, affichant toutes les tâches existantes triées par ordre d'échéance.
 * @see Task,TaskView
 */
public class GeneralPanel extends ObserverPanel {

    public GeneralPanel(){

    }

    /**
     * Met à jour l'affichage du generalPanel
     * @param o
     * @param arg
     * @throws NullPointerException
     */
    public void update(Observable o, Object arg) {
        removeAll();
        ArrayList<Task> tasks = Task.allTasks();
        Task.sortByDueDate(tasks);
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
