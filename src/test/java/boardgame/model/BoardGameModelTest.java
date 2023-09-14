package boardgame.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardGameModelTest {

    private BoardGameModel model;

    @BeforeEach
    public void setup() {
        model = new BoardGameModel();
    }

    @Test
    public void testInitialBoardState() {
        for (int i = 0; i < BoardGameModel.BOARD_SIZE; i++) {
            for (int j = 0; j < BoardGameModel.BOARD_SIZE; j++) {
                Assertions.assertEquals(GameSquare.EMPTY, model.squareProperty(i, j).get());
            }
        }
    }

    @Test
    public void testPlayerNames() {
        model.setRedPlayerName("Shah");
        model.setBluePlayerName("Mi");

        Assertions.assertEquals("Shah", model.getNameOfRedPlayer());
        Assertions.assertEquals("Mi", model.getNameOfBluePlayer());
    }

    @Test
    public void testInvalidMove() {
        model.move(0, 0);
        model.move(0, 0);

        Assertions.assertEquals(GameSquare.RED, model.squareProperty(0, 0).get());
        Assertions.assertFalse(model.isGameEnded());
    }

    @Test
    public void testHorizontalWinningCondition() {
        model.move(0, 0);
        model.move(1, 0);
        model.move(0, 1);
        model.move(1, 1);
        model.move(0, 2);

        Assertions.assertTrue(model.isGameEnded());
    }

    @Test
    public void testVerticalWinningCondition() {
        model.move(0, 0);
        model.move(0, 1);
        model.move(1, 0);
        model.move(1, 1);
        model.move(2, 0);

        Assertions.assertTrue(model.isGameEnded());
    }

    @Test
    public void testDiagonalWinningCondition() {
        model.move(0, 0);
        model.move(1, 0);
        model.move(1, 1);
        model.move(2, 1);
        model.move(2, 2);

        Assertions.assertTrue(model.isGameEnded());
    }

    @Test
    public void testAntiDiagonalWinningCondition() {
        model.move(0, 4);
        model.move(1, 3);
        model.move(2, 2);
        model.move(3, 1);
        model.move(4, 0);

        Assertions.assertTrue(model.isGameEnded());
    }

    @Test
    public void testPlayerTurnsAndMoveCounters() {
        Assertions.assertTrue(model.isRedTurn());

        model.move(0, 0);

        Assertions.assertFalse(model.isRedTurn());

        Assertions.assertEquals(1, model.NumberOfMovesRedProperty().get());
        Assertions.assertEquals(0, model.NumberOfMovesBlueProperty().get());

        model.move(1, 0);

        Assertions.assertTrue(model.isRedTurn());

        Assertions.assertEquals(1, model.NumberOfMovesRedProperty().get());
        Assertions.assertEquals(1, model.NumberOfMovesBlueProperty().get());
    }
}