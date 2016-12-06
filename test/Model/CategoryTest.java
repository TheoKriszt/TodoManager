package Model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest extends TestCase {

    private Category base;

    @Before
    public void setUp() throws Exception {
        base = new Category("Base");

    }


    @Test (expected = IllegalArgumentException.class)
    public void nameIsSet(){
        new Category("");
    }

    @Test
    public void builtWithName(){
        assertEquals("Base", base.getName());
    }

    @Test
    public void testAddTask() throws Exception {

    }

    @Test
    public void testCategoryAucune() throws Exception {

    }

    @Test
    public void testMoveTaskToCategory() throws Exception {

    }

    @Test
    public void testRemoveTaskFromCategory() throws Exception {

    }

    @Test
    public void testArchiveCompletedTask() throws Exception {

    }

    @Test
    public void testRenameCategory() throws Exception {

    }

    @Test
    public void testRemoveCategory() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testGetCategories() throws Exception {

    }

    @Test
    public void testSetCategories() throws Exception {

    }

    @Test
    public void testGetTasks() throws Exception {

    }
}