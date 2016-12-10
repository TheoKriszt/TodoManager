package View;

import Model.Task;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Sur classe vue, affichage intermédiaire, affichant toutes les tâches existantes triées par ordre d'échéance intermédiaire décroissante.
 */
public class IntermediatePanel extends ObserverPanel {


    /**
     * Met à jour l'affichage de l'intermediatePanel
     * @param o
     * @param arg
     * @throws NullPointerException
     */
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
