package group2.Characters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import group2.GamePanel;

/**
 * Represents an enemy character that moves within the game.
 */
public class MovingEnemy extends Character {
    // arrays to hold the different animation sprites
    private BufferedImage[] walkingUpFrames = new BufferedImage[3];
    private BufferedImage[] walkingDownFrames = new BufferedImage[3];
    private BufferedImage[] walkingLeftFrames = new BufferedImage[3];
    private BufferedImage[] walkingRightFrames = new BufferedImage[3];
    private int currentFrameIndex;
    private int slowDown = 0;

    // keeps track of last movement to display correct animation
    private int lastDirection = -1;

    // keeps the enemy moving in a straight line for awhile before changing
    // direction
    private int stepsInCurrentDirection = 0;
    private final int maxStepsInDirection = 50;

    String dialogues[] = new String[20];

    /**
     * Constructs a new MovingEnemy with the specified position, size, and speed.
     *
     * @param x      The x-coordinate of the enemy's initial position.
     * @param y      The y-coordinate of the enemy's initial position.
     * @param width  The width of the enemy's bounding box.
     * @param height The height of the enemy's bounding box.
     * @param speed  The speed at which the enemy moves.
     */
    public MovingEnemy(int x, int y, int width, int height, int speed, int index) {
        super(x, y, width, height, speed); // 4 = speed (temp)
        this.index = index;
        // System.out.println("TESTING: Loading MovingEnemy images into our arrays!\n");
        walkingUpFrames = loadFrames("/resources/sprites/MovingEnemy/meUp", 3);
        walkingDownFrames = loadFrames("/resources/sprites/MovingEnemy/meDown", 3);
        walkingLeftFrames = loadFrames("/resources/sprites/MovingEnemy/meLeft", 3);
        walkingRightFrames = loadFrames("/resources/sprites/MovingEnemy/meRight", 3);
        setDialogue();
    }

    /**
     * Loads animation frames from the specified path.
     *
     * @param path       The path to the animation frames.
     * @param frameCount The number of frames to load.
     * @return An array of BufferedImage representing the animation frames.
     */
    private BufferedImage[] loadFrames(String path, int frameCount) {
        BufferedImage[] frames = new BufferedImage[frameCount];
        try {
            for (int i = 0; i < frameCount; i++) {
                // System.out.println("TESTING: Image Path: " + "game" + path + (i + 1) +
                // ".png\n");
                frames[i] = ImageIO.read(new File("game" + path + (i + 1) + ".png"));

                // testing
                // System.out.println("MovingEnemy Images loaded successfully!\n");
            }
        } catch (IOException e) {
            System.out.println("ERROR: Couldn't load MovingEnemy images!\n");
            e.printStackTrace();
        }
        return frames;
    }

    /**
     * Draws the enemy on the graphics context.
     *
     * @param g2 The Graphics2D context.
     * @param gp The GamePanel containing the main character.
     */
    public void draw(Graphics2D g2, GamePanel gp) {

        // System.out.println("TESTING: Drawing movingEnemy");
        BufferedImage frame = getCurrentFrame();

        int screenX = positionX + gp.mainCharacter.mcX - gp.mainCharacter.positionX;
        int screenY = positionY + gp.mainCharacter.mcY - gp.mainCharacter.positionY;

        if (frame != null) {
            g2.drawImage(frame, screenX, screenY, width, height, null);
        }
    }

    public void setDialogue() {
        dialogues[0] = "BRUH... Play better...";
    }

    public void speak(GamePanel gp) {
        GamePanel.currentDialogue = dialogues[0];
    }

    /**
     * Moves the enemy based on its current direction.
     */
    public void move() {
        // Handle character movement based on user input
        // currently movement is being handled here
        // instead -> call udpateLocation to check for stuff

        // -1/1 is n/s, -2/2 is e/w
        if (stepsInCurrentDirection >= maxStepsInDirection) {
            // Change direction and reset the step counter
            lastDirection = getRandomValue();
            stepsInCurrentDirection = 0;

            // Increase the sleep duration to slow down the enemy's movement after changing
            // direction

        } else {
            // Continue moving in the current direction
            updateLocation(lastDirection, "enemy");
            stepsInCurrentDirection++;
        }

        changeFrame();

        // arbritrarily using walkingUpFrames as that value = 6
        if (currentFrameIndex >= walkingUpFrames.length) {
            currentFrameIndex = 0;
        }
    }

    /**
     * Gets the current animation frame for rendering.
     *
     * @return The BufferedImage representing the current animation frame.
     */
    public BufferedImage getCurrentFrame() {
        BufferedImage[] currentFrames;

    // Use the last direction to determine the sprites
    switch (lastDirection) {
        case -1:
            currentFrames = walkingUpFrames;
            break;
        case 1:
            currentFrames = walkingDownFrames;
            break;
        case -2:
            currentFrames = walkingLeftFrames;
            break;
        case 2:
            currentFrames = walkingRightFrames;
            break;
        default:
            // Use a default or standing frame when no movement keys are pressed
            // For instance, using walkingDownFrames as the default frame
            currentFrames = walkingDownFrames;
            break;
    }

    // Ensure currentFrameIndex is within bounds of the currentFrames array
    if (currentFrameIndex >= 0 && currentFrameIndex < currentFrames.length) {
        return currentFrames[currentFrameIndex];
    } else {
        // If index is out of bounds, return the first frame (safeguarding)
        return currentFrames[0];
    }
    }

    /**
     * Gets the current animation frame index.
     *
     * @return The current animation frame index.
     */
    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }

    /**
     * Gets the animation frames for walking up.
     *
     * @return The array of BufferedImage representing walking up frames.
     */
    public BufferedImage[] getWalkingUpFrames() {
        return walkingUpFrames;
    }

    /**
     * Changes the animation frame based on a counter.
     */
    private void changeFrame() {
        slowDown++;
        if (slowDown == 5) {
            slowDown = 0;
            currentFrameIndex++;
        }
    }

    /**
     * Generates a random movement direction for the enemy.
     *
     * @return The randomly generated movement direction.
     */
    public static int getRandomValue() {
        Random random = new Random();

        // Generating a random index to select a value from the array
        int[] values = { -1, 1, -2, 2 };
        int randomIndex = random.nextInt(values.length);

        return values[randomIndex];
    }

    /**
     * Gets the last movement direction of the enemy.
     *
     * @return The last movement direction. Possible values: -1 (up), 1 (down), -2
     *         (left), 2 (right).
     */
    public int getLastDirection() {
        return lastDirection;
    }

    /**
     * Gets the maximum number of steps the enemy can move in the current direction
     * before changing its direction.
     *
     * @return The maximum number of steps in the current direction.
     */
    public int getMaxStepsInDirection() {
        return maxStepsInDirection;
    }
}
