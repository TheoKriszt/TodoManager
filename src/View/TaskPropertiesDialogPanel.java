package View;

import Model.LongTask;
import Model.Task;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Properties;

/**
 * Created by Theo on 10/12/2016.
 */
public class TaskPropertiesDialogPanel extends JFrame{

    private JPanel content;
    private JTextField nameField;
    private JCheckBox completedCheckBox;
    private JComponent progressContainer;
    private JTextArea descriptifArea;
    private JLabel nameLabel, progressLabel, descriptifLabel, startDateLabel, endDateLabel;
    private JButton yesButton, noButton;
    private JDatePanelImpl startDatePicker;
    private JPanel  nameLine = new JPanel(),
            progressLine = new JPanel(),
            descriptifLine = new JPanel(),
            datesLine = new JPanel(new GridLayout(1, 2)),
            datePickersLine = new JPanel(),
            confirmLine = new JPanel();

    public JDatePanelImpl getEndDatePicker() {
        return endDatePicker;
    }

    public JDatePanelImpl getStartDatePicker() {
        return startDatePicker;
    }

    private JDatePanelImpl endDatePicker;


    public JTextField getNameField() {
        return nameField;
    }

    public JCheckBox getCompletedCheckBox() {
        return completedCheckBox;
    }

    public JComponent getProgress() {
        return progressContainer;
    }

    public JTextArea getDescriptifArea() {
        return descriptifArea;
    }

    public JButton getNoButton() {
        return noButton;
    }

    public JButton getYesButton() {
        return yesButton;
    }

    public TaskPropertiesDialogPanel(){
        setLocationRelativeTo(null);





        yesButton = new JButton("Confirmer");
        noButton = new JButton("Annuler");

        confirmLine.add(yesButton);
        confirmLine.add(noButton);

        nameLabel = new JLabel("Nom : ");
        progressLabel = new JLabel("Avancement : ");
        descriptifLabel = new JLabel("Description : ");
        startDateLabel = new JLabel("Date de début : ");
        endDateLabel = new JLabel("Date de fin : ");

        nameLine.add(nameLabel);
        progressLine.add(progressLabel);
        descriptifLine.add(descriptifLabel);
        datesLine.add(startDateLabel);
        datesLine.add(endDateLabel);

        nameField = new JTextField(40);

        nameLine.add(nameField);

        progressContainer = new JSpinner(
                new SpinnerNumberModel(
                        0,   //initial value
                        0,   //min
                        100, //max
                        1)  //step
        );

        progressLine.add(progressContainer);

        descriptifArea = new JTextArea(10, 30);
        descriptifLine.add(descriptifArea);

        Properties p = new Properties();
        p.put("text.today", "Today"); //Date par défaut du datepicker : aujourd'hui
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        UtilDateModel startModel = new UtilDateModel();
        UtilDateModel endModel = new UtilDateModel();

        startDatePicker = new JDatePanelImpl(startModel, p);
        endDatePicker = new JDatePanelImpl(endModel, p);
        datePickersLine.add(startDatePicker);
        datePickersLine.add(endDatePicker);

        content = new JPanel();
        content.add(nameLine);
        content.add(progressLine);
        content.add(descriptifLine);
        content.add(datesLine);
        content.add(datePickersLine);
        content.add(confirmLine);

        //content.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setContentPane(content);
        getContentPane().setLayout(
                new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS)
        );
        pack();
        revalidate();

    }

    public void loadFrom(Task t){
        System.out.println("SPDP::loadFrom("+ (t==null ? "null" : t.getName()) +")");


        if (t != null){ //Si la tâche est déjà initialisée : on ne fait que la modifier
            nameField.setText(t.getName());
            descriptifArea.setText(t.getContenu());
            if (!(t instanceof LongTask)){ //Si ce n'est qu'une tâche simple : virer le datePicker de début, le selecteur de progress
                datePickersLine.remove(startDatePicker);
                progressLine.remove(progressContainer);
                progressContainer = new JCheckBox("(Terminée)", (t.getProgress()==100));
                progressLine.add(progressContainer);
            }else { //C'est une tache longue
                ((JSpinner) progressContainer).setValue(t.getProgress());

                org.joda.time.LocalDate startDate = ((LongTask) t).getStartDate();
                startDatePicker.getModel().setDate(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth());
            }

            org.joda.time.LocalDate endDate = t.getEcheance();
            endDatePicker.getModel().setDate(endDate.getYear(), endDate.getMonthOfYear(), endDate.getDayOfMonth());

        }else { // la tâche n'existe pas encore : il va faloir la créer

        }


    }

}
