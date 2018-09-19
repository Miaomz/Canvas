package casual.canvas.data;

import casual.canvas.entity.Shape;
import casual.canvas.util.JsonUtil;
import casual.canvas.util.LoggerUtil;
import casual.canvas.util.PathUtil;
import casual.canvas.util.ResultMessage;
import com.alibaba.fastjson.JSONException;
import weka.classifiers.Classifier;
import weka.core.SerializationHelper;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static casual.canvas.util.ConstantString.EXTENSION;
import static casual.canvas.util.ConstantString.SLASH;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public class DataServiceImpl implements DataService{

    @Override
    public ResultMessage savePainting(List<Shape> shapes, String fileName) {
        if (shapes == null || fileName == null || fileName.isEmpty()){
            return ResultMessage.ARG_ABSENT;
        }
        if (!fileName.endsWith(EXTENSION)){
            return ResultMessage.WRONG_EXT;
        }

        String content = JsonUtil.toJson(shapes);
        String path = PathUtil.getFilePath() + SLASH + fileName;
        return writeFile(path, content);
    }

    @Override
    public List<Shape> loadPainting(String fileName){
        if (fileName == null || fileName.isEmpty()){//preconditions
            return new ArrayList<>();
        }

        String path = PathUtil.getFilePath() + SLASH + fileName;
        String content = readFile(path);
        try {
            List<Shape> shapes = JsonUtil.toArray(content, Shape.class);
            if (shapes == null){//compare to null, empty list is better
                shapes = new ArrayList<>();
            }
            return shapes;
        }catch (JSONException e){
            LoggerUtil.getLogger().warning(e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Shape> loadPainting(File file) {
        if (!file.exists()){
            LoggerUtil.getLogger().info(new IOException("file to be loaded doesn't exist"));
            return new ArrayList<>();
        }

        String content = readFile(file.getPath());
        List<Shape> shapes = JsonUtil.toArray(content, Shape.class);

        if (shapes == null){
            return new ArrayList<>();
        }
        return shapes;
    }

    @Override
    public List<File> getRecentFiles() {
        File file = new File(PathUtil.getFilePath());
        if (file.isDirectory()){
            File[] files = file.listFiles((File dir, String name) -> name.endsWith(EXTENSION));
            if (files == null || files.length == 0){
                return new ArrayList<>();
            }

            List<File> fileList = new ArrayList<>(Arrays.asList(files));
            fileList.sort((File o1, File o2) ->
                    (int)((o1.lastModified() - o2.lastModified()) / Math.abs(o1.lastModified() - o2.lastModified())));
            Collections.reverse(fileList);
            return fileList;

        } else {
            LoggerUtil.getLogger().warning(new IOException("default folder is not directory"));
            return new ArrayList<>();
        }
    }

    @Override
    public ResultMessage saveClassifier(Classifier classifier) {
        try {
            File file = new File(getClass().getResource("/model").getPath() + SLASH + "classifier.model");
            SerializationHelper.write(file.getPath(), classifier);
            return ResultMessage.SUCCESS;
        } catch (Exception e){
            LoggerUtil.getLogger().warning(e);
            return ResultMessage.FAILURE;
        }
    }

    @Override
    public Classifier loadClassifier() {
        try {
            URL url = getClass().getResource("/model/classifier.model");
            if (url == null){//the file doesn't exist in first call
                return null;
            }

            File file = new File(url.toURI());
            return (Classifier) SerializationHelper.read(file.getPath());
        } catch (Exception e){
            LoggerUtil.getLogger().info(e);
            return null;
        }
    }

    private ResultMessage writeFile(String path, String content) {
        File file = new File(path);
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {//if parent file doesn't exist and fail to make dir
            return ResultMessage.FAILURE;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            if (!file.exists() && !file.createNewFile()) {//if file doesn't exist and program fails to create file
                throw new IOException("fail to create file");
            }
            bufferedWriter.write(content);
            bufferedWriter.flush();
            return ResultMessage.SUCCESS;
        } catch (IOException e) {
            LoggerUtil.getLogger().info(e);
            return ResultMessage.FAILURE;
        }
    }

    private String readFile(String path) {
        File file = new File(path);
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line);
            }
        } catch (IOException e) {
            LoggerUtil.getLogger().info(e);
        }
        return new String(content);
    }
}
