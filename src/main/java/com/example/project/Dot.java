package com.example.project;


//Dot only needs a constructor
public class Dot extends Sprite{
    // bonus intializing for the visuals
    private boolean trail; // trail after the snail moves
    private boolean victoryTrail; // a dot after the snail wins!
    private boolean gameOverTrail; // a dot after the snail loses :(

    // constructor for the dot
    /**
     * 
     * @param x the x position of the dot (or the distance from the bottom left horizontally)
     * @param y the y position of the dot (or the distance from the bottom left vertically)
     * @param trail if there is a trail or not(for the visuals)
     */
    public Dot(int x, int y, boolean trail) {
        super(x, y); // refers to the Sprite class, which also takes an x and a y
        this.trail = trail; // VISUALS: sets the trail to either true or false
        // VISUALS FOR WINNING/LOSING
        victoryTrail = false; // false by default (changes to true upon victory)
        gameOverTrail = false; // false by default (changes to true upon defeat)
    }

    // constructor for if there is NO trail (same thing but sets trail to default/default sprite)
    public Dot(int x, int y) {
        super(x, y);
        this.trail = false;
        victoryTrail = false;
        gameOverTrail = false;
    }

    // getter methods
    public boolean getTrail() {return trail;}
    public boolean getVictoryTrail() {return victoryTrail;}
    public boolean getGameOverTrail() {return gameOverTrail;}

    // for VISUALS
    public void victory() {victoryTrail = true;} // changes the victory state of the dot to TRUE
    public void gameOver() {gameOverTrail = true;} // changes the game over state of the dot to TRUE

    // for VISUALS
    // changes the trail as well, but it toggles between if it is true or not already
    public void changeTrail() {
        if (!trail) {
            trail = true;
        } if (trail) {
            trail = false;
        }
    }

}
