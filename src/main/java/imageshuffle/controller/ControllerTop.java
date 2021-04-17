package imageshuffle.controller;

import imageshuffle.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControllerTop {

    private Main mainInst = Main.getInstance();

    @FXML
    public void toInput(ActionEvent event) {
        mainInst.setPage("/fxml/input.fxml", "データ入力");
    }

    @FXML
    public void toOutput(ActionEvent event) {
        mainInst.setPage("/fxml/output.fxml", "テスト");
    }
}
