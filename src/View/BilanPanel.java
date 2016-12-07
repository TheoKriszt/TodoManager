package View;

import Model.Bilan;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * Created by Antho on 07/12/2016.
 */
public class BilanPanel extends JPanel {

    private JPanel northPanel = new JPanel(new BorderLayout(0,25));
    private JPanel centerPan = new JPanel();


    public BilanPanel(){
        setLayout(new BorderLayout());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateFormatter dateFormatter=new DateFormatter(sdf);
        DefaultFormatterFactory dateFormatterFactory =new DefaultFormatterFactory(dateFormatter,new DateFormatter(),dateFormatter);

        JLabel periode = new JLabel("Saisir la période pour l'édition du bilan");

        JLabel db = new JLabel("Début de la période :");
        JFormattedTextField dateDebut = new JFormattedTextField(dateFormatterFactory);
        JPanel jpDebut = new JPanel(new BorderLayout());
        jpDebut.add(db, BorderLayout.NORTH);
        jpDebut.add(dateDebut, BorderLayout.SOUTH);

        JLabel df = new JLabel("Fin de la période :");
        JFormattedTextField dateFin = new JFormattedTextField(dateFormatterFactory);
        JPanel jpFin = new JPanel(new BorderLayout());
        jpFin.add(df,BorderLayout.NORTH);
        jpFin.add(dateFin,BorderLayout.SOUTH);

        //Todo : gérer le listener
        JButton bilan = new JButton("Afficher le bilan");
        bilan.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    LocalDate db = sdf.parse(dateDebut.getText());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                JPanel containtBilan = new ContaintBilanPanel()
            }
        });
        JPanel jpButton = new JPanel();
        jpButton.add(bilan);

        northPanel.add(periode);
        JPanel jp = new JPanel(new BorderLayout(50,0));
        jp.add(jpDebut);
        jp.add(jpFin);
        jp.add(jpButton);
        northPanel.add(jp);

    }
}
