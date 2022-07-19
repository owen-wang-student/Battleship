/*
 * Orientation.java
 * Enum used for the orientation a ship.
 * Owen Wang
 * ICS4U1
 * 2022-06-08
 */
package battleship.model;

import java.util.Random;

public enum Orientation {
    HORIZONTAL, VERTICAL;

    /**
     * Returns a random orientation
     *
     * @return - a random item from the enum's values
     */
    public static Orientation getRandomOrientation() {
        Random random = new Random();
        return values()[random.nextInt(2)];
    }
}
