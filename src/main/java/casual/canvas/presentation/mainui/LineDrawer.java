package casual.canvas.presentation.mainui;

import casual.canvas.entity.Line;
import casual.canvas.entity.Shape;
import casual.canvas.util.Color;
import casual.canvas.util.LoggerUtil;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * To implement drawing function
 * lines are only temporarily drawn, and will be removed after the shape is made
 * @author miaomuzhi
 * @since 2018/9/16
 */
class LineDrawer {
    //data to draw a line
    private double startX;
    private double startY;

    //data to draw a shape
    private boolean isDrawing = false;
    private List<Line> linesCache = new ArrayList<>(100);//average capacity

    void beginDraw(MouseEvent event){
        isDrawing = true;
        startX = event.getX();
        startY = event.getY();
    }

    Line draw(MouseEvent event){
        if (!isDrawing){
            LoggerUtil.getLogger().warning(new Exception("draw when it is not drawing"));
            return null;
        }

        //get end point's position
        double x = event.getX();
        double y = event.getY();

        Line line = new Line(startX, startY, x, y);
        linesCache.add(line);
        startX = x;
        startY = y;
        return line;
    }

    Shape stopDraw(){
        Shape shape = new Shape(Color.BLACK, linesCache);
        linesCache.clear();
        isDrawing = false;
        return shape;
    }
}
