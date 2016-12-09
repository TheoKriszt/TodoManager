package Model;

import View.TaskView;

import java.awt.*;
import java.io.Serializable;
import org.joda.time.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;

/**
 * Created by achaillot on 05/12/16.
 */
public class Task extends Observable implements Serializable {

    protected String name = "";
    protected String contenu = "";
    protected LocalDate echeance = LocalDate.now();
    protected LocalDate doneDate = null;
    protected int progress = 0;
    protected transient TaskView view;

    public Task(String name) throws UnsupportedOperationException, IllegalArgumentException{
        setName(name); //tentera de donner un nom, assure de base l'unicité par noms
        Category c = Category.getAucune();
        c.addTask(this);
    }

    public LocalDate getEcheance() {
        return echeance;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws UnsupportedOperationException, IllegalArgumentException {
        if (doneDate != null){
            throw new UnsupportedOperationException("Impossible de renommer une tâche après sa complétion");
        }
        if (name.isEmpty()){
            throw new IllegalArgumentException("Interdiction de donner un nom vide à une tâche");
        }
        if (Task.findByName(name) != null){
            throw new IllegalArgumentException("Le nom de tâche " + name + " est déjà pris");
        }
        this.name = name;
    }

    public Task(String name,Category c){
        this(name);
        c.addTask(this);
    }

    public void setContenu(String contenu){
        this.contenu = contenu;
    }

    public void setEcheance(LocalDate ld) throws IllegalArgumentException{
        if (!ld.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Une tâche ne peut être repoussée que dans le futur");
        }
        echeance = ld;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {

        if (progress > 100 || progress < 0){
            throw new IllegalArgumentException("L'avancement doit être compris entre 0 et 100");
            //Todo : ajouter tests ad hoc
        }

        if(progress == 100){
            doneDate = LocalDate.now();
        }
        this.progress = progress;
        //Todo : remove Task de la category : envoyer un notify (a la fin)
    }


    public boolean isLate(){
        return LocalDate.now().isAfter(echeance) && progress < 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        if (!(o.getClass() == this.getClass())) return false;

        Task task = (Task) o;

        return name.equals(task.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     *
     * @return la liste des tâches ordonnée par date d'écheance croissante
     */
    public static void sortByDueDate(ArrayList<Task> tasks){
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (o1.echeance.equals(o2.echeance)) return 0;
                return (o1.echeance.isBefore(o2.echeance)) ? -1 : 1;
            }
        });
    }

    public boolean isBetween(LocalDate start,LocalDate end){
        System.out.println(start + "---" + end + " echeance : " + echeance);
        if(echeance.isEqual(start) || echeance.isEqual(end)){
            return true;
        }
        else if(echeance.isBefore(end) && echeance.isAfter(start)){
            return true;
        }else{
            return false;
        }
    }

    public boolean releasedLate(){
            return doneDate.isAfter(echeance);
    }

    public String getContenu() {
        return contenu;
    }

    public String toString(){
        return "["+echeance+"] : " + name;
    }

    /**
     *
     * @return toutes les tâches existantes
     */
    public static ArrayList<Task> allTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        for (Category category : Category.getCategories()){
            for (Task t : category.getTasks()){
                tasks.add(t);
            }
        }
        return tasks;
    }

    public void setView(TaskView v){
        view = v;
        addObserver(v);
        update();
    }
    public TaskView getView() {
        return view;
    }

    //Todo : empecher deux tâches d'avoir le même nom
    public static Task findByName(String n){
        ArrayList<Task> tasks = allTasks();
        Task t = null;
        for (Task ts : tasks){
            if (ts.getName().equals(n)){
                t = ts;
            }
        }
        return t;
    }

    public Category findContainer(){
        for (Category c : Category.getCategories()){
            if (c.getTasks().contains(this))
                return c;
        }
        return null;
    }

    public void moveToCategory(Category dest){
        ArrayList<Category> cats = Category.getCategories();
        Category myCat = null;

        //Recherche de la catégorie contenante : parcours partiel
        for (Category c : cats){
            if (c.getTasks().contains(this)){
                myCat = c;
                break;
            }
        }

        myCat.moveTaskToCategory(this, dest);
    }

    /**
     * Permet de déclencher un setChanged/notifyObservers depuis l'exterieur
     */
    public void update(){
        setChanged();
        notifyObservers();
        System.out.println("Fin Task::update()");
    }

    /**
     * Supprime la tâche pour de bon
     */
    public void eraseTask(){
        findContainer().eraseTask(this);
    }
}
