package com.battleShips;
import java.util.*;

    public class BattleShips {
        public static int numRows = 10;
        public static int numCols = 10;
        public static int playerShips;
        public static int computerShips;
        public static String[][] grid = new String[numRows][numCols];
        public static int[][] missedGuesses = new int[numRows][numCols];

        public static void main(String[] args){
            System.out.println("**** Welcome to Battle Ships game ****");
            System.out.println("Right now, sea is empty\n");

            //Ocean Map
            Samudra();

            //Player's Ships
            kapalSaya();

            //Computer's Ships
            kapalCom();

            //BATTLE
            do {
                Battle();
            }while(BattleShips.playerShips != 0 && BattleShips.computerShips != 0);

            //Game Result
            gameOver();
        }

        public static void Samudra(){
            //First section of Ocean Map
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();

            //Middle section of Ocean Map
            for(int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j] = " ";
                    if (j == 0)
                        System.out.print(i + "|" + grid[i][j]);
                    else if (j == grid[i].length - 1)
                        System.out.print(grid[i][j] + "|" + i);
                    else
                        System.out.print(grid[i][j]);
                }
                System.out.println();
            }

            //Last section of Ocean Map
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();
        }

        public static void kapalSaya(){
            Scanner input = new Scanner(System.in);

            System.out.println("\nDeploy your ships:");
            //Deploying five ships for player
            BattleShips.playerShips = 5;
            for (int i = 1; i <= BattleShips.playerShips; ) {
                System.out.print("Enter X coordinate for your " + i + " ship: ");
                int x = input.nextInt();
                System.out.print("Enter Y coordinate for your " + i + " ship: ");
                int y = input.nextInt();

                if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[y][x] == " "))
                {
                    grid[y][x] =   "@";
                    i++;
                }
                else if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && grid[y][x] == "@")
                    System.out.println("You can't place two or more ships on the same location");
                else if((x < 0 || x >= numRows) || (y < 0 || y >= numCols))
                    System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
            }
            printOceanMap();
        }

        public static void kapalCom(){
            System.out.println("\nComputer is deploying ships");
            //Deploying five ships for computer
            BattleShips.computerShips = 5;
            for (int i = 1; i <= BattleShips.computerShips; ) {
                int x = (int)(Math.random() * 10);
                int y = (int)(Math.random() * 10);

                if((x >= 0 && x < numRows) && (y >= 0 && y < numCols) && (grid[y][x] == " "))
                {
                    grid[x][y] =   "\u00A0"; //Non-breaking Space Unicode or Invisible space
                    System.out.println(i + ". ship DEPLOYED");
                    i++;
                }
            }
            printOceanMap();
        }

        public static void Battle(){
            playerTurn();
            computerTurn();

            printOceanMap();

            System.out.println();
            System.out.println("Your ships: " + BattleShips.playerShips + " | Computer ships: " + BattleShips.computerShips);
            System.out.println();
        }

        public static void playerTurn(){
            System.out.println("\nYOUR TURN");
            int x = -1, y = -1;
            do {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter X coordinate: ");
                x = input.nextInt();
                System.out.print("Enter Y coordinate: ");
                y = input.nextInt();

                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
                {
                    if (grid[y][x] == "\u00A0") //if computer ship is already there; computer loses ship
                    {
                        System.out.println("Boom! You sunk the ship!");
                        grid[y][x] = "!"; //Hit mark
                        --BattleShips.computerShips;
                    }
                    else if (grid[y][x] == "@") {
                        System.out.println("Oh no, you sunk your own ship :(");
                        grid[y][x] = "x";
                        --BattleShips.playerShips;
                        //++BattleShips.computerShips;
                    }
                    else if (grid[y][x] == " ") {
                        System.out.println("Sorry, you missed");
                        grid[y][x] = "-";
                    }
                }
                else if ((x < 0 || x >= numRows) || (y < 0 || y >= numCols))  //invalid guess
                    System.out.println("You can't place ships outside the " + numRows + " by " + numCols + " grid");
            }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
        }

        public static void computerTurn(){
            System.out.println("\nCOMPUTER'S TURN");
            //Guess co-ordinates
            int x = -1, y = -1;
            do {
                x = (int)(Math.random() * 10);
                y = (int)(Math.random() * 10);

                if ((x >= 0 && x < numRows) && (y >= 0 && y < numCols)) //valid guess
                {
                    if (grid[y][x] == "@") //if player ship is already there; player loses ship
                    {
                        System.out.println("The Computer sunk one of your ships!");
                        grid[y][x] = "x";
                        --BattleShips.playerShips;
                        //++BattleShips.computerShips;
                    }
                    else if (grid[x][y] == "\u00A0") {
                        System.out.println("The Computer sunk one of its own ships");
                        grid[y][x] = "!";
                    }
                    else if (grid[y][x] == " ") {
                        System.out.println("Computer missed");
                        //Saving missed guesses for computer
                        if(missedGuesses[y][x] != 1)
                            missedGuesses[y][x] = 1;
                    }
                }
            }while((x < 0 || x >= numRows) || (y < 0 || y >= numCols));  //keep re-prompting till valid guess
        }

        public static void gameOver(){
            System.out.println("Your ships: " + BattleShips.playerShips + " | Computer ships: " + BattleShips.computerShips);
            if(BattleShips.playerShips > 0 && BattleShips.computerShips <= 0)
                System.out.println("Hooray! You won the battle :)");
            else
                System.out.println("Sorry, you lost the battle");
            System.out.println();
        }

        public static void printOceanMap(){
            System.out.println();
            //First section of Ocean Map
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();

            //Middle section of Ocean Map
            for(int x = 0; x < grid.length; x++) {
                System.out.print(x + "|");

                for (int y = 0; y < grid[x].length; y++){
                    System.out.print(grid[x][y]);
                }

                System.out.println("|" + x);
            }

            //Last section of Ocean Map
            System.out.print("  ");
            for(int i = 0; i < numCols; i++)
                System.out.print(i);
            System.out.println();
        }
    }