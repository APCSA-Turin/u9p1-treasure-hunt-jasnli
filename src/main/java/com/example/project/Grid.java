package com.example.project;


//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    // initializes the variables
    private Sprite[][] grid; // this is the grid which is represented by a 2D array. it will contain all the other sprites
    private int size; // this is the size of the board, which will be used to create the grid 2D array. the grid will be size x size.

    /**
     * This is the constructor of the grid object, it will set the grid to all dots by default
     * @param size the size of the board
     */
    public Grid(int size) { //initialize and create a grid with all DOT objects <- // yes this is what it does
        this.size = size; // size is entered in as a parameter
        grid = new Sprite[size][size]; // the grid is then instantiated and create to be size by size
        // this loop interates through each index in each array in grid and places a Dot there as a placeholder
        for (int i = 0 ; i < grid.length ; i++) { // goes through every array in grid first
            for (int j = 0 ; j < grid[i].length ; j++) { // goes through every index in that array next
                grid[i][j] = new Dot(i, j); // puts a placeholder Dot in that spot
            }
        }
    }

    // getter
    public Sprite[][] getGrid(){return grid;}
 
    // places a sprite in the array based off of the x and y coordinate. However the x and y are based off of the
    // cartesian plane, while the grid places it based on rows going from top to bottom and columns going from left
    // to right (which is the same). column will be the x since it is going left to right (no change)
    // row will be the size - 1 - y since u need to get the row that is y away from the bottom, which is index size 
    // in the array, so you must substract y from the size and 1 (so it doesn't go out of bounds)
    public void placeSprite(Sprite s){ //place sprite in new spot
        grid[size - 1 - s.getY()][s.getX()] = s;
    }

    // this method will place the sprite based off of the direction that is inputted, replacing the sprite in that
    // the sprite in the old location with a placeholder Dot so that it doesn't create duplicates of it
    public void placeSprite(Sprite s, String direction) { //place sprite in a new spot based on direction
        // creates a new dot based off of the players CURRENT position (after moving)
        Dot d = new Dot(s.getX(), s.getY(), true);
        // moves that Dot backwards in order to put it in the spot where the player WAS (prior to moving)
        if (direction.equals("w")) {
            d.move("s");
        }
        if (direction.equals("s")) {
            d.move("w");
        }
        if (direction.equals("a")) {
            d.move("d");
        }
        if (direction.equals("d")) {
            d.move("a");
        }
        // places BOTH sprites, the Dot in the place where the player was and the player in the place it moved to
        placeSprite(d);
        placeSprite(s);
    }

    // displays the current grid based off of the object at each spot
    public void display() { //print out the current grid to the screen 
        // iterates through the grid first using two enhanced for loops
        for (Sprite[] x : grid) {
            for (Sprite spot : x) {
                // check if the object in that spot is a Player
                if (spot instanceof Player) {
                    // if it is a player, then print that players picture (display the PLAYER picture there)
                    System.out.print(((Player) spot).getPicture());
                }
                // check if the object in that spot is an Enemy
                else if (spot instanceof Enemy) {
                    // if it is an enemy, print the ROOSTER emoji
                    System.out.print("🐓");
                }
                // check if the object in that spot is a Trophy
                else if (spot instanceof Trophy) {
                    // if it is a trophy, print the FRENCHFRY emoji
                    System.out.print("🍟");
                }
                // check if the object is a Treasure (after trophy since trophies are treasures)
                else if (spot instanceof Treasure) {
                    // if it is a treasure, print the SALT emoji (cus snails like salt or something like that)
                    System.out.print("🧂");
                }
                // check if the object in that spot is just a Dot
                else if (spot instanceof Dot) {
                    // if it is a dot, if it has a trail (true), print the BLUE CIRCLE
                    if (((Dot)spot).getTrail()) {
                        System.out.print("🟦");
                    } else { // if it doesnt have a trail, print the GREEN CIRCLE
                        System.out.print("🟩");
                    }    
                }
            }
            // skips a line after every element in that row is printed
            System.out.println();
        }
    }
    
    // displays the grid in the case of a loss (game over)
    public void gameover(){ //use this method to display a loss
        // iterates through every item in the grid by going through every array and every item at the array
        for (Sprite[] x : grid) {
            for (Sprite spot : x) {
                // if that element is a Dot and that Dot has a game over status of true (the variable in the Dot class)
                if (spot instanceof Dot && ((Dot)spot).getGameOverTrail()) {
                    // PRINT THE BROWN SQUARE
                    System.out.print("🟫");
                }
                // if that element is a Dot and that Dot has a game over status of false (the variable in the Dot class)
                if (spot instanceof Dot && !(((Dot)spot).getGameOverTrail())) {
                    // PRINT THE GREEN SQUARE
                    System.out.print("🟩");
                }     
                // if the element is a Player
                if (spot instanceof Player) {
                    // PRINT THE PLAYER PICTURE
                    System.out.print(((Player) spot).getPicture());
                }
            }
            // skips a line after each element in that row is printed
            System.out.println();
        }
    }

    // displays the grid in the case of a win (after collecting the trophy)
    public void win(){ //use this method to display a win 
        // iterate through every array in grid and every item in each array
        for (Sprite[] x : grid) {
            for (Sprite spot : x) {
                // if that item is a Dot and that item has the victory status (variable in Dot)
                if (spot instanceof Dot && ((Dot)spot).getVictoryTrail()) {
                    // PRINT THE CROWN
                    System.out.print("👑");
                }
                // if that item is a Dot and that item does not have the victory status (variable in Dot)
                if (spot instanceof Dot && !(((Dot)spot).getVictoryTrail())) {
                    // PRINT THE GREEN SQUARE
                    System.out.print("🟩");
                }     
                // if that item is a Player
                if (spot instanceof Player) {
                    // PRINT THE PLAYER PICTURE
                    System.out.print(((Player) spot).getPicture());
                }
            }
            // skip a line after each item in that row is printed
            System.out.println();
        }
    }

    // FOR VISUALS (WIN VISUALS)
    public boolean isValid(int x, int y) {
        // checks if the x and y values are valid by checking if they are within the size (so not out of bounds)
        // or if its not negative (not out of bounds)
        if (x < size && y < size && x >= 0 && y >= 0) {
            return true;
        } else {
            return false;
        }
    }


}