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
 * Created by Antho on 09/12/2016.
 */
public class BilanController {

    private Bilan bilan;

    public BilanController(Bilan b){
        bilan = b;
    }

    public void setListener(BilanPanel bp){
        JButton bilan = bp.getBilanButton();
        JDatePanelImpl startModel = bp.getStartDatePicker();
        JDatePanelImpl endModel = bp.getEndDatePicker();



//        start = new LocalDate(startModel.getModel().getYear(), startModel.getModel().getMonth(), startModel.getModel().getDay());
//        end = new LocalDate(endModel.getModel().getYear(), endModel.getModel().getMonth(), endModel.getModel().getDay());


        bilan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateContent(startModel, endModel);
            }
        });
    }

    private void updateContent(JDatePanelImpl start, JDatePanelImpl end){
        System.out.println("BilanController::updateContent()");
        bilan.update(start, end);
    }

}
