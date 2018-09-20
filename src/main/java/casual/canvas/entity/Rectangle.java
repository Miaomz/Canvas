package casual.canvas.entity;

import casual.canvas.util.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author miaomuzhi
 * @since 2018/9/14
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rectangle extends Shape{

    protected Line diagonal;

    public Rectangle(Shape shape){
        super(Color.RED, shape.getLines());
    }

}
