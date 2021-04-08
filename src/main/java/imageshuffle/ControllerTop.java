package imageshuffle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControllerTop extends ControllerAbstract {

    @FXML
    public void toInput(ActionEvent event) {
        mainInst.setPage("/input.fxml", "データ入力");
    }

    @FXML
    public void toOutput(ActionEvent event) {
        mainInst.shuffleFileList(); //確認テストでは出力順を毎回ランダムにする
        mainInst.setPage("/output.fxml", "テスト");
    }

    @Override
    public void toTop(ActionEvent event) {}
}
