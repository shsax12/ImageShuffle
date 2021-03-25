package imageshuffle;

import java.io.File;
import java.io.Serializable;

public class Dataset implements Serializable {
    private File image;
    private String text;
    private String fileName;

    public void setImage(File image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
