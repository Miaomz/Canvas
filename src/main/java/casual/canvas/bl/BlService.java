package casual.canvas.bl;

import casual.canvas.entity.Shape;
import casual.canvas.util.ResultMessage;

import java.io.File;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public interface BlService {

    ResultMessage savePainting(List<Shape> shapes, String fileName);

    List<Shape> loadPainting(String fileName);

    List<Shape> loadPainting(File file);
}
