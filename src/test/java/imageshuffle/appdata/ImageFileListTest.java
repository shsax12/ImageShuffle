package imageshuffle.appdata;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ImageFileListTest {

    private final ImageFileList imageFiles = new ImageFileList();

    @Test
    public void getImageFileList() {
        List<File> imageFileList = imageFiles.getImageFileList("src/test/resources/image");

        assertThat(imageFileList.size(), is(2));
        assertThat(imageFileList.get(0).getName(), is("coffee.jpg"));
        assertThat(imageFileList.get(1).getName(), is("training.jpg"));
    }
}
