/*
 * Coordinate.java
 * Coordinate on a board, represented by the row and column. Used to pack two
 * pieces of data into one. Used by classes in model and ai package.
 * ICS4U1
 * 2022-06-10
 */

package battleship.model;

public class Coordinate {
    private int row;
    private int column;

    /**
     * Assigns class member variables row and column with the respective parameters.
     *
     * @param row    - the coordinate row.
     * @param column - the coordinate column.
     */
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the coordinate row.
     *
     * @return - the coordinate row.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the coordinate column.
     *
     * @return - the coordinate column.
     */
    public int getColumn() {
        return column;
    }
}
