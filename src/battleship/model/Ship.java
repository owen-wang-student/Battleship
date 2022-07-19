/*
 * Ship.java
 * Ship class that contains methods for controller logic i.e. whether ship contains
 * coordinate, whether ship is sunk, and methods for view  i.e. ship type,
 * orientation, and coordinates.
 * logic.
 * Owen Wang
 * ICS4U1
 * 2022-06-10
 */

package battleship.model;

public class Ship {
    private Type type;
    private int length;
    private Orientation orientation;
    private int row;
    private int column;
    private int hitCounter = 0;

    /**
     * Assigns class member variables type and orientation with the respective
     * parameters. Uses parameter coordinates to determine value of variables
     * row and  column.
     *
     * @param type - the ship type.
     * @param orientation - the ship orientation.
     * @param coordinates - the ships starting (top left) coordinates.
     */
    public Ship(Type type, Orientation orientation, Coordinate coordinates) {
        this.type = type;
        length = type.getLength();
        this.orientation = orientation;
        row = coordinates.getRow();
        column = coordinates.getColumn();
    }

    /**
     * Returns whether the ship contains a target coordinate.
     *
     * @param target - coordinate that is being checked
     * @return - whether the ship contains the target
     */
    public boolean containsCoordinates(Coordinate target) {
        int targetRow = target.getRow(), targetColumn = target.getColumn();
        // Determines if target lies within the range of starting and ending segment of ship
        if (orientation == Orientation.HORIZONTAL) {
            return targetRow == row && targetColumn >= column && targetColumn < column + length;
        } else {
            return targetColumn == column && targetRow >= row && targetRow < row + length;
        }
    }

    /**
     * Updates the ships hit counter.
     */
    public void hit() {
        hitCounter++;
    }

    /**
     * Returns whether the ship has been sunk
     *
     * @return - whether the ship has been sunk.
     */
    public boolean isSunk() {
        return hitCounter == length;
    }

    /**
     * Returns the ship type, used by view package for rendering ship.
     *
     * @return - the ship type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the ship orientation, used by view package for rendering
     * ship.
     *
     * @return -  the ship orientation.
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Returns the ship starting row, used by view package for rendering ship.
     *
     * @return - the ship starting row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the ship starting column, used by the view package for rendering
     * ship.
     *
     * @return - the ships starting column
     */
    public int getColumn() {
        return column;
    }
}
