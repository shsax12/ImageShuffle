package imageshuffle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerInput extends ControllerAbstract implements Initializable {

    @FXML
    private ImageView imgView;
    @FXML
    private TextField imgText;

    private int countNext = 0;

    private int regIndex;

    private List<Dataset> dataListCopy = new ArrayList<>(dataList);

    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {
        imgDisplay();
    }

    private void imgDisplay() {
        try {
            Image image = new Image(fileList[countNext].toURI().toURL().toString());
            imgView.setImage(image);
        } catch (MalformedURLException ex) {
            mainInst.printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        }

        regIndex = mainInst.registered(fileList[countNext].getName(), dataList);
        if (regIndex == -1) {
            imgText.clear();
        } else {
            imgText.setText(dataList.get(regIndex).getText());
        }
    }

    @FXML
    public void next(ActionEvent event) {
        countNext++;

        //データ登録
        if (countNext <= fileList.length) {
            String s = imgText.getText();

            //未登録の場合
            if (regIndex == -1) {
                Dataset dataset = new Dataset();
                dataset.setImage(fileList[countNext-1]);
                dataset.setText(s);
                dataset.setFileName(fileList[countNext-1].getName());
                dataListCopy.add(dataset);
                mainInst.setDataList(dataListCopy);
            //テキストが異なる場合
            } else if (!dataList.get(regIndex).getText().equals(s)) {
                dataListCopy.get(regIndex).setText(s);
                mainInst.setDataList(dataListCopy);
            }
        }

        //データ表示
        if (countNext < fileList.length) {
            imgDisplay();
        }

        if (countNext >= fileList.length) {
            imgView.setImage(null);
            imgText.clear();
            mainInst.printDialog("information", "全ての画像を読み込みました。");
        }
    }

    @FXML
    @Override
    public void toTop(ActionEvent event) {
        mainInst.dataWrite(dataListCopy,
                System.getProperty("user.home") + "/image-shuffle/datalist.obj");
        setTopPage();
    }
}
