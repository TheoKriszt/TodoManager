package View;

import javax.swing.*;

/**
 * Created by Antho on 07/12/2016.
 */
public class TestFrame extends JFrame {

    public TestFrame(){
        this.setTitle("Test");
        this.setSize(1000,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new BilanPanel());
        this.setVisible(true);
    }
    
}
