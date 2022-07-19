/*
 *
 *
 *
 *
 */

package battleship.view;

import battleship.model.BoardData;
import battleship.model.Stats;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {
    private BoardData userBoardData;
    private Stats stats;
    private JLabel winStats = new JLabel();
    private JLabel winner = new JLabel();
    private JButton menuButton = new JButton("To Menu");
    private ImageIcon backgroundIcon = new ImageIcon("src\\res\\background.png");

    /**
     *
     * @param userBoardData
     * @param stats
     */
    public ResultPanel(BoardData userBoardData, Stats stats) {
        setBounds(0, 0, 1280, 720);
        setBackground(new Color(194, 195, 199));
        setLayout(null);

        winner.setBounds(0, 60, 1280, 100);
        winner.setForeground(Color.WHITE);
        winner.setHorizontalAlignment(SwingConstants.CENTER);
        winner.setFont(new Font("", Font.BOLD, 75));
        add(winner);

        winStats.setBounds(200, 240, 880, 120);
        winStats.setForeground(Color.WHITE);
        winStats.setFont(new Font("", Font.BOLD, 35));
        add(winStats);

        menuButton.setBounds(520, 420, 240, 60);
        add(menuButton);

        this.userBoardData = userBoardData;
        this.stats = stats;
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
     */
    public void showWinner() {
        if (userBoardData.fleetSunk()) {
            winner.setText("COMPUTER WON");
            new SoundEffect("src\\res\\lose.wav");
        } else {
            winner.setText("USER WON    ");
            new SoundEffect("src\\res\\win.wav");
        }
        winStats.setText(String.format("<html>USER WINS: %d<br/>COMPUTER WINS: %d<html>", stats.getUserWins(), stats.getComputerWins()));
    }

    /**
     *
     * @return
     */
    public JButton getMenuButton() {
        return menuButton;
    }


}
