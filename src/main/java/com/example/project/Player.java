package com.example.project;

//DO NOT DELETE ANY METHODS BELOW
public class Player extends Sprite {
    private int treasureCount;
    private int numLives;
    private boolean win;
    private String picture;
    private int movements;
    private boolean canMove;
    private int starterLives;

    public Player(int x, int y) { //set treasureCount = 0 and numLives = 2 
        super(x, y);
        numLives = 2;
        win = false;
        treasureCount = 0;
        picture = "🐌";
        movements = 0;
        canMove = true;
        starterLives = numLives;
    }


    public int getTreasureCount(){return treasureCount;}
    public int getLives(){return numLives;}
    public boolean getWin(){return win;}
    public String getPicture() {return picture;}
    public boolean canMove() {return canMove;}
  
    public String getCoords() {
        return "Player:" + super.getCoords();
    }

    //move method should override parent class, sprite
    public void move(String direction) { //move the (x,y) coordinates of the player
        movements++;
        if (direction.equals("w")) {
            setY(getY() + 1);
        }
        if (direction.equals("a")) {
            setX(getX() - 1);
        }
        if (direction.equals("s")) {
            setY(getY() - 1);
        }
        if (direction.equals("d")) {
            setX(getX() + 1);
        }
    }


    public void interact(int size, String direction, int numTreasures, Object obj) { // interact with an object in the position you are moving to 
        //numTreasures is the total treasures at the beginning of the game
        // ENEMY INTERACTION
        canMove = true;
        if (obj instanceof Enemy) {
            numLives --;
        }
        if (obj instanceof Treasure) {
            if (numTreasures != treasureCount && obj instanceof Trophy) {
                canMove = false;
                
            } else {
                if (numTreasures == treasureCount) {
                    System.out.println("hi");
                    System.out.println(numTreasures);
                    System.out.println(treasureCount);
                    if (obj instanceof Trophy) {
                        treasureCount ++; 
                        win = true;
                    }
                } 
                if (numTreasures != treasureCount) {
                    treasureCount ++;
                }
            }
            
        }

        // BONUS VISUALS
        if (obj instanceof Grid) {
            if (((Grid) obj).getGrid()[size - 1 - getY()][getX()] instanceof Dot) {
                if (((Dot) (((Grid) obj).getGrid()[size - 1 - getY()][getX()])).getTrail()) {
                    picture = "🐍";
                    movements = 0;
                }
            } 
            if (movements >= 5) {
                movements = 0;
                picture = "🐌";
            }
        }

        // damage visuals
        if (numLives <= 0.5 * starterLives) {
            picture = "🐛";
        } if (numLives == 0) {
            picture = "💀";
        }
        
    }


    public boolean isValid(int size, String direction){ //check grid boundaries
        if (direction.equals("w")) {
            if (getY() < size - 1) {
                return true;
            }
        }
        if (direction.equals("a")) {
            if (getX() > 0) {
                return true;
            }
        }
        if (direction.equals("s")) {
            if (getY() > 0) {
                return true;
            }
        }
        if (direction.equals("d")) {
            if (getX() < size - 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRowCol(int size) {
        int row = size - getY() - 1;
        int col = getX();
        return "Player:" + super.getRowCol(size);
    }


   
}



