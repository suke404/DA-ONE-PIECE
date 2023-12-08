package group2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import group2.Rewards.BaseReward;

class BaseRewardTest {

    @Test
    void testConstructor() {
        int positionX = 1;
        int positionY = 2;
        int height = 32;
        int width = 32;
        int rewardValue = 10;
        int[] mainCharacterData = { 0, 0, 0, 0, 32 };

        BaseReward reward = new BaseReward(positionX, positionY, height, width, rewardValue, mainCharacterData);

        assertEquals(positionX * height, reward.getPositionX());
        assertEquals(positionY * height, reward.getPositionY());
        assertEquals(height, reward.getHeight());
        assertEquals(width, reward.getWidth());
        assertEquals(rewardValue, reward.getEffectValue());
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
            new BaseReward(positionX, positionY, height, width, invalidRewardValue, mainCharacterData);
        });
    }

    @Test
    void testUpdateAnimation() {
        BaseReward reward = new BaseReward(1, 1, 32, 32, 10, new int[] { 0, 0, 0, 0, 32 });

        for (int i = 0; i < 10; i++) {
            reward.updateAnimation();
        }

        assertEquals(1, reward.getCurrentFrameIndex());
    }

    @Test
    void testChangeFrame() {
        BaseReward reward = new BaseReward(1, 1, 32, 32, 10, new int[] { 0, 0, 0, 0, 32 });

        for (int i = 0; i < 7; i++) {
            reward.changeFrame();
        }

        assertEquals(1, reward.getCurrentFrameIndex());
    }

    @Test
    void testGetCurrentFrame() {
        BaseReward reward = new BaseReward(1, 1, 32, 32, 10, new int[] { 0, 0, 0, 0, 32 });

        BufferedImage[] frames = reward.loadFrames("/resources/sprites/BaseReward/reward", 6);

        reward.setBufferedImage(frames);
        reward.setCurrentFrameIndex(2);

        assertEquals(frames[2], reward.getCurrentFrame());
    }

}
