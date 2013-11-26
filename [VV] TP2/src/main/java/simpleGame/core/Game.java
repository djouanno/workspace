package simpleGame.core;

import simpleGame.exception.OutOfBoardException;

/**
 * Class that allows to displayer the board and control the game.
 * @author Erwan Bousse
  */
public class Game {

    /**
     * The board of the game
     */
    private Board board;

    /**
     * Constructs a Game with 2 pawns on a 4x4 board.
     */
    public Game() {
        board = new Board(2,4,4);
    }

    /**
     * The game is over if there is only one pawn left or if
     * a pawn possesses 5 gold or more.
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return (board.numberOfPawns()==1)
               || (board.maxGold() >= 3);
    }

    /**
     * To present the state of the game: the board and is the game is over or not.
     * @return A string that describes the current state of the game.
     */
    public String toString() {
        String result = board.toString();
        if (isGameOver())
            result +="\n\n Game over";
        return result;
    }

    /**
     * Moves the current pawn in a given direction.
     * @param d The direction in which the pawn must move.
     * @return A message regarding what happened.
     * @throws OutOfBoardException If the move is invalid regarding the board.
     */
    public String moveNextPawn(Direction d) throws
        OutOfBoardException {
        return this.board.getNextPawn().move(d);
    }

}
