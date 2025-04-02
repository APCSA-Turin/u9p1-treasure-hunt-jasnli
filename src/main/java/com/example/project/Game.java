package com.example.project;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game{
    private Grid grid;
    private Player player;
    private Enemy[] enemies;
    private Treasure[] treasures;
    private Trophy trophy;
    private int size; 

    public Game(){ //the constructor should call initialize() and play()
        Scanner scan = new Scanner(System.in);
        System.out.println("Choose your gamemode (easy, medium hard): ");
        String d = scan.nextLine();
        initialize(d);
        play();
    }

    public static void clearScreen() { //do not modify
        try {
            final String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                // Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Unix-based (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(){ //write your game logic here
        Scanner scanner = new Scanner(System.in);

        // as long as the player hasn't won yet and the player still has lives, then loop the game
        while(!player.getWin() && player.getLives() > 0){
            
            // Ms. Turin wrote this to make the game wait
            try {
                Thread.sleep(100); // Wait for 1/10 seconds // 100 param
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearScreen(); // Clear the screen at the beggining of the while loop

            // The game...
            if (!player.getWin() && player.getLives() > 0) { // if the game ISNT over (no victory or loss so the win is false and the lives is not 0)
                grid.display(); // show the grid
                System.out.println("Move Direction (w, a, s, d) : "); // ask for a move direction
                System.out.println("Treasure Count: 🧂 " + player.getTreasureCount()); // display the treasures collected
                System.out.println("Lives Remaining: 💓 " + player.getLives()); // displays the player's remaining lives
                String direction = scanner.nextLine(); // checks for player input and sets it to the direction variable
                // if the movement is valid (not out of bounds)
                if (player.isValid(size, direction)) {
                    // move in that direction
                    player.move(direction);
                    // then interact with the object in that direction
                    player.interact(size, direction, treasures.length, grid); // FOR VISUALS (trail)
                    player.interact(size, direction, treasures.length, grid.getGrid()[size - 1 - player.getY()][player.getX()]); // puts in the object at that spot 
                    // if the player is able to move (so if the player isnt trying to go to a trophy with an insufficient amount of trophies)                
                    if (player.canMove()) {
                        // place that sprite there
                        grid.placeSprite(player, direction);
                    } else {
                        // otherwise if the player cant move, move that sprite backwards one spot
                        if (direction.equals("w")) {
                            player.move("s");
                        }
                        if (direction.equals("s")) {
                            player.move("w");
                        }
                        if (direction.equals("a")) {
                            player.move("d");
                        }
                        if (direction.equals("d")) {
                            player.move("a");
                        }
                    }
                }
            }
            
            //  VICTORY ANIMATION
            if (player.getWin()) {
                clearScreen();
            }
            // if the player wins
            if (player.getWin()) {
                // build a mountain
                for (int x = 0 ; x < size / 2 ; x++ ){
                    for (int y = x ; y < size - x; y++) {
                        clearScreen();
                        grid.win();
                        Dot d = new Dot(y, x);
                        d.victory();
                        // ignore the player
                        if (!(grid.getGrid()[size - 1 - x][y] instanceof Player)) {
                            grid.placeSprite(d);
                        } else {
                            grid.placeSprite(player);
                        }
                        // i copied the method above to make it wait so there is an animation
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }     
                }
                clearScreen();
                grid.win(); 
            }    


            // game over LOSE screen
            if (player.getLives() == 0) {
                clearScreen();
            }
            // if the lives reach 0
            if (player.getLives() == 0) {
                // iterate to fill the board fully
                for (int x = 0 ; x < size ; x++ ){
                    for (int y = 0 ; y < size; y++) {
                        clearScreen();
                        grid.gameover();
                        Dot d = new Dot(y, x);
                        d.gameOver();
                        // ignore the player
                        if (!(grid.getGrid()[size - 1 - x][y] instanceof Player)) {
                            grid.placeSprite(d);
                        }  else {
                            grid.placeSprite(player);
                        }
                        // same waiting method as above
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }     
                }
                clearScreen();
                grid.gameover();
            }

        }    
    }

    // default initialize
    public void initialize(){
        size = 10;
        grid = new Grid(size);
        player = new Player(0, 0);
        Enemy enemy = new Enemy(5, 5);
        Enemy enemy2 = new Enemy(7,8);
        Treasure treasure = new Treasure(2, 2);
        Treasure treasure2 = new Treasure(1,7);
        Trophy trophy = new Trophy(1, 0);

        // Row 0: [ ][ ][ ][ ][ ][ ][ ][ ][ ][W]
        // Row 1: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 2: [ ][T][ ][ ][ ][ ][ ][E][ ][ ]
        // Row 3: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 4: [ ][ ][ ][ ][ ][E][ ][ ][ ][ ]
        // Row 5: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 6: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 7: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 8: [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        // Row 9: [P][T][ ][ ][ ][ ][ ][ ][ ][ ]
        //         0  1  2  3  4  5  6  7  8  9
        // Place objects on the grid
        grid.placeSprite(player);
        grid.placeSprite(enemy);
        grid.placeSprite(enemy2);
        treasures = new Treasure[3];
        treasures[0] = treasure;
        treasures[1] = treasure2;
        treasures[2] = trophy;
        enemies = new Enemy[2];
        enemies[0] = enemy;
        enemies[1] = enemy2;
        grid.placeSprite(treasure);
        grid.placeSprite(treasure2);
        grid.placeSprite(trophy);
    }

    // difficulty intialize
    public void initialize(String difficulty){
        // EASY MODE
        // 5 lives, 12 x 12 board, 2 treasures, 2 enemies
        if (difficulty.toLowerCase().equals("easy")) {
            size = 12;
            grid = new Grid(size);
            player = new Player(0, 0, 5);
            enemies = new Enemy[2];
            treasures = new Treasure[2];
        } 
        // MEDIUM MODE
        // 3 lives, 10 x 10 board, 4 treasures, 5 enemies
        else if (difficulty.toLowerCase().equals("medium")) {
            size = 10;
            grid = new Grid(size);
            player = new Player(0, 0, 3);
            enemies = new Enemy[5];
            treasures = new Treasure[4];
        }
        // HARD MODE
        // 2 life, 8 x 8 board, 5 treasures, 8 enemies
        else if (difficulty.toLowerCase().equals("hard")) {
            size = 8;
            grid = new Grid(size);
            player = new Player(0, 0, 2);
            enemies = new Enemy[8];
            treasures = new Treasure[5];
        } 
        // DEFAULT
        else {
            initialize();
        }
        
        // SETTING UP THE BOARD
        // fill enemies list and treasures list w/ temporary enemies and treasures (pos 0,0 to prevent spawning them on the player)
        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy(0, 0);
        }
        for (int i = 0; i < treasures.length; i++) {
            treasures[i] = new Treasure(0,0);  
        }
        trophy = new Trophy(0, 0);

        // randomly generate enemies
        // hi i spent a LONG TIME on this part idk how i managed this but i did it, yay!
        // iterates for EACH enemy
        for (int i = 0; i < enemies.length; i++) {
            boolean same = true; // check if the same enemy position exists in the list already and if we need to iterate through again
            boolean hitSame = false; // check if we hit that same enemy position, used for the otherwise statement
            int trueNewX = 0; // the NEW x that the NEW enemy will have
            int trueNewY = 0; // the NEW y that the NEW enemy will have
            // while there are enemies with the same coordinates
            while (same) {
                hitSame = false; // reset this value
                int newX = (int) (Math.random() * (size - 2)); // generate a new random x
                int newY = (int) (Math.random() * (size - 2)); // generate a new random y
                // iterates through the enemy list
                for (int j = 0 ; j < enemies.length; j++) {
                    // checks to see if any enemies have these coords already, if they do, then set same to true, and hitSame to true (to indicate that we need to loop it)
                    if (enemies[j].getX() == newX && enemies[j].getY() == newY) {
                        same = true;
                        hitSame = true;         
                    }
                }
                // if there was no enemy with the same coords (if hitSame was not hit), then it will set same to false and end that loop
                if (!hitSame) {
                    same = false;
                    trueNewX = newX;
                    trueNewY = newY;
                }
            }
            // create that new enemy, and move on to the next one
            enemies[i] = new Enemy(trueNewX, trueNewY);
        }
        
        // randomly generate treasures
        for (int i = 0; i <= treasures.length; i++) {
            boolean hitSame = false; // check if the same enemy position exists in the list already and if we need to iterate through again
            boolean same = true; // check if we hit that same enemy position, used for the otherwise statement
            int trueNewX = 0; // the NEW x that the NEW enemy will have
            int trueNewY = 0; // the NEW y that the NEW enemy will have
            // while there are enemies with the same coordinates
            while (same) {
                hitSame = false; // reset this value
                int newX = (int) (Math.random() * (size - 2)); // generate a new random x
                int newY = (int) (Math.random() * (size - 2)); // generate a new random y
                // iterates through the enemy list
                for (int j = 0 ; j < enemies.length; j++) {
                    // iterates through the treasures list
                    for (int k = 0; k < treasures.length; k++) {
                        // checks to see if any treasures or enemies have these coords already, if they do, then set same to true, and hitSame to true (to indicate that we need to loop it)
                        if ((enemies[j].getX() == newX && enemies[j].getY() == newY) || (treasures[k].getX() == newX && treasures[k].getY() == newY)) {
                            same = true;
                            hitSame = true;
                        }
                    }
                }
                // if there was no enemy or treasure with the same coords (if hitSame was not hit), then it will set same to false and end that loop
                if (!hitSame) {
                    same = false;
                    trueNewX = newX;
                    trueNewY = newY;
                }
            }
            // sets up the treasure and moves onto the next one, for the final treasure (not in the array), make it a trophy and set up that trophy
            if (i == treasures.length) {
                trophy = new Trophy(trueNewX, trueNewY);
            } else {
                treasures[i] = new Treasure(trueNewX, trueNewY);
            }
        }

        // GRID PLACEMENT
        grid.placeSprite(player);
        grid.placeSprite(trophy);
        for (Treasure t : treasures) {
            grid.placeSprite(t);
        }
        for (Enemy e : enemies) {
            grid.placeSprite(e);
        }
        
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String answer = "yes";
        while (answer.equals("yes")) {
            Game game = new Game();
            System.out.println("Would you like to play again? (type yes)");
            answer = scan.nextLine();
        }
        
    }
}