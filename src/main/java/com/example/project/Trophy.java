package com.example.project;

//only needs a constructor
public class Trophy extends Treasure{ //child of Treasure
    public Trophy(int x, int y){
        // calls the super to intialize the position since the trophy IS a sprite
        super(x, y);
    }
}
