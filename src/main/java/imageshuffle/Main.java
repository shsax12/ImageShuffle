package imageshuffle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main extends Application {

    private static Main singleton;

    private Parent root;
    private Stage stage;

    private List<Dataset> dataList = new ArrayList<>(); //画像＆テキストのセット
    private File[] fileList; //指定ディレクトリにある画像ファイル

    private final String HOME = System.getProperty("user.home");

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void start(Stage primaryStage) {
        singleton = this;

        dataList = dataRead(HOME + "/image-shuffle/datalist.obj");
        File imgDir = new File(HOME + "/image-shuffle/image");
        fileList = imgDir.listFiles(filter);

        if (imgDir.exists() && fileList.length > 0) {
            stage = primaryStage;
            setPage("/top.fxml", "トップページ");
            stage.show();
        } else {
            printDialog("warning",
                    "imageファイルを正しく配置してください。\n " + "/user/image-shuffle/image");
            logger.warn("image directory or file is not found.");
        }
    }

    /**
     * 既存のdatalistファイルを読み込む
     * @param filePath datalistファイルのパス
     * @return Dataset型のList
     */
    private List<Dataset> dataRead(String filePath) {
        List<Dataset> dataList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            dataList = (List<Dataset>) ois.readObject();
        } catch (FileNotFoundException ex) {
            printDialog("information", "登録済みデータはありません。");
            logger.info("datalist file not found.");
        } catch (IOException ex) {
            printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        } catch (ClassNotFoundException ex) {
            printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        }

        return dataList;
    }

    /**
     * datalistをファイルに出力する
     * @param datalist 出力するdatalist
     * @param filePath 出力するファイルパス
     */
    public void dataWrite(List datalist, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(datalist);
        } catch (IOException ex) {
            printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        }
    }

    FilenameFilter filter = (dir, name) ->
            name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");

    public void setPage(String fxml, String title) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxml));
            stage.setTitle(title);
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        }
    }

    /**
     * アラートのダイアログを表示する
     * @param alertType アラートレベル
     * @param message 表示するメッセージ
     */
    public void printDialog(String alertType, String message) {
        Alert dialog;

        switch (alertType) {
            case "information":
                dialog = new Alert(AlertType.INFORMATION);
                break;
            case "warning":
                dialog = new Alert(AlertType.WARNING);
                break;
            case "error":
                dialog = new Alert(AlertType.ERROR);
                break;
            default:
                dialog = new Alert(AlertType.INFORMATION);
        }

        dialog.setHeaderText(null);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    public void shuffleFileList() {
        List<File> list = new ArrayList<>(Arrays.asList(fileList));
        Collections.shuffle(list);
        File[] flist = list.toArray(new File[list.size()]);
        fileList = flist;
    }

    /**
     * 画像ファイルが既存のdatalistに含まれているかを確認する
     * @param filename 画像ファイル名
     * @param dataList 既存のdatalist
     * @return 含まれている場合のdatalistのindex
     */
    public int registered(String filename, List<Dataset> dataList) {
        int i = 0;
        for (Dataset data : dataList) {
            if (data.getFileName().equals(filename)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public List<Dataset> getDataList() {
        return dataList;
    }

    public void setDataList(List<Dataset> dataList) {
        this.dataList = dataList;
    }

    public File[] getFileList() {
        return fileList;
    }

    public static Main getInstance() {
        return singleton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
