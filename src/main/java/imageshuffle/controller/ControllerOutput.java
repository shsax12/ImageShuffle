package imageshuffle.controller;

import imageshuffle.util.Util;
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
import java.util.Collections;
import java.util.ResourceBundle;

public class ControllerOutput extends ControllerAbstract implements Initializable {

    @FXML
    private ImageView imgView;
    @FXML
    private Label imgText;
    @FXML
    private Label imgNo;

    private int countNext = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {
        Collections.shuffle(imageFileList); //確認テストでは画像をランダムにする
        imgDisplay();
    }

    private void imgDisplay() {
        try {
            File imgFile = imageFileList.get(countNext/2);
            Image image = new Image(imgFile.toURI().toURL().toString());
            imgView.setImage(image);
            imgText.setText("");
            imgNo.setText((countNext/2 + 1) + "/" + imageFileList.size());
        } catch (MalformedURLException ex) {
            Util.printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        }
    }

    @FXML
    public void next(ActionEvent event) {
        countNext++;

        int idx = countNext / 2;

        if (idx > imageFileList.size()-1) {
            imgView.setImage(null);
            imgText.setText("");
            imgNo.setText("");
            Util.printDialog("information", "テスト終了です。");
            return;
        }

        //画像のみ表示
        if (countNext % 2 == 0) {
            imgDisplay();
        //テキストも表示
        } else {
            int regIndex = datasets.datasetRegistered(imageFileList.get(idx).getName(), datasetList);

            if (regIndex == -1) {
                imgText.setText("未登録");
            } else {
                imgText.setText(datasetList.get(regIndex).getText());
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
