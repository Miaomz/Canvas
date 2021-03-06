package casual.canvas.data;

import casual.canvas.entity.Shape;
import casual.canvas.util.ResultMessage;
import weka.classifiers.Classifier;

import java.io.File;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
public interface DataService {

    ResultMessage savePainting(List<Shape> shapes, String fileName);

    List<Shape> loadPainting(String fileName);

    List<Shape> loadPainting(File file);

    List<File> getRecentFiles();

    /**
     * save a built classifier
     * @param classifier built classifier
     * @return result message
     */
    ResultMessage saveClassifier(Classifier classifier);

    /**
     * load saved classifier
     * @return classifier
     */
    Classifier loadClassifier();
}
