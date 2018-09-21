package casual.canvas.presentation.mainui;

import casual.canvas.entity.*;
import casual.canvas.util.LoggerUtil;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * To draw the shapes, including Shape's subclass
 * @author miaomuzhi
 * @since 2018/9/20
 */
public class ShapeDrawer {

    //state
    private boolean isRecognizing = true;

    /**
     * user changed the mode
     */
    public void changeState(boolean isRecognizing){
        this.isRecognizing = isRecognizing;
    }

    /**
     * draw shape according to the state of this Object
     * @param context graphic context of canvas
     * @param shape shape to be drawn
     */
    public void drawShape(GraphicsContext context, Shape shape){
        if (!isRecognizing){
            drawAsShape(context, shape);
            return;
        }

        Class c = shape.getClass();
        if (c.equals(Shape.class)){
            drawAsShape(context, shape);
        } else if (c.equals(Circle.class)){
            drawAsCircle(context, shape);
        } else if (c.equals(Triangle.class)){
            drawAsTriangle(context, shape);
        } else if (c.equals(Rectangle.class)){
            drawAsRectangle(context, shape);
        } else {
            LoggerUtil.getLogger().warning(new Exception("an unknown type"));
        }

        context.setStroke(Color.BLACK);//change back to Black
    }


    private void drawAsShape(GraphicsContext context, Shape shape){
        List<Line> lines = shape.getLines();
        if (lines == null || lines.isEmpty()){//check
            return;
        }

        context.setStroke(Color.BLACK);
        for (Line line : lines) {
            context.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
    }

    private void drawAsCircle(GraphicsContext context, Shape shape){
        if (!shape.getClass().equals(Circle.class)){
            return;
        }

        Circle circle = (Circle) shape;
        context.setStroke(shape.getColor().transform());
        double radius = circle.getRadius();
        //stroke oval
        context.strokeOval(circle.getCenterX()-radius, circle.getCenterY()-radius, radius*2, radius*2);
    }

    private void drawAsTriangle(GraphicsContext context, Shape shape){
        if (!shape.getClass().equals(Triangle.class)){
            return;
        }

        context.setStroke(shape.getColor().transform());
        shape.getLines().forEach(line -> context.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));
    }

    private void drawAsRectangle(GraphicsContext context, Shape shape){
        if (!shape.getClass().equals(Rectangle.class)){
            return;
        }

        context.setStroke(shape.getColor().transform());
        shape.getLines().forEach(line -> context.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY()));
    }
}
