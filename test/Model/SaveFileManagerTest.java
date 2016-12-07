package Model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import static org.junit.Assert.*;

public class SaveFileManagerTest {

    private SaveFileManager O;

    @Before
    public void setUp() throws Exception {
        O = SaveFileManager.O();
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
        O.readFromFile();
        //System.out.println(Category.getCategories());
        //System.out.println("Size : " + Category.getCategories().size());

        assertEquals(3, Category.getCategories().size());

    }

    @Test
    public void testPersistenceCategorie() throws Exception {
        O.readFromFile();
        O.saveToFile();
        ArrayList<Category> before = (ArrayList<Category>) Category.getCategories().clone();
        Category.setCategories(null);
        O.readFromFile();
        assertEquals(before, Category.getCategories());

    }

    @Test
    public void testPersistenceSuppressionCategorie() throws Exception {

        O.readFromFile();
        int oldCount = Category.getCategories().size();
        oldCount--;
        Category cat = Category.getCategories().get(1);
        cat.removeCategory();
        O.saveToFile();
        O.readFromFile();
        assertEquals(oldCount, Category.getCategories().size());




    }

    @Test
    public void testPersistenceTache() throws Exception {
        O.readFromFile();
        Task t = new SimpleTask("Doit persister");
        O.saveToFile();
        O.readFromFile();

        assertTrue(
                Category.categoryAucune().getTasks().contains(t)
        );

    }
}