// Sprite Source: https://opengameart.org/content/pixel-coins-asset

/**
 * The BaseReward class represents the required rewards that need to be
 * Collected in order to win.
 * 
 * Rewards should increase the player's score
 * 
 * Each reward has an associated animation that plays when it appears in the game.
 *
 * The class extends the StaticEntity class and implements methods for animations and drawing.
 */
package group2.Rewards;

import group2.Game;
import group2.StaticEntity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import group2.GamePanel;

public class BaseReward extends StaticEntity {

    // attributes
    private Boolean isRequired;

    // arrays to hold the different animation sprites
    private BufferedImage[] bufferedImage = new BufferedImage[6];

    private int currentFrameIndex;

    private int slowDown = 0;

    /**
     * Constructs a BaseReward object with the specified parameters.
     *
     * @param positionX         The X-coordinate of the reward in the game world.
     * @param positionY         The Y-coordinate of the reward in the game world.
     * @param height            The height of the reward.
     * @param width             The width of the reward.
     * @param rewardValue       The positive value representing the impact of the
     *                          reward on the score.
     * @param mainCharacterData An array containing main character data: [mcX, mcY,
     *                          mcPosX, mcPosY, TILE_SIZE].
     * @throws IllegalArgumentException If the rewardValue is not greater than 0.
     */

    public BaseReward(int positionX, int positionY, int height, int width, int rewardValue, int[] mainCharacterData) {
        super(positionX, positionY, height, width, rewardValue, mainCharacterData);
        if (rewardValue <= 0) {
            throw new IllegalArgumentException("Reward value must be greater than 0!");
        }
        this.isRequired = true;

        bufferedImage = loadFrames("/resources/sprites/BaseReward/reward", 6);
    }

    /**
     * Loads animation frames from the specified path.
     *
     * @param path       The path to the animation frames.
     * @param frameCount The number of frames in the animation.
     * @return An array of BufferedImages representing the animation frames.
     */
    public BufferedImage[] loadFrames(String path, int frameCount) {
        BufferedImage[] frames = new BufferedImage[frameCount];
        try {
            for (int i = 0; i < frameCount; i++) {
                // testing
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
        if (slowDown == 7) {
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
     * Draws the punishment on the graphics context.
     *
     * @param g2 The Graphics2D context to draw on.
     * @param gp The GamePanel containing the main character information.
     */
    public void draw(Graphics2D g2, GamePanel gp) {
        BufferedImage frame = getCurrentFrame();
        // System.out.println("reward is at: " + getPositionX() + ", " +
        // getPositionY());

        if (frame != null) {
            // Draw the frame at the desired position (e.g., getX() and getY())
            int screenX = getPositionX() + gp.mainCharacter.mcX - gp.mainCharacter.positionX;
            int screenY = getPositionY() + gp.mainCharacter.mcY - gp.mainCharacter.positionY;

            g2.drawImage(frame, screenX, screenY, getWidth(), getHeight(), null);
        }
    }

    // getters and setter
    public int getRewardValue() {
        return this.getEffectValue();
    }

    public void setRewardValue(int rewardValue) {
        this.setEffectValue(rewardValue);
    }

    public Boolean getIsRequired() {
        return isRequired;
    }

    public void setRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * Increases the game score based on the reward value.
     *
     * @param game        The Game object.
     * @param rewardValue The reward value to add to the player score.
     */
    public void increaseScore(Game game, int rewardValue) {
        game.updateScore(rewardValue);
    }

    // Returns the current index frame
    public int getCurrentFrameIndex() {
        return currentFrameIndex;
    }

    // sets the current index frame
    public void setCurrentFrameIndex(int currentFrameIndex) {
        this.currentFrameIndex = currentFrameIndex;
    }

    // sets the current frame image
    public void setBufferedImage(BufferedImage[] bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
