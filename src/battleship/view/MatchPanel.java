/*
 *
 *
 *
 *
 */

package battleship.view;

import battleship.model.BoardData;

import javax.swing.*;
import java.awt.*;

public class MatchPanel extends JPanel {
    private UserBoardGraphics userBoardGraphics;
    private ComputerBoardGraphics computerBoardGraphics;
    private JLabel userBoardLabel = new JLabel("User Board");
    private JLabel computerBoardLabel = new JLabel("Computer Board");
    private JButton quitButton = new JButton("Quit Button");
    private ImageIcon backgroundIcon = new ImageIcon("src\\res\\background.png");


    /**
     *
     * @param userBoardData
     * @param computerBoardData
     */
    public MatchPanel(BoardData userBoardData, BoardData computerBoardData) {
        setBounds(0, 0, 1280, 720);
        setLayout(null);

        userBoardLabel.setBounds(675, 25, 550, 60);
        userBoardLabel.setFont(new Font("", Font.BOLD, 25));
        userBoardLabel.setForeground(Color.WHITE);
        userBoardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(userBoardLabel);

        computerBoardLabel.setBounds(60, 25, 550, 60);
        computerBoardLabel.setFont(new Font("", Font.BOLD, 25));
        computerBoardLabel.setForeground(Color.WHITE);
        computerBoardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(computerBoardLabel);

        quitButton.setBounds(0, 0, 100, 40);
        add(quitButton);

        userBoardGraphics = new UserBoardGraphics(userBoardData, 670, 85);
        add(userBoardGraphics);

        computerBoardGraphics = new ComputerBoardGraphics(computerBoardData, 60, 85);
        add(computerBoardGraphics);
    }

    /**
     *
     * @param graphics
     */
    @Override
    public void paintComponent(Graphics graphics) {
        backgroundIcon.paintIcon(this, graphics, 0, 0);
    }

    /**
     *
     * @return
     */
    public ComputerBoardGraphics getComputerBoardGraphics() {
        return computerBoardGraphics;
    }

    /**
     *
     * @return
     */
    public JButton getQuitButton() {
        return quitButton;
    }
}

