// Sprite Source: https://opengameart.org/content/camp-fire-animation-for-rpgs-finished

/**
 * The Punishment class represents a punishment in the game world.
 * Punishments are static and when the player collides with them, the 
 * Player's score should be reduced. If their score is less than 
 * Zero, the game ends.
 * 
 * Each punishment has an associated animation that plays when it appears in the game.
 *
 *
 * The class extends the StaticEntity class and implements methods for animations and drawing.
 */

package group2.Rewards;

import group2.StaticEntity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import group2.GamePanel;

import group2.Game;

public class Punishment extends StaticEntity {

    // arrays to hold the different animation sprites
    private BufferedImage[] bufferedImage = new BufferedImage[5];
    private int currentFrameIndex;
    private int slowDown = 0;

    /**
     * Constructs a Punishment object with the specified parameters.
     *
     * @param positionX         The X-coordinate of the punishment in the game
     *                          world.
     * @param positionY         The Y-coordinate of the punishment in the game
     *                          world.
     * @param height            The height of the punishment.
     * @param width             The width of the punishment.
     * @param punishValue       The negative value representing the impact of the
     *                          punishment on the score.
     * @param mainCharacterData An array containing main character data: [mcX, mcY,
     *                          mcPosX, mcPosY, TILE_SIZE].
     * @throws IllegalArgumentException If the punishValue is not negative.
     */

    public Punishment(int positionX, int positionY, int height, int width, int punishValue, int[] mainCharacterData) {
        super(positionX, positionY, height, width, punishValue, mainCharacterData);
        if (punishValue >= 0) {
            throw new IllegalArgumentException("Punishment value must be negative!");
        }
        bufferedImage = loadFrames("/resources/sprites/Punishment/punish", 5);
    }

    /**
     * Loads animation frames from the specified path.
     *
     * @param path       The path to the animation frames.
     * @param frameCount The number of frames in the animation.
     * @return An array of BufferedImages representing the animation frames.
     */

    private BufferedImage[] loadFrames(String path, int frameCount) {
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

    private void changeFrame() {
        slowDown++;
        if (slowDown == 5) {
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

        if (frame != null) {
            // Draw the frame at the desired position (e.g., getX() and getY())
            int screenX = getPositionX() + gp.mainCharacter.mcX - gp.mainCharacter.positionX;
            int screenY = getPositionY() + gp.mainCharacter.mcY - gp.mainCharacter.positionY;

            g2.drawImage(frame, screenX, screenY, getWidth(), getHeight(), null);
        }
    }

    // getters and setters
    public int getPunishValue() {
        return this.getEffectValue();
    }

    public void setPunishValue(int punishValue) {
        this.setEffectValue(punishValue);
    }

    /**
     * Decreases the game score based on the punishment value.
     *
     * @param game        The Game object.
     * @param punishValue The punishment value to subtract from the score.
     */
    public void decreaseScore(Game game, int punishValue) {
        game.updateScore(punishValue);

    }
}
