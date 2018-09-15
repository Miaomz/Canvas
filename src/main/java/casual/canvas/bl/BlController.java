package casual.canvas.bl;

import casual.canvas.data.DataService;
import casual.canvas.entity.Shape;
import casual.canvas.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public class BlController implements BlService {

    private DataService dataService;

    @Override
    public ResultMessage savePainting(List<Shape> shapes, String fileName) {
        return dataService.savePainting(shapes, fileName);
    }

    @Override
    public List<Shape> loadPainting(String fileName) {
        return dataService.loadPainting(fileName);
    }
}
