package imageshuffle.appdata;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DatasetListTest {

    private final DatasetList datasets = new DatasetList();

    @Test
    public void readDatasetListFile() {
        List<Dataset> datasetList = datasets.readDatasetListFile(
                DatasetListTest.class.getClassLoader().getResource("datasetlist.obj").getPath());

        assertThat(datasetList.get(0).getFileName(), is("coffee.jpg"));
        assertThat(datasetList.get(0).getText(), is("coffee"));
        assertThat(datasetList.get(1).getFileName(), is("training.jpg"));
        assertThat(datasetList.get(1).getText(), is("training"));
    }

    @Test
    public void writeDatasetListFile() throws Exception {
        Dataset dataset = new Dataset();
        dataset.setImage(new File(new URI(DatasetListTest.class.getClassLoader()
                .getResource("image/coffee.jpg").toString())));
        dataset.setText("coffee");
        dataset.setFileName("image/coffee.jpg");

        List<Dataset> datasetList = new ArrayList<>();
        datasetList.add(dataset);

        String datasetListFilePath = System.getProperty("user.home") + "/image-shuffle/test/datasetlist.obj";
        datasets.writeDatasetListFile(datasetList, datasetListFilePath);

        File outFile = new File(datasetListFilePath);
        assertThat(outFile.exists(), is(true));
    }

    @Test
    public void datasetRegistered() {
        List<Dataset> datasetList = datasets.readDatasetListFile(
                DatasetListTest.class.getClassLoader().getResource("datasetlist.obj").getPath());

        assertThat(datasets.datasetRegistered("coffee.jpg", datasetList), is(0));
        assertThat(datasets.datasetRegistered("family.jpg", datasetList), is(-1));
    }
}