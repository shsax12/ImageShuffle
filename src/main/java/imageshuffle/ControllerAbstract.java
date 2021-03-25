package imageshuffle;

import javafx.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public abstract class ControllerAbstract {

    protected Main mainInst;
    protected List<Dataset> dataList;
    protected File[] fileList;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public ControllerAbstract() {
        mainInst = Main.getInstance();
        dataList = mainInst.getDataList();
        fileList = mainInst.getFileList();
    }

    protected void setTopPage() {
        mainInst.setPage("/top.fxml", "トップページ");
    }

    protected int registered(String filename) {
        int i = 0;
        for (Dataset data : dataList) {
            if (data.getFileName().equals(filename)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public abstract void toTop(ActionEvent event);
}
