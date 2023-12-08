// Sprite Source: https://opengameart.org/content/hero-character-sprite-sheet

package group2.Characters;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import group2.GamePanel;
import group2.KeyHandler;
import group2.tile.TileManager;

/**
 * Class for the main character
 */
public class MainCharacter extends Character {
    KeyHandler mcKey;
    public final int mcX;
    public final int mcY;
    int mcSpeed = 4;
    public GamePanel gp;
    public int score = 0;
    public final int scoreToWin = 1;
    public boolean hit;

    // arrays to hold the different animation sprites
    private BufferedImage[] walkingUpFrames = new BufferedImage[6];
    private BufferedImage[] walkingDownFrames = new BufferedImage[6];
    private BufferedImage[] walkingLeftFrames = new BufferedImage[6];
    private BufferedImage[] walkingRightFrames = new BufferedImage[6];
    private int currentFrameIndex;
    private int slowDown = 0;

    /**
     * 
     * @param x          x
     * @param y          y
     * @param width      width
     * @param height     height
     * @param keyHandler reads player input
     * @param gp         master game panel
     */
    public MainCharacter(int x, int y, int width, int height, KeyHandler keyHandler, GamePanel gp) {
        // sent to parent class: character
        super(x, y, width - 15, height - 15, 4);
        this.positionX = x;
        this.positionY = y;
        this.gp = gp;

        mcX = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);
        mcY = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);
        // this.mcX = x;
        // this.mcY = y;

        // should be smaller than character itself
        // Need more testing for sure, the measure is pretty off i think
        // need to open a paint or sth to cheak how many pixel is the character
        collisionArea = new Rectangle();
        collisionArea.x = 8;
        collisionArea.y = 16;
        collisionArea.width = 32;
        collisionArea.height = 32;

        // keyhandler is handled here because it is unique to main character
        mcKey = keyHandler;
        // calls the method that loads our sprites into our arrays. There is
        // a total of 6 frames for each direction

        // System.out.println("TESTING: Loading MainCharacter images into our
        // arrays!\n");
        walkingUpFrames = loadFrames("/resources/sprites/MainCharacter/mcUp", 6);
        walkingDownFrames = loadFrames("/resources/sprites/MainCharacter/mcDown", 6);
        walkingLeftFrames = loadFrames("/resources/sprites/MainCharacter/mcLeft", 6);
        walkingRightFrames = loadFrames("/resources/sprites/MainCharacter/mcRight", 6);

    }

    /**
     * cycle through images for walking animations
     * 
     * @param path       image path
     * @param frameCount amount of frames
     * @return list of images to buffer
     */
    private BufferedImage[] loadFrames(String path, int frameCount) {
        BufferedImage[] frames = new BufferedImage[frameCount];
        try {
            for (int i = 0; i < frameCount; i++) {
                // System.out.println("TESTING: Image Path: " + "game" + path + (i + 1) +
                // ".png\n");
                frames[i] = ImageIO.read(new File("game" + path + (i + 1) + ".png"));

                // testing
                // System.out.println("Images loaded successfully!\n");
            }
        } catch (IOException e) {
            System.out.println("ERROR: Couldn't load images!\n");
            e.printStackTrace();
        }
        return frames;
    }

    /**
     * buffer to make frame changes more smoother
     */
    private void changeFrame() {
        slowDown++;
        if (slowDown == 7) {
            slowDown = 0;
            currentFrameIndex++;
        }
    }

    /**
     * when player presses f, it will check for an endpoint, and if score is
     * reached, game will end
     */
    public void action() {
        if (mcKey.fPressed) {
            int gridX = positionX / 48;
            int gridY = positionY / 48;
            int gridType = TileManager.checkTile(gridX, gridY);
            System.out.println(GamePanel.score);

            if (gridType != 9) {
                return;
            }
            System.out.println("at the end!");

            // This is the win condition
            if (GamePanel.score >= scoreToWin) {
                GamePanel.gameEnd = true;
            }
        }

        if (mcKey.oPressed) {
            GamePanel.gameEnd = true;
        }
    }

    /**
     * checks key input and send info to updateLocation() to move player
     */
    public void move() {
        // Handle character movement based on user input
        // currently movement is being handled here
        // instead -> call udpateLocation to check for stuff

        // 1/-1 is n/s, 2/-2 is e/w

        if (mcKey.shiftPressed) {
            sprint = true;
        } else {
            sprint = false;
        }

        if (mcKey.upPressed == true || mcKey.downPressed == true ||
                mcKey.leftPressed == true || mcKey.rightPressed == true) {

            // if enemy hits player
            if (hit) {
                GamePanel.score--;
                hit = false;
            }
            if (mcKey.upPressed) {
                updateLocation(-1, "player");
                changeFrame();
            } else if (mcKey.downPressed) {
                changeFrame();
                updateLocation(1, "player");
            } else if (mcKey.leftPressed) {
                updateLocation(-2, "player");
                changeFrame();
            } else if (mcKey.rightPressed) {
                updateLocation(2, "player");
                changeFrame();
            }

            // arbritrarily using walkingUpFrames as that value = 6
            if (currentFrameIndex >= walkingUpFrames.length) {
                currentFrameIndex = 0;
            }
        }
    }

    /**
     * check frames if key input changes
     */
    public BufferedImage getCurrentFrame() {
        BufferedImage[] currentFrames;

        if (mcKey.upPressed) {
            currentFrames = walkingUpFrames;
        } else if (mcKey.downPressed) {
            currentFrames = walkingDownFrames;
        } else if (mcKey.leftPressed) {
            currentFrames = walkingLeftFrames;
        } else if (mcKey.rightPressed) {
            currentFrames = walkingRightFrames;
        } else {
            // Use a default frame or standing frame when no movement keys are pressed
            // For example, you can use walkingDownFrames as the default frame
            currentFrames = walkingDownFrames;
        }

        return currentFrames[currentFrameIndex];
    }

    /**
     * draw player
     * 
     * @param g2 object
     */
    public void draw(Graphics2D g2) {
        BufferedImage frame = getCurrentFrame();

        if (frame != null) {
            g2.drawImage(frame, mcX, mcY, width, height, null);
        }
    }

    /**
     * Retrieves the KeyHandler associated with the MainCharacter.
     *
     * @return The KeyHandler object used for handling input events for the
     *         MainCharacter.
     */
    public KeyHandler getMcKey() {
        return mcKey;
    }

}
