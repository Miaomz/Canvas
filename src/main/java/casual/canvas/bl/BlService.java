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

    /**
     * save painting
     * @param shapes list of shapes, could be null or empty
     * @param fileName name of file, including extension
     * @return SUCCESS if it saves successfully;
     * FAILURE if it fail to write file or file's data has broken;
     * ARG_ABSENT if the shape or fileName is null or empty
     * WRONG_EXT if extension of fileName isn't '.mcv'
     */
    ResultMessage savePainting(List<Shape> shapes, String fileName);

    /**
     * load painting
     * @param fileName file name, including extension
     * @return all shapes stored, null if it fails to load
     */
    List<Shape> loadPainting(String fileName);

    /**
     * load painting
     * @param file File object
     * @return all shapes stored, null if it fails to load
     */
    List<Shape> loadPainting(File file);

    /**
     * get recent files in the default dir
     * @return recent files, decreasing by date
     */
    List<File> getRecentFiles();

    /**
     * recognize single shape
     * @param shape Shape instance
     * @return  which subclass type should it be turned into
     */
    Class recognizeShape(Shape shape);
}
