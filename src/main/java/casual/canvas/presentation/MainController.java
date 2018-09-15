package casual.canvas.presentation;

import casual.canvas.bl.BlFactory;
import casual.canvas.bl.BlService;
import casual.canvas.entity.Line;
import casual.canvas.entity.Shape;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/14
 */
public class MainController {

    private DisplayedData displayedData = DisplayedData.getInstance();
    private BlService blService = BlFactory.getInstance().getBlService();

    @FXML
    private Canvas canvas;

    //data to draw line
    private double startX;
    private double startY;

    //data to draw shape
    private boolean isDrawing = false;
    private List<Line> linesCache = new ArrayList<>(100);//average capacity

    public void initialize(){
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        canvas.getGraphicsContext2D().setLineWidth(1);
    }

    @FXML
    private void beginDraw(MouseEvent event){
        isDrawing = true;
        startX = event.getX();
        startY = event.getY();
    }

    @FXML
    private void draw(MouseEvent event){
        double x = event.getX();
        double y = event.getY();
        if (isDrawing){
            canvas.getGraphicsContext2D().strokeLine(startX, startY, x, y);
            linesCache.add(new Line(startX, startY, x, y));
        }
        startX = x;
        startY = y;
    }

    @FXML
    private void stopDraw(){
        displayedData.getDisplayedLines().add(new Shape(Color.BLACK, linesCache));
        linesCache.clear();
        isDrawing = false;
    }

    @FXML
    private void save(){
        //TODO
        blService.savePainting(null, null);
    }

    @FXML
    private void revert(){
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    private void quit(){
        System.exit(0);
    }
}
