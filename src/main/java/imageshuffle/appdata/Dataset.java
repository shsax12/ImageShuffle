package imageshuffle.appdata;

import java.io.File;
import java.io.Serializable;

/**
 * 画像とテキストの対応を持つクラス
 */
public class Dataset implements Serializable {
    private File image; //画像ファイル
    private String text; //対応するテキスト
    private String fileName; //画像ファイル名

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
