package Model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CategoryTest{

    private Category base;
    private Task t;


    @Before
    public void setUp(){
        Category none = new Category("Aucune");
        Category perso = new Category("Personnel");
        Category travail = new Category("Travail");
        ArrayList<Category> cats = new ArrayList<>();
        cats.add(none);
        cats.add(perso);
        cats.add(travail);
        Category.setCategories(cats);

        t = new Task("move me", Category.getAucune());
        base = new Category("Base");

    }


    @Test(expected=IllegalArgumentException.class)
    public void testBuiltWithNoName(){
        Category failure = new Category(""); // lance la IllegalArgumentException attendue
    }


    @Test
    public void testBuiltWithRightName(){
        assertEquals("Base", base.getName());
    }



    @Test
    public void testAddTask() throws Exception {
        Integer oldCount = base.getTasks().size();
        oldCount++;
        base.addTask(new Task("test"));
        Integer newCount = base.getTasks().size();
        assertEquals(oldCount, newCount);

    }


    @Test
    public void testCategoryAucune() throws Exception {
        assertEquals(Category.getAucune(), new Category("Aucune"));

    }
    //Todo : empecher suppression categorie Aucune ou son renommage

    //TODO : tenter d'ajouter une catégorie déjà existante : s'attendre à un fail
    @Test (expected = IllegalArgumentException.class)
    public void testNonDoublon(){
        new Category("Aucune"); //La catégorie Aucune existe de base, levera une exception pour duplicat de nom
    }

    @Test
    public void testMoveTaskToCategory() throws Exception {

        Category.getAucune().moveTaskToCategory(t, base);
        assertEquals(t, base.getTasks().get(0));
    }


    @Test
    public void testRemoveTaskFromCategory() throws Exception {
        base.addTask(t); // ajout testé par ailleurs
        base.removeTaskFromCategory(t);
        assertFalse(base.getTasks().contains(t));
    }

    @Ignore
    @Test
    public void testArchiveCompletedTask() throws Exception {
        //Todo : compléter si nécessaire
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
    public void testEquals() throws Exception {
        Category doppelganger = new Category("Base");
        if (base.equals(doppelganger)){
            assertEquals(base.getName(), doppelganger.getName());
        }
    }


    @Ignore
    @Test
    public void testGetCategories() throws Exception {
        Category none = new Category("Aucune");
        ArrayList<Category> cats = Category.getCategories();

    }

    @Test
    public void testSetCategories() throws Exception {
        Category.setCategories(new ArrayList<>());
        assertTrue(Category.getCategories().isEmpty());

    }


    @Test
    public void testGetTasks() throws Exception {
        base.addTask(t);
        assertEquals(t, base.getTasks().get(0));
    }

}