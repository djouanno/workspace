package simpleGame.core;

import java.util.ArrayList;
import java.util.Random;

/**
 * Describes the board on which Pawns can move. It is of rectangular shape, and is made of squares. One of the square is a "bonus"
 * square: it allows pawns to be stronger.
 * 
 * @author Erwan Bousse
 * 
 */
public class Board
{

    /**
     * The number of squares on the x axis.
     */
    private final int       xSize;

    /**
     * The number of squares on the y axis.
     */
    private final int       ySize;

    /**
     * The Pawns that currently are on the board.
     */
    private ArrayList<Pawn> pawns;


    /**
     * The x position of the bonus square
     */
    private final int       xBonusSquare;

    /**
     * the y position of the bonus square
     */
    private final int       yBonusSquare;

    /**
     * An iterator pointing towards the current pawn that must play.
     */
    private Pawn            currentPawn;



    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }


    /**
     * Constructs a board, with a number of pawns and a size. The pawns are spread randomly. The bonus square is selected
     * randomly.
     * 
     * @param numberOfPawns
     *            The number of pawns.
     * @param sizeX
     *            The number of squares on the x axis.
     * @param sizeY
     *            The number of squares on the y axis.
     */
    public Board(final int numberOfPawns, final int sizeX, final int sizeY) {
        final Random random = new Random();
        this.xSize = sizeX;
        this.ySize = sizeY;
        this.xBonusSquare = random.nextInt(xSize);
        this.yBonusSquare = random.nextInt(ySize);
        this.pawns = new ArrayList<Pawn>();
        for (int i = 0; i < numberOfPawns; i++) {
            final Pawn pawn = new Pawn(Character.forDigit(i, 10), random.nextInt(xSize), random.nextInt(ySize), this);
            this.addPawn(pawn);
        }

        currentPawn = pawns.get(0);
    }

    /**
     * Finds the content of a square.
     * 
     * @param x
     *            The x axis value.
     * @param y
     *            The y axis value.
     * @return The pawn found, or null if no pawn.
     */
    public Pawn getSquareContent(final int x, final int y) {
        for (final Pawn p : pawns) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        return null;
    }

    /**
     * Removes a pawn from the board.
     * 
     * @param pawn
     *            The pawn to remove.
     */
    public void removePawn(final Pawn pawn) {
        pawns.remove(pawn);
    }

    /**
     * Adds a pawn to the board.
     * 
     * @param pawn
     *            The pawn to add.
     */
    public void addPawn(final Pawn pawn) {
        if (getSquareContent(pawn.getX(), pawn.getY()) == null) {
            this.pawns.add(pawn);
        }
    }

    /**
     * Decides whether a square is bonus or not.
     * 
     * @param x
     *            The x axis value.
     * @param y
     *            The y axis value.
     * @return True if the square is bonus, false otherwise.
     */
    public boolean isBonusSquare(final int x, final int y) {
        return x == xBonusSquare && y == yBonusSquare;
    }


    /**
     * Finds the number of pawns on the board.
     * 
     * @return The number of pawns on the board.
     */
    public int numberOfPawns() {
        return pawns.size();
    }

    /**
     * Computes the maximum amount of golf that a Pawn has.
     * 
     * @return The maximum amount of golf that a Pawn has.
     */
    public int maxGold() {
        int max = 0;
        for (final Pawn p : pawns) {
            max = Math.max(max, p.getGold());
        }
        return max;
    }

    /**
     * Picks the next pawn that is allowed to play.
     * 
     * @return The next pawn that is allowed to play.
     */
    public Pawn getNextPawn() {
        if (pawns.size() == 1) {
            currentPawn = pawns.get(0);
            return pawns.get(0);
        }
        final Pawn result = currentPawn;
        currentPawn = this.pawns.get((this.pawns.indexOf(currentPawn) + 1) % this.pawns.size());
        return result;
    }

    /**
     * Computes the char that should be displayed to represent the square or its content.
     * 
     * @param x
     *            The x axis value.
     * @param y
     *            The y axis value.
     * @return # if bonus, . if empty, c if current Pawn, a number for a non-current Pawn
     */
    public char squareContentSprite(final int x, final int y) {
        char result;
        final Pawn content = getSquareContent(x, y);
        if (content == null) {
            if (isBonusSquare(x, y)) {
                result = '#';
            } else {
                result = '.';
            }
        } else {
            if (content == currentPawn) {
                result = 'c';
            } else {
                result = content.getLetter();
            }
        }
        return result;
    }

    /**
     * Computes a String that represents the whole board.
     */
    @Override
    public String toString() {
        String result = "";

        for (int y = ySize - 1; y >= 0; y--) {
            for (int x = 0; x < xSize; x++) {
                result += squareContentSprite(x, y);
            }
            result += "\n";
        }
        return result;
    }

    public Pawn getCurrentPawn() {
        return currentPawn;
    }

    public void setCurrentPawn(final Pawn currentPawn) {
        this.currentPawn = currentPawn;
    }

    public ArrayList<Pawn> getPawns() {
        return pawns;
    }

    public void setPawns(final ArrayList<Pawn> pawns) {
        this.pawns = pawns;
    }
}
