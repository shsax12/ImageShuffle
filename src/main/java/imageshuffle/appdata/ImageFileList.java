package imageshuffle.appdata;

import imageshuffle.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 入力画像ファイルを扱うクラス
 */
public class ImageFileList implements ImageFileListInterface {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<File> getImageFileList(String imgDirPath) {
        File imgDir = new File(imgDirPath);
        File[] imageFileArray = imgDir.listFiles(filter);

        //画像のディレクトリやファイルがなければ強制終了
        if (!imgDir.exists() || imageFileArray.length == 0) {
            Util.printDialog("error",
                    "imageファイルを正しく配置してください。\n " + "/user-home/image-shuffle/image");
            logger.error("image directory or file is not found.");
            System.exit(0);
        }

        List<File> imageFileList = new ArrayList<>(Arrays.asList(imageFileArray));

        return imageFileList;
    }

    FilenameFilter filter = (dir, name) ->
            name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png");
}
