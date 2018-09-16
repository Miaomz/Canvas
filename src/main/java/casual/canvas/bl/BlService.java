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
     *
     * @param fileName
     * @return
     */
    List<Shape> loadPainting(String fileName);

    /**
     *
     * @param file
     * @return
     */
    List<Shape> loadPainting(File file);
}
