package boardgame;


import java.io.IOException;

import boardgame.model.BoardGameModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class MainMenuController {

    private BoardGameModel model = new BoardGameModel();

    @FXML
    private TextField RedPlayer;

    @FXML
    private TextField BluePlayer;

    @FXML
    private void switchScene(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ui.fxml"));
        model.setRedPlayerName(RedPlayer.getText());
        model.setBluePlayerName(BluePlayer.getText());
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
