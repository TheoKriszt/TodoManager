package Controller;

import Model.Bilan;
import View.BilanPanel;
import View.ContaintBilanPanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import static org.joda.time.DateTimeZone.UTC;

/**
 * Classe de contrôle pour le bilan
 */
public class BilanController {

    private Bilan bilan;

    /**
     * Constructeur du BilanController
     * @param b Le bilan à contrôler
     */
    public BilanController(Bilan b){
        bilan = b;
    }

    /**
     * Définie les différents listeners nécessaires aux composants du bilan
     * @param bp La vue du bilan sur laquel sera appliqué les listeners
     */
    public void setListener(BilanPanel bp){
        JButton bilan = bp.getBilanButton();
        JDatePanelImpl startModel = bp.getStartDatePicker();
        JDatePanelImpl endModel = bp.getEndDatePicker();
        bilan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContent(startModel, endModel);
            }
        });
    }

    /**
     * Met à jour le contenue du bilan
     * @param start la nouvelle date de début pour le bilan
     * @param end   la nouvelle date de fin pour le bilan
     * @see Bilan#update(JDatePanelImpl, JDatePanelImpl)
     */
    private void updateContent(JDatePanelImpl start, JDatePanelImpl end){
        System.out.println("BilanController::updateContent()");
        bilan.update(start, end);
    }

}
