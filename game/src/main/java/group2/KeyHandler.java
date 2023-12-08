package group2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler class implements the KeyListener interface to handle keyboard
 * input in our game.
 * It tracks the state of WASD, shift, and f
 */
public class KeyHandler implements KeyListener {

    GamePanel gp;

    // indicates whether the associated key is pressed or not
    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed, fPressed, oPressed;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Invoked when the associated key is pressed.
     *
     * @param e The KeyEvent that occurred.
     */
    // updates our booleans for when a key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (GamePanel.gameState != 3) {
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            
            //end game
            if (code == KeyEvent.VK_O) {
                oPressed = true;
            }

            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }

            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }

            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }

            if (code == KeyEvent.VK_SHIFT) {
                shiftPressed = true;
            }

            if (code == KeyEvent.VK_F) {
                fPressed = true;
            }
        }

        // Dialogue state
        else if (GamePanel.gameState == gp.diaglogueState) {
            if (code == KeyEvent.VK_ENTER) {
                GamePanel.gameState = 0;
            }
        }
    }

    /**
     * Invoked when the associated key is released
     *
     * @param e The KeyEvent that occurred.
     */
    // updates our booleans for when a key is released
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }

        if (code == KeyEvent.VK_F) {
            fPressed = false;
        }

        //end game
        if (code == KeyEvent.VK_O) {
            oPressed = false;
        }
    }

}