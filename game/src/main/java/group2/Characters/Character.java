package group2.Characters;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import group2.GamePanel;
import group2.tile.TileManager;

/**
 * The base class representing a character in the game.
 */
public class Character {
    /**
     * character co-ordinate positions, width, height, and speed
     */
    public Integer speed, positionX, positionY, width, height;
    /*
     * if the character is sprinting
     */
    protected boolean sprint;
    /*
     * movement direction
     */
    protected int index = 0;

    // checking for collision
    public Rectangle collisionArea;
    public boolean collisionOn = false;

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    private BufferedImage currentFrame;
    // public int direction //could be a vector
    // might need key/value to call on?

    // 2 differerent sprites
    // private int spriteCodeLeft, spriteCodeRight;

    /**
     * Creates a new character with the specified initial position, size, and speed.
     *
     * @param x      The initial X-coordinate.
     * @param y      The initial Y-coordinate.
     * @param length The length (height and width) of the character.
     * @param width  The width of the character.
     * @param speed  The speed of the character.
     */
    public Character(int x, int y, int length, int width, int speed) {
        this.positionX = x * length;
        this.positionY = y * length;
        this.height = length;
        this.width = width;

        this.speed = speed;
        // get current grid position

    }

    /**
     * @return returns speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed new speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public BufferedImage getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(BufferedImage currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * @return width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * 
     * @return height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * 
     * @return x
     */
    public int getX() {
        return this.positionX;
    }

    /**
     * 
     * @return y
     */
    public int getY() {
        return this.positionY;
    }

