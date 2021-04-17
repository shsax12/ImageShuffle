package imageshuffle;

import imageshuffle.util.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main extends Application {

    private static Main singleton;

    private Parent root;
    private Stage stage;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void start(Stage primaryStage) {
        singleton = this;
        stage = primaryStage;
        setPage("/fxml/top.fxml", "トップページ");
        stage.show();
    }

    public void setPage(String fxmlPath, String title) {
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setTitle(title);
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            Util.printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        }
    }

    public static Main getInstance() {
        return singleton;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
