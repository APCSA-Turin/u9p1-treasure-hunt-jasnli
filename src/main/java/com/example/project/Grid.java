package com.example.project;


//DO NOT DELETE ANY METHODS BELOW
public class Grid{
    private Sprite[][] grid;
    private int size;

    public Grid(int size) { //initialize and create a grid with all DOT objects
        this.size = size;
        grid = new Sprite[size][size];
        for (int i = 0 ; i < grid.length ; i++) {
            for (int j = 0 ; j < grid[i].length ; j++) {
                grid[i][j] = new Dot(i, j);
            }
        }
    }

 
    public Sprite[][] getGrid(){return grid;}



    public void placeSprite(Sprite s){ //place sprite in new spot
        grid[size - 1 - s.getY()][s.getX()] = s;
    }

    public void placeSprite(Sprite s, String direction) { //place sprite in a new spot based on direction
        Dot d = new Dot(s.getX(), s.getY(), true);
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
        placeSprite(d);
        placeSprite(s);
    }


    public void display() { //print out the current grid to the screen 
        for (Sprite[] x : grid) {
            for (Sprite spot : x) {
                if (spot instanceof Player) {
                    System.out.print(((Player) spot).getPicture());
                }
                else if (spot instanceof Enemy) {
                    System.out.print("🐓");
                }
                else if (spot instanceof Trophy) {
                    System.out.print("🍟");
                }
                else if (spot instanceof Treasure) {
                    System.out.print("🧂");
                }
                
                else if (spot instanceof Dot) {
                    if (((Dot)spot).getTrail()) {
                        System.out.print("🟦");
                    } else {
                        System.out.print("🟩");
                    }
                    
                }
            }
            System.out.println();
        }
    }
    
    public void gameover(){ //use this method to display a loss
        for (Sprite[] x : grid) {
            for (Sprite spot : x) {
                
                if (spot instanceof Dot && ((Dot)spot).getGameOverTrail()) {
                    System.out.print("🟫");
                }
                if (spot instanceof Dot && !(((Dot)spot).getGameOverTrail())) {
                    System.out.print("🟩");
                }     
                if (spot instanceof Player) {
                    System.out.print(((Player) spot).getPicture());
                }
            }
            System.out.println();
        }
    }

    public void win(){ //use this method to display a win 
        for (Sprite[] x : grid) {
            for (Sprite spot : x) {
                if (spot instanceof Dot && ((Dot)spot).getVictoryTrail()) {
                    System.out.print("👑");
                }
                if (spot instanceof Dot && !(((Dot)spot).getVictoryTrail())) {
                    System.out.print("🟩");
                }     
                if (spot instanceof Player) {
                    System.out.print(((Player) spot).getPicture());
                }
            }
            System.out.println();
        }
        
}

    public boolean isValid(int x, int y) {
        if (x < size && y < size && x >= 0 && y >= 0) {
            return true;
        } else {
            return false;
        }
    }


}