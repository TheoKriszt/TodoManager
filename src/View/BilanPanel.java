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


    public BilanPanel(){
        this.setLayout(new BorderLayout(0,100));

        /* fonctionne avec JFormattedTextField mais problème avec le parse
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        DateFormatter dateFormatter=new DateFormatter(sdf);
        DefaultFormatterFactory dateFormatterFactory =new DefaultFormatterFactory(dateFormatter,new DateFormatter(),dateFormatter);
        */

        /**
         * TODO : insérer les datePickers comme il faut
         */
        Properties p = new Properties();
        p.put("text.today", "Today"); //Date par défaut du datepicker : aujourd'hui
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        UtilDateModel startModel = new UtilDateModel();
        UtilDateModel endModel = new UtilDateModel();
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel, p);
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, p);

        DateTime startDate = (DateTime) startDatePanel.getModel().getValue();
        DateTime endDate = (DateTime) startDatePanel.getModel().getValue();

        //Gestion de la convertion calendar => localDate a l'aide de JodaTime
        /*TimeZone tz = startDate.getTimeZone();
        DateTimeZone jodaTz = DateTimeZone.forID(tz.getID());
        DateTime startDateTime = new DateTime(startDate.getTimeInMillis(),jodaTz);*/

        /*tz = endDate.getTimeZone();
        jodaTz = DateTimeZone.forID(tz.getID());
        DateTime endDateTime = new DateTime(endDate.getTimeInMillis(),jodaTz);*/

        JLabel periode = new JLabel("Saisir la période pour l'édition du bilan");

        JLabel db = new JLabel("Début de la période :");
        JTextField dateDebut = new JTextField();
        JPanel jpDebut = new JPanel(new BorderLayout());
        jpDebut.add(db, BorderLayout.NORTH);
        jpDebut.add(startDatePanel, BorderLayout.SOUTH);

        JLabel df = new JLabel("Fin de la période :");
        JTextField dateFin = new JTextField();
        JPanel jpFin = new JPanel(new BorderLayout());
        jpFin.add(df,BorderLayout.NORTH);
        jpFin.add(endDatePanel,BorderLayout.SOUTH);

        //Todo : gérer le listener
        JButton bilan = new JButton("Afficher le bilan");
        bilan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                    /*Date d = sdf.parse(dateDebut.getText());
                    LocalDate ldb = LocalDate.from(Instant.ofEpochMilli(d.getTime()));
                    d = sdf.parse(dateFin.getText());
                    LocalDate ldf = LocalDate.from(Instant.ofEpochMilli(d.getTime()));*/
                centerPan.removeAll();
                //Todo : removeAll ineffectif (déjà résolu ?)
                //Todo : Utiliser un formatter si besoin, adapter l'architecture au datePicker
                System.out.println("date de debut : " + startDatePanel.getModel().getValue().toString());
                System.out.println("date de fin : " + endDatePanel.getModel().getValue().toString());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
                dtf.withLocale(Locale.FRANCE);
                LocalDate ldb = startDate.toLocalDate();
                LocalDate ldf = endDate.toLocalDate();

                JPanel containtBilan = new ContaintBilanPanel(ldb,ldf);
                centerPan.add(containtBilan);
                centerPan.validate();

            }
        });
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
}
