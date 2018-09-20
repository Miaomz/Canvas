package casual.canvas.presentation.mainui;

import casual.canvas.entity.*;
import casual.canvas.util.LoggerUtil;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/20
 */
public class ShapeDrawer {

    //state
    private boolean isRecognizing = true;

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

        context.setStroke(shape.getColor().transform());
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
        context.strokeOval(circle.getCenterX()-radius, circle.getCenterY()-radius, radius, radius);
    }

    private void drawAsTriangle(GraphicsContext context, Shape shape){
        if (!shape.getClass().equals(Triangle.class)){
            return;
        }

        drawAsShape(context, shape);
    }

    private void drawAsRectangle(GraphicsContext context, Shape shape){
        if (!shape.getClass().equals(Rectangle.class)){
            return;
        }

        drawAsShape(context, shape);
    }
}
