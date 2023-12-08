package group2;

/**
 * The StaticEntity class is responsible for representing any non-moving,
 * non-terrain entity on the map (items, traps etc.).
 */
public class StaticEntity {

    // The current X and Y coordinates on the map. Should not move
    private int positionX;
    private int positionY;

    private int screenX;

    // Returns the width of the screen
    public int getScreenX() {
        return screenX;
    }

    // Returns the height of the screen
    private int screenY;

    public int getScreenY() {
        return screenY;
    }

    // Height and width of the entity hitbox, determines collision
    private int height;
    private int width;

    // This value determines the punishment/reward put on the player
    private int effectValue;

    // Tracks the position of the main character
    private int mcX;
    private int mcY;
    private int mcPosX;
    private int mcPosY;

    private int TILE_SIZE;

    /**
     * Constructs a StaticEntity object with the specified parameters.
     *
     * @param positionX         The X-coordinate of the entity in the game world.
     * @param positionY         The Y-coordinate of the entity in the game world.
     * @param height            The height of the entity.
     * @param width             The width of the entity.
     * @param effectValue       The value representing the impact of the entity.
     * @param mainCharacterData An array containing main character data: [mcX, mcY,
     *                          mcPosX, mcPosY, TILE_SIZE].
     */
    public StaticEntity(int positionX, int positionY, int height, int width, int effectValue, int[] mainCharacterData) {

        this.height = height;
        this.width = width;
        this.effectValue = effectValue;

        this.mcX = mainCharacterData[0];
        this.mcY = mainCharacterData[1];
        this.mcPosX = mainCharacterData[2];
        this.mcPosY = mainCharacterData[3];
        this.TILE_SIZE = mainCharacterData[4];

        this.positionX = positionX * TILE_SIZE;
        this.positionY = positionY * TILE_SIZE;

        updatePosition(this.mcPosX, this.mcPosY);
    }

    // getters and setters for our class attributes
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getPositionX() {

        return positionX;
    }

    public void setPositionX(int mcPosX) {
        int worldX = positionX * TILE_SIZE;
        int screenX = worldX - mcX + mcPosX;
        this.positionX = screenX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int mcPosY) {
        int worldY = positionX * TILE_SIZE;
        int screenY = worldY - mcY + mcPosY;
        this.positionY = screenY;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public void setEffectValue(int effectValue) {
        this.effectValue = effectValue;
    }

    /**
     * Updates the position of the entity on the screen based on the main
     * character's position.
     *
     * @param mcPosX The X-coordinate of the main character's position.
     * @param mcPosY The Y-coordinate of the main character's position.
     */
    public void updatePosition(int mcPosX, int mcPosY) {
        int worldX = positionX * TILE_SIZE;
        screenX = worldX - mcX + mcPosX;

        int worldY = positionY * TILE_SIZE;
        screenY = worldY - mcY + mcPosY;
    }

}
