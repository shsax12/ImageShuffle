package imageshuffle;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MainTest {

    private final Main target = new Main();

    @Test
    public void dataRead() throws Exception {
        List<Dataset> dataList = testDataListRead(target);

        assertThat(dataList.get(0).getFileName(), is("coffee.jpg"));
        assertThat(dataList.get(0).getText(), is("coffee"));
        assertThat(dataList.get(1).getFileName(), is("training.jpg"));
        assertThat(dataList.get(1).getText(), is("training"));
    }

    /**
     * テスト用のdatalistファイルを読み込む
     * @param target Mainクラスのインスタンス
     * @return Dataset型のList
     * @throws Exception
     */
    private List<Dataset> testDataListRead(Main target) throws Exception {
        //privateメソッド（dataRead）を呼び出す
        Method method = Main.class.getDeclaredMethod("dataRead", String.class);
        method.setAccessible(true);
        List<Dataset> dataList = (List<Dataset>)method.invoke(target,
                MainTest.class.getClassLoader().getResource("datalist.obj").getPath());

        return dataList;
    }

    @Test
    public void dataWrite() throws Exception {
        Dataset dataset = new Dataset();
        dataset.setImage(new File(new URI(MainTest.class.getClassLoader()
                .getResource("coffee.jpg").toString())));
        dataset.setText("coffee");
        dataset.setFileName("coffee.jpg");

        List<Dataset> dataList = new ArrayList<>();
        dataList.add(dataset);

        String filePath = System.getProperty("user.home") + "/image-shuffle/datalist_test.obj";
        target.dataWrite(dataList, filePath);

        File file = new File(filePath);
        assertThat(file.exists(), is(true));
    }

    @Test
    public void registered() throws Exception {
        List<Dataset> dataList = testDataListRead(target);

        assertThat(target.registered("coffee.jpg", dataList), is(0));
        assertThat(target.registered("family.jpg", dataList), is(-1));
    }
}