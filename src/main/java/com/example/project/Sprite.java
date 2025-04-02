package com.example.project;

public class Sprite {
    // intialize instance variables x and y position
    private int x, y;

    // constructor to set the x to x and y to y
    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // getters
    public int getX(){return x;}
    public int getY(){return y;}

    // setters to allow changes to the parent class (this class)
    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}

    // returns the String coords in the format (x,y) without the need to call getX and getY since this is in the parent class (local here)
    public String getCoords(){ //returns the coordinates of the sprite ->"(x,y)"
        return "(" + x + "," + y + ")";
    }

    // returns the String row and column in the format [row][col] without the need to call getX and getY since x and y are in this class
    public String getRowCol(int size){ //returns the row and column of the sprite -> "[row][col]"
        int row = size - y - 1; // this is size - 1 - y since row going down the grid will be from up to down while y is from down to up, so you need to account for that difference
        int col = x;
        return "[" + row + "][" + col +"]";
    }
    
    // default move
    public void move(String direction) { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
        if (direction.equals("w")) {
            // if the movement is w or UPWARDS, then add 1 to the y (height)
            setY(getY() + 1);
        }
        if (direction.equals("a")) {
            // if the movement is a or LEFT, then subtract 1 from the x (horizontal)
            setX(getX() - 1);
        }
        if (direction.equals("s")) {
            // if the movement is s or DOWN, then subtract 1 from the y (height)
            setY(getY() - 1);
        }
        if (direction.equals("d")) {
            // if the movement is d or RIGHT, then add 1 to the x (horizontal)
            setX(getX() + 1);
        }
    }

    // i left this empty
    public void interact() { //you can leave this empty
        // Default behavior (can be overridden by subclasses)
    }



}
