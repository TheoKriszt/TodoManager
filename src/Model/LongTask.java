package Model;


import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.time.temporal.ChronoUnit;

/**
 * Classe modèle des tâches longues, permettant d'effectuer tout type d'opération sur celle ci.
 * C'est une classe fille de la classe Task.
 * Elle possède une date de fin (échéance) ainsi qu'une date de début !
 *
 * @see Task,Controller.TaskController,View.TaskView
 */
public class LongTask extends Task {

    LocalDate startDate = LocalDate.now();

    /**
     * Constructeur de tâche.
     * Deux tâches ne peuvent pas avoir le même nom.
     * Par défault elle sera référencé dans la catégorie "Aucune".
     *
     * @param name Nom de la tâche
     * @throws UnsupportedOperationException
     * @throws IllegalArgumentException
     * @see Task#setName(String)
     * @param name
     */
    public LongTask(String name) {
        super(name);
    }

    /**
     *
     * Constructeur d'une tâche.
     * La tâche est créé avec un nom et une catégorie précisé en paramètre.
     *
     * @param name Nom de la tâche
     * @param c Catégorie de la tâche
     * @see Task(String)
     */
    public LongTask(String name, Category c) {
        super(name, c);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Modifie ou initialise la date de début de la tâche longue.
     * La date de début ne peut pas être antérieure à l'échéance de la tâche.
     *
     * @param startDate date du début de tâche
     * @throws IllegalArgumentException
     */
    public void setStartDate(LocalDate startDate) {
        if (startDate.isAfter(echeance)){
            throw new IllegalArgumentException("La date de début ne peut être antérieure à l'échéance");
        }
        if (startDate.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("La tâche ne peut pas être prévue avant aujourd'hui");
        }
        this.startDate = startDate;
    }

    public int getDuration(){
        return Days.daysBetween(startDate, echeance).getDays();
    }

    /**
     * A partir de la durée de la tâche longue on obtient 4 jours :
     *          - Le jour qu'il est à [durée/4] depuis le début de la tâche
     *          - Le jour qu'il est à [durée/2] depuis le début de la tâche
     *          - Le jour qu'il est à [3*durée/4] depuis le début de la tâche
     *          - Le jour qu'il est à [durée] depuis le début de la tâche
     *
     * @return Un tableau de 4 cases contenant les 4 jours
     */
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
