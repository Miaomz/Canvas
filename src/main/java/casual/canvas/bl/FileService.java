package casual.canvas.bl;

import casual.canvas.entity.Shape;
import casual.canvas.util.JsonUtil;
import casual.canvas.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public class FileService {

    public ResultMessage savePainting(List<Shape> shapes, String fileName) {
        String content = JsonUtil.toJson(shapes);

        return null;
    }

    public List<Shape> loadPainting(String fileName) {
        return null;
    }
}
