package Controller;

import Model.LongTask;
import Model.Task;
import View.TaskPropertiesDialogPanel;
import View.TaskView;
import org.joda.time.LocalDate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Created by Theo on 10/12/2016.
 */
public class TaskPropertiesController {

    private Task task;

    private TaskPropertiesDialogPanel taskPropertiesDialogPanel;

    public TaskPropertiesController(Task t){
        task = t;
    }

    public void setListeners(TaskPropertiesDialogPanel tpdp){
        taskPropertiesDialogPanel = tpdp;

        JButton yesButton = tpdp.getYesButton(),
                noButton = tpdp.getNoButton();

        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                yesButtonPressed();
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noButtonPressed();
            }
        });

    }

    private void noButtonPressed() {
        taskPropertiesDialogPanel.setVisible(false);

    }

    /**
     * Extrait les informations du dialogue pour créer ou éditer la tâche correspondante
     */
    private void writeToTask() {
        System.out.println("Writing gathered info to task");
        JComponent progressContainer = taskPropertiesDialogPanel.getProgress();
        String name = taskPropertiesDialogPanel.getNameField().getText(),
                descriptif = taskPropertiesDialogPanel.getDescriptifArea().getText();
        int progress;
        if (progressContainer instanceof JSpinner){
            progress = (int) ((JSpinner)progressContainer).getValue();
        }else {
            progress = ((JCheckBox)progressContainer).isSelected()? 100 : 0; //Tâche courte : 0 ou 100%
        }
        Date startSourceDate = (Date) taskPropertiesDialogPanel.getStartDatePicker().getModel().getValue();
        Date endSourceDate = (Date) taskPropertiesDialogPanel.getEndDatePicker().getModel().getValue();

        System.out.println("Dates brutes : " + startSourceDate + " --> " + endSourceDate);


        if (task != null){ // La tâche existe déjà, on ne fait que la modifier
            try{
                if (!task.getName().equals(name)){
                    task.setName(name);
                }

                task.setContenu(descriptif);
                task.setProgress(progress);

                if (endSourceDate != null){ //Si une nouvelle échéance a été précisée
                    System.out.println("Màj de l'echeance");
                    task.setEcheance( LocalDate.fromDateFields(endSourceDate) );
                }


                if (task instanceof LongTask && startSourceDate != null){
                    System.out.println("Maj de la startDate");
                    ((LongTask)task).setStartDate(LocalDate.fromDateFields(startSourceDate));
                }
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Erreur lors de l'édition : " + e.getMessage(), "Erreur d'édition", JOptionPane.ERROR_MESSAGE);
            }
            task.update();
            task.findContainer().update();
        }else{ //la tâche n'existait pas encore, on a donc demandé à la créer
            if (endSourceDate == null){
                endSourceDate = new Date();
            }

            if (startSourceDate == null){
                startSourceDate = new Date();
            }
            try{
                if (startSourceDate != null){
                    task = new LongTask(name);
                    task.setEcheance(LocalDate.fromDateFields(endSourceDate));
                    ((LongTask)task).setStartDate(LocalDate.fromDateFields(startSourceDate));
                }else{
                    task = new Task(name);
                    task.setEcheance( LocalDate.fromDateFields(endSourceDate) );
                }

                TaskController tc = new TaskController(task);
                TaskView tv = new TaskView(tc);
                task.setView(tv);
                task.setProgress(progress);
                task.setContenu(descriptif);

                task.update();
                task.findContainer().update();

            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Erreur lors de l'édition : " + e.getMessage(), "Erreur d'édition", JOptionPane.ERROR_MESSAGE);
                if (task != null){
                    task.eraseTask(); //Création échouée : nettoyer
                }

            }

        }
    }

    private void yesButtonPressed() {
        taskPropertiesDialogPanel.setVisible(false);
        writeToTask();


    }

}
