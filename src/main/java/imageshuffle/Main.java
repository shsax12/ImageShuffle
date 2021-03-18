package imageshuffle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static Main singleton;

    private Parent root;
    private Stage stage;

    private List<Dataset> dataList = new ArrayList<>(); //画像＆テキストのセット
    private File[] fileList; //指定ディレクトリにある画像ファイル

    private final String HOME = System.getProperty("user.home");

    @Override
    public void start(Stage primaryStage) {
        singleton = this;

        dataList = dataRead();
        File imgFile = new File(HOME + "/image-shuffle/image");
        fileList = imgFile.listFiles(filter);

        stage = primaryStage;
        setPage("/top.fxml", "トップページ");
        stage.show();
    }

    private List<Dataset> dataRead() {
        List<Dataset> dataList = new ArrayList<>();
        try {
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(HOME + "/image-shuffle/datalist.obj"));
            dataList = (List<Dataset>)ois.readObject();
        } catch(IOException ex) {
            System.out.println("datalist file not found.");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return dataList;
    }

    public void dataWrite(List datalist) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream(HOME + "/image-shuffle/datalist.obj"));
            oos.writeObject(datalist);
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                oos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
            ex.printStackTrace();
        }
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
