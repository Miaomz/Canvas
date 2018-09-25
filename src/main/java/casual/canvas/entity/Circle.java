package casual.canvas.entity;

import casual.canvas.util.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

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

        double distSum = 0;
        for (Line line: lines){
            distSum += sqrt(pow(line.getStartX()-centerX, 2) + pow(line.getStartY()-centerY, 2));
        }
        radius = distSum / lines.size();
    }


}
