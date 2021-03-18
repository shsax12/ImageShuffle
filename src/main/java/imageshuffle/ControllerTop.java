package imageshuffle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.util.*;

public class ControllerTop extends ControllerAbstract {

    @FXML
    public void toInput(ActionEvent event) {
        mainInst.setPage("/input.fxml", "データ入力");
    }

    @FXML
    public void toOutput(ActionEvent event) {
        List<File> list = new ArrayList<>(Arrays.asList(fileList));
        Collections.shuffle(list);
        File[] flist = list.toArray(new File[list.size()]);
        mainInst.setFileList(flist);

        mainInst.setPage("/output.fxml", "テスト");
    }

    @Override
    public void toTop(ActionEvent event) {}
}
