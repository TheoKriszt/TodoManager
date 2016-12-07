package View;



import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Antho on 07/12/2016.
 */
public class BilanPanel extends JPanel {

    private JPanel northPanel = new JPanel(new BorderLayout(0,25));
    private JPanel centerPan = new JPanel();


    public BilanPanel(){
        this.setLayout(new BorderLayout());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
                    /*Date d = sdf.parse(dateDebut.getText());
                    LocalDate ldb = LocalDate.from(Instant.ofEpochMilli(d.getTime()));
                    d = sdf.parse(dateFin.getText());
                    LocalDate ldf = LocalDate.from(Instant.ofEpochMilli(d.getTime()));*/
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
                dtf.withLocale(Locale.FRANCE);
                LocalDate ldb = LocalDate.parse(dateDebut.getText(),dtf);
                LocalDate ldf = LocalDate.parse(dateFin.getText(),dtf);
                JPanel containtBilan = new ContaintBilanPanel(ldb,ldf);
                centerPan.add(containtBilan);

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
}
