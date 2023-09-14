package boardgame;

import boardgame.model.BoardGameModel;
import boardgame.results.GameResult;
import boardgame.results.GameResultManager;
import boardgame.results.JsonGameResultManager;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.ZonedDateTime;

public class BoardGameController {

    @FXML
    private GridPane board;

    private BoardGameModel model = new BoardGameModel();

    @FXML
    private TextField RedMoves;

    @FXML
    private TextField BlueMoves;

    private ZonedDateTime gameStartTime;

    @FXML
    private void initialize() {
        Logger.info("THE GAME STARTED");
        Logger.info("RED PLAYER TURN");
        gameStartTime = ZonedDateTime.now();
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
        RedMoves.textProperty().bind(model.NumberOfMovesRedProperty().asString());
        BlueMoves.textProperty().bind(model.NumberOfMovesBlueProperty().asString());
    }
    @FXML
    private void switchScene(ActionEvent event) throws IOException {
        if (model.isGameEnded()) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/resultpage.fxml"));
            stage.setTitle("Top 5 Players");
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("GAME NOT ENDED");
            alert.setContentText("The game is not yet ended. Please continue playing.");
            alert.showAndWait();
        }
    }

    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);

        piece.fillProperty().bind(
                new ObjectBinding<>() {
                    {
                        super.bind(model.squareProperty(i, j));
                    }

                    @Override
                    protected Paint computeValue() {
                        return switch (model.squareProperty(i, j).get()) {
                            case EMPTY -> Color.TRANSPARENT;
                            case RED -> Color.RED;
                            case BLUE -> Color.BLUE;
                        };
                    }
                }
        );
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        if (!model.isGameEnded()) {
            var square = (StackPane) event.getSource();
            var row = GridPane.getRowIndex(square);
            var col = GridPane.getColumnIndex(square);
            Logger.info("Click on square ({}, {})", row, col);
            model.move(row, col);

            if (model.isGameEnded()) {
                Logger.info("Game has ended");
                handleGameOver();
            }
            if (model.isRedTurn()){
                Logger.info("RED PLAYER TURN");
            }
            else {
                Logger.info("BLUE PLAYER TURN");
            }
        }
    }

    private void handleGameOver() {
        String winningPlayerName = model.getWinner();
        String winningPlayerColor = (winningPlayerName.equals(model.getNameOfRedPlayer())) ? "RED" : "BLUE";

        int redMoves = model.NumberOfMovesRedProperty().get();
        int blueMoves = model.NumberOfMovesBlueProperty().get();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("GAME OVER");
        alert.setContentText(String.format("%s (%s) WINS WITH %s MOVES", winningPlayerName, winningPlayerColor, (winningPlayerColor.equals("RED") ? redMoves : blueMoves)));
        alert.showAndWait();

        GameResult gameResult = GameResult.builder()
                .redPlayer(model.getNameOfRedPlayer())
                .bluePlayer(model.getNameOfBluePlayer())
                .numberOfMovesRed(redMoves)
                .numberOfMovesBlue(blueMoves)
                .winnerName(winningPlayerName)
                .dateStarted(gameStartTime)
                .build();
        try {
            GameResultManager manager = new JsonGameResultManager(Paths.get("results.json"));
            manager.add(gameResult);
        } catch (IOException e) {
            Logger.error(e, "Failed to save game result");
        }
    }
}