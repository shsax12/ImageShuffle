package imageshuffle.appdata;

import imageshuffle.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 画像とテキストのデータリストを扱うクラス
 */
public class DatasetList implements DatasetListInterface {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 既存のdatasetlistファイルを読み込む
     * @param datasetListFilePath datasetlistファイルのパス
     * @return Dataset型のList
     */
    @Override
    public List<Dataset> readDatasetListFile(String datasetListFilePath) {
        List<Dataset> datasetList = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datasetListFilePath))) {
            datasetList = (List<Dataset>) ois.readObject();
        } catch (FileNotFoundException ex) {
            Util.printDialog("information", "登録済みデータはありません。");
            logger.info("datasetlist file not found.");
        } catch (IOException ex) {
            Util.printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        } catch (ClassNotFoundException ex) {
            Util.printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        }

        return datasetList;
    }

    /**
     * datasetlistをファイルに出力する
     * @param datasetlist 出力するdatasetlist
     * @param datasetListFilePath 出力するファイルパス
     */
    @Override
    public void writeDatasetListFile(List<Dataset> datasetlist, String datasetListFilePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(datasetListFilePath))) {
            oos.writeObject(datasetlist);
        } catch (IOException ex) {
            Util.printDialog("error", "予期しないエラーが発生しました。");
            logger.error("unexpected error.", ex);
        }
    }

    /**
     * 画像ファイルが既存のdatasetlistに含まれているかを確認する
     * @param imgFileName 画像ファイル名
     * @param datasetList 既存のdatasetlist
     * @return 含まれている場合のdatasetlistのindex
     */
    @Override
    public int datasetRegistered(String imgFileName, List<Dataset> datasetList) {
        int i = 0;
        for (Dataset dataset : datasetList) {
            if (dataset.getFileName().equals(imgFileName)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
