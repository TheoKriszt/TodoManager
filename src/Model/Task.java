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
        if (this.name.equals(name) && this != null){
            //return; //Pas de changement, inutile de poursuivre
        }

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
        if (ld.isBefore(LocalDate.now(DateTimeZone.UTC))){
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

    /**
     *
     * @return le compaateur utilisé pour trier les tâches par echeance intermediaire décroissante
     */
    protected static Comparator<Task> getIntermediateComparator(){
        return (new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if (o1.getNextEcheance().equals(o2.getNextEcheance())) return 0;
                return (o1.getNextEcheance().isBefore(o2.getNextEcheance())) ? -1 : 1;
            }
        });
    }

    /**
     *
     * @return la liste des tâches ordonnée par date d'écheance croissante
     */
    public static void sortByIntermediateDueDate(ArrayList<Task> tasks){
        tasks.sort(getIntermediateComparator());
    }

    public boolean isBetween(LocalDate start,LocalDate end){
        if (start == null || end == null) return false;
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
        String ret = echeance + " "  + name + "(" + progress + "%) ===> " + findContainer().getName() + " done the " + doneDate;
        return ret;
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
    }

    /**
     * Supprime la tâche pour de bon
     */
    public void eraseTask(){
        findContainer().eraseTask(this);
    }

    /**
     *
     * @return la prochaine date d'échéance intermédiaire
     */
    public LocalDate getNextEcheance(){
        return echeance;
    }

    /**
     * Détermine et renvoie dans l'ordre les tâches du top 8... Plutôt top 9 d'ailleurs
     * @param tasks le set de tâches disponibles
     * @return la liste du top, avec dans l'ordre :
     *  - La (ou l'une des) tâche(s) la plus longue, on estime que c'est la plus importante
     *  - Max. 3 tâches sur le long terme, par priorité d'échéance intermediaire
     *  - Max 5 tâches courtes, par priorité d'échéance
     */
    public static ArrayList<Task> findTop8Tasks(ArrayList<Task> tasks) {
        ArrayList<Task> top = new ArrayList<>();


        Task.sortByIntermediateDueDate(tasks);
        if (!tasks.isEmpty()){
            top.add(tasks.remove(0));
            tasks.trimToSize();
        }


        ArrayList<Task> longues = new ArrayList<>(),
                        courtes = new ArrayList<>();

        for (Task t : tasks){
            if (t instanceof LongTask) longues.add(t);
            else courtes.add(t);
        }

        Task.sortByIntermediateDueDate(courtes);
        Task.sortByIntermediateDueDate(longues);

        for (int i=0; i<3; i++){
            if (!longues.isEmpty()){
                top.add(longues.remove(0));
                longues.trimToSize();
            }
        }

        for (int i=0; i<5; i++){
            if (!courtes.isEmpty()){
                top.add(courtes.remove(0));
                courtes.trimToSize();
            }
        }

        return top;

    }
}
