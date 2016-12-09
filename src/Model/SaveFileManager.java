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

    /**
     * Initialise depuis le fichier de persistence la liste des catégories (et indirectement, leur tâches respectives)
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void readFromFile() throws IOException, ClassNotFoundException {
        FileInputStream fint;
        try {
            fint = new FileInputStream("SaveFile.ser");
            System.err.println("Lu depuis le fichier");

        }catch (FileNotFoundException e){ //Le fichier de persistence n'existe pas
            createSampleSaveFile(); //Forcer la création d'un fichier sample
            System.err.println("Fichier créé de force");
            fint = new FileInputStream("SaveFile.ser");
        }


        ois = new ObjectInputStream(fint);

        ArrayList<Category> categories = (ArrayList < Category >) ois.readObject();

        Category.setCategories(categories);
        Category.getAucune().update();


        if(ois != null){
            ois.close();
        }
    }

    // crée un fichier sérialisé avec les deux catégories par défault. Est appelé uniquement si le fichier sérialisé n'existe pas déjà.

    /**
     * Créé un fichier de persistence contenant les catégories demandées par défaut : Travail, Personnel et la catégorie fourre-tout "Aucune"
     *
     * @throws IOException
     */
    public void createSampleSaveFile() throws IOException {
        FileOutputStream fout = new FileOutputStream("SaveFile.ser");
        oos = new ObjectOutputStream(fout);

        ArrayList<Category> categories = new ArrayList<>();

        Category aucune = new Category("Aucune");
        Category perso = new Category("Personnel");
        Category travail = new Category("Travail");

        categories.add(aucune);
        categories.add(perso);
        categories.add(travail);

        saveToFile();

        if(oos != null){
            oos.flush();
            oos.close();
        }
    }

    /**
     * Sauvegarde la liste des Catégories (et indirectement leur tâches respectives) dans le fichier de persistence "SaveFile.ser"
     * @throws IOException
     */
    public void saveToFile() throws IOException {
        FileOutputStream fout = new FileOutputStream("SaveFile.ser");
        oos = new ObjectOutputStream(fout);
        oos.writeObject(Category.getCategories());

        if(oos != null){
            oos.flush();
            oos.close();
        }
    }




}
