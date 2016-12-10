package Model;

import Controller.CategoryController;
import Controller.TaskController;
import View.CategoryView;
import View.ObserverPanel;
import View.TaskView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe Modèle des catégories, permettant d'effectuer tout type d'opérations sur celle ci.
 * @see CategoryController
 * @see CategoryView
 * @see View.CategoriesPanel
 */
public class Category extends Observable implements Serializable {

    private String name ="";
    private ArrayList<Task> tasks; //Une Catégorie contient une liste de tâches
    private static ArrayList<Category> categories = new ArrayList<Category>(); //Les Catégories sont recensées dans une liste générale
    static transient TodoManager todoManager; //Les catégories sont rattachées à un TodoManager
    private transient CategoryView view;

    /**
     * Constructeur par nom.
     * Deux catégories ne peuvent pas avoir le même nom.
     * @param name Nom de la catégorie
     * @throws IllegalArgumentException
     */
    public Category(String name) throws IllegalArgumentException{
        if (name.isEmpty()){
            throw new IllegalArgumentException();
        }
        if (Category.findByName(name) != null){
            throw new IllegalArgumentException("La catégorie \""+name+"\" existe déjà");
        }

        this.name = name;
        this.tasks = new ArrayList<Task>();
        categories.add(this);

        setObservers();
    }

    /**
     * Mise en place du pattern Observable Observer pour
     * @see Observer
     */
    private void setObservers() {
        deleteObservers(); // fais le ménage dans les observers éventuellement récupérés auparavant
        addObserver(todoManager.getFrame()); //les màj seront transmis et dirigés correctement depuis la fenêtre principale
        setChanged();
    }

    /**
     * Méthode définissant associant la catégorie au TodoManager
     * @param todoManager
     * @see TodoManager
     */
    public static void setTodoManager(TodoManager todoManager) {
        Category.todoManager = todoManager;
    }

    /**
     * Ajout d'une tâche dans la catégorie.
     * A chaque ajout les tâches présentent dans la catégorie sont triées (par ordre d'échéance).
     * @param t Tâche à ajouter.
     * @see Task
     */
    public void addTask(Task t){
        tasks.add(t);
        Task.sortByDueDate(tasks);
        setChanged();
    }

    public static Category getAucune(){
        return categories.get(0);
    }

    /**
     * Déplacement d'une tâche d'une catégorie à une autre.
     *
     * @param t Tâche que l'on déplace
     * @param c Catégorie dans laquelle on déplace la tâche
     * @see Task
     */
    public void moveTaskToCategory(Task t,Category c){
        System.out.println("Moving task " + t.getName() + " from cat " + getName() + " to " + c.getName());
        eraseTask(t);
        c.addTask(t);
        update();
//        setChanged();
//        notifyObservers();
    }


    /**
     * Retirer une tâche d'une catégorie
     * @param t tâche à retiré
     * @deprecated
     */
    @Deprecated
    public void removeTaskFromCategory(Task t){
        System.out.println("Removing task " + t.getName() + " from cat " + name);
        if(tasks.contains(t)) {
            tasks.remove(t);
            tasks.trimToSize();
            getAucune().addTask(t);

        }else System.err.println("Task not found in cat");
        setChanged();
        notifyObservers();
    }

    /**
     * Renommer une catégorie.
     * Une catégorie ne peut pas être renommer si le nom choisi est celui d'une catégorie déjà existante.
     * La catégorie par défault "Aucune" n'est pas renommable.
     *
     * @param newName Nouveau nom
     * @throws UnsupportedOperationException,IllegalArgumentException
     */
    public void renameCategory(String newName) throws UnsupportedOperationException {
        if (equals(getAucune())){
            throw new UnsupportedOperationException("Interdiction de renommer la Catégorie \""+getName()+"\"");
        }
        if (Category.findByName(newName) != null){
            throw new IllegalArgumentException("Impossible de renommer la catégorie : une autre catégorie nommée " + newName + " existe déjà");
        }
        this.name = newName;
        update();
    }

    /**
     * Supprimer une catégorie.
     * La catégorie par défault "Aucune" ne peut pas être supprimé.
     * Les tâches présente dans la catégorie supprimée sont envoyées dans "Aucune".
     *
     * @throws UnsupportedOperationException
     */
    public void removeCategory(){
        if (equals(getAucune())){
            throw new UnsupportedOperationException("Interdiction de supprimer la categorie \""+getName()+"\"");
        }
        //Iteration sur un clone sinon rencontre des problèmes de modifications concurrentes sur l'ArrayList
        for (Task t : (ArrayList<Task>)tasks.clone()){
            t.moveToCategory(getAucune());
        }
        categories.remove(this);
        categories.trimToSize();

        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        return name.equals(category.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public static ArrayList<Category> getCategories() {
        return categories;
    }

    /**
     * Appelé après la désérialisation du fichier de persistence
     * Assure la création des Contrôleurs et vues associés aux modèles (de Tâches comme de Catégories) issus du fichier de persistence
     * @param categories la liste des catégories extraite du fichier de persistence
     */
    public static void setCategories(ArrayList<Category> categories) {
        Category.categories = categories;

        for (Category c : categories){
            for (Task t : c.getTasks()){
                TaskController tc = new TaskController(t);
                TaskView tv = new TaskView(tc);
                t.setView(tv);
            }

            CategoryController cc = new CategoryController(c);
            CategoryView cv = new CategoryView(cc);
            c.setView(cv);
            c.setObservers();
            c.update();
        }

        getAucune().update();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return  name;
    }

    public CategoryView getView() {
        return view;
    }

    public void setView(CategoryView v){
        view = v;
        addObserver(v);
        update();
    }

    /**
     * Méthode de recherche de catégorie par nom.
     *
     * @param n nom recherché
     * @return Category
     */
    public static Category findByName(String n){
        for (Category c : getCategories()){
            if (c.getName().equals(n)){
                return c;
            }
        }
        return null;
    }

    /**
     * Retire une tâche d'une catégorie
     *
     * @param task tâche à retirer
     */
    public void eraseTask(Task task) {
        if (tasks.contains(task)){
            tasks.remove(task);
            tasks.trimToSize();
            update();
        }
    }

    /**
     * Met à jour la catégorie et prévient la vue.
     */
    public void update(){
        setChanged();
        notifyObservers();
    }
}
