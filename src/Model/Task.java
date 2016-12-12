package Model;

import View.TaskView;

import java.io.Serializable;
import org.joda.time.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Observable;

/**
 * Classe Modèle des tâches, permettant d'effectuer tout types d'opérations sur celle ci.
 * Une tâche représentant quelque chose à faire pour une date donnée, elle est définie par son nom.
 *
 * @see TaskView,LongTask,Controller.TaskController
 */
public class Task extends Observable implements Serializable {

    protected String name = "";
    protected String contenu = "";
    protected LocalDate echeance = LocalDate.now();
    protected LocalDate doneDate = null;
    protected int progress = 0;
    protected transient TaskView view;

    /**
     * Constructeur de tâche.
     * Deux tâches ne peuvent pas avoir le même nom.
     * Par défault elle sera référencé dans la catégorie "Aucune".
     *
     * @param name Nom de la tâche
     * @throws UnsupportedOperationException
     * @throws IllegalArgumentException
     * @see Task#setName(String)
     */
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

    /**
     * Méthode de renommage pour une tâche.
     * Une tâche ne peut pas être renommé si :
     *          - On la modifie après qu'elle ait été réalisé.
     *          - On lui donne le nom vide ("").
     *          - Le nom choisi est déjà pris par une autre tâche.
     * @param name Nouveau nom de la tâche
     * @throws UnsupportedOperationException
     * @throws IllegalArgumentException
     */
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

    /**
     * Constructeur d'une tâche.
     * La tâche est créé avec un nom et une catégorie précisé en paramètre.
     *
     * @param name Nom de la tâche
     * @param c Catégorie de la tâche
     * @see Task(String)
     */
    public Task(String name,Category c){
        this(name);
        c.addTask(this);
    }

    public void setContenu(String contenu){
        this.contenu = contenu;
    }

    /**
     * modification de la date d'échéance pour une tâche.
     * La date d'échéance ne peut être repoussée que dans le futur.
     *
     * @param ld Nouvelle échéance
     * @throws IllegalArgumentException
     */
    public void setEcheance(LocalDate ld) throws IllegalArgumentException{
        if (ld.isBefore(LocalDate.now(DateTimeZone.UTC))){

            throw new IllegalArgumentException("Une tâche ne peut être repoussée que dans le futur");

        }
        echeance = ld;
    }

    public int getProgress() {
        return progress;
    }

    /**
     * Modification de la progression d'une tâche.
     * Doit être compris entre 0 et 100, il ne peut pas regresser.
     *
     * @param progress Nouvel avancement
     * @throws IllegalArgumentException
     */
    public void setProgress(int progress) {

        if (progress > 100 || progress < 0){
            throw new IllegalArgumentException("L'avancement doit être compris entre 0 et 100");
        }
        if (this.progress > progress){
            throw new IllegalArgumentException("L'avancement ne peut évoluer que de façon croissante");
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
     * Tri les tâches par date d'échéance croissante.
     * @param tasks liste de tâche à trier
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
     *
     * @return le comparateur utilisé pour trier les tâches par echeance intermediaire décroissante
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

    /**
     * Méthode vérifiant que l'échéance d'une tâche est bien comprise entre deux dates.
     *
     * @param start date du début de l'encadrement
     * @param end   date de fin de l'encadrement
     * @return true si l'échéance est comprise entre les deux dates , false sinon.
     */
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
        String ret = name + "(" + progress + "%)";
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

    /**
     * définie la vue de la tâche.
     * @param v La view qui va être observer
     */
    public void setView(TaskView v){
        view = v;
        addObserver(v);
        update();
    }
    public TaskView getView() {
        return view;
    }

    /**
     * Recherche une tâche portant le nom passé en paramètre.
     *
     * @param n Le nom de la tâche que l'on recherche
     * @return Tâche portant le nom passé en paramètre
     */
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

    /**
     * Recherche la catégorie de la tâche
     *
     * @return La catégorie de la tâche
     */
    public Category findContainer(){
        for (Category c : Category.getCategories()){
            if (c.getTasks().contains(this))
                return c;
        }
        return null;
    }

    /**
     * Déplace la tâche dans la catégorie de destination.
     * @param dest Catégorie dans laquelle la tâche se déplacera
     */
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

        ArrayList<Task> longues = new ArrayList<>();
        ArrayList<Task> courtes = new ArrayList<>();

        for (Task t : tasks){
            if (t instanceof LongTask) longues.add((LongTask) t);
            else courtes.add(t);
        }

        longues.sort(new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                LongTask t1 = (LongTask)o1;
                LongTask t2 = (LongTask)o2;

                if (t1.getDuration() == t2.getDuration())
                    return 0;

                return (t1.getDuration() > t2.getDuration()) ? 1 : -1;
            }
        });

        if (!longues.isEmpty()){
            top.add(longues.remove(0));
            longues.trimToSize();
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

    /**
     *
     * @return le messsage a afficher selon que la tâche soit à rendre aujourd'hui, dans X jours ou soit en retard
     */
    public String getTimeLeftMessage(){
        String ret = "";

        int diff = Days.daysBetween(LocalDate.now(), echeance).getDays();

        if (diff > 0){
            ret = diff + " jours restants";
        }else if (diff < 0){
            ret = "dûe depuis " + -diff + " jours";
        }else{
            ret = "dûe aujourd'hui";
        }

        return ret;

    }
}
