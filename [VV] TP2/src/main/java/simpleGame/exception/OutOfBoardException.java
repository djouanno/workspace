package simpleGame.exception;

public class OutOfBoardException extends
    Exception {


    private static final long serialVersionUID =
        7958968763680190848L;

    public OutOfBoardException(int x, int y) {
        super("This square does not exist: "+x+","+y);
    }

}
