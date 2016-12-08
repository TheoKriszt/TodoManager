package Model;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by achaillot on 06/12/16.
 * Gestionnaire de lecture / écriture des divers éléments du projet depuis / vers un fichier
 * Usage du design pattern singleton : empêche l'accès multiple au(x) fichier(s) de sauvegarde et conserve leur intégrité
 */

/**
 */
public class SaveFileManager  {


    private static SaveFileManager O;
    private TodoManager todoManager;
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;


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
        todoManager.setSaveFileManager(this);
    }

    public void readFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fint = null;
        try {
            fint = new FileInputStream("SaveFile.ser");
        }catch (FileNotFoundException e){

            readWhenFileDoesntExist();

        }finally {
            fint = new FileInputStream("SaveFile.ser");
        }

        ois = new ObjectInputStream(fint);
        Category.setCategories(readCategories(ois));
        if(ois != null){
            ois.close();
        }
    }

    // crée un fichier sérialisé avec les deux catégories par défault. Est appelé uniquement si le fichier sérialisé n'existe pas déjà.
    public void readWhenFileDoesntExist() throws IOException {
        FileOutputStream fout = new FileOutputStream("SaveFile.ser");
        oos = new ObjectOutputStream(fout);
        Category aucune = new Category("Aucune");
        Category perso = new Category("Personnelle");
        Category travail = new Category("Travail");
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(aucune);
        categories.add(perso);
        categories.add(travail);
        //oos.writeObject(categories);
        Category.setCategories(categories);
        saveToFile();
        /*oos.writeObject(aucune);
        oos.writeObject(perso);
        oos.writeObject(travail);*/
        if(oos != null){
            oos.flush();
            oos.close();
        }
    }

    public void saveToFile() throws IOException {
        FileOutputStream fout = new FileOutputStream("SaveFile.ser");
        oos = new ObjectOutputStream(fout);
        writeCategories(Category.getCategories());
        if(oos != null){
            oos.flush();
            oos.close();
        }
    }

    public static ArrayList<Category> readCategories(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        return (ArrayList<Category>) ois.readObject();
    }

    public static void writeCategories(ArrayList<Category> categories) throws IOException {
            oos.writeObject(categories);
    }
}
