package Model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by achaillot on 06/12/16.
 */
public class Bilan {

    private LocalDate start = LocalDate.now().minusDays(15);
    private LocalDate end = LocalDate.now().plusDays(15);
    private ArrayList<Task> tasks = new ArrayList<>();
    private float tasksReleasedInTime = 0f;
    private float tasksReleasedLate = 0f;
    private float tasksNotReleasedAndLate = 0f;


    private static Bilan O = new Bilan();

    private Bilan(){

    }

    public static Bilan instance() {
        return O;
    }

    private void loadTasks(){
        ArrayList<Category> categories = Category.getCategories();
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
                }
            }
        }
    }

    private void getPercentage(float f){
        if (!tasks.isEmpty()){
            f = (100*f)/tasks.size();
        }else {
            f = 0f;
        }
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

    public float getTasksReleasedInTime() {
        return tasksReleasedInTime;
    }

    public float getTasksReleasedLate() {
        return tasksReleasedLate;
    }

    public float getTasksNotReleasedAndLate() {
        return tasksNotReleasedAndLate;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
