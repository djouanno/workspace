package simpleGame.cli;

import java.util.Scanner;

import simpleGame.core.Direction;
import simpleGame.core.Game;
import simpleGame.exception.OutOfBoardException;

public class CLIMain {
	
	private static Game game;

    public static void main(String[] args) {

        game = new Game();
        Scanner scanner = new Scanner(System.in);
        while(!game.isGameOver()) {
            System.out.println(game.toString());
            int chosenint;
            for (chosenint = -10;
                    chosenint >= Direction.values().length
                    || chosenint <0; chosenint=scanner.nextInt()) {
                int counter = 0;
                for(Direction d : Direction.values()) {
                    System.out.println(counter + ": "+d.name());
                    counter++;
                }
                System.out.println("Please chose a direction: ");
            }
            try {
                System.out.println(game.moveNextPawn(
                                       Direction.values()[chosenint]));
            } catch (OutOfBoardException e) {
                System.out.println("You can't go that way!");
            }
        }
        scanner.close();

    }

}
