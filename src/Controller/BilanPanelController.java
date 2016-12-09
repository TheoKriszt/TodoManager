package Controller;

import View.BilanPanel;
import View.ContaintBilanPanel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.joda.time.LocalDate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Antho on 09/12/2016.
 */
public class BilanPanelController {

    public BilanPanelController(){

    }

    public void setListener(BilanPanel bp){
        JButton bilan;
        JPanel centerPan;
        JDatePanelImpl startDatePanel,endDatePanel;
        bilan = bp.getBilanButton();
        centerPan = bp.getCenterPan();
        startDatePanel = bp.getStartDatePanel();
        endDatePanel = bp.getEndDatePanel();

        bilan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centerPan.removeAll();

                LocalDate ldb = new LocalDate(startDatePanel.getModel().getYear(), startDatePanel.getModel().getMonth(), startDatePanel.getModel().getDay());
                LocalDate ldf = new LocalDate(endDatePanel.getModel().getYear(), endDatePanel.getModel().getMonth(), endDatePanel.getModel().getDay());

                JPanel containtBilan = new ContaintBilanPanel(ldb,ldf);
                centerPan.add(containtBilan);
                centerPan.validate();
            }
        });
    }

}
