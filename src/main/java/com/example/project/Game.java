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

    public Game(int size){ //the constructor should call initialize() and play()
        this.size = size;
        initialize();
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
        

        while(!player.getWin() && player.getLives() > 0){
            try {
                Thread.sleep(100); // Wait for 1/10 seconds // 100 param
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clearScreen(); // Clear the screen at the beggining of the while loop

            // temporarily the game
            if (!player.getWin() && player.getLives() > 0) {
                grid.display();
                System.out.println("Move Direction (w, a, s, d) : ");
                System.out.println("Treasure Count: " + player.getTreasureCount());
                System.out.println(player.getCoords());
                System.out.println(player.getRowCol(size));
                System.out.println(player.getLives());
                System.out.println("CHICKEN COORD: " + enemies[0].getCoords());
                System.out.println(player.getWin());
                String direction = scanner.nextLine();
                if (player.isValid(size, direction)) {
                    player.move(direction);
                    player.interact(size, direction, treasures.length - 1, grid); // FOR VISUALS
                    player.interact(size, direction, treasures.length - 1, grid.getGrid()[size - 1 - player.getY()][player.getX()]);
                    System.out.println("Treasure Count: " + player.getTreasureCount());
                    
                    if (player.canMove()) {
                        
                        grid.placeSprite(player, direction);
                    } else {
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
            
            
            // victory anim
            if (player.getWin()) {
                clearScreen();
            }
            if (player.getWin()) {
                for (int x = 0 ; x < size / 2 ; x++ ){
                    for (int y = x ; y < size - x; y++) {
                        clearScreen();
                        grid.win();
                        Dot d = new Dot(y, x);
                        d.victory();
                        if (!(grid.getGrid()[size - 1 - y][x] instanceof Player)) {
                            grid.placeSprite(d);
                        }  else {
                            grid.placeSprite(player);
                        }
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
            // game over screen
            if (player.getLives() == 0) {
                clearScreen();
            }
            if (player.getLives() == 0) {
                for (int x = 0 ; x < size ; x++ ){
                    for (int y = 0 ; y < size; y++) {
                        clearScreen();
                        grid.gameover();
                        Dot d = new Dot(y, x);
                        d.gameOver();
                        if (!(grid.getGrid()[size - 1 - y][x] instanceof Player)) {
                            grid.placeSprite(d);
                        }  else {
                            grid.placeSprite(player);
                        }
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

    public void initialize(){
        int size = 10;
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

    public static void main(String[] args) {

        Game game = new Game(10);
    }
}