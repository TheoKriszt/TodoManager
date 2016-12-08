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
        removeTaskFromCategory(t);
        c.addTask(t);
    }

    // vérifier que on est bien sur la catégory contenante
    public void removeTaskFromCategory(Task t){
        if(tasks.contains(t)) {
            tasks.remove(t);
            tasks.trimToSize();
            getAucune().addTask(t);
        }
    }

    public void archiveCompletedTask(Task t){
        //Todo : retirer des categories et archiver la task
    }

    public void renameCategory(String newName) throws UnsupportedOperationException {
        if (equals(getAucune())){
            throw new UnsupportedOperationException("Interdiction de renommer la Catégorie \""+getName()+"\"");
        }
        this.name = newName;
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
            removeTaskFromCategory(t);
        }
        categories.remove(this);
        categories.trimToSize();
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
    }

    public static Category findByName(String n){
        for (Category c : getCategories()){
            if (c.getName().equals(n)){
                return c;
            }
        }
        return null;
    }
}
