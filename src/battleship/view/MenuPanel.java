/*
 *
 *
 *
 *
 */

package battleship.view;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JLabel titleLabel = new JLabel();
    private JButton matchSetupButton = new JButton("PLAY GAME");
    private JButton instructionButton = new JButton("INSTRUCTIONS");
    private ImageIcon backgroundIcon = new ImageIcon("src\\res\\background.png");

    /**
     *
     */
    public MenuPanel() {
        setBounds(0, 0, 1280, 720);
        setLayout(null);

        titleLabel.setBounds(298, 35, 683, 262);
        titleLabel.setIcon(new ImageIcon("src\\res\\title.png"));
        add(titleLabel);

        matchSetupButton.setBounds(515, 335, 250, 50);
        add(matchSetupButton);

        instructionButton.setBounds(515, 410, 250, 50);
        add(instructionButton);
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
    public JButton getMatchSetupButton() {
        return matchSetupButton;
    }

    /**
     *
     * @return
     */
    public JButton getInstructionButton() {
        return instructionButton;
    }
}
