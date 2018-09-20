package casual.canvas.bl;

import casual.canvas.data.DataService;
import casual.canvas.data.DataServiceImpl;
import casual.canvas.entity.*;
import casual.canvas.util.Color;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


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
        File file = new File(getClass().getResource("/third.mcv").toURI());
        List<Shape> shapes = dataService.loadPainting(file);
        int[][] image = recognizer.getImageFromShape(shapes.get(0));
        for (int[] ints : image) {
            for (int anInt : ints) {
                System.out.print(anInt==1 ? "0" : " ");
            }
            System.out.println();
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void normalizeLinesTest() throws Exception{
        Method method = Recognizer.class.getDeclaredMethod("normalizeLines", Shape.class);
        method.setAccessible(true);
        File file = new File(getClass().getResource("/third.mcv").toURI());
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

    @Test
    public void trainTest() throws Exception{
        Method method = Recognizer.class.getDeclaredMethod("train");
        method.setAccessible(true);
        method.invoke(recognizer);
    }

    @Test
    public void recognizeShapeTest() throws Exception{
        File file = new File(getClass().getResource("/test.mcv").toURI());
        List<Shape> shapes = dataService.loadPainting(file);
        assertEquals(Triangle.class, recognizer.recognizeShape(shapes.get(0)));
    }

    @Test
    public void recognizeShapeTest2() throws Exception{
        File file = new File(getClass().getResource("/test2.mcv").toURI());
        List<Shape> shapes = dataService.loadPainting(file);
        assertEquals(Circle.class, recognizer.recognizeShape(shapes.get(0)));
    }

    @Test
    public void recognizeShapeTest3() throws Exception{
        File file = new File(getClass().getResource("/Rectangle.mcv").toURI());
        List<Shape> shapes = dataService.loadPainting(file);
        assertEquals(Rectangle.class, recognizer.recognizeShape(shapes.get(0)));
    }
}