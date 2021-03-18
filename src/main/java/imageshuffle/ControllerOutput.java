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
        next(new ActionEvent());
    }

    @FXML
    public void next(ActionEvent event) {
        int idx = countNext / 2;

        if (idx > fileList.length-1) {
            imgView.setImage(null);
            imgText.setText("Finished!");
            return;
        }

        int regIdx = registered(fileList[idx].getName());

        if (countNext % 2 == 0) {
            //画像のみ表示
            try {
                File imgFile = fileList[idx];
                Image image = new Image(imgFile.toURI().toURL().toString());
                imgView.setImage(image);
                imgText.setText("");
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        } else {
            //テキストも表示
            if (regIdx == -1) {
                imgText.setText("未登録");
            } else {
                imgText.setText(dataList.get(regIdx).text);
                imgText.setAlignment(Pos.CENTER);
            }
        }
        countNext++;
    }

    @FXML
    @Override
    public void toTop(ActionEvent event) {
        setTopPage();
    }
}
