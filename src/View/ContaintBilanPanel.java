package View;

import Model.Bilan;
import Model.Task;
import javax.swing.*;
import java.awt.*;
import org.joda.time.*;

/**
 * Panel gérant l'affichage du bilan
 * @see BilanPanel
 */
public class ContaintBilanPanel extends JPanel {

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private JLabel name, taskNotReleasedAndLate, taskReleasedInTime, taskReleasedLate;
    private JPanel westPanel, eastPanel;

    /**
     * Constructeur du ContaintBilanPanel.
     * organise et structure l'affichage du bilan.
     * affichage des tâches à gauche, les différents pourcentages à droite.
     * @param b bilan contenant les données à afficher
     */
    public ContaintBilanPanel(Bilan b){
        LocalDate db, df;
        db = b.getStart();
        df = b.getEnd();
        GridLayout gl = new GridLayout(2,1);
        int nbLineOfGl = 2;
        westPanel = new JPanel(gl);
        eastPanel = new JPanel(new GridLayout(3,1));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBorder(BorderFactory.createTitledBorder("Bilan"));

        this.dateDebut = db;
        this.dateFin = df;

        System.out.println("bilan pour : " + this.dateDebut + " à " + this.dateFin);
        System.out.println("Tâches non réalisé et en retard : " + b.getPercentageTasksNotReleasedAndLate() + " %");

        taskNotReleasedAndLate = new JLabel("Tâches non réalisées et en retard : " + b.getPercentageTasksNotReleasedAndLate() + " %",JLabel.CENTER);
        taskReleasedInTime = new JLabel("Tâches réalisées dans les temps : " + b.getPercentageTasksReleasedInTime() + " %",JLabel.CENTER);
        taskReleasedLate = new JLabel("Tâches réalisées en retard : " + b.getPercentageTasksReleasedLate() + " %",JLabel.CENTER);

        eastPanel.add(taskNotReleasedAndLate);
        eastPanel.add(taskReleasedLate);
        eastPanel.add(taskReleasedInTime);

        westPanel.add(new JLabel("Tâches à échéances comprises dans la période : "));

        for(Task t : b.getTasks()){
            nbLineOfGl++;
            westPanel.add(new JLabel(t.toString()));
            gl.setRows(nbLineOfGl);
            westPanel.setLayout(gl);
        }

        add(eastPanel,BorderLayout.EAST);
        add(westPanel, BorderLayout.WEST);


    }

}
