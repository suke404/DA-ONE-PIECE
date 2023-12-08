package group2;

import group2.Characters.MainCharacter;

public class Game {

    // class attributes
    private Board board;
    private MainCharacter mainCharacter;
    // private int score;

    // constructor
    public Game(Board board, MainCharacter mainCharacter) {
        this.board = board;
        this.mainCharacter = mainCharacter;
        // this.score = 0;
    }

    // function
    public void updateScore(int rewardValue) {
        // this.score += rewardValue;
    }
}
