// Sprite Sources: https://opengameart.org/content/happy-beer-mug-animated

package group2.Rewards;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import group2.GamePanel;

public class BonusRewards extends BaseReward {

    // sets the bounds for the random multiplier applied to base reward value
    private static final int MIN_MULTIPLIER = 2;
    private static final int MAX_MULTIPLIER = 10;

    // arrays to hold the different animation sprites
    private BufferedImage[] bufferedImage = new BufferedImage[4];
    private int currentFrameIndex;
    private int slowDown = 0;

    /**
     * Constructs a BonusRewards object with the specified parameters.
     *
     * @param positionX         the x-coordinate of the position
     * @param positionY         the y-coordinate of the position
     * @param height            the height of the bonus reward
     * @param width             the width of the bonus reward
     * @param rewardValue       the base reward value before applying the bonus
     *                          multiplier
     * @param mainCharacterData an array containing data about the main character
     */
    public BonusRewards(int positionX, int positionY, int height, int width, int rewardValue, int[] mainCharacterData) {
        super(positionX, positionY, height, width, calculateBonus(rewardValue), mainCharacterData);
        bufferedImage = loadFrames("/resources/sprites/BonusReward/bonus", 4);
    }

    /**
     * Loads animation frames from the specified path with the given frame count.
     *
     * @param path       the path to the sprite images
     * @param frameCount the number of frames in the animation
     * @return an array of BufferedImage containing the animation frames
     */
    public BufferedImage[] loadFrames(String path, int frameCount) {
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

    public void updateAnimation() {
        // Advance the frame index for the animation
        changeFrame();
    }

    public void changeFrame() {
        slowDown++;
        if (slowDown == 10) {
            slowDown = 0;
            currentFrameIndex++;
        }

        // Reset the frame index to loop the animation
        if (currentFrameIndex >= bufferedImage.length) {
            currentFrameIndex = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        return bufferedImage[currentFrameIndex];
    }

    /**
     * Draws the bonus reward on the specified Graphics2D object and GamePanel.
     *
     * @param g2 the Graphics2D object on which to draw the bonus reward
     * @param gp the GamePanel containing information about the game state
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        BufferedImage frame = getCurrentFrame();

        if (frame != null) {
            // Draw the frame at the desired position (e.g., getX() and getY())
            int screenX = getPositionX() + gp.mainCharacter.mcX - gp.mainCharacter.positionX;
            int screenY = getPositionY() + gp.mainCharacter.mcY - gp.mainCharacter.positionY;

            g2.drawImage(frame, screenX, screenY, getWidth(), getHeight(), null);
        }
    }

    /**
     * Calculates the bonus value by applying a random multiplier to the base reward
     * value.
     *
     * @param rewardValue the base reward value before applying the bonus multiplier
     * @return the calculated bonus value
     */
    private static int calculateBonus(int rewardValue) {
        Random random = new Random();
        int randomMultiplier = random.nextInt(MAX_MULTIPLIER - MIN_MULTIPLIER + 1) + MIN_MULTIPLIER;
        return rewardValue * randomMultiplier;
    }

}
