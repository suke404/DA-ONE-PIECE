package group2;

import java.util.Random;
import java.util.Stack;

import java.lang.Math;


/**
 * <h1>Board Class </h1>
 * This class holds the variables and methods necessary for generating the maze. The output which comes from getMap() will be a 2D array and each value
 * in that index will be what represent what spawns on the board. 0 = walkable path, 1 = wall, 3 = base reward, 4 = bonus reward, 5 = trap, 6 = enemy
 */
public class Board {

    protected int[][] spawnable;

    protected int mapWidth;
    protected int mapHeight;

    protected int roomWidth;
    protected int roomHeight;

    protected int roomSpawnFrequency;

    protected int[][] terrain;
  
   private Random rand = new Random();

    /**
     * This is the constructor for the board class which initializes all the values
     * @param width this is the width 2D map grid
     * @param height this is the height of the 2D map grid
     * @param roomWidth this is the width of the room randomly spawns on the map
     * @param roomHeight this is the height of the room that randomly spawns on the map
     */
    public Board(int width, int height, int roomWidth, int roomHeight) {
        mapWidth = width;
        mapHeight = height;
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        terrain = new int[mapWidth][mapHeight];
        roomSpawnFrequency = (int) (this.mapWidth * this.mapHeight * 0.025);
    }

    /**
     * This function utilizes recursive backtracking to generate a maze. During which it will also spawn rooms and other entities such as rewards
     * and enemies
     */
    public void updateTerrain() {
        if (this.mapWidth <= 1 || this.mapHeight <= 1){
            return;
        }
        for (int i = 0; i < this.mapHeight; i++) {
            for (int j = 0; j < this.mapWidth; j++) {
                if (i % 2 == 0 || j % 2 == 0)
                    this.terrain[i][j] = 1;
                else
                    this.terrain[i][j] = 2;
            }
        }

        Stack<Integer> visited = new Stack<Integer>();
        int currentIndex = this.mapWidth + 1;
        visited.push(currentIndex);
        this.recursiveBacktracking(1, 1, visited);
        int randomColumn = rand.nextInt(this.mapWidth-2) + 1;
        this.terrain[this.mapHeight-2][randomColumn] = 9;

    }
    /**
     * This function verfies if an item is able to spawn in a index of the 2D grid
     * @param row this is the row in which the entity will be spawned
     * @param col this is the col in which the entity will be spawned
     * @return boolean This will return a boolean value of whether the entity can spawn in the given row and col
     */
    public boolean validSpawn(int row, int col){
        int playerDistance = (int) Math.sqrt(Math.pow(row -1,2) + Math.pow(col-1,2));
        if (this.terrain[row][col] == 0 && playerDistance > 0){
            return true;
        }
        return false;
    }
    /**
     * This method is responsible for the spawning of an entity and setting the value of the index in the 2D grid to the entity value
     * @param row this represents the row in which the entity will spawn
     * @param col   this represents the col in which the entity wil spawn
     * @param entity this represents an integer value which represents what entity type it will be
     * 
     */
    public void spawnEntity(int row, int col, int entity) {
        if (this.validSpawn(row, col)) {
            this.terrain[row][col] = entity;
        } else {
            this.terrain[row][col] = 0;
        }
    }
    /**
     * Returns the map
     * @return int[][] the map
     */
     public int[][] getMap(){
        return this.terrain;
    }   
    /**
     * This is the recursive call required for recursive backtracking. It works by starting at a point and randomly picks an adjacent node. If that node 
     * has not been visited, it will be added to the visited stack. If all adjacent nodes are vistied then continuously pop the stack until a node has adjacent
     * nodes available. Repeat until the stack is empty.
     * @param x this is the current column in the 2D grid
     * @param y this is the current row in the 2D grid
     * @param visited this is the stack that contains all visited nodes
     */
    private void recursiveBacktracking(int x, int y, Stack<Integer> visited) {
        int currentIndex = y * this.mapWidth + x;
        //Loop until there are adjacent nodes that are unvisited
        while ((x + 2 >= this.mapWidth || this.terrain[y][x + 2] == 0) &&
                (x - 2 < 0 || this.terrain[y][x - 2] == 0) &&
                (y + 2 >= this.mapHeight || this.terrain[y + 2][x] == 0) &&
                (y - 2 < 0 || this.terrain[y - 2][x] == 0)) {
            //Exit if stack is empty
            if (visited.isEmpty()) {
                return;
            }
            currentIndex = visited.pop();
            //convert the index from the stack to a row and col value
            y = currentIndex / this.mapWidth;
            x = currentIndex % this.mapWidth;
        }
        int direction;
        int dX = 0;
        int dY = 0;
        int newX = 0;
        int newY = 0;
        do {
            dX = 0;
            dY = 0;
            direction = rand.nextInt(4);
            newY = currentIndex / this.mapWidth;
            newX = currentIndex % this.mapWidth;
            switch (direction) {
                //right
                case 0:
                    dX = 2;
                    break;
                //down
                case 1:
                    dY = 2;
                    break;
                //left
                case 2:
                    dX = -2;
                    break;
                //up
                case 3:
                    dY = -2;
                    break;
            }
            newX += dX;
            newY += dY;
        } while (newX >= this.mapWidth || newX < 0 || newY >= this.mapHeight || newY < 0
                || this.terrain[newY][newX] == 0);

        int newIndex = this.mapWidth * newY + newX;

        //set the value to 0 to produce a walkable path
        this.terrain[y + dY / 2][x + dX / 2] = 0;
        this.terrain[newY][newX] = 0;


        //Room generating has different conditions depending on the direction
        boolean spawnRoom = rand.nextInt(10) == 0;

        int roomWidth = this.roomWidth;
        int roomHeight = this.roomHeight;
        if (spawnRoom && newX + roomWidth < this.mapWidth && newX - roomWidth >= 0 && newY + roomHeight < this.mapHeight
                && newY - roomHeight >= 0) {
            spawnRoom(newX, newY, roomWidth, roomHeight, direction, visited);
        }
        //Spawning an endpoint for the player. spawnPoint boolean ensures that there is exactly one endpoint spawning. Distance checker will make sure that it is far from player
        visited.push(newIndex);
        recursiveBacktracking(newX, newY, visited);
    }

