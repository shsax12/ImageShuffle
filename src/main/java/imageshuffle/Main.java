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
import java.util.List;

public class Main extends Application {

    private static Main singleton;

    private Parent root;
    private Stage stage;

    //画像＆テキストのセット
    private List<Dataset> dataList = new ArrayList<>();
    //指定ディレクトリにある画像ファイル
    private File[] fileList;

    private final String HOME = System.getProperty("user.home");

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void start(Stage primaryStage) {
        singleton = this;

        dataList = dataRead();
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

    private List<Dataset> dataRead() {
        List<Dataset> dataList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(HOME + "/image-shuffle/datalist.obj"))) {
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

    public void dataWrite(List datalist) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(HOME + "/image-shuffle/datalist.obj"))) {
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

    public List<Dataset> getDataList() {
        return dataList;
    }

    public void setDataList(List<Dataset> dataList) {
        this.dataList = dataList;
    }

    public File[] getFileList() {
        return fileList;
    }

    public void setFileList(File[] fileList) {
        this.fileList = fileList;
    }

    public static Main getInstance() {
        return singleton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
