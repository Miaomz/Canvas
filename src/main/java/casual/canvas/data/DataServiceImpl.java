package casual.canvas.data;

import casual.canvas.entity.Shape;
import casual.canvas.util.JsonUtil;
import casual.canvas.util.LoggerUtil;
import casual.canvas.util.PathUtil;
import casual.canvas.util.ResultMessage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static casual.canvas.util.ConstantString.SLASH;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public class DataServiceImpl implements DataService{

    @Override
    public ResultMessage savePainting(List<Shape> shapes, String fileName) {
        if (shapes == null){
            return ResultMessage.FAILURE;
        }
        if (fileName == null || fileName.isEmpty()){
            return ResultMessage.FAILURE;
        }

        String content = JsonUtil.toJson(shapes);
        String path = PathUtil.getFilePath() + SLASH + fileName;
        return writeFile(path, content);
    }

    @Override
    public List<Shape> loadPainting(String fileName) {
        if (fileName == null || fileName.isEmpty()){
            return new ArrayList<>();
        }

        String path = PathUtil.getFilePath() + SLASH + fileName;
        String content = readFile(path);
        List<Shape> shapes = JsonUtil.toArray(content, Shape.class);
        return shapes;
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
