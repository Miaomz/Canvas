package casual.canvas.entity;

import casual.canvas.util.Color;
import javafx.scene.canvas.GraphicsContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * blue indicates the Circle
 * @author miaomuzhi
 * @since 2018/9/14
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Circle extends Shape {

    protected double centerX;

    protected double centerY;

    protected double radius;

    public Circle(Shape s){
        super(Color.BLUE, s.getLines());

        double sumX = 0;
        double sumY = 0;
        for (Line line : lines) {//only record the start point
            sumX += line.getStartX();
            sumY += line.getStartY();
        }
        centerX = sumX / lines.size();
        centerY = sumY / lines.size();
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setStroke(color.transform());
        context.strokeOval(centerX-radius, centerY-radius, radius, radius);
    }
}
