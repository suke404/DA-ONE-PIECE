package group2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import group2.Characters.MainCharacter;
import group2.Characters.MovingEnemy;
import group2.Menus.WinMenu;
import group2.Rewards.BaseReward;
import group2.Rewards.BonusRewards;
import group2.Rewards.Punishment;
import group2.tile.TileManager;
import group2.ui.WinMenu;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int ORIGINAL_TILE_SIZE = 16;
    final int SCALE = 3;

    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    public final int MAX_SCREEN_COL = 16;
    public final int MAX_SCREEN_ROW = 16;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;

    // World settings
    public final int MAX_WORLD_COL = 51;
    public final int MAX_WORLD_ROW = 51;
    public final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    public final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;

    // Game state
    public static int gameState = 3;
    public static int welcome = 1;
    public final int diaglogueState = 3;
    public static String currentDialogue = "Welcome to the DA ONE PIECE!";

    // Main Character Settings
    final int MC_SPAWN_X = 1 * TILE_SIZE;
    final int MC_SPAWN_Y = 1 * TILE_SIZE;

    // Entity settings
    final int BASE_REWARD_SIZE = 20;
    final int PUNISH_SIZE = 50;
    final int BONUS_REWARD_SIZE = 25;
    final int STANDARD_REWARD_VALUE = 1;
    final int STANDARD_PUNISH_VALUE = -1;

    // FPS
    final int FPS = 60;

    // gameLogic
    public static boolean gameEnd = false;
    public static int score;

    Board map = new Board(MAX_WORLD_COL, MAX_WORLD_ROW, 5, 5);

    TileManager tileM = new TileManager(this, map);

    // keyhandler for user input for main character
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;


    // Main Character
    public static ArrayList<MainCharacter> characters = new ArrayList<MainCharacter>();
    public MainCharacter mainCharacter = new MainCharacter(TILE_SIZE, TILE_SIZE, TILE_SIZE, TILE_SIZE, keyH, this);

    // Moving Enemy
    // MovingEnemy movingEnemy = new MovingEnemy(300, 300, TILE_SIZE, TILE_SIZE, 4);
    public static ArrayList<MovingEnemy> movingEnemies = new ArrayList<MovingEnemy>();
    // Base Rewards
    public static ArrayList<BaseReward> baseRewards = new ArrayList<BaseReward>();
    // BaseReward baseReward = new BaseReward(200, 200, BASE_REWARD_SIZE,
    // BASE_REWARD_SIZE, STANDARD_REWARD_VALUE);

    // Punishment
    // Punishment punishment = new Punishment(250, 250, PUNISH_SIZE, PUNISH_SIZE,
    // STANDARD_PUNISH_VALUE);
    public static ArrayList<Punishment> punishments = new ArrayList<Punishment>();
    // Bonus Reward
    // BonusRewards bonusReward = new BonusRewards(100, 200, BONUS_REWARD_SIZE,
    // BONUS_REWARD_SIZE, STANDARD_REWARD_VALUE);

    public static ArrayList<BonusRewards> bonusRewards = new ArrayList<BonusRewards>();

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        // better rendering performance
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        initializeEntities();
    }

    /**
     * This function spawns the enemy from the map generated in the board class. Each number is associated with its respective entity
     */
    public void initializeEntities() {
        characters.add(mainCharacter);

        int[][] map = tileM.getMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int value = map[j][i];

                // Passing in index of of 2D array and convert to world coordinates in
                // constructor

                int[] characterInput = { mainCharacter.mcX, mainCharacter.mcY, mainCharacter.positionX,
                        mainCharacter.positionY, TILE_SIZE };
                switch (value) {
                    //If the value in the 2D array is 3 it spawns a bonus reward
                    case 3:
                        BonusRewards bonusReward = new BonusRewards(j, i, BONUS_REWARD_SIZE, BONUS_REWARD_SIZE,
                                STANDARD_REWARD_VALUE, characterInput);
                        bonusRewards.add(bonusReward);
                        break;
                    //If the value is 4 it spawns a base reward
                    case 4:
                        BaseReward baseReward = new BaseReward(j, i, BASE_REWARD_SIZE, BASE_REWARD_SIZE,
                                STANDARD_REWARD_VALUE, characterInput);
                        baseRewards.add(baseReward);
                        break;
                    //If the value is 5 it spawns a moving enemy
                    case 5:
                        int index = movingEnemies.size();
                        MovingEnemy movingEnemy = new MovingEnemy(j, i, TILE_SIZE, TILE_SIZE, 4, index);
                        movingEnemies.add(movingEnemy);
                        break;
                    //If the value is 6 it spawns a punishment
                    case 6:
                        Punishment punishment = new Punishment(j, i, PUNISH_SIZE, PUNISH_SIZE, STANDARD_PUNISH_VALUE,
                                characterInput);
                        punishments.add(punishment);
                        break;
                }
            }
        }
    }
    /**
     * This function takes in the graphics2D object to spawn the dialogue within the dialogue window
     * @param g2
     */
    public void drawDiagloueScreen(Graphics2D g2) {

        // window
        int x = ORIGINAL_TILE_SIZE * 2;
        int y = ORIGINAL_TILE_SIZE / 2;
        int width = SCREEN_WIDTH - (ORIGINAL_TILE_SIZE * 4);
        int hegiht = ORIGINAL_TILE_SIZE * 10;

        drawSubWindow(x, y, width, hegiht, g2);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += ORIGINAL_TILE_SIZE + 10;
        y += ORIGINAL_TILE_SIZE + 20;
        g2.drawString(currentDialogue, x, y);
    }

    /**
     * This function draws the dialogue window itself with its position coordinates and dimensions as arguments
     * @param x
     * @param y
     * @param width
     * @param height
     * @param g2
     */
    public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // game loop: the core of the whole program
    @Override
    public void run() {
        // tileM.mapTileNum[2][2] = 9;
        double drawInterval = 1000000000 / FPS; // 0.01666 seoncds
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null && !gameEnd) {
            // receive update
            mainCharacter.move();
            mainCharacter.action();

            for (MovingEnemy i : movingEnemies) {
                // sending gp in so it can get mc offsets
                i.move();
            }

            // draw the screen with updated things
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                // if the update and repaint took more time than
                // the drawInerval then no time is left
                // rare but just in case
                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime); // typecast

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("stopped");
        // spawn window here
        new WinMenu();
    }
    /**
     * This function draws each of the entities that was spawned through initializeEntities()
     */
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // draw tile first as the first layer
        tileM.draw(g2);

        mainCharacter.draw(g2);

        for (MovingEnemy i : movingEnemies) {
            // sending gp in so it can get mc offsets
            i.draw(g2, this);
        }

        for (BonusRewards i : bonusRewards) {
            // i.updatePosition(mainCharacter.positionX, mainCharacter.positionY);
            i.updateAnimation();
            i.draw(g2, this);
        }

        for (Punishment i : punishments) {
            // i.updatePosition(mainCharacter.positionX, mainCharacter.positionY);
            i.updateAnimation();
            i.draw(g2, this);
        }

        for (BaseReward i : baseRewards) {
            // i.updatePosition(mainCharacter.positionX, mainCharacter.positionY);
            i.updateAnimation();
            i.draw(g2, this);
        }

        if (gameState == welcome) {
            currentDialogue = "Welcome to the DA ONE PIECEs!";
            drawDiagloueScreen(g2);
            gameState = 0;
        }

        if (gameState == diaglogueState) {
            drawDiagloueScreen(g2);
        }

        g2.dispose();

    }
}
