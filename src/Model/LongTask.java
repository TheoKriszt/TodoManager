package Model;

import java.time.LocalDate;
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
        if (startDate.isAfter(echeance)){
            throw new IllegalArgumentException("La date de début ne peut être antérieure à l'échéance");
        }
        this.startDate = startDate;
    }

    public long getDuration(){
        return ChronoUnit.DAYS.between(startDate, echeance); // durée de la tâche
    }

    @Override
    public boolean isLate() {
        LocalDate ld25, ld50, ld75, ld100;
        long d = getDuration();

        ld25 = startDate.plusDays(d/4);
        ld50 = startDate.plusDays(d/2);
        ld75 = startDate.plusDays(3*d/4);
        ld100 = startDate.plusDays(d);

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
