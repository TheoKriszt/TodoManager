package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by achaillot on 05/12/16.
 */
public class Category implements Serializable {

    private String name ="";
    private ArrayList<Task> tasks;
    private static ArrayList<Category> categories = new ArrayList<Category>(Collections.singletonList(new Category("Aucune")));

    public Category(String name){
        this.name = name;
        this.tasks = new ArrayList<Task>();
        this.categories.add(this);
    }

    public void addTask(Task t){
        tasks.add(t);
        Task.sortByDueDate(tasks);
    }

    public static Category categoryAucune(){
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
            categoryAucune().addTask(t);
        }
    }

    public void archiveCompletedTask(Task t){
        //Todo : retirer des categories et archiver la task
    }

    public void renameCategory(String newName){
        this.name = newName;
    }

    public void removeCategory(){
        for (Task t : tasks){
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
}
