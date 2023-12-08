package group2;

import org.junit.Test;
import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
public class GamePanelTest {
    @Test
    public void testConstructor(){
        GamePanel gamePanel = new GamePanel();
        assertTrue(true);
    }

    @Test
    public void testInitializeEntities(){
        GamePanel gamePanel = new GamePanel();
        gamePanel.initializeEntities();
        assertTrue(true);
    }
}
