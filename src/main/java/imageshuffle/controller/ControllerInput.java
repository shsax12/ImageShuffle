package imageshuffle.controller;

import imageshuffle.appdata.Dataset;
import imageshuffle.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerInput extends ControllerAbstract implements Initializable {

    @FXML
    private ImageView imgView;
    @FXML
    private TextField imgText;

    private int countNext = 0;

    private int regIndex;

    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {
        imgDisplay();
    }

    private void imgDisplay() {
        try {
            Image image = new Image(imageFileList.get(countNext).toURI().toURL().toString());
            imgView.setImage(image);
        } catch (MalformedURLException ex) {
            Util.printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        }

        regIndex = datasets.datasetRegistered(imageFileList.get(countNext).getName(), datasetList);
        if (regIndex == -1) {
            imgText.clear();
        } else {
            imgText.setText(datasetList.get(regIndex).getText());
        }
    }

    @FXML
    public void next(ActionEvent event) {
        countNext++;

        //データ登録
        if (countNext <= imageFileList.size()) {
            String inputText = imgText.getText();

            //未登録の場合
            if (regIndex == -1) {
                Dataset dataset = new Dataset();
                dataset.setImage(imageFileList.get(countNext-1));
                dataset.setText(inputText);
                dataset.setFileName(imageFileList.get(countNext-1).getName());
                datasetList.add(dataset);
            //テキストが異なる場合
            } else if (!datasetList.get(regIndex).getText().equals(inputText)) {
                datasetList.get(regIndex).setText(inputText);
            }
        }

        //データ表示
        if (countNext < imageFileList.size()) {
            imgDisplay();
        }

        if (countNext >= imageFileList.size()) {
            imgView.setImage(null);
            imgText.clear();
            Util.printDialog("information", "全ての画像を読み込みました。");
        }
    }

    @FXML
    @Override
    public void toTop(ActionEvent event) {
        //トップページに戻るときにdatasetlistをファイルに出力する
        datasets.writeDatasetListFile(datasetList,
                System.getProperty("user.home") + "/image-shuffle/datasetlist.obj");
        setTopPage();
    }
}
