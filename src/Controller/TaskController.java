package Controller;

import Model.Task;
import View.TaskView;

/**
 * Created by Theo on 08/12/2016.
 */
public class TaskController {
    private Task task;

    public TaskController(TaskView v){
        //init listeners
        task = new Task(v);
    }
}
