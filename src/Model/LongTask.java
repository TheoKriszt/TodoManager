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
        if (startDate.isAfter(echeance)){
            throw new IllegalArgumentException("La date de début ne peut être antérieure à l'échéance");
        }
        this.startDate = startDate;
    }

    public int getDuration(){
        return Days.daysBetween(startDate, echeance).getDays();
    }

    private LocalDate[] getQuartiles(){
        LocalDate[] quartiles = new LocalDate[4];
        long d = getDuration();

        quartiles[0] = startDate.plusDays((int) (d/4));
        quartiles[1] = startDate.plusDays((int) (d/2));
        quartiles[2] = startDate.plusDays((int) ((3*d)/4));
        quartiles[3] = startDate.plusDays((int) (d));

        return quartiles;
    }

    /**
     * Pour chaque échéance intermediaire décroissante (réparties en quartiles),
     * @return true si l'avancement est inférieur à celui attendu pour cette échéance intermediaire
     */
    @Override
    public boolean isLate() {
        LocalDate ld25, ld50, ld75, ld100;
        LocalDate[] quartiles = getQuartiles();

        LocalDate now = LocalDate.now();

        return (
                  (now.isAfter(quartiles[3]) && progress < 100)
               || (now.isAfter(quartiles[2]) && progress < 75)
               || (now.isAfter(quartiles[1]) && progress < 50)
               || (now.isAfter(quartiles[0]) && progress < 25)
                );

    }

    /**
     *
     * @return la prochaine date d'échéance intermédiaire
     */
    @Override
    public LocalDate getNextEcheance(){
        LocalDate[] quartiles = getQuartiles();

        if (progress > 75) return quartiles[3];
        else if (progress > 50) return quartiles[2];
        else if (progress > 25) return quartiles[1];
        else return quartiles[0];

    }


}
