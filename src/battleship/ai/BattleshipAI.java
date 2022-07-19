/*
 * Main.java
 * Owen Wang
 * ICS4U1
 * 2022-06-08
 */

package battleship.ai;

import battleship.model.BoardData;
import battleship.model.Coordinate;
import battleship.model.Type;

import java.util.*;

public class BattleshipAI {
    private boolean[][] validShots = new boolean[BoardData.BOARD_SIZE][BoardData.BOARD_SIZE];
    private int[][] adjacencyArray = new int[][]{{0,1}, {1,0}, {0,-1}, {-1,0}}; // Array used to calculate coordinates of adjacent tiles
    private Stack<Coordinate> targetingShots = new Stack<>();
    private HashSet<Type> typesRemaining = new HashSet<>();
    private Type shortestTypeRemaining;
    private Coordinate prevShot;

    /**
     * Clears all data the AI currently knows.
     */
    public void reset() {
        for (int row = 0; row < BoardData.BOARD_SIZE; row++) {
            for (int column = 0; column < BoardData.BOARD_SIZE; column++) {
                validShots[row][column] = true;
            }
        }
        targetingShots.clear();
        prevShot = null;
        typesRemaining.addAll(Arrays.asList(Type.values()));
        shortestTypeRemaining = Type.DESTROYER;
    }

    /**
     * Updates the battleship AI's knowledge, used to update the targeting shots
     * and types remaining list.
     *
     * @param shotHit - whether the previous shot hit
     * @param typeSunk - the previous ship type sunk (null if no ship was sunk)
     */
    public void update(boolean shotHit, Type typeSunk) {
        /*
         * If a shot hit a ship, then one of the adjacent coordinates must another
         * segment of a ship.
         */
        if (shotHit) {
            for (int[] adjacent : adjacencyArray) {
                Coordinate adjCoordinate = new Coordinate(prevShot.getRow() + adjacent[0], prevShot.getColumn() + adjacent[1]);
                // Checks if the adjacent coordinate is out of bounds
                if (adjCoordinate.getRow() >= 0 && adjCoordinate.getRow() < BoardData.BOARD_SIZE && adjCoordinate.getColumn() >= 0 && adjCoordinate.getColumn() < BoardData.BOARD_SIZE) {
                    targetingShots.add(adjCoordinate);
                }
            }
        }
        if (typeSunk != null) {
            shortestTypeRemaining = Type.CARRIER; // Assumes the shortest ship remaining is the longest ship possible
            for (Type typeRemaining : typesRemaining) {
                if (typeRemaining.getLength() < shortestTypeRemaining.getLength()) {
                    shortestTypeRemaining = typeRemaining;
                }
            }
            typesRemaining.remove(typeSunk);
        }
    }

    /**
     * Gets the AI's shot, and updates the AI's knowledge. Also validates all
     * shots before returning them, ensuring no invalid shots are returned.
     *
     * @return - return's the AI's shot
     */
    public Coordinate getShot() {
        Coordinate shot = null;
        /*
         * Attempts to find a targeting shot by repeatedly popping the top
         * element of the stack and checking if it is valid.
         */
        while (targetingShots.size() > 0) {
            shot = targetingShots.pop();
            if (validShots[shot.getRow()][shot.getColumn()]) {
                break;
            }
        }
        if (shot == null) { // No valid targeting shot is found
            ArrayList<Coordinate> huntingShots = new ArrayList<>();
            /*
             * Loops over every board coordinate, determining hunting shots
             * based on the shortest type remaining.
             */
            for (int row = 0; row < BoardData.BOARD_SIZE; row++) {
                for (int column = 0; column < BoardData.BOARD_SIZE; column++) {
                    if ((row + column) % shortestTypeRemaining.getLength() == 0 && validShots[row][column]) {
                        huntingShots.add(new Coordinate(row, column));
                    }
                }
            }
            Collections.shuffle(huntingShots); // Randomizes order of elements hunting shots
            shot = huntingShots.get(0);
        }
        // Updates the valid shots grid and previous shot
        validShots[shot.getRow()][shot.getColumn()] = false;
        return prevShot = shot;
    }
}