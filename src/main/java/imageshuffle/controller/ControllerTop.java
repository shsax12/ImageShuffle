package imageshuffle.controller;

import imageshuffle.Main;
import imageshuffle.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerTop  implements Initializable {

    static String genreChoice;

    private Main mainInst = Main.getInstance();

    @FXML
    private Label genrePrint;

    @Override
    public void initialize(URL url, ResourceBundle resourcebundle) {
        if (isGenreChoice()) {
            genrePrint.setText(genreChoice);
        } else {
            genrePrint.setText("未選択");
        }
    }

    @FXML
    public void selectGenre(ActionEvent event) {

        File dirFile = new File(System.getProperty("user.home") + "/image-shuffle");
        File dirFileArray[] = dirFile.listFiles();
        List<String> dirNameList = Arrays.stream(dirFileArray)
                .filter(File::isDirectory)
                .map(File::getName)
                .filter(dirName -> !dirName.equals("test") && !dirName.equals("logs"))
                .sorted(String::compareTo)
                .collect(Collectors.toList());

        if (dirNameList.size() == 0) {
            Util.printDialog("warning", "対象分野のディレクトリを作成してください。");
            return;
        }

        ChoiceDialog choiceDialog = new ChoiceDialog(dirNameList.get(0), dirNameList);
        choiceDialog.setHeaderText(null);
        Optional choice = choiceDialog.showAndWait();
        if (choice.isPresent()) {
            genreChoice = (String)choice.get();
            genrePrint.setText(genreChoice);
        }
    }

    @FXML
    public void toInput(ActionEvent event) {
        if (!isGenreChoice()) {
            Util.printDialog("warning", "対象分野が未選択です。");
            return;
        }

        mainInst.setPage("/fxml/input.fxml", "データ入力");
    }

    @FXML
    public void toOutput(ActionEvent event) {
        if (!isGenreChoice()) {
            Util.printDialog("warning", "対象分野が未選択です。");
            return;
        }

        mainInst.setPage("/fxml/output.fxml", "テスト");
    }

    private boolean isGenreChoice() {
        if (Objects.isNull(genreChoice)) {
            return false;
        } else {
            return true;
        }
    }
}
