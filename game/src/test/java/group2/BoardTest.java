package group2;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;



public class BoardTest {

    @Test
    public void createLargeBoardTest(){
        Board board = new Board(50, 50, 5, 5);
        board.updateTerrain();
        int [][]map = board.getMap();
        assertTrue(map.length == 50 && map[0].length == 50);
    }

    @Test
    public void createSmallBoardTest(){
        Board board = new Board(20, 20, 2, 2);
        board.updateTerrain();
        int [][]map = board.getMap();
        assertTrue(map.length == 20 && map[0].length == 20);
    }
    @Test
    public void createNoBoard(){
        Board board = new Board(0, 0, 0, 0);
        board.updateTerrain();
        int [][]map = board.getMap();
        assertTrue(map.length == 0);
    }
    @Test
    public void checkEndpoint(){
        Board board = new Board(51,51,5,5);
        board.updateTerrain();
        int [][]map = board.getMap();
        boolean endpoint = false;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if (map[i][j] == 9){
                    endpoint = true;
                    break;
                }
            }
        }
        assertTrue(endpoint);
    }
    @Test
    public void checkRewards(){
        Board board = new Board(51,51,5,5);
        board.updateTerrain();
        int [][]map = board.getMap();
        boolean reward = false;
        boolean bonusReward = false;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if (map[i][j] == 3){
                    bonusReward = true;
                    break;
                }
                if (map[i][j] == 4){
                    reward = true;
                    break;
                }
            }
        }
        assertTrue(bonusReward);
        assertTrue(reward);
    }
    @Test
    public void checkPunishments(){
        Board board = new Board(51,51,5,5);
        board.updateTerrain();
        int [][]map = board.getMap();
        boolean punishment = false;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if (map[i][j] == 6){
                    punishment = true;
                    break;
                }
            }
        }
        assertTrue(punishment);
    }
    @Test
    public void checkEnemy(){
        Board board = new Board(51,51,5,5);
        board.updateTerrain();
        int [][]map = board.getMap();
        boolean enemy = false;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[0].length; j++){
                if (map[i][j] == 5){
                    enemy = true;
                    break;
                }
            }
        }
        assertTrue(enemy);
    }

    @Test
    public void spawnEntityOnPlayerTest(){
        Board board = new Board(51,51,5,5);
        board.updateTerrain();   
        board.spawnEntity(1,1,5); 
        assertTrue(board.getMap()[1][1] == 0);
        assertTrue(board.validSpawn(0,0) == false);
    }
    @Test
    public void printMapTest(){
        Board board = new Board(51,51,5,5);
        board.updateTerrain();
        board.printMap();
        assertTrue(true);
    }

}
