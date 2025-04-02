package com.example.project;

//Enemy only need constructor and getCoords() getRowCol()
public class Enemy extends Sprite{ //child  of Sprite
    // takes in an x and a y, same behaviour thing as sprite
    public Enemy(int x, int y) {
        super(x, y);
    }


    //the methods below should override the super class 
    // this method calls the super class' getCoords method, which automatically puts the coordinates
    // in coordinate layout (x, y) and just adds the words "Enemy:" in front of it
    @Override
    public String getCoords(){ //returns "Enemy:"+coordinates
        return "Enemy:" + super.getCoords();
    }

    // this method calls the super class' getRowCol method, which automatically puts the row and column
    // in the layout [row][column] and just adds the words "Enemy:" in front of it
    @Override
    public String getRowCol(int size){ //return "Enemy:"+row col
        return "Enemy:" + super.getRowCol(size);
    }
}