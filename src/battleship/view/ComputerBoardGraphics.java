package battleship.view;

import battleship.model.BoardData;
import battleship.model.GameState;
import battleship.model.Ship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ComputerBoardGraphics extends BoardGraphics {
    private Point crosshairPoint;
    private ImageIcon crosshairIcon = new ImageIcon("src\\res\\crosshair.png");

    /**
     *
     *
     * @param boardData - board data, that is data-bound for rendering graphics.
     * @param x - x pos of top left corner of board
     * @param y - y pos of top left corner of board
     */
    public ComputerBoardGraphics(BoardData boardData, int x, int y) {
        super(boardData, x, y);

        addMouseMotionListener(new MouseAdapter() {
            /**
             *
             *
             * @param mouseEvent - event that is generated when mouse is moved inside the board
             */
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                crosshairPoint = mouseEvent.getPoint();
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            /**
             *
             *
             * @param mouseEvent - event that is generated when mouse leaves the bounds of the board
             */
            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                crosshairPoint = null;
                repaint();
            }
        });
    }

    /**
     *
     * Paint computer's ships. Ships are not painted unless they are sunk, or
     * the match has ended (to show the user where ships were hidden).
     *
     * @param graphics2D -
     */
    @Override
    protected void drawShips(Graphics2D graphics2D) {
        for (Ship ship : boardData.getShips()) {
            if (!ship.isSunk() && GameState.getState() != GameState.RESULT) {
                continue;
            }
            String shipImagePath = String.format("src\\res\\%s_%s.png", ship.getType(), ship.getOrientation()).toLowerCase();;
            new ImageIcon(shipImagePath).paintIcon(this, graphics2D, ship.getColumn() * TILE_SIZE, ship.getRow() * TILE_SIZE);
        }
    }

    /**
     * Paints crosshair where the user's mouse is located, if the mouse point
     * is contained in the board's boundaries.
     *
     * @param graphics -
     */
    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics); // Calls super class method to clear anything previously painted.
        if (crosshairPoint != null) {
            // Offsets paint location to the left and up, to center crosshair icon over cursor.
            crosshairIcon.paintIcon(this, graphics, crosshairPoint.x - 24, crosshairPoint.y - 24);
        }
    }
}
