package com.example.project;


//Dot only needs a constructor
public class Dot extends Sprite{
    private boolean trail;
    private boolean victoryTrail;
    private boolean gameOverTrail;

    public Dot(int x, int y, boolean trail) {
        super(x, y);
        this.trail = trail;
        victoryTrail = false;
        gameOverTrail = false;
    }

    public Dot(int x, int y) {
        super(x, y);
        this.trail = false;
        victoryTrail = false;
        gameOverTrail = false;
    }

    public boolean getTrail() {return trail;}
    public boolean getVictoryTrail() {return victoryTrail;}
    public boolean getGameOverTrail() {return gameOverTrail;}
    public void victory() {victoryTrail = true;}
    public void gameOver() {gameOverTrail = true;}

    public void changeTrail() {
        if (!trail) {
            trail = true;
        } if (trail) {
            trail = false;
        }
    }

}
