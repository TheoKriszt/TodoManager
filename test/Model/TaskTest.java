package Model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TaskTest{

    private Task endsToday;
    private Task endsTomorrow;
    private Task endedYesterday;
    private Task aMonthAgo;
    private Task inAMonth;

    @Before
        public void setUp() throws Exception {

        Category none = new Category("Aucune");
        Category perso = new Category("Personnel");
        Category travail = new Category("Travail");
        ArrayList<Category> cats = new ArrayList<>();
        cats.add(none);
        cats.add(perso);
        cats.add(travail);
        Category.setCategories(cats);

        endsToday = new SimpleTask("today");
        endsTomorrow = new SimpleTask("tomorrow");
        endedYesterday = new SimpleTask("yesterday");
        aMonthAgo = new LongTask("A month ago");
        inAMonth = new SimpleTask("In a month");

        endsToday.setProgress(100);

        endsTomorrow.echeance = (LocalDate.now().plusDays(1));
        endedYesterday.echeance = (LocalDate.now().minusDays(1));
        aMonthAgo.echeance = (LocalDate.now().minusMonths(1));
        inAMonth.echeance = (LocalDate.now().plusMonths(1));
    }

    @Test (expected=IllegalArgumentException.class)
    public void testNomNonvide(){
        Task nomVide = new SimpleTask(""); //argument chaîne vide interdit
    }

    @Test
    public void testSetContenu() throws Exception {

        endsToday.setContenu("du contenu");
        assertEquals("du contenu", endsToday.getContenu());
    }

    @Test
    public void testSetEcheance() throws Exception {
        endedYesterday.setEcheance(LocalDate.now().plusDays(1));
        assertEquals(LocalDate.now().plusDays(1), endedYesterday.echeance);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSetEcheanceTropTot() throws Exception {
        endsTomorrow.setEcheance(LocalDate.now());
    }

    @Test
    public void testGetProgress() throws Exception {
        assertThat(endsToday.getProgress(), is(100));
    }

    @Test
    public void testSetProgress() throws Exception {
        endsToday.setProgress(0);
        assertThat(endsToday.getProgress(), is(0));
    }

    @Test
    public void testIsLate() throws Exception {
        assertTrue(endedYesterday.isLate()); //sensée finir hier, avancement à 0 --> en retard
    }

    @Test
    public void testLongIsLate() throws Exception {
        LongTask late = new LongTask("en retard");
        late.setStartDate(LocalDate.now().minusDays(10)); // début il y a 10 jours, fin aujourd'hui
        late.setProgress(50); //Avancement : 50% --> pas assez : en retard

        assertTrue(late.isLate());

    }

    @Test
    public void testLongIsNotLate() throws Exception {
        assertFalse(inAMonth.isLate());
    }

    @Test
    public void testEquals() throws Exception {
        Task a = new SimpleTask(" ");
        Task b = new SimpleTask(" ");
        assertEquals(a, b);
    }

    @Test
    public void testDifferents() throws Exception {
        Task a = new SimpleTask(" ");
        Task b = new LongTask(" ");
        assertNotEquals(a, b);
    }

    @Test
    public void testSortByDueDate() throws Exception {

        ArrayList<Task> tasks  = new ArrayList<>();
        tasks.add(endsToday);
        tasks.add(endedYesterday);

        Task.sortByDueDate(tasks);


        LocalDate first = tasks.get(0).echeance,
                second = tasks.get(1).echeance;

        assertFalse(first.isAfter(second)); //L'élément second est postérieur ou simultané au premier
    }

    @Test (expected = IllegalArgumentException.class)
    public void teststartDateCoherente(){
        LongTask task = new LongTask("weirdo");
        task.setStartDate(LocalDate.now().plusMonths(1));
    }

    @Test
    public void testIsBetween() throws Exception {
        assertTrue(endsToday.isBetween(
                LocalDate.now(), LocalDate.now().plusDays(1) // Aujourd'hui est bien entre now() et demain
        ));

    }

    @Test
    public void testIsNotBetweenButAfter() throws Exception {
        assertFalse(endsTomorrow.isBetween(
                LocalDate.now().minusDays(1), LocalDate.now() // Demain n'est pas entre hier et aujourd'hui
        ));
    }

    @Test
    public void testIsNotBetweenButBefore() throws Exception {
        assertFalse(endedYesterday.isBetween(
                LocalDate.now(), LocalDate.now().plusDays(1) // Hier n'est pas entre aujourd'hui et demain
        ));

    }

    @Test
    public void testNotReleasedLate() throws Exception {
        assertFalse(endsToday.releasedLate()); // complétée aujourd'hui, en temps et en heure
    }
}