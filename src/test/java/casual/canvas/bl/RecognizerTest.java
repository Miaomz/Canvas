package casual.canvas.bl;

import casual.canvas.data.DataService;
import casual.canvas.data.DataServiceImpl;
import casual.canvas.entity.Line;
import casual.canvas.entity.Shape;
import casual.canvas.util.Color;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author miaomuzhi
 * @since 2018/9/18
 */
public class RecognizerTest {

    private Recognizer recognizer;
    private DataService dataService;

    @Before
    public void setup(){
        recognizer = new Recognizer();
        dataService = new DataServiceImpl();
    }

    @Test
    public void getImageFromShapeTest() throws Exception{
        File file = new File(getClass().getResource("/another.mcv").toURI());
        List<Shape> shapes = dataService.loadPainting(file);
        shapes.forEach(shape -> recognizer.getImageFromShape(shape));
    }

    @Test
    public void getImageFromShapeTest1() throws Exception{
        File file = new File(getClass().getResource("/demo.mcv").toURI());
        List<Shape> shapes = dataService.loadPainting(file);
        int[][] image = recognizer.getImageFromShape(shapes.get(0));
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                if (image[i][j] == 1){
                    System.out.print("0");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void normalizeLinesTest() throws Exception{
        Method method = Recognizer.class.getDeclaredMethod("normalizeLines", Shape.class);
        method.setAccessible(true);
        File file = new File(getClass().getResource("/demo.mcv").toURI());
        List<Shape> shapes = dataService.loadPainting(file);
        List<Line> lines = (List<Line>)method.invoke(recognizer, shapes.get(0));

        lines.forEach(line -> {
            line.setStartX(line.getStartX()*100);
            line.setEndX(line.getEndX()*100);
            line.setStartY(line.getStartY()*100);
            line.setEndY(line.getEndY()*100);
        });
        List<Shape> result = new ArrayList<>(Collections.singleton(new Shape(Color.BLACK, lines)));
        dataService.savePainting(result, "result.mcv");
    }
}