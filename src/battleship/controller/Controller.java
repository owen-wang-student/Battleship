/*
 * Controller.java
 * Class that initializes the model and view, and connects them. Controls data
 * and state of the game. Any events created in the GUI that update the game
 * data or state are observed by the controller. Uses observer pattern. Uses
 * dependency injection design pattern to pass model off to the view for data
 * binding.
 * Daniel Alp
 * ICS4U1
 * 2022-06-08
 */

package battleship.controller;

import battleship.ai.BattleshipAI;
import battleship.model.*;
import battleship.view.BoardGraphics;
import battleship.view.Frame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller {
    private BoardData userBoardData = new BoardData();
    private BoardData computerBoardData = new BoardData();
    private BattleshipAI battleshipAI = new BattleshipAI();
    private StatTracker statTracker = new StatTracker();
    private Frame frame = new Frame(userBoardData, computerBoardData, statTracker.getStats());
    private Orientation userCurShipOrientation = null; // Orientation of the current ship being placed by user
    private Type userCurShipType = null; // Type of current ship being placed by user.

    /**
     * Immediately creates listeners for every panel.
     */
    public Controller() {
        addMenuPanelListeners();
        addInstructionPanelListeners();
        addSetupPanelListeners();
        addSetupPanelListeners();
        addMatchPanelListeners();
    }

    /**
     * Creates listeners for the menu pane.
     */
    private void addMenuPanelListeners() {
        frame.getMenuPanel().getMatchSetupButton().addActionListener(new ActionListener() {
            /**
             *
             * @param actionEvent -
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                reset();
                frame.getSetupPanel().resetTypeOptions();
                frame.showPanel("setup");
            }
        });
    }

    /**
     *
     */
    private void addSetupPanelListeners() {
        frame.getSetupPanel().getUserBoardGraphics().addMouseListener(new MouseAdapter() {
            /**
             *
             * @param mouseEvent - mouse event that is generated when user clicks on the user board.
             */
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (!SwingUtilities.isLeftMouseButton(mouseEvent)) { // Validates mouse event
                    return;
                }
                Coordinate coordinate = BoardGraphics.pointToBoardCoordinates(mouseEvent.getPoint());
                if (userCurShipType == null || userCurShipOrientation == null) { // Checks whether ship options are selected
                    return;
                }
                if (!userBoardData.isValidPlacement(userCurShipType.getLength(), userCurShipOrientation, coordinate)) {
                    return;
                }
                userBoardData.getShips().add(new Ship(userCurShipType, userCurShipOrientation, coordinate));
                frame.repaint();
                frame.getSetupPanel().updateTypeOptions(userCurShipType);
                userCurShipType = null;
                userCurShipOrientation = null;
            }
        });

        /*
         * Loops through every type option,
         */
        for (JRadioButton typeOption : frame.getSetupPanel().getTypeOptions()) {
            typeOption.addActionListener(new ActionListener() {
                /**
                 *
                 * @param actionEvent - event that is generated when the button is interacted with
                 */
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    userCurShipType = Type.valueOf(typeOption.getName());
                }
            });
        }

        /*
         *
         */
        for (JRadioButton orientationOption : frame.getSetupPanel().getOrientationOptions()) {
            orientationOption.addActionListener(new ActionListener() {
                /**
                 *
                 * @param actionEvent - event that is generated when the button is interacted with
                 */
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    userCurShipOrientation = Orientation.valueOf(orientationOption.getName());
                }
            });
        }

        frame.getSetupPanel().getResetButton().addActionListener(new ActionListener() {
            /**
             *
             * @param actionEvent - event that is generated when the button is interacted with
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userBoardData.reset();
                frame.repaint();
            }
        });

        frame.getSetupPanel().getMatchStartButton().addActionListener(new ActionListener() {
            /**
             * Displays match panel if all user ships are placed.
             *
             * @param actionEvent - event that is generated when the button is interacted with
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (userBoardData.getShips().size() == Type.values().length) {
                    frame.showPanel("match");
                    GameState.setState(GameState.MATCH);
                }
            }
        });

        frame.getSetupPanel().getAutoButton().addActionListener(new ActionListener() {
            /**
             *
             * @param actionEvent - event that is generated when the button is interacted with
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userBoardData.reset();
                for (Type type : Type.values()) {
                    userBoardData.randomlyPlaceShip(type, Orientation.getRandomOrientation());
                    frame.getSetupPanel().updateTypeOptions(type);
                }
                frame.repaint();
            }
        });
    }

    /**
     *
     */
    private void addInstructionPanelListeners() {
        frame.getInstructionPanel().getMatchSetupButton().addActionListener(new ActionListener() {
            /**
             *
             * @param actionEvent - event that is generated when button is interacted with.
             */
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                reset();
                frame.getSetupPanel().resetTypeOptions();
                frame.showPanel("setup");
            }
        });
    }

    /**
     *
     */
    private void addMatchPanelListeners() {
        frame.getMatchPanel().getComputerBoardGraphics().addMouseListener(new MouseAdapter() {
            /**
             *
             * @param mouseEvent - event that is generated when user clicks on the computer board.
             */
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (!SwingUtilities.isLeftMouseButton(mouseEvent)) { // Validates mouse event
                    return;
                }
                Coordinate userTargetCoordinates = BoardGraphics.pointToBoardCoordinates(mouseEvent.getPoint());
                if (computerBoardData.getEnemyShots()[userTargetCoordinates.getRow()][userTargetCoordinates.getColumn()]) {
                    return;
                }
                if (GameState.getState() == GameState.MATCH) {
                    nextMove(computerBoardData, userTargetCoordinates);
                }
                if (GameState.getState() == GameState.MATCH) {
                    nextMove(userBoardData, battleshipAI.getShot());
                }
                frame.repaint();
            }
        });
    }

    /**
     *
     */
    private void reset() {
        userBoardData.reset();
        computerBoardData.reset();
        battleshipAI.reset();
        for (Type type : Type.values()) {
            computerBoardData.randomlyPlaceShip(type, Orientation.getRandomOrientation());
        }
    }

    /**
     * Plays next move of the game, then checks if the game is over. Updates
     * model, game state, battleship AI and graphics accordingly.
     *
     * @param targetBoardData - board data that is being fired at
     * @param targetCoordinates - coordinates that the board is being fired at
     */
    private void nextMove(BoardData targetBoardData, Coordinate targetCoordinates) {
        // Updates battleship AI if it is currently the AI's turn.
        if (targetBoardData == userBoardData) {
            battleshipAI.update(targetBoardData.getFiredAt(targetCoordinates), targetBoardData.getTypeSunk());
        } else {
            targetBoardData.getFiredAt(targetCoordinates);
        }

        if (targetBoardData.fleetSunk()) {
            GameState.setState(GameState.RESULT); // Sets game state to result, preventing user from firing at the board.
            if (targetBoardData == userBoardData) {
                statTracker.getStats().increaseComputerWins();
            } else {
                statTracker.getStats().increaseUserWins();
            }
            statTracker.updateStatsFile(); // Updates stats file after every game, letting
            frame.showPanel("result");
        }
    }
}
