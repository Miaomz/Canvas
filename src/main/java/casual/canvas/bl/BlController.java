package casual.canvas.bl;

import casual.canvas.data.DataFactory;
import casual.canvas.data.DataService;
import casual.canvas.entity.Shape;
import casual.canvas.util.ResultMessage;

import java.io.File;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public class BlController implements BlService {

    private DataService dataService = DataFactory.getDataFactory().getDataService();
    private Recognizer recognizer = new Recognizer();

    @Override
    public ResultMessage savePainting(List<Shape> shapes, String fileName) {
        return dataService.savePainting(shapes, fileName);
    }

    @Override
    public List<Shape> loadPainting(String fileName) {
        return dataService.loadPainting(fileName);
    }

    @Override
    public List<Shape> loadPainting(File file) {
        return dataService.loadPainting(file);
    }

    @Override
    public List<File> getRecentFiles() {
        return dataService.getRecentFiles();
    }

    @Override
    public List<Shape> recognizeShapes(List<Shape> shapes) {
        return recognizer.recognizeShapes(shapes);
    }

    @Override
    public Class recognizeShape(Shape shape) {
        return recognizer.recognizeShape(shape);
    }
}
