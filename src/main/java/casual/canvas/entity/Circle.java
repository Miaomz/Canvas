package casual.canvas.entity;

import lombok.*;

/**
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
        super(s.getLines());
        double sumX = 0;
        double sumY = 0;
        for (Line line : lines) {//only record the start point
            sumX += line.getStartX();
            sumY += line.getStartY();
        }
        centerX = sumX / lines.size();
        centerY = sumY / lines.size();
    }
}
