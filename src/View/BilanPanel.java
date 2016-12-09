package View;



import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.joda.time.*;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Antho on 07/12/2016.
 */
public class BilanPanel extends ObserverPanel {

    private JPanel northPanel = new JPanel(new BorderLayout(0,25));
    private JPanel centerPan = new JPanel();
    private JButton bilan;
    private JDatePanelImpl startDatePanel,endDatePanel;


    public BilanPanel(){
        this.setLayout(new BorderLayout(0,100));

        Properties p = new Properties();
        p.put("text.today", "Today"); //Date par défaut du datepicker : aujourd'hui
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        UtilDateModel startModel = new UtilDateModel();
        UtilDateModel endModel = new UtilDateModel();
        startDatePanel = new JDatePanelImpl(startModel, p);
        endDatePanel = new JDatePanelImpl(endModel, p);

        JLabel periode = new JLabel("Saisir la période pour l'édition du bilan");

        JLabel db = new JLabel("Début de la période :");
        JPanel jpDebut = new JPanel(new BorderLayout());
        jpDebut.add(db, BorderLayout.NORTH);
        jpDebut.add(startDatePanel, BorderLayout.SOUTH);

        JLabel df = new JLabel("Fin de la période :");
        JPanel jpFin = new JPanel(new BorderLayout());
        jpFin.add(df,BorderLayout.NORTH);
        jpFin.add(endDatePanel,BorderLayout.SOUTH);

        bilan = new JButton("Afficher le bilan");
        JPanel jpButton = new JPanel();
        jpButton.add(bilan);

        northPanel.add(periode,BorderLayout.NORTH);
        JPanel jp = new JPanel(new GridLayout(1,3,50,0));
        jp.add(jpDebut);
        jp.add(jpFin);
        jp.add(jpButton);
        northPanel.add(jp,BorderLayout.CENTER);
        this.add(northPanel,BorderLayout.NORTH);
        this.add(centerPan, BorderLayout.CENTER);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("BilanPanel::update()");

    }

    public JButton getBilanButton(){
        return bilan;
    }

    public JPanel getCenterPan() {
        return centerPan;
    }

    public JDatePanelImpl getStartDatePanel() {
        return startDatePanel;
    }

    public JDatePanelImpl getEndDatePanel() {
        return endDatePanel;
    }
}
