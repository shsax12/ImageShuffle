package imageshuffle.util;

import javafx.scene.control.Alert;

public class Util {

    /**
     * アラートのダイアログを表示する
     * @param alertType アラートレベル
     * @param message 表示するメッセージ
     */
    public static void printDialog(String alertType, String message) {
        Alert dialog;

        switch (alertType) {
            case "information":
                dialog = new Alert(Alert.AlertType.INFORMATION);
                break;
            case "warning":
                dialog = new Alert(Alert.AlertType.WARNING);
                break;
            case "error":
                dialog = new Alert(Alert.AlertType.ERROR);
                break;
            default:
                dialog = new Alert(Alert.AlertType.INFORMATION);
        }

        dialog.setHeaderText(null);
        dialog.setContentText(message);
        dialog.showAndWait();
    }
}
