package View;

import Controller.BilanController;
import Controller.MainFrameController;
import Model.Bilan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe général des vues, regroupant tous les composants de l'application.
 * C'est la fenêtre de l'application où tout sera affiché. elle possède :
 *              - Un Menu (menuBar)
 *              - des Onglets (tabbedPane)
 *              - des composants (tabGeneral,tabCategories, tabBilan, tabIntermediaire, tabTop8)
 */
public class MainFrame extends JFrame implements Observer {

    private JMenuBar menuBar;
    private JMenu menuFichier, menuEdition;
    private JMenuItem menuItemQuitter, menuItemNewTask, menuItemNewCategory;
    private JTabbedPane tabbedPane;
    private JComponent tabGeneral, tabCategories, tabBilan, tabIntermediaire, tabTop8;


    public MainFrame(MainFrameController mfc){
        super("Todo List Manager");

        setMinimumSize(new Dimension(950, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        buildMenu();
        buildTabs();
        mfc.setListeners(this);

        setVisible(true);
    }

    /**
     * Créé les onglets qui donneront les différents modes d'affichage de l'application
     */
    private void buildTabs() {
        tabbedPane = new JTabbedPane();
        tabGeneral = new GeneralPanel ();
        tabCategories = new CategoriesPanel();
        tabIntermediaire = new IntermediatePanel();
        tabTop8 = new Top8Panel();

        Bilan bilan = new Bilan();
        BilanController bpc = new BilanController(bilan);
        tabBilan = new BilanPanel(bpc);
        bilan.setView((BilanPanel) tabBilan);

        tabbedPane.addTab("Général", tabGeneral);
        tabbedPane.addTab("Intermediaire", tabIntermediaire);
        tabbedPane.addTab("Catégories", tabCategories);
        tabbedPane.addTab("Top 8", tabTop8);
        tabbedPane.addTab("Bilan", tabBilan);

        setContentPane(tabbedPane);
    }

    /**
     * Créé l'arborescence des menus (Quitter, Nouvelle tâche, Nouvelle Cétagorie ...)
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
    /**
     *  Met à jour l'affichage de la fenêtre lors d'un changement d'onglet
     */
    public void update(Observable o, Object arg) {
        System.out.println("Sending main update");
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
