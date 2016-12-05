package Model;

/**
 * Created by achaillot on 05/12/16.
 */
public abstract class Task {

    private String name = "";
    private String contenue = "";

    public Task(String name){
        this.name = name;
        Category c = Category::categoryAucune();
    }

    public Task(String name,Category c){
        this(name);
        c.addTask(this);
    }

    public void setContenue(String contenue){
        this.contenue = contenue;
    }


}
