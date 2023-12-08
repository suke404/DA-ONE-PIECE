package group2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

import group2.Rewards.BonusRewards;
public class BonusRewardTest {
    @Test
    void testConstructor() {
        int positionX = 1;
        int positionY = 2;
        int height = 32;
        int width = 32;
        int rewardValue = 10;
        int[] mainCharacterData = { 0, 0, 0, 0, 32 };

        BonusRewards reward = new BonusRewards(positionX, positionY, height, width, rewardValue, mainCharacterData);

        assertEquals(positionX * height, reward.getPositionX());
        assertEquals(positionY * height, reward.getPositionY());
        assertEquals(height, reward.getHeight());
        assertEquals(width, reward.getWidth());
        assertEquals(rewardValue, 10);
        assertTrue(reward.getIsRequired());
    }

    @Test
    void testConstructorInvalidRewardValue() {
        int positionX = 1;
        int positionY = 2;
        int height = 32;
        int width = 32;
        int invalidRewardValue = 0;
        int[] mainCharacterData = { 0, 0, 0, 0, 32 };

        assertThrows(IllegalArgumentException.class, () -> {
            new BonusRewards(positionX, positionY, height, width, invalidRewardValue, mainCharacterData);
        });
    }

    @Test
    void testUpdateAnimation() {
        BonusRewards reward = new BonusRewards(1, 1, 32, 32, 10, new int[] { 0, 0, 0, 0, 32 });

        for (int i = 0; i < 10; i++) {
            reward.updateAnimation();
        }

        assertEquals(0, reward.getCurrentFrameIndex());
    }

    @Test
    void testChangeFrame() {
        BonusRewards reward = new BonusRewards(1, 1, 32, 32, 10, new int[] { 0, 0, 0, 0, 32 });

        for (int i = 0; i < 7; i++) {
            reward.changeFrame();
        }

        assertEquals(0, reward.getCurrentFrameIndex());
    }

    @Test
    void testGetCurrentFrame() {
        BonusRewards reward = new BonusRewards(1, 1, 32, 32, 10, new int[] { 0, 0, 0, 0, 32 });

        BufferedImage[] frames = reward.loadFrames("/resources/sprites/BaseReward/reward", 6);

        reward.setBufferedImage(frames);
        reward.setCurrentFrameIndex(2);

        assertEquals(frames[2], reward.getCurrentFrame());
    }
}