    /**
     * checks for collisions and if none, updates location accordingly, also handles
     * game rewards and punishments for box collisions
     * 
     * @param direction the direction to be moved in
     * @param type      the type of entity that is attempting to move (example:
     *                  player)
     */
    // collisions will be in another file, so we will be sending the arrays
    protected void updateLocation(int direction, String type) {
        int gridSize = 48;
        // get new position
        // first copy old

        // new position is the furthest reach of our move (ex move right, position new =
        // x + length + speed)
        int checkPosition, checkGrid, a, b, gridType, gridTypeAlt = 0;
        int speed = this.speed;
        if (sprint) {
            speed += 5;
        }

        // System.out.println("character is at: " + positionX + ", " + positionY);
        switch (direction) {
            // up
            case -1:
                // I want to do box collisions on the grids to my left
                // box collision takes (x, y) -> top left, width, and height
                checkPosition = positionY + direction * speed; // subtracts speed from Y (up)
                checkGrid = checkPosition / gridSize;

                //
                a = positionX / gridSize;
                b = (positionX + width) / gridSize;
                gridType = TileManager.checkTile(a, checkGrid);
                gridTypeAlt = TileManager.checkTile(b, checkGrid);

                if (gridType == 1 || gridTypeAlt == 1) {
                    // partial movement to grid position
                    // get the y co-ordinate of interfering grid, -> positionY
                    positionY = (positionY + (direction * speed) + height) / gridSize; // idk why but adding height in
                                                                                       // there works?
                    positionY *= gridSize; // normalize
                    positionY++;
                } else {
                    positionY += direction * speed;
                }

                break;
            // down
            case 1:
                // I want to do box collisions on the grids to my left
                // box collision takes (x, y) -> top left, width, and height
                checkPosition = positionY + height + direction * speed; // add speed from Y + height (down)
                checkGrid = checkPosition / gridSize;
                //
                a = positionX / gridSize;
                b = (positionX + width) / gridSize;
                gridType = TileManager.checkTile(a, checkGrid);
                gridTypeAlt = TileManager.checkTile(b, checkGrid);

                if (gridType == 1 || gridTypeAlt == 1) {
                    // partial movement to grid position
                    // get the y co-ordinate of interfering grid, -> positionY
                    // positionY = (positionY + (direction * speed))/gridSize;
                    // positionY *= gridSize;
                    // positionY --;
                } else {
                    positionY += direction * speed;
                }
                break;
            // left
            case -2:
                direction /= 2;
                checkPosition = positionX + direction * speed; // subtracts speed from Y (up)
                checkGrid = checkPosition / gridSize;

                //
                a = positionY / gridSize;
                b = (positionY + height) / gridSize;
                gridType = TileManager.checkTile(checkGrid, a);
                gridTypeAlt = TileManager.checkTile(checkGrid, b);

                if (gridType == 1 || gridTypeAlt == 1) {
                    // partial movement to grid position
                    // get the y co-ordinate of interfering grid, -> positionY
                    positionX = (positionX + (direction * speed) + width) / gridSize; // idk why but adding width in
                                                                                      // there works?
                    positionX *= gridSize; // normalize
                    positionX++; // offset by 1
                } else {
                    positionX += direction * speed;
                }

                break;
            // right
            case 2:
                direction /= 2;
                checkPosition = positionX + width + direction * speed; // add speed from Y + height (down)
                checkGrid = checkPosition / gridSize;
                //
                a = positionY / gridSize;
                b = (positionY + height) / gridSize;
                gridType = TileManager.checkTile(checkGrid, a);
                gridTypeAlt = TileManager.checkTile(checkGrid, b);

                if (gridType == 1 || gridTypeAlt == 1) {
                    // partial movement to grid position
                    // get the y co-ordinate of interfering grid, -> positionY
                    // positionX = (positionX + (direction * speed))/gridSize;
                    // positionX *= gridSize;
                    // positionX --;
                } else {
                    positionX += direction * speed;
                }
                break;
        }

        // check index and direction

        // takes list of objects in grid spot, runs collision detection on those object
        if (type == "enemy") {
            if (boxCollision(positionX, positionY, width, height, GamePanel.characters.get(0).positionX,
                    GamePanel.characters.get(0).positionY, GamePanel.characters.get(0).width,
                    GamePanel.characters.get(0).height)) {

                GamePanel.characters.get(0).hit = true;

                GamePanel.gameState = 3;
                GamePanel.currentDialogue = "YOU SUCK.";
                System.out.println("enemy touched");
            }
        }

        if (type != "player") {
            return;
        }

        // confirmed

        System.out.println(GamePanel.score);

        for (int i = 0; i < GamePanel.movingEnemies.size(); i++) {
            if (boxCollision(positionX, positionY, width, height, GamePanel.movingEnemies.get(i).positionX,
                    GamePanel.movingEnemies.get(i).positionY, GamePanel.movingEnemies.get(i).width,
                    GamePanel.movingEnemies.get(i).height)) {
                GamePanel.score--;
                GamePanel.movingEnemies.remove(i);
                GamePanel.gameState = 3;
                GamePanel.currentDialogue = "YOU SUCK.";
                System.out.println("enemy touched");
            }
        }

        for (int i = 0; i < GamePanel.baseRewards.size(); i++) {
            // fill in the rest
            if (boxCollision(positionX, positionY, width, height, GamePanel.baseRewards.get(i).getPositionX(),
                    GamePanel.baseRewards.get(i).getPositionY(), GamePanel.baseRewards.get(i).getWidth(),
                    GamePanel.baseRewards.get(i).getHeight())) {

                GamePanel.baseRewards.remove(i);
                GamePanel.score++;

            }
        }

        for (int i = 0; i < GamePanel.bonusRewards.size(); i++) {
            // fill in the rest
            if (boxCollision(positionX, positionY, width, height, GamePanel.bonusRewards.get(i).getPositionX(),
                    GamePanel.bonusRewards.get(i).getPositionY(), GamePanel.bonusRewards.get(i).getWidth(),
                    GamePanel.bonusRewards.get(i).getHeight())) {

                GamePanel.gameState = 3;
                GamePanel.currentDialogue = "NICE";
                GamePanel.bonusRewards.remove(i);
                GamePanel.score += 5;

            }
        }

        for (int i = 0; i < GamePanel.punishments.size(); i++) {
            // fill in the rest
            if (boxCollision(positionX, positionY, width, height, GamePanel.punishments.get(i).getPositionX(),
                    GamePanel.punishments.get(i).getPositionY(), GamePanel.punishments.get(i).getWidth(),
                    GamePanel.punishments.get(i).getHeight())) {

                GamePanel.gameState = 3;
                GamePanel.currentDialogue = "You have lost everything";

                GamePanel.gameEnd = true;
                // System.exit(0);
            }
        }

        return;
    }

    public boolean inBounds(int x, int xi, int dx) {
        if (x >= xi && x <= xi + dx) {
            return true;
        }

        // else
        return false;
    }

    public boolean inBox(int xi, int dx, int ai, int da) {
        // extra condition - box x is smaller than a
        if (inBounds(xi, ai, da)) {
            return true;
        }

        // check left OR right
        if (inBounds(ai, xi, dx) || inBounds(ai + da, xi, dx)) {
            return true;
        }

        // else:
        return false;
    }

    /**
     * 
     * @param b1x @param b1y: co-ordinates of top left for box 1
     * @param b1w @param b1h: dimesions for box 1
     * 
     * @param b2x @param b2y: (x, y) top left for box 2
     * 
     * @param b2w @param b2h: co-ordinates for box 2
     * 
     * @return
     */
    public boolean boxCollision(int b1x, int b1y, int b1w, int b1h, int b2x, int b2y, int b2w, int b2h) {
        // horizontal
        if (!inBox(b1x, b1w, b2x, b2w)) {
            return false;
        }

        // vertical
        if (!inBox(b1y, b1h, b2y, b2h)) {
            return false;
        }

        // else:
        return true;
    }

    public void speak() {
    };
}
