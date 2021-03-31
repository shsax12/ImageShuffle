package imageshuffle;

import javafx.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Objects;

public abstract class ControllerAbstract {

    protected Main mainInst;
    protected List<Dataset> dataList;
    protected File[] fileList;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public ControllerAbstract() {
        mainInst = Main.getInstance();

        if (Objects.nonNull(mainInst)) {
            dataList = mainInst.getDataList();
            fileList = mainInst.getFileList();
        }
    }

    protected void setTopPage() {
        mainInst.setPage("/top.fxml", "トップページ");
    }

    public abstract void toTop(ActionEvent event);
}
