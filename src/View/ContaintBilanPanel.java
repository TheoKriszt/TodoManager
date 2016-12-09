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

    public ContaintBilanPanel(LocalDate db, LocalDate df){

        super(new BorderLayout(100,30));
        GridLayout gl = new GridLayout(2,1);
        int nbLineOfGl = 2;
        westPanel = new JPanel(gl);
        eastPanel = new JPanel(new GridLayout(3,1));
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.dateDebut = db;
        this.dateFin = df;
        Bilan bilan = Bilan.instance();
        bilan.setStart(this.dateDebut);
        bilan.setEnd(this.dateFin);
        bilan.loadTasks();

        System.out.println("bilan pour :" + this.dateDebut + " à " + this.dateFin);
        System.out.println("Tâches non réalisé et en retard : " + bilan.getPercentageTasksNotReleasedAndLate() + " %");

        name = new JLabel("Bilan",JLabel.CENTER);
        taskNotReleasedAndLate = new JLabel("Tâches non réalisé et en retard : " + bilan.getPercentageTasksNotReleasedAndLate() + " %",JLabel.CENTER);
        taskReleasedInTime = new JLabel("Tâches réalisé dans les temps : " + bilan.getPercentageTasksReleasedInTime() + " %",JLabel.CENTER);
        taskReleasedLate = new JLabel("Tâches réalisé en retard : " + bilan.getPercentageTasksReleasedLate() + " %",JLabel.CENTER);

        eastPanel.add(taskNotReleasedAndLate);
        eastPanel.add(taskReleasedLate);
        eastPanel.add(taskReleasedInTime);

        westPanel.add(new JLabel("Tâches à réaliser sur la période : "));

        System.out.println("avant" + bilan.getTasks().size());
        for(Task t : bilan.getTasks()){
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

  /*  public void paintComponent(Graphics g){
        Bilan bilan = Bilan.instance();
        bilan.loadTasks();
        bilan.setStart(this.dateDebut);
        bilan.setEnd(this.dateFin);
        int height = this.getHeight();

        g.drawLine(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
        for(Task t : bilan.getTasks()){
            g.drawString(t.toString(),10,height);
            height -= 15;
        }
        g.drawString("Tâches non réalisé et en retard : ", this.getWidth()/2 + 10, this.getHeight()/3);
        g.drawString(Float.toString(bilan.getTasksNotReleasedAndLate()) + "%", this.getWidth()/2 + 50, this.getHeight()/3);

        g.drawString("Tâches réalisé en retard : ", this.getWidth()/2 + 10, this.getHeight()/2);
        g.drawString(Float.toString(bilan.getTasksReleasedLate()) + "%", this.getWidth()/2 + 50, this.getHeight()/2);

        g.drawString("Tâches réalisé dans les temps : ", this.getWidth()/2 + 10, 3*this.getHeight()/4);
        g.drawString(Float.toString(bilan.getTasksReleasedInTime()) + "%", this.getWidth()/2 + 50, 3*this.getHeight()/4);

        paint(g);

        System.out.println("lalala");
    }*/

}
