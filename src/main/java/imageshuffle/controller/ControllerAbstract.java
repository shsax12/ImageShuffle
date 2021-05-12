package imageshuffle.controller;

import imageshuffle.Main;
import imageshuffle.appdata.*;
import javafx.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public abstract class ControllerAbstract {

    protected DatasetListInterface datasets = new DatasetList();
    protected ImageFileListInterface imageFiles = new ImageFileList();

    protected List<Dataset> datasetList;
    protected List<File> imageFileList;

    private final String HOME = System.getProperty("user.home");

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public ControllerAbstract() {
        //genreChoiceがnullでないことは確認済み
        datasetList = datasets.readDatasetListFile(
                HOME + "/image-shuffle/" + ControllerTop.genreChoice +  "/datasetlist.obj");
        imageFileList = imageFiles.getImageFileList(
                HOME + "/image-shuffle/" + ControllerTop.genreChoice);
    }

    protected void setTopPage() {
        Main mainInst = Main.getInstance();
        mainInst.setPage("/fxml/top.fxml", "トップページ");
    }

    public abstract void toTop(ActionEvent event);
}
