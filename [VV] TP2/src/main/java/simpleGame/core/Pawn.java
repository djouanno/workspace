package simpleGame.core;

import simpleGame.exception.OutOfBoardException;

public class Pawn {


    /**
     * Position on the x axis
     */
    private int x;

    /**
     * Position on the y axis
     */
    private int y;

    /**
     * The board on which the pawn is located
     */
    private Board board;

    /**
     * The remaining number of hitpoints of the pawn
     */
    private int hitpoints;

    /**
     * The collected gold of the pawn.
     */
    private int gold;

    /**
     * The char that represents the pawn.
     */
    private char letter;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getLetter() {
        return letter;
    }

    public int getGold() {
        return gold;
    }



    /**
     * Creates a Pawn with 2 hitpoints and 0 gold.
     * @param n The letter that represents the pawn.
     * @param x Position on the x axis
     * @param y Position on the y axis
     * @param board The board on which the pawn is located
     */
    public Pawn(char n, int x, int y, Board board) {
        this.letter = n;
        this.x = x;
        this.y = y;
        this.board = board;
        this.hitpoints = 2;
        this.gold = 0;
    }


    /**
     * Moves the pawn in a direction.
     * @param d The direction to move the pawn to.
     * @return A message that explain what happened during the movement.
     * @throws OutOfBoardException If the pawn tries to move out of the board.
     */
    public String move(Direction d) throws
        OutOfBoardException {

        String message = "";

        int newx = x;
        int newy = y;

        switch(d) {
        case Up:
            newy++ ;
            break;
        case Down:
            newy-- ;
            break;
        case Left:
            newx-- ;
            break;
        case Right:
            newx++ ;
            break;
        }

        if 	(newy <= board.getYSize()
                && newx <= board.getXSize()
                && newy > 0
                && newx > 0) {
            Pawn content = board.getSquareContent(newx,newy);
            if (content == null) {
                x = newx;
                y = newy;
            }
            else {
                message = this.attack(content);
            }
        } else {
            throw new OutOfBoardException(newx,newy);
        }

        return message;
    }

    /**
     * Makes the pawn attack another pawn.
     * The enemy pawn should suffer 1 damage,
     * but it should suffer 2 damages if the current pawn
     * is on a bonus square.
     * @param ennemy The attacked pawn.
     * @return A message that explain what happened during the assault.
     */
    private String attack(Pawn enemy) {
        String message=this.letter + "attacks!\n";
        if (this.board.isBonusSquare(x, y))
            message+=enemy.suffer(2);
        else
            message+=enemy.suffer(1);
        if (enemy.isDead()) gold++;
        return message;
    }

    /**
     * To make the Pawn lose hitpoints.
     * If the pawn reaches 0 hitpoints, it is removed
     * from the board.
     * @param i The number of hitpoints to lose.
     * @return A message that explain what happened with the wounds.
     */
    private String suffer(int i) {
        String message = this.letter+" loses "+i
                         +" hitpoints. ";
        hitpoints = hitpoints - i;
        if (hitpoints <= 0) {
            this.board.removePawn(this);
            message += this.letter+" is dead.";
        }
        return message;
    }
    
    /**
     * To know whether a pawn is dead or not.
     * @return True if the pawn is dead, false otherwise.
     */
    public boolean isDead() {
    	return this.hitpoints == 0;
    }


}
