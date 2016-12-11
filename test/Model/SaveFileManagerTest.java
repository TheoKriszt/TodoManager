package Model;

import Controller.MainFrameController;
import View.MainFrame;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SaveFileManagerTest {

    private SaveFileManager sfm;

    @Before
    public void setUp() throws Exception {
        //O = SaveFileManager.O();
        TodoManager tm = new TodoManager(); // L'instance du gestionnaire de TodoList

        MainFrameController mainFrameController = new MainFrameController(tm); //La fenêtre de l'appi et son contrôleur
        MainFrame mainFrame = new MainFrame(mainFrameController);
        sfm = SaveFileManager.O(); //Le gestionnaire de sauvegarde (singleton)
        sfm.setTodoManager(tm);
        sfm.readFromFile(); //Chargement / création du fichier de sauvegarde et import des Tâches
    }

    /**
     * Quand le fichier de sauvegarde n'existe pas il faut initialiser la TodoList avec un set de catégories par défaut
     * Vérifie que les 3 categories "d'usine" sont présentes après initialisation
     */
    @Test
    public void testPopulateWhenNoSaveFile() throws Exception{

        File save = new File("SaveFile.ser");
        save.delete();
        //Category.setCategories(new ArrayList<>());
        //System.out.println(Category.getCategories());
        sfm.readFromFile();
        //System.out.println(Category.getCategories());
        //System.out.println("Size : " + Category.getCategories().size());

        assertEquals(3, Category.getCategories().size());

    }

    @Test
    public void testPersistenceCategorie() throws Exception {
        sfm.readFromFile();
        Category.getCategories().clear(); // wipe local list
        Category persiste = new Category("cette categorie a persisté");
        Category.getCategories().add(persiste);
        sfm.saveToFile();
        Category.getCategories().clear(); // wipe local list
        sfm.readFromFile(); //reload from save

        assertTrue(Category.getCategories().contains(persiste));

    }

    @Test
    public void testPersistenceTache() throws Exception {
        sfm.readFromFile();
        Category.getAucune().getTasks().clear();


        Task t = new Task("Doit persister"); //dans Aucune
        sfm.saveToFile();

        Category.getAucune().getTasks().clear();

        sfm.readFromFile(); // "Doit persister" est rapatrié depuis le fichier

        assertTrue(
                Category.getAucune().getTasks().contains(t)
        );

    }
}