package Model;

import View.BilanPanel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.joda.time.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

/**
 * Created by achaillot on 06/12/16.
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
     * Combien de tâches sur la période ont été :
     *  - finies dans les temps
     *  - finies en retard
     *  - toujours pas finies... et en retard !
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
     *
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

    public void update(JDatePanelImpl start, JDatePanelImpl end) {
        LocalDate startDate, endDate;
        startDate = LocalDate.fromDateFields((Date) start.getModel().getValue());
        endDate = LocalDate.fromDateFields((Date) end.getModel().getValue());

        this.start = startDate;
        this.end = endDate;

        loadTasks();
        System.out.println("Bilan chargé avec " + tasks.size() + " tâches sur la période");

        setChanged();
        notifyObservers();
    }
}
