package boardgame;

import boardgame.results.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.nio.file.Path;

public class ResultSceneController {

    @FXML
    private TableView<HighscoreResult> top5table;

    @FXML
    private TableColumn<GameResult, String> winnerName;

    @FXML
    private TableColumn<GameResult, Long> numberOfWins;

    @FXML
    void handleExit() {
        Platform.exit();
    }

    @FXML
    private void initialize() throws IOException {
        winnerName.setCellValueFactory(new PropertyValueFactory<>("winnerName"));
        numberOfWins.setCellValueFactory(new PropertyValueFactory<>("numberOfWins"));
        ObservableList<HighscoreResult> observableList = FXCollections.observableArrayList();
        observableList.addAll(new JsonGameResultManager(Path.of("results.json")).getBestPlayers(5));
        top5table.setItems(observableList);
    }
}
