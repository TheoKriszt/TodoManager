package View;

import Controller.BilanPanelController;
import Controller.CategoryController;
import Controller.MainFrameController;
import Controller.TaskController;
import Model.Category;
import Model.Task;
import Model.TodoManager;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Theo on 07/12/2016.
 */
public class MainFrame extends JFrame implements Observer {

    private JMenuBar menuBar;
    private JMenu menuFichier, menuEdition;
    private JMenuItem menuItemQuitter, menuItemNewTask, menuItemNewCategory;
    private JTabbedPane tabbedPane;
    private JComponent tabGeneral, tabCategories, tabBilan;


    public MainFrame(MainFrameController mfc){
        super("Todo List Manager");
        setLocationRelativeTo(null);
        setSize(new Dimension(750, 600));

        //todoManager = tm; //todo : move
        //tm.setFrame(this);

        buildMenu();
        buildTabs();
        mfc.setListeners(this);

        setVisible(true);
    }

    @Deprecated
    private void setListeners() {
        //TODO : déplacer le code inline vers un controlleur plus propre

        //Ajouter des garde-fous à la fermeture du programme : sauvegarder la todoList au préalable
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

        menuItemNewCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Category c;
                CategoryController cc;
                CategoryView cv;

                String s = (String) JOptionPane.showInputDialog(
                        null,
                        "Donner un nom à la nouvelle catégorie",
                        "Nouvelle catégorie",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        "Nouvelle catégorie");

                try {
                    c = new Category(s);
                    cc = new CategoryController(c);
                    cv = new CategoryView(cc);
                    c.setView(cv);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la création de la catégorie: " + ex.getMessage(), "Erreur de création", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        menuItemNewTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Task t;
                TaskController tc;
                TaskView tv;

                String s = (String) JOptionPane.showInputDialog(
                        null,
                        "Donner un nom à la nouvelle tâche",
                        "Nouvelle tâche",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        "Nouvelle tâche");

                try {
                    t = new Task(s);
                    tc = new TaskController(t);
                    tv = new TaskView(tc);
                    t.setView(tv);
                    //t.update();
                    //t.findContainer().update();
                    update(null, null);

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la création de la tâche: " + ex.getMessage(), "Erreur de création", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
                System.out.println("new Tab is " + sourceTabbedPane.getComponentAt(index).getClass().getName());
                ((ObserverPanel)sourceTabbedPane.getComponentAt(index)).update(null, null);

            }
        };
        tabbedPane.addChangeListener(changeListener);
    }

    /**
     * Créé les onglets qui donneront les différents modes d'affichage de l'application
     */
    private void buildTabs() {
        tabbedPane = new JTabbedPane();
        tabGeneral = new GeneralPanel ();
        tabCategories = new CategoriesPanel();
        tabBilan = new BilanPanel();

        BilanPanelController bpc = new BilanPanelController(); //todo : refactor sans singleton (dumb idea ?)
        bpc.setListener((BilanPanel) tabBilan);

        tabbedPane.addTab("Général", tabGeneral);
        tabbedPane.addTab("Catégories", tabCategories);
        tabbedPane.addTab("Bilan", tabBilan);

        setContentPane(tabbedPane);
    }

    /**
     * Créé l'arborescence des menus (Quiter, Nouvelle tâche, Nouvelle Cétagorie ...)
     */
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

        setJMenuBar(menuBar);
    }

    @Override
    public void update(Observable o, Object arg) {
        ObserverPanel activePanel = (ObserverPanel) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
        activePanel.update(null, null);
    }

    public JMenuItem getMenuItemQuitter() {
        return menuItemQuitter;
    }

    public JMenuItem getMenuItemNewTask() {
        return menuItemNewTask;
    }

    public JMenuItem getMenuItemNewCategory() {
        return menuItemNewCategory;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
