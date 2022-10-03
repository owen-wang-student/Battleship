/*
 * BoardGraphics.java
 *
 * Owen Wang
 * ICS4U1
 * 2022-06-08
 */

package battleship.view;

import battleship.model.BoardData;
import battleship.model.Coordinate;
import battleship.model.GameState;
import battleship.model.Ship;

import javax.swing.*;
import java.awt.*;

public abstract class BoardGraphics extends JPanel {
    public static final int TILE_SIZE = 55;

    protected BoardData boardData;

    /**
     *
     * @param boardData
     * @param x
     * @param y
     */
    public BoardGraphics(BoardData boardData, int x, int y) {
        this.boardData = boardData;
        setBounds(x, y, BoardData.BOARD_SIZE * TILE_SIZE, BoardData.BOARD_SIZE * TILE_SIZE);
        setOpaque(true);
        setBackground(new Color(41, 173, 255));
        setBorder(BorderFactory.createLineBorder(new Color(95, 87, 79), 5));
    }

    /**
     * Takes point (x, y) and converts it to board coordinates (row, column).
     * Returns the board coordinates of the point.
     *
     * @param point - point being converted to board coordinates.
     * @return - board coordinates of the point.
     */
    public static Coordinate pointToBoardCoordinates(Point point) {
        return new Coordinate(point.y / BoardGraphics.TILE_SIZE, point.x / BoardGraphics.TILE_SIZE);
    }

    /**
     *
     * @param graphics
     */
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics); // Calls superclass method to erase anything previously painted on component.
        /*
         * Creates Graphics2D object by class-casting superclass Graphics object.
         * Graphics2D class contains methods TODO
         */
        Graphics2D graphics2D = (Graphics2D) graphics;
        drawGridlines(graphics2D);
        drawShips(graphics2D);
        if (GameState.getState() != GameState.SETUP) {
            drawShots(graphics2D);
        }
    }

    /**
     * Draws the gridlines of the board.
     *
     * @param graphics2D
     */
    private void drawGridlines(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(7));
        graphics2D.setColor(new Color(95, 87, 79));
        for (int line = 0; line < 10; line++) {
            graphics2D.drawLine(0, line * TILE_SIZE, BoardData.BOARD_SIZE * TILE_SIZE, line * TILE_SIZE);
            graphics2D.drawLine(line * TILE_SIZE, 0, line * TILE_SIZE, BoardData.BOARD_SIZE * TILE_SIZE);
        }
    }

    /**
     *
     * @param graphics2D
     */
    protected void drawShots(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(10));
        // Enables antialiasing to paint smoother shapes.
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int row = 0; row < BoardData.BOARD_SIZE; row++) {
            for (int column = 0; column < BoardData.BOARD_SIZE; column++) {
                if (boardData.getEnemyShots()[row][column]) { // A tile that has not been fired is skipped
                    boolean shipHit = false;
                    // Iterates over every ship to determine if any ship is hit in the current coordinate.
                    for (Ship ship : boardData.getShips()) {
                        if (ship.containsCoordinates(new Coordinate(row, column))) {
                            shipHit = true;
                            break;
                        }
                    }
                    if (!shipHit) { // Uses condition result to determine which symbol to paint.
                        graphics2D.setColor(new Color(255, 241, 232));
                        graphics2D.fillOval(column * TILE_SIZE + 20, row * TILE_SIZE + 20, TILE_SIZE - 40, TILE_SIZE - 40);
                    } else {
                        graphics2D.setColor(new Color(255, 0, 77));
                        graphics2D.drawLine(column * TILE_SIZE + 20, row * TILE_SIZE + 20, (column + 1) * TILE_SIZE - 20, (row + 1) * TILE_SIZE - 20);
                        graphics2D.drawLine((column + 1) * TILE_SIZE - 20, row * TILE_SIZE + 20, column * TILE_SIZE + 20, (row + 1) * TILE_SIZE - 20);
                    }
                }
            }
        }
    }

    /**
     * Abstract method inherited by subclasses,
     *
     * @param graphics2D
     */
    protected abstract void drawShips(Graphics2D graphics2D);
}
