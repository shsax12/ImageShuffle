package imageshuffle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainApp extends Application {

    public static MainApp singleton;

    private Parent root;
    private Stage stage;

    public List<Dataset> dataList = new ArrayList<>(); //画像＆テキストのセット
    public File[] fileList; //指定ディレクトリにある画像ファイル

    private final String HOME = System.getProperty("user.home");

    @Override
    public void start(Stage primaryStage) throws Exception {
        singleton = this;
        stage = primaryStage;
        root = FXMLLoader.load(getClass().getResource("/top.fxml"));
        stage.setTitle("トップページ");
        stage.setScene(new Scene(root));
        stage.show();
        dataList = objRead();
        File imgFile = new File(HOME + "/image-shuffle/image");
        fileList = imgFile.listFiles(filter);
    }

    private List<Dataset> objRead() {
        List<Dataset> retObject = new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(HOME + "/image-shuffle/datalist.obj"));
            retObject = (List<Dataset>)ois.readObject();
        } catch(IOException e) {
            System.out.println("datalist file not found.");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return retObject;
    }

    FilenameFilter filter = new FilenameFilter() {
        public boolean accept(File file, String str){
            if (str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith(".png")) {
                return true;
            } else {
                return false;
            }
        }
    };

    public void setTopPage() {
        try {
            root = FXMLLoader.load(getClass().getResource("/top.fxml"));
            stage.setTitle("トップページ");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInputPage() {
        try {
            root = FXMLLoader.load(getClass().getResource("/input.fxml"));
            stage.setTitle("データ入力");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOutputPage() {
        try {
            root = FXMLLoader.load(getClass().getResource("/output.fxml"));
            stage.setTitle("テスト");
            stage.setScene(new Scene(root));

            List<File> list = new ArrayList<>();
            for (File f: fileList) {
                list.add(f);
            }
            Collections.shuffle(list);
            for (int i = 0; i < list.size(); i++) {
                fileList[i] = list.get(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MainApp getInstance() {
        return singleton;
    }
}
