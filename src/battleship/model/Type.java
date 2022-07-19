/*
 * Type.java
 * Owen Wang
 * ICS4U1
 * 2022-06-08
 */

package battleship.model;

public enum Type {
    // Passes the length for each type of ship as an argument when initializing enum values
    CARRIER(5), BATTLESHIP(4), CRUISER(3), SUBMARINE(3), DESTROYER(2);

    private int length;

    /**
     * Assigns class member variable length to the respective parameter for
     * each specific type.
     *
     * @param length
     */
    Type(int length) {
        this.length = length;
    }

    /**
     * Returns the length of the specific type.
     *
     * @return - length of the specific type.
     */
    public int getLength() {
        return length;
    }
}
