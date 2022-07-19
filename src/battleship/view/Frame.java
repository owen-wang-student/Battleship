/*
 * Frame.java
 * Creates all graphical components, and exposes methods for getting components,
 * so that Controller class can attach listeners to them. Any UI interactions
 * that do not update model are handled by the UI without delegating to
 * the Controller class.
 * Daniel Alp
 * ICS4U1
 * 2022-06-08
 */

package battleship.view;

import battleship.model.BoardData;
import battleship.model.Stats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    private MenuPanel menuPanel;
    private InstructionPanel instructionPanel;
    private SetupPanel setupPanel;
    private MatchPanel matchPanel;
    private ResultPanel resultPanel;
    private JPanel cardPanel = new JPanel();
    private CardLayout cardLayout = new CardLayout();

    /**
     * Sets basic frame properties, creates components and listeners for them.
     *
     * @param userBoardData - data for user board
     * @param computerBoardData - data for computer board
     * @param stats - stats object used to track overall statistics
     */
    public Frame(BoardData userBoardData, BoardData computerBoardData, Stats stats) {
        setTitle("ICS4U1 CPT - Java Battleship");
        setSize(1280, 720);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel backgroundImage = new JLabel();
        backgroundImage.setIcon(new ImageIcon("src\\res\\background.png"));
        backgroundImage.setBounds(0, 0, 1280, 720);

        cardPanel.setBounds(0, 0, 1280, 720);
        cardPanel.setLayout(cardLayout);
        add(cardPanel);

        SplashScreen splashScreen = new SplashScreen();
        cardPanel.add(splashScreen);
        cardLayout.addLayoutComponent(splashScreen, "splashscreen");

        menuPanel = new MenuPanel();
        menuPanel.getInstructionButton().addActionListener(new ActionListener() {
            /**
             *
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showPanel("instruction");
            }
        });
        cardLayout.addLayoutComponent(menuPanel, "menu");
        cardPanel.add(menuPanel);

        instructionPanel = new InstructionPanel();
        instructionPanel.getMatchSetupButton().addActionListener(new ActionListener() {
            /**
             *
             *
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showPanel("setup");
            }
        });
        cardPanel.add(instructionPanel);
        cardLayout.addLayoutComponent(instructionPanel, "instruction");

        setupPanel = new SetupPanel(userBoardData);
        setupPanel.getInstructionButton().addActionListener(new ActionListener() {
            /**
             *
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                confirmExit("setup");
            }
        });
        cardLayout.addLayoutComponent(setupPanel, "setup");
        cardPanel.add(setupPanel);

        matchPanel = new MatchPanel(userBoardData, computerBoardData);
        matchPanel.getQuitButton().addActionListener(new ActionListener() {
            /**
             *
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                confirmExit("match");
            }
        });
        cardLayout.addLayoutComponent(matchPanel, "match");
        cardPanel.add(matchPanel);

        resultPanel = new ResultPanel(userBoardData, stats);
        resultPanel.getMenuButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                showPanel("menu");
            }
        });
        cardPanel.add(resultPanel);
        cardLayout.addLayoutComponent(resultPanel, "result");

        setVisible(true);

        cardLayout.show(cardPanel, "splashscreen");
        splashScreen.fakeLoading();
        cardLayout.show(cardPanel, "menu");
    }

    public void showPanel(String panelName) {
        /*
         * If the panel to be shown is the result panel, there is a two-second
         * delay before the panel is shown. Uses Timer class instead of the Thread.sleep
         * as Timer class does not block the GUI thread, allowing the user to still interact
         * with the GUI.
         */
        if (panelName.equals("result")) {
            Timer delayTimer = new Timer(2000, new ActionListener() {
                /**
                 * Shows result panel, and calls method for it to update the winner of the current match.a
                 *
                 * @param actionEvent - event that is generated after timer delay passes
                 */
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    cardLayout.show(cardPanel, "result");
                    resultPanel.showWinner();
                }
            });
            delayTimer.start();
            delayTimer.setRepeats(false);
        } else {
            cardLayout.show(cardPanel, panelName);
        }
    }

    /**
     * Generates a confirmation dialog box, and prompts user to confirm the
     * action. If user confirms action, respective destination panel is shown.
     *
     * @param currentPanel
     */
    public void confirmExit(String currentPanel) {
        int response = JOptionPane.showConfirmDialog(
                Frame.this,
                "Are you sure you want to exit " + currentPanel + "? Progress will not be saved.",
                "exitMatch",
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            if (currentPanel.equals("setup")) {
                showPanel("else");
            } else if (currentPanel.equals("match")) {
                showPanel("menu");
            }
            showPanel("instruction");
        }
    }

    /**
     * Return menu panel
     *
     * @return - menu panel
     */
    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    /**
     *
     * @return - instruction panel
     */
    public InstructionPanel getInstructionPanel() {
        return instructionPanel;
    }

    /**
     *
     * @return - setup panel
     */
    public SetupPanel getSetupPanel() {
        return setupPanel;
    }

    /**
     *
     * @return - match panel
     */
    public MatchPanel getMatchPanel() {
        return matchPanel;
    }
}
