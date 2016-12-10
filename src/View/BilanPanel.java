package View;

import Controller.BilanController;
import Model.Bilan;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * BilanPanel est la classe s'occupant de l'affichage générale de la fenêtre du bilan séparé en deux parties.
 * Elle contient : -   un northPanel permettant la selection de la période et un bouton permettant l'affichage du bilan.
 *                 -   un centerPanel contenant l'affichage du bilan.
 */
public class BilanPanel extends ObserverPanel {

    private JPanel centerPanel, northPanel, askForDatesPanel;
    private JButton bilanButton;
    private JDatePanelImpl startDatePicker, endDatePicker;
    private JLabel dateDebutLabel, dateFinLabel;

    /**
     * Constructeur du BilanPanel.
     * Le controller passé en paramètre permet la mise en place des listener nécessaire au Panel.
     *
     * @param bpc controller du BilanPanel.
     * @see BilanController
     */
    public BilanPanel(BilanController bpc){
        /**
         * Le choix de la période avec des JDatePickers se fait dans le northPanel
         * L'affichage du bilan arrive dans le centerPanel
         */

        setLayout(new BorderLayout(0, 100));

        createNorthPanel();

        centerPanel = new JPanel();

        add(centerPanel, BorderLayout.CENTER);
        bpc.setListener(this);
    }

    /**
     * Méthode permettant de créer le NorthPanel
     * Les indicateurs "Entrez date de début" et "Entrez date de fin" sont en north
     * Le JDatePicker de date de début en west, celui de date de fin en east
     * Le bouton pour appeler le rendu est en south
     */
    private void createNorthPanel() {
        northPanel =new JPanel(new BorderLayout(0, 25));
        northPanel.setBorder(BorderFactory.createTitledBorder("Saisir la période pour l'édition du bilan"));

        Properties p = new Properties();
        p.put("text.today", "Today"); //Date par défaut du datepicker : aujourd'hui
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        UtilDateModel startModel = new UtilDateModel();
        UtilDateModel endModel = new UtilDateModel();

        startDatePicker = new JDatePanelImpl(startModel, p);
        endDatePicker = new JDatePanelImpl(endModel, p);

        northPanel.add(startDatePicker, BorderLayout.WEST);
        northPanel.add(endDatePicker, BorderLayout.EAST);

        dateDebutLabel = new JLabel("Début de la période :");
        dateFinLabel = new JLabel("Fin de la période :", SwingConstants.RIGHT);
        JPanel dateLabels = new JPanel(new GridLayout(1, 2));

        dateLabels.add(dateDebutLabel);
        dateLabels.add(dateFinLabel);
        northPanel.add(dateLabels, BorderLayout.NORTH);

        bilanButton = new JButton("Afficher le bilan");
        northPanel.add(bilanButton, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("BilanPanel::update()");
        if (o instanceof Bilan){
            Bilan b = (Bilan)o;
            System.out.println("Update sur vue, avec " + b.getStart() + " --> " + b.getEnd());
            centerPanel.removeAll();
            ContaintBilanPanel containtBilan = new ContaintBilanPanel(b);

            centerPanel.add(containtBilan);
            centerPanel.revalidate();
            centerPanel.repaint();
        }


    }

    public JButton getBilanButton(){
        return bilanButton;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public JDatePanelImpl getStartDatePicker() {
        return startDatePicker;
    }

    public JDatePanelImpl getEndDatePicker() {
        return endDatePicker;
    }
}
