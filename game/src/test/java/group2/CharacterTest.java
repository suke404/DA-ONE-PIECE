package group2;

import group2.Characters.Character;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CharacterTest {

    @Test
    void testInitialization() {
        int x = 2;
        int y = 3;
        int length = 32;
        int width = 16;
        int speed = 5;

        Character character = new Character(x, y, length, width, speed);

        assertEquals(x * length, character.getX());
        assertEquals(y * length, character.getY());
        assertEquals(length, character.getHeight());
        assertEquals(width, character.getWidth());
        assertEquals(speed, character.getSpeed());
        assertNull(character.getCurrentFrame());
    }

    @Test
    void testSetters() {
        Character character = new Character(0, 0, 32, 16, 5);

        int newX = 3;
        int newY = 4;
        int newSpeed = 8;

        character.setPositionX(newX);
        character.setPositionY(newY);
        character.setSpeed(newSpeed);

        assertEquals(newX, character.getX());
        assertEquals(newY, character.getY());
        assertEquals(newSpeed, character.getSpeed());
    }

    @Test
    void testBoxCollision() {
        Character character = new Character(0, 0, 32, 16, 5);

        assertTrue(character.boxCollision(0, 0, 16, 16, 10, 10, 16, 16));
        assertFalse(character.boxCollision(0, 0, 16, 16, 20, 20, 16, 16));
    }

}
