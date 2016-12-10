package View;

import Model.Task;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Sur classe vue des Tâches, les affichant selon un tri spécifique.
 * 8 tâches sont afficher : au maximum 1 tâche importante , 3 tâches moyennes  et 5 normales.
 *
 * @see Task,TaskView
 */
public class Top8Panel extends ObserverPanel {

    public Top8Panel(){

    }


    /**
     * Met à jour l'affichage des 8 tâches.
     * @param o
     * @param arg
     * @throws NullPointerException
     *
     * @see Task#findTop8Tasks(ArrayList)
     */
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
