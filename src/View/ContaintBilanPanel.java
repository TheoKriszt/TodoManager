package View;

import Model.Bilan;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * Created by Antho on 07/12/2016.
 */
public class ContaintBilanPanel extends JPanel {

    private LocalDate dateDebut;
    private LocalDate dateFin;

    public ContaintBilanPanel(LocalDate db, LocalDate df){
        this.dateDebut = db;
        this.dateFin = df;
    }

    public void paintComponent(Graphics g){
        Bilan bilan = Bilan.instance();
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
    }

}
