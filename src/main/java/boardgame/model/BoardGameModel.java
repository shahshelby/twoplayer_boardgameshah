package boardgame.model;

import javafx.beans.property.*;
import lombok.Getter;
import lombok.Setter;
import org.tinylog.Logger;


/**
 * Represents the logic of a board game.
 */
public class BoardGameModel {

    /** The size of the board (5x5). */
    public static final int BOARD_SIZE = 5;

    private ReadOnlyObjectWrapper<GameSquare>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];
    private static ReadOnlyStringWrapper redPlayerName = new ReadOnlyStringWrapper("");
    private static ReadOnlyStringWrapper bluePlayerName = new ReadOnlyStringWrapper("");
    private ReadOnlyIntegerWrapper NumberOfMovesRed = new ReadOnlyIntegerWrapper(0);
    private ReadOnlyIntegerWrapper NumberOfMovesBlue = new ReadOnlyIntegerWrapper(0);
    private ReadOnlyBooleanWrapper isGameEnded = new ReadOnlyBooleanWrapper(false);

    @Getter
    @Setter
    private static String Winner;

    private ReadOnlyBooleanWrapper isRedTurn = new ReadOnlyBooleanWrapper(true);
    private ReadOnlyBooleanWrapper isNextPlayerRed = new ReadOnlyBooleanWrapper(false);

    /**
     * Constructor for creating a new instance of BoardGameModel.
     * Sets up the board with empty squares.
     */
    public BoardGameModel() {
        for (var i = 0; i < BOARD_SIZE; i++) {
            for (var j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<>(GameSquare.EMPTY);
            }
        }
    }

    /**
     * Returns the property of the square at the specified position on the board.
     *
     * @param i the row index of the position
     * @param j the column index of the position
     * @return the read-only property of the square
     */
    public ReadOnlyObjectProperty<GameSquare> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * {@return the name of the red player}
     */
    public String getNameOfRedPlayer() {
        return redPlayerName.get();
    }

    /**
     * {@return the name of the blue player}
     */
    public String getNameOfBluePlayer() {
        return bluePlayerName.get();
    }

    /**
     * Sets the name of the red player.
     * @param name the name of the red player
     */
    public void setRedPlayerName(String name) {
        redPlayerName.set(name);
    }

    /**
     * Sets the name of the blue player.
     * @param name the name of the blue player
     */
    public void setBluePlayerName(String name) {
        bluePlayerName.set(name);
    }

    /**
     * Provides number of moves made by the red player.
     * @return property of the number of moves made by the red player
     */
    public ReadOnlyIntegerProperty NumberOfMovesRedProperty() {
        return NumberOfMovesRed.getReadOnlyProperty();
    }

    /**
     * Provides number of moves made by the blue player.
     * @return property of the number of moves made by the blue player
     */
    public ReadOnlyIntegerProperty NumberOfMovesBlueProperty() {
        return NumberOfMovesBlue.getReadOnlyProperty();
    }

    /**
     * Checks if the game has ended.
     * @return true if the game has ended, false otherwise
     */
    public boolean isGameEnded() {
        return isGameEnded.get();
    }

    /** Checks if it is currently the red player's turn.
     * @return true if it is the red player's turn, false if it's the blue player's turn
     */
    public boolean isRedTurn() {
        return isRedTurn.get();
    }

    /**
     * Makes a move at the specified position on the board.
     * Updates the game state accordingly.
     *
     * @param i the row index of the position
     * @param j the column index of the position
     */
    public void move(int i, int j) {
        if (board[i][j].get() == GameSquare.EMPTY && !isGameEnded()) {
            board[i][j].set(isRedTurn.get() ? GameSquare.RED : GameSquare.BLUE);
            if (checkWinningCondition(i, j)) {
                isGameEnded.set(true);
                setWinner(isRedTurn.get() ? getNameOfBluePlayer() : getNameOfRedPlayer());
            }

            if (isRedTurn.get()) {
                NumberOfMovesRed.set(NumberOfMovesRed.get() + 1);
                isNextPlayerRed.set(false);
            } else {
                NumberOfMovesBlue.set(NumberOfMovesBlue.get() + 1);
                isNextPlayerRed.set(true);
            }

            isRedTurn.set(!isRedTurn.get());
        }
        if (board[i][j].get() == GameSquare.RED || board[i][j].get() == GameSquare.BLUE && !isGameEnded()){
            Logger.info("THE POSITION IS NOT EMPTY");
        }
    }

    private boolean checkWinningCondition(int row, int col) {
        return checkHorizontalCondition(row) || checkVerticalCondition(col) ||
                checkDiagonalCondition() || checkAntiDiagonalCondition();
    }

    private boolean checkHorizontalCondition(int row) {
        for (int j = 0; j < BOARD_SIZE - 2; j++) {
            GameSquare currentCoin = board[row][j].get();
            if (currentCoin == GameSquare.EMPTY) {
                continue;
            }
            for (int k = j + 1; k < BOARD_SIZE - 1; k++) {
                GameSquare nextCoin = board[row][k].get();
                if (nextCoin == GameSquare.EMPTY) {
                    continue;
                }
                for (int l = k + 1; l < BOARD_SIZE; l++) {
                    GameSquare thirdCoin = board[row][l].get();
                    if (thirdCoin == GameSquare.EMPTY) {
                        continue;
                    }
                    if (currentCoin == nextCoin && nextCoin == thirdCoin) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkVerticalCondition(int col) {
        for (int i = 0; i < BOARD_SIZE - 2; i++) {
            GameSquare currentCoin = board[i][col].get();
            if (currentCoin == GameSquare.EMPTY) {
                continue;
            }
            for (int k = i + 1; k < BOARD_SIZE - 1; k++) {
                GameSquare nextCoin = board[k][col].get();
                if (nextCoin == GameSquare.EMPTY) {
                    continue;
                }
                for (int l = k + 1; l < BOARD_SIZE; l++) {
                    GameSquare thirdCoin = board[l][col].get();
                    if (thirdCoin == GameSquare.EMPTY) {
                        continue;
                    }
                    if (currentCoin == nextCoin && nextCoin == thirdCoin) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalCondition() {
        for (int i = 0; i < BOARD_SIZE - 2; i++) {
            for (int j = 0; j < BOARD_SIZE - 2; j++) {
                GameSquare currentCoin = board[i][j].get();
                if (currentCoin == GameSquare.EMPTY) {
                    continue;
                }
                for (int k = i + 1, l = j + 1; k < BOARD_SIZE - 1 && l < BOARD_SIZE - 1; k++, l++) {
                    GameSquare nextCoin = board[k][l].get();
                    if (nextCoin == GameSquare.EMPTY) {
                        continue;
                    }
                    for (int m = k + 1, n = l + 1; m < BOARD_SIZE && n < BOARD_SIZE; m++, n++) {
                        GameSquare thirdCoin = board[m][n].get();
                        if (thirdCoin == GameSquare.EMPTY) {
                            continue;
                        }
                        if (currentCoin == nextCoin && nextCoin == thirdCoin) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkAntiDiagonalCondition() {
        for (int i = 0; i < BOARD_SIZE - 2; i++) {
            for (int j = BOARD_SIZE - 1; j >= 2; j--) {
                GameSquare currentCoin = board[i][j].get();
                if (currentCoin == GameSquare.EMPTY) {
                    continue;
                }
                for (int k = i + 1, l = j - 1; k < BOARD_SIZE - 1 && l >= 1; k++, l--) {
                    GameSquare nextCoin = board[k][l].get();
                    if (nextCoin == GameSquare.EMPTY) {
                        continue;
                    }
                    for (int m = k + 1, n = l - 1; m < BOARD_SIZE && n >= 0; m++, n--) {
                        GameSquare thirdCoin = board[m][n].get();
                        if (thirdCoin == GameSquare.EMPTY) {
                            continue;
                        }
                        if (currentCoin == nextCoin && nextCoin == thirdCoin) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}