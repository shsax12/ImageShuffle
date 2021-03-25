package imageshuffle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOutput extends ControllerAbstract implements Initializable {

    @FXML
    private ImageView imgView;
    @FXML
    private Label imgText;

    private int countNext = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {
        imgDisplay();
    }

    private void imgDisplay() {
        try {
            File imgFile = fileList[countNext/2];
            Image image = new Image(imgFile.toURI().toURL().toString());
            imgView.setImage(image);
            imgText.setText("");
        } catch (MalformedURLException ex) {
            mainInst.printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        }
    }

    @FXML
    public void next(ActionEvent event) {
        countNext++;

        int idx = countNext / 2;

        if (idx > fileList.length-1) {
            imgView.setImage(null);
            imgText.setText("");
            mainInst.printDialog("information", "テスト終了です。");
            return;
        }

        //画像のみ表示
        if (countNext % 2 == 0) {
            imgDisplay();
        //テキストも表示
        } else {
            int regIndex = registered(fileList[idx].getName());

            if (regIndex == -1) {
                imgText.setText("未登録");
            } else {
                imgText.setText(dataList.get(regIndex).getText());
                imgText.setAlignment(Pos.CENTER);
            }
        }
    }

    @FXML
    @Override
    public void toTop(ActionEvent event) {
        setTopPage();
    }
}
