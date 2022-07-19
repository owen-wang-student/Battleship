/*
 * Stats.java
 * Used to track stats of the game, i.e. total user and computer wins. Implements
 * marker interface Serializable, allowing the class to be serialized into a txt
 * file.
 * Daniel Alp
 * ICS4U1
 * 2022-06-08
 */

package battleship.model;

import java.io.Serializable;

public class Stats implements Serializable {
    int userWins = 0;
    int computerWins = 0;

    /**
     *
     * @return
     */
    public int getUserWins() {
        return userWins;
    }

    /**
     *
     * @return
     */
    public int getComputerWins() {
        return computerWins;
    }

    /**
     *
     */
    public void increaseUserWins() {
        userWins++;
    }

    /**
     *
     */
    public void increaseComputerWins() {
        computerWins++;
    }
}