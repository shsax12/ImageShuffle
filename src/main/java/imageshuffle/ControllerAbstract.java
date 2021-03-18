package imageshuffle;

import java.io.File;
import java.util.List;

public abstract class ControllerAbstract implements Controller {

    protected Main mainInst;
    protected List<Dataset> dataList;
    protected File[] fileList;

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
        for(Dataset data: dataList) {
            if(data.fileName.equals(filename)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
