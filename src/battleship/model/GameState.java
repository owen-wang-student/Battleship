/*
 * GameState.java
 * Enum for the current states of the game. Used by classes in the view package
 * to decide how to render the user and computer boards.
 * Owen Wang
 * ICS4U1
 * 2022-06-10
 */

package battleship.model;

public enum GameState {
    SETUP, MATCH, RESULT;

    private static GameState state;

    /**
     * Returns the current state of the game.
     *
     * @return - current state of the game
     */
    public static GameState getState() {
        return state;
    }

    /**
     * Updates the current state of the game.
     *
     * @param state - current state of the game.
     */
    public static void setState(GameState state) {
        GameState.state = state;
    }
}
