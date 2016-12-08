package View;

import Model.TodoManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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

        todoManager = tm;
        tm.setFrame(this);

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
                System.out.println("Appel à close() depuis le bouton fenêtre");
            }
        });

        menuItemQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Appel à close() depuis le menuItem");
                todoManager.close();

            }
        });

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
            }
        };
        tabbedPane.addChangeListener(changeListener);
    }

    private void buildTabs() {
        tabbedPane = new JTabbedPane();

        tabGeneral = new GeneralPanel ();
        tabCategories = new CategoriesPanel();
        tabBilan = new BilanPanel();

        tabbedPane.addTab("Général", tabGeneral);
        tabbedPane.addTab("Catégories", tabCategories);
        tabbedPane.addTab("Bilan", tabBilan);


        setContentPane(tabbedPane);

    }

    public ArrayList<ObserverPanel> getTabs(){
        ArrayList<ObserverPanel> tabs = new ArrayList<>();
        for (int i=0; i<tabbedPane.getTabCount(); i++){
            //tabs.add((ObserverPanel) tabbedPane.getTabComponentAt(i));
            tabs.add((ObserverPanel) tabbedPane.getComponentAt(i));
        }
        //tabs.add((ObserverPanel) tabbedPane.getComponentAt(1));
        return tabs;
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
