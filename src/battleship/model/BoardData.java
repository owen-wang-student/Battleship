/*
 * BoardData.java
 * BoardData class contains methods for game logic i.e. validating ship placement,
 * randomly placing ships, getting fired at, etc. and exposes getter methods
 * Daniel Alp
 * ICS4U1
 * 2022-06-08
 */

package battleship.model;

import java.util.ArrayList;
import java.util.Collections;

public class BoardData {
    public static final int BOARD_SIZE = 10;
    private ArrayList<Ship> ships = new ArrayList<>();
    private boolean[][] enemyShots = new boolean[BOARD_SIZE][BOARD_SIZE]; // 2-D boolean array representing where the board has been fired at
    private Type typeSunk; // Ship sunk on a player's turn

    /**
     * Resets all board data, i.e. clearing all ships and enemy shots.
     */
    public void reset() {
        ships.clear();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                enemyShots[row][column] = false;
            }
        }
    }

    /**
     * Returns whether a ship placement is valid.
     *
     * @param length - the length of the ship being considered
     * @param orientation - the orientation of the ship being considered
     * @param coordinate - the coordinate of the ship being considered
     * @return - whether the ship placement is valid.
     */
    public boolean isValidPlacement(int length, Orientation orientation, Coordinate coordinate) {
        // Checks whether the ship placement is out of bounds
        if (orientation == Orientation.HORIZONTAL && coordinate.getColumn() + length > BOARD_SIZE ||
                orientation == Orientation.VERTICAL && coordinate.getRow() + length > BOARD_SIZE) {
            return false;
        }
        /*
         * Iterates over every segment in the ship, then loops through every placed ship,
         * checking if contains that segment's coordinates.
         */
        for (int segment = 0; segment < length; segment++) {
            Coordinate segmentCoordinates;
            /*
             * Determine where the segment coordinates are relative to the initial coordinates
             * using the ship orientation and segment value.
             */
            if (orientation == Orientation.HORIZONTAL) {
                segmentCoordinates = new Coordinate(coordinate.getRow(), coordinate.getColumn() + segment);
            } else {
                segmentCoordinates = new Coordinate(coordinate.getRow() + segment, coordinate.getColumn());
            }
            for (Ship ship : ships) {
                if (ship.containsCoordinates(segmentCoordinates)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Takes parameters for the type and orientation of a ship, then creates
     * the ship in a random valid position.
     *
     * @param type - type of ship being placed
     * @param orientation - orientation of ship being placed
     */
    public void randomlyPlaceShip(Type type, Orientation orientation) {
        ArrayList<Coordinate> validCoordinates = new ArrayList<>();
        /*
         * Loops over every board coordinate, adding the coordinate if the
         * ship placement is valid.
         */
        for (int row = 0; row < BoardData.BOARD_SIZE; row++) {
            for (int column = 0; column < BoardData.BOARD_SIZE; column++) {
                Coordinate coordinates = new Coordinate(row, column);
                if (isValidPlacement(type.getLength(), orientation, coordinates)) {
                    validCoordinates.add(coordinates);
                }
            }
        }
        Collections.shuffle(validCoordinates); // Randomizes order of elements
        ships.add(new Ship(type, orientation, validCoordinates.get(0)));
    }


    /**
     * The board gets fired at
     *
     * @param targetCoordinates - coordinates that the board is fired at.
     * @return - whether the shot hit a ship.
     */
    public boolean getFiredAt(Coordinate targetCoordinates) {
        enemyShots[targetCoordinates.getRow()][targetCoordinates.getColumn()] = true;
        typeSunk = null; // Assumes that the shot did not sink any ship
        /*
         *
         */
        for (Ship ship : ships) {
            if (ship.containsCoordinates(targetCoordinates)) {
                ship.hit();
                if (ship.isSunk()) { // Updates type sunk if a ship is sunk
                    typeSunk = ship.getType();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Return whether all ships on the board have been sunk.
     *
     * @return - whether the board has been sunk.
     */
    public boolean fleetSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return the ship type sunk on the current turn
     *
     * @return -
     */
    public Type getTypeSunk() {
        return typeSunk;
    }

    /**
     * Returns board ships, used by Controller class for
     *
     * @return -
     */
    public ArrayList<Ship> getShips() {
        return ships;
    }

    /**
     * Returns board enemy shots, used for
     *
     * @return -
     */
    public boolean[][] getEnemyShots() {
        return enemyShots;
    }
}