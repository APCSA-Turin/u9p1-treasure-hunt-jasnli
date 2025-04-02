package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Player extends Sprite {
    // instance variables
    private int treasureCount; // the num of treasures the player has
    private int numLives; // the num of lines the player has remaining
    private boolean win; // if the player has won or not (used for the loop to run the game)
    private String picture; // the player's display picture
    private int movements; // how many movements the player has made (used for display)
    // EXPLANATION: after 5 movements, the player turns from a snake back into a snail
    private boolean canMove; // if this is true, the player is allowed to move, otherwise it isn't (for trophy interaction)
    private int starterLives; // the number of lives at the start (for VISUALS)

    // CONSTRUCTOR: constructs the player class with an x and y position
    public Player(int x, int y) { //set treasureCount = 0 and numLives = 2 
        super(x, y); // constructs and sets the x and y by referring to the sprite class, which sets the x to x and y to y
        numLives = 2; // default starts with 2
        win = false; // win is false by default
        treasureCount = 0; // 0 treasures collected at the start of the game
        picture = "🐌"; // the player display picture (subject to changes throughout the game)
        movements = 0; // the players movements
        canMove = true; //  if the player is able to move
        starterLives = numLives; // set the starterlives to number of lives at the start (2)
    }

    // CONSTRUCTOR: constructs the player class with an x and y position and lives (EXTRA CREDIT)
    public Player(int x, int y, int lives) { //set treasureCount = 0 and numLives = 2 
        super(x, y); // constructs and sets the x and y by referring to the sprite class, which sets the x to x and y to y
        numLives = lives; // default starts with 2
        win = false; // win is false by default
        treasureCount = 0; // 0 treasures collected at the start of the game
        picture = "🐌"; // the player display picture (subject to changes throughout the game)
        movements = 0; // the players movements
        canMove = true; //  if the player is able to move
        starterLives = numLives; // set the starterlives to number of lives at the start (2)
    }

    // getters
    public int getTreasureCount(){return treasureCount;}
    public int getLives(){return numLives;}
    public boolean getWin(){return win;}
    public String getPicture() {return picture;}
    public boolean canMove() {return canMove;}
    // returns the coordinates as a string, with Player: coords
    public String getCoords() {
        // calls the getCoords method from the parent class to avoid having repetitive code since they have the same exact functions
        // just has a "Player:" in front
        return "Player:" + super.getCoords();
    }

    // allows the player to move in a certain direction (passed as a parameter)
    //move method should override parent class, sprite
    public void move(String direction) { //move the (x,y) coordinates of the player
        movements++; // the counter (for VISUALS)
        if (direction.equals("w")) {
            // if it is a movement UPWARDS (increase in y by one)
            setY(getY() + 1); // add 1 to y
        }
        if (direction.equals("a")) {
            // if it is a movement LEFT (decrease in x by one)
            setX(getX() - 1); // subtract 1 from x
        }
        if (direction.equals("s")) {
            // if it is a movement DOWNWARDS (decrease in y by one)
            setY(getY() - 1); // subtract 1 from y
        }
        if (direction.equals("d")) {
            // if it is a movement RIGHT (increase in x by one)
            setX(getX() + 1); // add 1 to x
        }
    }

    /**
     *  method to interact with an object
     * @param size the size of the board
     * @param direction the direction that the player moved in
     * @param numTreasures the number of treasures at the start of the game (excluding trophy), necessary to track if treasures collected is enough
     * @param obj the object at the tile the player just moved to
     */
    public void interact(int size, String direction, int numTreasures, Object obj) { // interact with an object in the position you are moving to 
        //numTreasures is the total treasures at the beginning of the game

        // ENEMY INTERACTION
        canMove = true; // sets to true at every interaction since it will be turned false later under a condition
        // if the object is an enemy
        if (obj instanceof Enemy) { 
            numLives --; // decrease lives by 1
        }
        // if the object is a treasure (includes trophies)
        if (obj instanceof Treasure) {
            // if the object is a treasure that is a trophy and the number of treasures collected aren't sufficient (not equal to the required treasures collected)
            if (numTreasures != treasureCount && obj instanceof Trophy) {
                // set canMove to false, making it so the player will not move in the direction of the trophy
                canMove = false;
            } else { // otherwise
                if (numTreasures == treasureCount) { // if the treasure the player has collected is sufficient
                    if (obj instanceof Trophy) {
                        // add one more to the treasure count (collect the trophy) and win the game (set win to true)
                        // the canMove would be true here so the player would be able to collect the trophy
                        treasureCount ++; 
                        win = true;
                    }
                } 
                // if there is NOT a sufficient number of treasures collected and it is NOT a trophy
                if (numTreasures != treasureCount) {
                    // increase the count by 1 (collect the treasure)
                    treasureCount ++;
                }
            }
        }

        // VISUALS (we pass in a grid here)
        if (obj instanceof Grid) {
            // check the object with the coords of the position the player is trying to go to. if the object is a Dot, then...
            if (((Grid) obj).getGrid()[size - 1 - getY()][getX()] instanceof Dot) {
                // check if the Dot has a trail (the trail appears after the snail moves and behind the snail)
                if (((Dot) (((Grid) obj).getGrid()[size - 1 - getY()][getX()])).getTrail()) {
                    // if the snail steps on its track basically, it changes into a snake!
                    picture = "🐍";
                    // reset the movements to 0 to reset the counter
                    movements = 0;
                }
            } 
            // if the player has moved 5 or more times without stepping on its trail...
            if (movements >= 5) {
                // turn it back into a snail
                movements = 0;
                picture = "🐌";
            }
        }

        // DAMAGE VISUALS
        if (numLives <= 0.5 * starterLives) {
            // if the lives are less than or equal to half (1), then change it into a caterpillar
            picture = "🐛";
        } if (numLives == 0) {
            // if the lives are 0, then change it into a skull
            picture = "💀";
        }
    }

    // check for if the movements is off to the boundaries
    public boolean isValid(int size, String direction){ //check grid boundaries
        // check if the movement is w or UPWARDS
        if (direction.equals("w")) {
            // if it is upwards, then check if that tile it is going to is valid (if it is not an index that is outside the grid, so NOT size and NOT anything greater than size)
            if (getY() < size - 1) {
                return true;
            }
        }
        // check if the movement is a or LEFT
        if (direction.equals("a")) {
            // if it is left, then check if that tile you are moving left to is in the grid or not (if it is negative, then its not and will not return true)
            if (getX() > 0) {
                return true;
            }
        }
        // check if the movement is s or DOWNWARDS
        if (direction.equals("s")) {
            // if it is down, then check if that tile you are moving down to is in the grid or not (if it is negative, then it is not and will not return true)
            if (getY() > 0) {
                return true;
            }
        }
        // check if the movement is d or RIGHT
        if (direction.equals("d")) {
            // if it is righ, then check if that tile you are moving right to is in the grid or not (if it is outside of the grid, it means it has a index greater than or equal to size )
            if (getX() < size - 1) {
                return true;
            }
        }
        // if none of these are valid, return false
        return false;
    }

    // returns the row and the column in format [row][column], except with the words "Player:" in front of it
    @Override
    public String getRowCol(int size) {
        int row = size - getY() - 1;
        int col = getX();
        // we make a call to the super class here to reduce redundancy since the getRowCol method already represents [row][column] in the parent class
        return "Player:" + super.getRowCol(size);
    }
}



