package Model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TaskTest extends TestCase {

    private Task endsToday;
    private Task endsTomorrow;
    private Task endedYesterday;
    private Task aMonthAgo;
    private Task inAMonth;

    @BeforeClass
    public void initCategories(){
        Category.getCategories();
    }

    @Before
        public void setUp() throws Exception {

        endsToday = new SimpleTask("today");
        endsTomorrow = new SimpleTask("tomorrow");
        endedYesterday = new SimpleTask("yesterday");
        aMonthAgo = new SimpleTask("A month ago");
        inAMonth = new SimpleTask("In a month");

        endsTomorrow.setEcheance(LocalDate.now().plusDays(1));
        endedYesterday.setEcheance(LocalDate.now().minusDays(1));
        aMonthAgo.setEcheance(LocalDate.now().minusMonths(1));
        inAMonth.setEcheance(LocalDate.now().plusMonths(1));
    }

    @Test (expected=IllegalArgumentException.class)
    public void testNomNonvide(){
        SimpleTask nomVide = new SimpleTask(""); //argument chaîne vide interdit
    }

    @Test
    public void testSetContenu() throws Exception {



    }

    @Test
    public void testSetEcheance() throws Exception {

    }

    @Test
    public void testGetProgress() throws Exception {

    }

    @Test
    public void testSetProgress() throws Exception {

    }

    @Test
    public void testIsLate() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {

    }

    @Test
    public void testSortByDueDate() throws Exception {

    }

    @Test
    public void testIsBetween() throws Exception {

    }

    @Test
    public void testReleasedLate() throws Exception {

    }
}