    public void spawnRoom(int newX, int newY, int roomWidth, int roomHeight, int direction,  Stack<Integer> visited){
        switch (direction) {        
            case 0:

            for (int i = newY - roomHeight / 2; i < newY + roomHeight; i++) {
                for (int j = newX; j < roomWidth; j++) {
                    this.terrain[i][j] = 0;
                    int index = i * this.mapWidth + j;
                    int randNum = rand.nextInt(15);
                    if (randNum == 0){
                        this.spawnEntity(i,j,4);
                    }
                    if (randNum == 1){
                        this.spawnEntity(i,j,3);
                    }
                    if (randNum == 3){
                        this.spawnEntity(i,j,5);
                    }
                    if (randNum == 4){
                        this.spawnEntity(i,j,6);
                    }
                    if (i % 2 != 0 && j % 2 != 0) {
                        visited.push(index);
                    }
                }
            }
            break;
            case 1:
            for (int i = newY; i < newY + roomHeight; i++) {
                for (int j = newX - roomWidth / 2; j < newX + roomWidth; j++) {
                    this.terrain[i][j] = 0;
                    int index = i * this.mapWidth + j;
                    int randNum = rand.nextInt(15);
                    if (randNum == 0){
                        this.spawnEntity(i,j,4);
                    }
                    if (randNum == 1) {
                        this.spawnEntity(i, j, 3);
                    }
                    if (randNum == 3){
                            this.spawnEntity(i,j,5);
                    }
                    if (randNum == 4){
                        this.spawnEntity(i,j,6);
                    }
                    if(i%2 != 0 && j%2 != 0){
                        visited.push(index);
                    }
                }
            }
            break;
            case 2:
            for (int i = newY - roomHeight / 2; i < newY + roomHeight; i++) {
                for (int j = newX; j > newX - roomWidth; j--) {
                    this.terrain[i][j] = 0;
                    int index = i * this.mapWidth + j;
                    int randNum = rand.nextInt(15);
                    if (randNum == 0){
                        this.spawnEntity(i,j,4);
                    }
                    if (randNum == 1) {
                        this.spawnEntity(i, j, 3);
                    }
                    if (randNum == 3) {
                        this.spawnEntity(i, j, 5);
                    }
                    if (randNum == 4){
                        this.spawnEntity(i,j,6);
                    }
                    if(i%2 != 0 && j%2 != 0){
                        visited.push(index);
                    }
                }
            }
            break;
            case 3:
            for (int i = newY; i > newY - roomHeight; i--) {
                for (int j = newX - roomWidth / 2; j < newX + roomWidth; j++) {
                    this.terrain[i][j] = 0;
                    int index = i * this.mapWidth + j;
                    int randNum = rand.nextInt(15);
                    if (randNum == 0){
                        this.spawnEntity(i,j,4);
                    }
                    if (randNum == 1) {
                        this.spawnEntity(i, j, 3);
                    }
                    if (randNum == 3) {
                        this.spawnEntity(i, j, 5);
                    }
                    if (randNum == 4){
                        this.spawnEntity(i,j,6);
                    }
                    if(i%2 != 0 && j%2 != 0){
                        visited.push(index);
                    }
                }
            }
            break;
    }
    }

}



