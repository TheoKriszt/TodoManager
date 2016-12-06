package Model;

/**
 * Created by achaillot on 06/12/16.
 */
public class SaveFileManager  {

    private static SaveFileManager O;
    private TodoManager todoManager;

    private SaveFileManager(){

    }

    public static SaveFileManager O(){
        if (O == null){
            O = new SaveFileManager();
        }
        return O;
    }

    public void setTodoManager(TodoManager todoManager) {
        this.todoManager = todoManager;
    }

    public void readFromFile(){
        //Todo : checker si le fichier existe !

    }

    public void saveToFile(){

    }
}
