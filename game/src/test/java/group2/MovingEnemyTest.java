package group2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import group2.Characters.MovingEnemy;

public class MovingEnemyTest {

    @Test
    public void testGetCurrentFrame() {
        MovingEnemy enemy = new MovingEnemy(0, 0, 32, 32, 5, 0);

        // Assuming initial direction is -1 (up), the current frame should be from
        // walkingUpFrames
        assertEquals(enemy.getCurrentFrame(), enemy.getWalkingUpFrames()[enemy.getCurrentFrameIndex()]);
    }

    @Test
    public void testGetRandomValue() {
        int randomValue = MovingEnemy.getRandomValue();

        // The random value should be one of the specified values (-1, 1, -2, 2)
        assertTrue(randomValue == -1 || randomValue == 1 || randomValue == -2 || randomValue == 2);
    }

    @Test
    public void testChangeDirectionAfterMaxSteps() {
        MovingEnemy enemy = new MovingEnemy(0, 0, 32, 32, 5, 0);

        // Simulate movement for maxStepsInDirection
        for (int i = 0; i < enemy.getMaxStepsInDirection(); i++) {
            enemy.move();
        }

        int initialDirection = enemy.getLastDirection();

        // After max steps, the direction should change
        enemy.move();
        int newDirection = enemy.getLastDirection();

        assertTrue(initialDirection != newDirection);
    }
}
