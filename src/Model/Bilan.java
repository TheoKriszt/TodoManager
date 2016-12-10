package Model;

import View.BilanPanel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.joda.time.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

/**
 * Bilan permet de recupérer les différentes tâches pour une période donnée.
 * Elle permet aussi de savoir combien de tâches sur la période ont été :
 *  - finies dans les temps
 *  - finies en retard
 *  - toujours pas finies... et en retard !
 */
public class Bilan extends Observable{

    private LocalDate start = LocalDate.now().minusDays(15);
    private LocalDate end = LocalDate.now().plusDays(15);
    private ArrayList<Task> tasks = new ArrayList<>();
    private float tasksReleasedInTime = 0f;
    private float tasksReleasedLate = 0f;
    private float tasksNotReleasedAndLate = 0f;
    private BilanPanel view;

    /**
     * Charge les stats  :
     * - La liste des tâches sur la période
     * - Combien de tâches sur la période ont été :
     *      - finies dans les temps
     *      - finies en retard
     *      - toujours pas finies... et en retard !
     */
    private void loadTasks(){
        ArrayList<Category> categories = Category.getCategories();
        tasks = new ArrayList<Task>();
        for (Category c : categories){
            for (Task t : c.getTasks()){
                if (t.isBetween(start, end)){
                    tasks.add(t);
                    if(t.doneDate != null){
                        if(t.releasedLate()){
                            tasksReleasedLate++;
                        }
                        else{
                            tasksReleasedInTime++;
                        }
                    }else{
                        if(t.isLate()){
                            tasksNotReleasedAndLate++;
                        }
                    }
                }else{
                    System.out.println(t + " n'est pas compris dedans");
                }
            }
        }
    }

    /**
     * Retourne le pourcentage
     * @param f le nombre de tâches concernées par le paramètre
     * @return le pourcentage (par règle de 3) de tâches concernées par le paramètre
     */
    private int getPercentage(float f){
        if (!tasks.isEmpty()){
            f = (100*f)/tasks.size();
        }else {
            f = 0f;
        }
        return (int)f;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public int getPercentageTasksReleasedInTime() {
        return getPercentage(tasksReleasedInTime);
    }

    public int getPercentageTasksReleasedLate() {
        return getPercentage(tasksReleasedLate);
    }

    public int getPercentageTasksNotReleasedAndLate() {
        return getPercentage(tasksNotReleasedAndLate);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setView(BilanPanel tabBilan) { // Todo : ?? Useless ?
        addObserver(tabBilan);
        view = tabBilan;
    }


    /**
     * Met à jour le bilan
     * @param start la nouvelle date de début pour le bilan
     * @param end   la nouvelle date de fin pour le bilan
     */
    public void update(JDatePanelImpl start, JDatePanelImpl end) {
        LocalDate startDate, endDate;

        Date startSourceDate = (Date) start.getModel().getValue();
        Date endSourceDate = (Date) end.getModel().getValue();

        if (startSourceDate == null){
            startSourceDate = new Date();
        }
        if (endSourceDate == null){
            endSourceDate = new Date();
        }

        startDate = LocalDate.fromDateFields(startSourceDate);
        endDate = LocalDate.fromDateFields(endSourceDate);

        this.start = startDate;
        this.end = endDate;

        loadTasks();
        System.out.println("Bilan chargé avec " + tasks.size() + " tâches sur la période");

        setChanged();
        notifyObservers();
    }
}
