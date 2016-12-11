package Model;

import Controller.MainFrameController;
import View.MainFrame;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CategoryTest{

    private static Category base, aucune, perso, travail;
    private static Task t;


    @BeforeClass
    public static void setUp(){
        //Initialisation comme dans le main
        TodoManager tm = new TodoManager(); // L'instance du gestionnaire de TodoList

        MainFrameController mainFrameController = new MainFrameController(tm); //La fenêtre de l'appi et son contrôleur
        MainFrame mainFrame = new MainFrame(mainFrameController);

        //Categories statiques, or le @Before les initialisera à chaque fois, lancant des Exceptions
        //On veut rester dans un contexte non statique pour l'initialisation (pas de @BeforeClass)
        // --> le try/catch ne fais lancer les initialisation que pour le premier test
        try{
            Category.getAucune();
        }catch (IndexOutOfBoundsException e){
            aucune = new Category("Aucune");
            base = new Category("Base");
            travail = new Category("Travail");
            perso = new Category("Personnel");
            t = new Task("Tâche");
        }

    }

    /**
     * Les catégories sans nom ne sont pas autorisées
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBuiltWithNoName(){
        Category failure = new Category(""); // lance la IllegalArgumentException attendue
    }

    /**
     * Une catégorie ne peut avoir le nom d'une autre
     */
    @Test(expected=IllegalArgumentException.class)
    public void testBuiltWithPreexistingName(){
        Category first = new Category("unNom");
        Category failure = new Category("unNom"); // lance la IllegalArgumentException attendue
    }

    @Test(expected=IllegalArgumentException.class)
    public void testRenameNameAlreadyTaken(){
        Category first = new Category("unNom");
        Category failure = new Category("unAutreNom");
        failure.renameCategory("unNom");
    }


    @Test
    public void testBuiltWithRightName(){
        assertEquals("Base", base.getName());
    } //



    @Test
    public void testAddTask() throws Exception {
        Integer oldCount = base.getTasks().size();
        oldCount++;
        base.addTask(new Task("test"));
        Integer newCount = base.getTasks().size();
        assertEquals(oldCount, newCount);
    }

    //Todo : empecher suppression categorie Aucune ou son renommage
    @Test (expected = UnsupportedOperationException.class)
    public void removeAucune(){
        Category.getAucune().removeCategory();
    }

    @Test (expected = UnsupportedOperationException.class)
    public void renameAucune(){
        Category.getAucune().renameCategory("SomeName");
    }

    @Test
    public void testMoveTaskToCategory() throws Exception {
        Category.getAucune().moveTaskToCategory(t, base);
        assertEquals(t, base.getTasks().get(0));
    }

    @Test
    public void testRenameCategory() throws Exception {
        base.renameCategory("Bond, James Bond");
        assertEquals("Bond, James Bond", base.getName());
    }

    @Test
    public void testRemoveCategory() throws Exception {
        base.addTask(t);
        base.removeCategory(); //Rapatrie les tâches de base vers la catégorie Aucune

        assertTrue(Category.getAucune().getTasks().contains(t));
    }

    @Test
    public void testGetTasks() throws Exception {
        base.addTask(t);
        assertEquals(t, base.getTasks().get(0));
    }

}