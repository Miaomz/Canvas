package casual.canvas.data;

import casual.canvas.entity.Shape;
import casual.canvas.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public interface DataService {

    ResultMessage savePainting(List<Shape> shapes, String fileName);

    List<Shape> loadPainting(String fileName);
}
