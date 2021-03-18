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

    private List<Dataset> dataListCopy = new ArrayList<>(dataList);

    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {
        imgDisplay();
    }

    @FXML
    public void next(ActionEvent event) {
        countNext++;

        //データ登録
        if (countNext <= fileList.length) {
            String s = imgText.getText();
            int regIdx = registered(fileList[countNext-1].getName());

            if (regIdx == -1) { //未登録の場合
                Dataset dataset = new Dataset();
                dataset.image = fileList[countNext-1];
                dataset.text = s;
                dataset.fileName = fileList[countNext-1].getName();
                dataListCopy.add(dataset);
                mainInst.setDataList(dataListCopy);
            } else if (!dataList.get(regIdx).text.equals(s)) { //テキストが異なる場合
                dataListCopy.get(regIdx).text = s;
                mainInst.setDataList(dataListCopy);
            }
        }

        //データ表示
        if (countNext < fileList.length) {
            imgDisplay();
        }

        if (countNext >= fileList.length) {
            imgView.setImage(null);
            imgText.setText("That's all!");
        }
    }

    private void imgDisplay() {
        try {
            Image image = new Image(fileList[countNext].toURI().toURL().toString());
            imgView.setImage(image);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }

        int regIdx = registered(fileList[countNext].getName());
        if(regIdx == -1) {
            imgText.clear();
        } else {
            imgText.setText(dataList.get(regIdx).text);
        }
    }

    @FXML
    @Override
    public void toTop(ActionEvent event) {
        mainInst.dataWrite(dataListCopy);
        setTopPage();
    }
}
