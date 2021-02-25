package imageshuffle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.util.*;

public class Controller {

    @FXML
    private ImageView imgViewIn;
    @FXML
    private ImageView imgViewOut;
    @FXML
    private TextField imageTextIn;
    @FXML
    private Label imageTextOut;

    private int countNext = 0;

    private final String HOME = System.getProperty("user.home");

    private MainApp mainInst = MainApp.getInstance();


    @FXML
    protected void toInput(ActionEvent event) {
        mainInst.setInputPage();
    }

    @FXML
    protected void nextIn(ActionEvent event) {
        countNext++;

        //データ登録
        if (countNext > 1 && countNext <= mainInst.fileList.length+1) {
            String s = imageTextIn.getText();
            int regIdx1 = registered(mainInst.fileList[countNext-2].getName());
            if (regIdx1 == -1) {
                Dataset dataset = new Dataset();
                dataset.image = mainInst.fileList[countNext-2];
                dataset.text = s;
                dataset.fileName = mainInst.fileList[countNext-2].getName();
                mainInst.dataList.add(dataset);
            } else if (!mainInst.dataList.get(regIdx1).text.equals(s)) {
                mainInst.dataList.get(regIdx1).text = s;
            }
        }

        //データ表示
        if (countNext <= mainInst.fileList.length) {
            try {
                Image image = new Image(mainInst.fileList[countNext-1].toURI().toURL().toString());
                imgViewIn.setImage(image);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            int regIdx2 = registered(mainInst.fileList[countNext-1].getName());
            if(regIdx2 == -1) {
                imageTextIn.clear();
            } else {
                imageTextIn.setText(mainInst.dataList.get(regIdx2).text);
            }
        }

        if (countNext > mainInst.fileList.length) {
            imgViewIn.setImage(null);
            imageTextIn.setText("That's all!");
        }
    }

    private int registered(String filename) {
        int i = 0;
        for(Dataset data: mainInst.dataList) {
            if(data.fileName.equals(filename)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @FXML
    protected void toTopSave(ActionEvent event) {
        objWrite(mainInst.dataList);
        mainInst.setTopPage();
    }

    private void objWrite(List datalist) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream(HOME + "/image-shuffle/datalist.obj"));
            oos.writeObject(datalist);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void toOutput(ActionEvent event) {
        mainInst.setOutputPage();
    }

    @FXML
    protected void nextOut(ActionEvent event) {
        int idx = countNext / 2;

        if (idx > mainInst.fileList.length-1) {
            imgViewOut.setImage(null);
            imageTextOut.setText("Finished!");
            return;
        }

        int regIdx1 = registered(mainInst.fileList[idx].getName());

        if (countNext % 2 == 0) {
            //画像のみ表示
            try {
                File imgFile = mainInst.fileList[idx];
                Image image = new Image(imgFile.toURI().toURL().toString());
                imgViewOut.setImage(image);
                imageTextOut.setText("");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            //テキストも表示
            if (regIdx1 != -1) {
                imageTextOut.setText(mainInst.dataList.get(regIdx1).text);
                imageTextOut.setAlignment(Pos.CENTER);
            }
        }
        countNext++;
    }

    @FXML
    protected void toTop(ActionEvent event) {
        mainInst.setTopPage();
    }
}
