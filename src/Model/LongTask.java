package Model;


import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.time.temporal.ChronoUnit;

/**
 * Created by achaillot on 06/12/16.
 */
public class LongTask extends Task {


    LocalDate startDate = LocalDate.now();

    public LongTask(String name) {
        super(name);
    }

    public LongTask(String name, Category c) {
        super(name, c);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        startDate.isAfter(echeance);
        if (startDate.isAfter(echeance)){
            throw new IllegalArgumentException("La date de début ne peut être antérieure à l'échéance");
        }
        this.startDate = startDate;
    }

    public int getDuration(){
        return Days.daysBetween(startDate, echeance).getDays();
    }

    @Override
    public boolean isLate() {
        LocalDate ld25, ld50, ld75, ld100;
        long d = getDuration();

        ld25 = startDate.plusDays((int) (d/4));
        ld50 = startDate.plusDays((int) (d/2));
        ld75 = startDate.plusDays((int) (3*d/4));
        ld100 = startDate.plusDays((int) d);

        LocalDate now = LocalDate.now();

        if (      (now.isAfter(ld100) && progress < 100)
               || (now.isAfter(ld75) && progress < 75)
               || (now.isAfter(ld50) && progress < 50)
               || (now.isAfter(ld25) && progress < 25)
                ){
            return true;
        }else{
            return false;
        }








    }


}
