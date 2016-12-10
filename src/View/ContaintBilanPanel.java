package View;

import Model.Bilan;
import Model.Task;
import javax.swing.*;
import java.awt.*;
import org.joda.time.*;

/**
 * Created by Antho on 07/12/2016.
 */
public class ContaintBilanPanel extends JPanel {

    private LocalDate dateDebut;
    private LocalDate dateFin;

    private JLabel name, taskNotReleasedAndLate, taskReleasedInTime, taskReleasedLate;
    private JPanel westPanel, eastPanel;

    public ContaintBilanPanel(Bilan b){
        LocalDate db, df;
        db = b.getStart();
        df = b.getEnd();
        GridLayout gl = new GridLayout(2,1);
        int nbLineOfGl = 2;
        westPanel = new JPanel(gl);
        eastPanel = new JPanel(new GridLayout(3,1));
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.dateDebut = db;
        this.dateFin = df;

        System.out.println("bilan pour : " + this.dateDebut + " à " + this.dateFin);
        System.out.println("Tâches non réalisé et en retard : " + b.getPercentageTasksNotReleasedAndLate() + " %");

        name = new JLabel("Bilan",JLabel.CENTER);
        taskNotReleasedAndLate = new JLabel("Tâches non réalisé et en retard : " + b.getPercentageTasksNotReleasedAndLate() + " %",JLabel.CENTER);
        taskReleasedInTime = new JLabel("Tâches réalisé dans les temps : " + b.getPercentageTasksReleasedInTime() + " %",JLabel.CENTER);
        taskReleasedLate = new JLabel("Tâches réalisé en retard : " + b.getPercentageTasksReleasedLate() + " %",JLabel.CENTER);

        eastPanel.add(taskNotReleasedAndLate);
        eastPanel.add(taskReleasedLate);
        eastPanel.add(taskReleasedInTime);

        westPanel.add(new JLabel("Tâches à réaliser sur la période : "));

        System.out.println("avant" + b.getTasks().size());
        for(Task t : b.getTasks()){
            nbLineOfGl++;
            System.out.println(t.toString());
            westPanel.add(new JLabel(t.toString()));
            gl.setRows(nbLineOfGl);
            westPanel.setLayout(gl);
        }

        add(name,BorderLayout.NORTH);
        add(eastPanel,BorderLayout.EAST);
        add(westPanel, BorderLayout.WEST);

    }

}
