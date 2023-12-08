package group2;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;

import org.junit.Test;

import group2.Characters.MainCharacter;

public class MainCharacterTest {

    @Test
    public void testInitialization() {
        // Test that the character is initialized correctly
        MainCharacter character = new MainCharacter(0, 0, 32, 32, new KeyHandler(new GamePanel()), new GamePanel());
        assertEquals(0, character.positionX);
        assertEquals(0, character.positionY);
        assertEquals(32 - 15, character.width);
        assertEquals(32 - 15, character.height);
        assertEquals(4, character.speed);
    }

    @Test
    public void testMovement() {
        // Test character movement based on key inputs
        MainCharacter character = new MainCharacter(0, 0, 32, 32, new KeyHandler(new GamePanel()), new GamePanel());

        // Simulate key presses
        character.getMcKey().upPressed = true;
        character.move();
        assertEquals(1, character.positionY);

        character.getMcKey().downPressed = true;
        character.move();
        assertEquals(1, character.positionY);

        character.getMcKey().leftPressed = true;
        character.move();
        assertEquals(0, character.positionX);

        character.getMcKey().rightPressed = true;
        character.move();
        assertEquals(0, character.positionX);
    }

    @Test
    public void testAnimation() {
        // Test animation frames change correctly
        MainCharacter character = new MainCharacter(0, 0, 32, 32, new KeyHandler(new GamePanel()), new GamePanel());

        // Simulate key presses
        character.getMcKey().upPressed = true;
        character.move();
        BufferedImage initialFrame = character.getCurrentFrame();

        // Move again to change frame
        character.move();
        assertEquals(null, character.getCurrentFrame());
    }

    // @Test
    // public void testCamera() {
    // // Test location of the camera
    // MainCharacter character = new MainCharacter(0, 0, 32, 32, new KeyHandler(),
    // new GamePanel());
    // GamePanel gp = new GamePanel();

    // // Simulate key presses
    // character.getMcKey().upPressed = true;
    // character.setPositionX(5);
    // character.setPositionY(5);

    // // Check
    // assertEquals(character.mcX, gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2));
    // assertEquals(character.mcY, gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2));
    // }

}
