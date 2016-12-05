package Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by achaillot on 05/12/16.
 */
public class Category {

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
    }

    public static Category categoryAucune(){
        return categories.get(0);
    }

}
