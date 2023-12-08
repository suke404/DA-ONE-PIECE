package group2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StaticEntityTest {
    @Test
    void testConstructorInitialization() {
        int positionX = 2;
        int positionY = 3;
        int height = 20;
        int width = 10;
        int effectValue = 5;
        int[] mainCharacterData = { 10, 15, 5, 7, 32 };

        StaticEntity staticEntity = new StaticEntity(positionX, positionY, height, width, effectValue,
                mainCharacterData);

        assertEquals(positionX * mainCharacterData[4], staticEntity.getPositionX());
        assertEquals(positionY * mainCharacterData[4], staticEntity.getPositionY());
        assertEquals(height, staticEntity.getHeight());
        assertEquals(width, staticEntity.getWidth());
        assertEquals(effectValue, staticEntity.getEffectValue());
    }

    @Test
    void testGettersAndSetters() {
        StaticEntity staticEntity = new StaticEntity(1, 1, 10, 5, 3, new int[] { 5, 5, 2, 2, 32 });

        staticEntity.setHeight(15);
        assertEquals(15, staticEntity.getHeight());

        staticEntity.setWidth(8);
        assertEquals(8, staticEntity.getWidth());

        staticEntity.setEffectValue(7);
        assertEquals(7, staticEntity.getEffectValue());

        staticEntity.setPositionX(3);
        assertEquals(1022, staticEntity.getPositionX());

        staticEntity.setPositionY(4);
        assertEquals(
                32703, staticEntity.getPositionY());
    }

    @Test
    void testUpdatePosition() {
        StaticEntity staticEntity = new StaticEntity(2, 2, 10, 5, 3, new int[] { 5, 5, 2, 2, 32 });
        staticEntity.updatePosition(8, 10);
        assertEquals(2051, staticEntity.getScreenX());
        assertEquals(2053, staticEntity.getScreenY());
    }

}
