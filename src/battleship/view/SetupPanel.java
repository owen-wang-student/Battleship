/*
 *
 *
 *
 *
 */

package battleship.view;

import battleship.model.BoardData;
import battleship.model.Orientation;
import battleship.model.Type;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupPanel extends JPanel {
    private UserBoardGraphics userBoardGraphics;
    private JRadioButton[] typeOptions = new JRadioButton[Type.values().length];
    private JRadioButton[] orientationOptions = new JRadioButton[Orientation.values().length];
    private ButtonGroup typeButtonGroup = new ButtonGroup();
    private ButtonGroup orientationButtonGroup = new ButtonGroup();
    private JButton resetButton = new JButton("Reset Board");
    private JButton matchStartButton = new JButton("Start Match");
    private JButton instructionButton = new JButton("Instructions");
    private JButton autoButton = new JButton("Auto place");
    private ImageIcon backgroundIcon = new ImageIcon("src\\res\\background.png");

    /**
     *
     * @param userBoardData
     */
    public SetupPanel(BoardData userBoardData) {
        setBounds(0, 0, 1280, 720);
        setLayout(null);

        /*
         * Creates
         */
        for (int option = 0; option < typeOptions.length; option++) {
            JRadioButton typeOption = new JRadioButton(Type.values()[option].toString());
            typeOption.setName(Type.values()[option].toString());
            typeOption.setBounds(60, 85 + option * 75, 300, 15); // Determines Y position using
            typeOption.setOpaque(false);
            typeOption.setForeground(Color.WHITE);
            typeOptions[option] = typeOption;
            add(typeOption);
            // Adds option to respective button group, preventing user from selecting multiple options
            typeButtonGroup.add(typeOption);
            JLabel typeLabel = new JLabel();
            typeLabel.setIcon(new ImageIcon(String.format("src\\res\\%s_%s.png", Type.values()[option].toString().toLowerCase(), "horizontal")));
            typeLabel.setBounds(60, 100 + option * 75, 300, 60);
            add(typeLabel);

        }

        /*
         *
         */
        for (int option = 0; option < orientationOptions.length; option++) {
            orientationOptions[option] = new JRadioButton(Orientation.values()[option].toString());
            orientationOptions[option].setName(Orientation.values()[option].toString());
            orientationOptions[option].setBounds(360, 85 + option * 75, 120, 25);
            orientationOptions[option].setOpaque(false);
            orientationOptions[option].setForeground(Color.WHITE);
            // Adds option to respective button group, preventing user from selecting multiple options
            orientationButtonGroup.add(orientationOptions[option]);
            add(orientationOptions[option]);
        }

        resetButton.setBounds(60, 575, 120, 60);
        resetButton.addActionListener(new ActionListener() {
            /**
             *
             * @param actionEvent
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                resetTypeOptions();
            }
        });
        add(resetButton);

        matchStartButton.setBounds(210, 575, 120, 60);
        add(matchStartButton);

        instructionButton.setBounds(360, 575, 120, 60);
        add(instructionButton);

        autoButton.setBounds(510, 575, 120, 60);
        add(autoButton);

        userBoardGraphics = new UserBoardGraphics(userBoardData, 670, 85);
        add(userBoardGraphics);


    }

    /**
     *
     *
     * @param graphics -
     */
    @Override
    public void paintComponent(Graphics graphics) {
        backgroundIcon.paintIcon(this, graphics, 0, 0);
    }

    /**
     * Re-enables all type options. Clears all previous selections.
     */
    public void resetTypeOptions() {
        for (JRadioButton option : typeOptions) {
            option.setEnabled(true);
            option.setSelected(false);
        }
        for (JRadioButton option : orientationOptions) {
            option.setSelected(false);
        }
        typeButtonGroup.clearSelection();
        orientationButtonGroup.clearSelection();
    }

    /**
     * Deselects the option associated with the type that is currently selected
     *
     * @param typeSelected - the type that is currently selected
     */
    public void updateTypeOptions(Type typeSelected) {
        for (JRadioButton typeOption : typeOptions) {
            if (typeOption.getName().equals(typeSelected.toString())) {
                typeOption.setEnabled(false);
            }
        }
        typeButtonGroup.clearSelection();
        orientationButtonGroup.clearSelection();
    }

    /**
     *
     *
     * @return
     */
    public UserBoardGraphics getUserBoardGraphics() {
        return userBoardGraphics;
    }

    /**
     *
     *
     * @return
     */
    public JRadioButton[] getTypeOptions() {
        return typeOptions;
    }

    /**
     *
     *
     * @return
     */
    public JRadioButton[] getOrientationOptions() {
        return orientationOptions;
    }

    /**
     *
     *
     * @return
     */
    public JButton getResetButton() {
        return resetButton;
    }

    /**
     *
     *
     * @return
     */
    public JButton getMatchStartButton() {
        return matchStartButton;
    }

    /**
     *
     *
     * @return
     */
    public JButton getInstructionButton() {
        return instructionButton;
    }

    /**
     *
     *
     * @return
     */
    public JButton getAutoButton() {
        return autoButton;
    }
}
