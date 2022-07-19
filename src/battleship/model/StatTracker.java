/*
 * StatTracker.java
 *
 * Daniel Alp
 * ICS4U1
 * 2022-06-08
 */

package battleship.model;

import java.io.*;

public class StatTracker {
    private Stats stats;
    private File statsFile = new File("stats.txt");

    /**
     *
     */
    public StatTracker() {
        /*
         * If stats file
         */
        if (!statsFile.exists()) {
            try {
                statsFile.createNewFile();
            } catch (IOException ioException) {
                System.out.println();
            }
            stats = new Stats();
        } else {
            /*
             * Uses resource management to automatically close object input stream after
             */
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(statsFile))) {
                stats = (Stats) objectInputStream.readObject();
            } catch (IOException ioException) {
                System.out.println();
            } catch (ClassNotFoundException classNotFoundException) {
                System.out.println();
            }

        }
    }

    /**
     * Returns the game statistics
     *
     * @return - the game statistics
     */
    public Stats getStats() {
        return stats;
    }

    /**
     *
     */
    public void updateStatsFile() {
        /*
         * Uses resource management to automatically try to close output stream after using it.
         */
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(statsFile))) {
            objectOutputStream.writeObject(stats);
            objectOutputStream.flush();
        } catch (IOException ioException) {
            System.out.println();
        }
    }
}
