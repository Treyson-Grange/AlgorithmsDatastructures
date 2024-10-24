import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class HexGame {
    final int TOP = 125;
    final int BOT = 126;
    final int LEFT = 124;
    final int RIGHT = 123;
    final String ASNI_RESET = "\u001B[0m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_BLUE = "\u001B[34m";

    HexGame() {
        UnionFindGraph graph = new UnionFindGraph(128);//I am going from 1-122 so i can do good math in printBoard()
        String[] blueOrRed = new String[128];
        this.graph = graph;
    }

    public int[] getNeighbors(int i) {
        if(i == 1) {return new int[]{TOP, LEFT, 2, 12};}//These are all checking for corners
        if(i == 11) {return new int[]{10,22,21,TOP,RIGHT};}//top right
        if(i == 121) {return new int[]{BOT,RIGHT,120,110};}//bottom right
        if(i == 111) {return new int[]{BOT,LEFT,100,112,101};}//bottom left
        if(i % 11 == 1) {return new int[]{i+1,i-11,i+11,i-10,LEFT};}//left wall
        if(i % 11 == 0) {return new int[]{i-1,i-11,i+11,i+10,RIGHT};}//right wall
        if(i < 11) {return new int[]{i+1,i-1,i+11,i+10,TOP};}
        if(i < 121 && i > 111){return new int[]{i+1,i-1,i-11,i-10,BOT};}
        else {return new int[]{i-1,i+1,i-11,i+11,i-10,i+10};}
    }


    public void printBoard() {
        int indent = 0;
        for(int i = 1; i < 122; i++) {//set.length is 127 so I have to do it this way
            if(i % 11 == 1) {
                for(int j = 0; j < indent; j++) {
                    System.out.print(" ");
                }
            }
            if(blueOrRed[i].equals("RED")) {// == red
                //if I on the graph is in the red group print red
                System.out.print(ANSI_RED + "R " + ASNI_RESET);
            }
            else if(blueOrRed[i].equals("BLUE")) {//== blue
                //else if I on the graph is in blue group, print it in blue
                System.out.print(ANSI_BLUE + "B " + ASNI_RESET);
            }
            else {
                //else print grey
                System.out.print("0 ");
            }
            if(i % 11 ==0) {
                System.out.println();
                //if i is on the very edge of the graph, we will print to a new line
                indent += 1;
                //in the if, we would increment a indent, so we get the effect from the example
            }
        }
        System.out.println();
    }

    public void mainGame(String fileName) throws FileNotFoundException {
        System.out.println("--------> Starting game with file name: " + fileName + ".");
        HexGame game = new HexGame();
        Arrays.fill(game.blueOrRed,"NONE");//top left TOP = 125 right = 123 BOT = 126 left = 124
        Arrays.fill(blueOrRed,"NONE");
        boolean winnerDecided = false;
        int moves = 0;
        game.blueOrRed[LEFT] = "BLUE";
        game.blueOrRed[RIGHT] = "BLUE";
        game.blueOrRed[BOT] = "RED";
        game.blueOrRed[TOP] = "RED";
        int toBoard;
        int i = 1;
        int[] neighbors;
        File file = new File("src/" + fileName);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextInt()) {
            i++;
            toBoard = scanner.nextInt();
            if(game.graph.find(TOP) == game.graph.find(BOT)) {
                System.out.println("--------> Red has won after " + (i-1) + " attempted moves! Here is the final board.\n");
                game.printBoard();
                winnerDecided = true;
                break;
            }
            if(game.graph.find(LEFT) == game.graph.find(RIGHT)) {
                System.out.println("--------> Blue has won after " + (i-1) + " attempted moves! Here is the final board.\n");
                game.printBoard();
                winnerDecided = true;
                break;
            }
            if(i % 2 == 0) {
                game.blueOrRed[toBoard] = "BLUE";
                neighbors = game.getNeighbors(toBoard);
                for (int neighbor : neighbors) {
                    if (game.blueOrRed[neighbor].equals("BLUE")) {
                        game.graph.union(toBoard, neighbor);
                    }
                }
            }
            else {
                game.blueOrRed[toBoard] = "RED";
                neighbors = game.getNeighbors(toBoard);
                for (int neighbor : neighbors) {
                    if (game.blueOrRed[neighbor].equals("RED")) {
                        game.graph.union(toBoard, neighbor);
                    }
                }
            }
        }
        if(!winnerDecided) {
            if(game.graph.find(TOP) == game.graph.find(BOT)) {
                System.out.println("--------> Red has won after " + (i-1) + " attempted moves! Here is the final board.\n");
            }
            if(game.graph.find(LEFT) == game.graph.find(RIGHT)) {
                System.out.println("--------> Blue has won after " + (i-1) + " attempted moves! Here is the final board.\n");
            }
            game.printBoard();
        }
        System.out.println("\n");
        scanner.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        HexGame start = new HexGame();
        start.mainGame("moves.txt");
        start.mainGame("moves2.txt");
        //start.mainGame("moves3.txt");
    }
    UnionFindGraph graph;
    String[] blueOrRed = new String[128];
}
