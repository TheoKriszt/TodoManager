package View;

import Model.Task;

import javax.swing.*;

/**
 * Created by Theo on 07/12/2016.
 */
public class GeneralPanel extends JPanel {

    public GeneralPanel(){
        for (Task t : Task.allTasks()){
            add(t.getView());
        }
    }

}
