package group2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import group2.Characters.Character;

public class CollisionTest {
    Character test = new Character(0, 0, 0, 0, 0);

    @Test
    public void testInBounds() {
        assertTrue(test.inBounds(5, 2, 5));
        assertFalse(test.inBounds(1, 5, 10));
        assertFalse(test.inBounds(15, 2, 5));
        assertTrue(test.inBounds(1, 0, 1));
    }

    @Test
    public void testInBox() {
        assertTrue(test.inBox(2, 5, 4, 8));
        assertFalse(test.inBox(2, 5, 9, 2));
        assertFalse(test.inBox(2, 5, 12, 3));

        // extra test: box 1 is within box 2
        assertTrue(test.inBox(2, 1, 0, 10));
        assertTrue(test.inBox(0, 10, 2, 1));
    }

    @Test
    public void testBoxCollision() {
        assertTrue(test.boxCollision(0, 0, 5, 5, 3, 3, 4, 4));
        assertFalse(test.boxCollision(0, 0, 5, 5, 6, 6, 2, 2));
        assertFalse(test.boxCollision(0, 0, 5, 5, 7, 7, 1, 1));

        // smaller than
        assertTrue(test.boxCollision(1, 1, 1, 1, 0, 0, 5, 5));
        assertTrue(test.boxCollision(1, 1, 10, 10, 2, 2, 5, 5));
    }
}
