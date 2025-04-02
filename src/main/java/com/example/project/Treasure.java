package com.example.project;

//only needs a constructor
public class Treasure extends Sprite{ //child of Sprite
    public Treasure(int x, int y) {
        // calls the super to intialize the position since the treasure IS a sprite
        super(x, y);
    }

}