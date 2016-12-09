package Model;

import View.CategoryView;
import View.ObserverPanel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by achaillot on 05/12/16.
 */
public class Category extends Observable implements Serializable {

    private String name ="";
    private ArrayList<Task> tasks; //Une Catégorie contient une liste de tâches
    private static ArrayList<Category> categories = new ArrayList<Category>(); //Les Catégories sont recensées dans une liste générale
    static transient TodoManager todoManager; //Les catégories sont rattachées à un TodoManager
    private transient CategoryView view;


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

    private void setObservers() {
        ArrayList<ObserverPanel> obs = todoManager.getFrame().getTabs();
        for (ObserverPanel o : obs){
            addObserver(o);
        }
        System.out.println("Notifying");
        setChanged();

    }

    public static void setTodoManager(TodoManager todoManager) {
        Category.todoManager = todoManager;
    }

    public void addTask(Task t){
        tasks.add(t);
        Task.sortByDueDate(tasks);
        setChanged();
    }

    public static Category getAucune(){
        return categories.get(0);
    }

    public void moveTaskToCategory(Task t,Category c){
        System.out.println("Moving task " + t.getName() + " from cat " + getName() + " to " + c.getName());
        eraseTask(t);
        c.addTask(t);
        setChanged();
        notifyObservers();
    }

    // vérifier que on est bien sur la catégory contenante
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

    public void archiveCompletedTask(Task t){
        //Todo : retirer des categories et archiver la task
    }

    public void renameCategory(String newName) throws UnsupportedOperationException {
        if (equals(getAucune())){
            throw new UnsupportedOperationException("Interdiction de renommer la Catégorie \""+getName()+"\"");
        }
        if (Category.findByName(newName) != null){
            throw new IllegalArgumentException("Impossible de renommer la catégorie : une autre catégorie nommée " + newName + " existe déjà");
        }
        this.name = newName;
        for (Task t : tasks){
            t.update();
        }
        setChanged();
        notifyObservers();
    }

    /**
     *
     */
    public void removeCategory(){
        if (equals(getAucune())){
            throw new UnsupportedOperationException("Interdiction de supprimer la categorie \""+getName()+"\"");
        }
        //Iteration sur un clone sinon rencontre des problèmes de modifications concurrentes sur l'ArrayList
        for (Task t : (ArrayList<Task>)tasks.clone()){
            t.moveToCategory(getAucune());
            //removeTaskFromCategory(t);
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

    public static void setCategories(ArrayList<Category> categories) {
        Category.categories = categories;
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
        setChanged();
        notifyObservers();
    }

    public static Category findByName(String n){
        for (Category c : getCategories()){
            if (c.getName().equals(n)){
                return c;
            }
        }
        return null;
    }

    public void eraseTask(Task task) {
        if (tasks.contains(task)){
            tasks.remove(task);
            tasks.trimToSize();
            setChanged();
            notifyObservers();
        }
    }

    @Deprecated
    public void eraseCategory(){
        removeCategory(); //expatrier les éventuelles tâches sur Aucune
        categories.remove(this);
        setChanged();
        notifyObservers();
    }
}
