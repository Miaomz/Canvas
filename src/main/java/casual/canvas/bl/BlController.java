package casual.canvas.bl;

import casual.canvas.entity.Shape;
import casual.canvas.util.ResultMessage;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public class BlController implements BlService {

    private static BlController blController;

    private BlController(){}

    public static BlController getBlController() {
        if (blController == null){
            blController = new BlController();
        }
        return blController;
    }

    @Override
    public ResultMessage savePainting(List<Shape> shapes, String fileName) {
        return null;
    }

    @Override
    public List<Shape> loadPainting(String fileName) {
        return null;
    }
}
