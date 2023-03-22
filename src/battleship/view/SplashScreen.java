/*
 *
 *
 *
 *
 */

package battleship.view;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JPanel {
    private ImageIcon splashScreenImg = new ImageIcon("src\\res\\splash_screen.png");
    private JProgressBar loadingBar = new JProgressBar();

    /**
     *
     */
    public SplashScreen() {
        setBounds(0, 0, 1280, 720);
        setLayout(null);

        loadingBar.setBounds(340, 345, 600, 30);
        add(loadingBar);
    }

    /**
     *
     */
    public void fakeLoading() {
        for (int percent = 0; percent <= 100; percent++) {
            try {
                loadingBar.setValue(percent);
                percent++;
                Thread.sleep(20);
            } catch (InterruptedException interruptedException) {

            }
        }
    }

    /**
     *
     * @param graphics
     */
    public void paintComponent(Graphics graphics) {
        splashScreenImg.paintIcon(this, graphics, 0, 0);
    }
}
