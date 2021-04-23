package imageshuffle.appdata;

import java.util.List;

public interface DatasetListInterface {
    List<Dataset> readDatasetListFile(String datasetListFilePath);
    void writeDatasetListFile(List<Dataset> datasetlist, String datasetListFilePath);
    int datasetRegistered(String imgFileName, List<Dataset> datasetList);
}
