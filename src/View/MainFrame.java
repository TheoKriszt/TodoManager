package View;

import Model.SimpleTask;
import Model.Task;
import Model.TodoManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Theo on 07/12/2016.
 */
public class MainFrame extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuFichier, menuEdition;
    private JMenuItem menuItemQuitter, menuItemNewTask, menuItemNewCategory;
    private JTabbedPane tabbedPane;
    private JComponent tabGeneral, tabCategories, tabBilan;
    //private JPanel generalPanel, categoriesPanel, bilanPPanel;

    private TodoManager todoManager;


    public MainFrame(TodoManager tm){
        super("Todo List Manager");
        setLocationRelativeTo(null);
        setSize(new Dimension(600, 500));

//        tabGeneral = new JPanel();      //TODO : spécialiser sur la bonne vue
//        tabCategories = new JPanel();   //Todo : idem
//        tabBilan = new JPanel();        //Todo : idem

        todoManager = tm;

        buildMenu();
        buildTabs();
        setListeners();
        fillTabs();

        setVisible(true);
    }

    private void fillTabs() {


    }

    private void setListeners() {

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                todoManager.close();
            }
        });

        menuItemQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                todoManager.close();
            }
        });
    }

    private void buildTabs() {
        tabbedPane = new JTabbedPane();

        /*
        tabGeneral = makeTextPanel("Général");
        tabCategories = makeTextPanel("Catégories");
        tabBilan = makeTextPanel("Bilan");
        */

        tabGeneral = new GeneralPanel();
        tabCategories = new JPanel();
        tabBilan = new JPanel();

        tabbedPane.addTab("Général", tabGeneral);
        tabbedPane.addTab("Catégories", tabCategories);
        tabbedPane.addTab("Bilan", tabBilan);

        setContentPane(tabbedPane);

    }

    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    private void buildMenu() {
        menuBar = new JMenuBar();
        menuFichier = new JMenu("Fichier");
        menuFichier.setMnemonic(KeyEvent.VK_F); //Touche d'accès rapide
        menuEdition = new JMenu("Edition");
        menuEdition.setMnemonic(KeyEvent.VK_E);

        menuBar.add(menuFichier);
        menuBar.add(menuEdition);

        menuItemQuitter = new JMenuItem("Quitter", KeyEvent.VK_Q);
        menuItemNewTask = new JMenuItem("Nouvelle tâche", KeyEvent.VK_T);
        menuItemNewCategory = new JMenuItem("Nouvelle catégorie", KeyEvent.VK_C);

        menuFichier.add(menuItemQuitter);
        menuEdition.add(menuItemNewTask);
        menuEdition.add(menuItemNewCategory);

        //ajouter plutot ici les menus ?
        setJMenuBar(menuBar);



    }
}
