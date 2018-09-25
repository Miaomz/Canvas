package casual.canvas.entity;

import casual.canvas.util.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author miaomuzhi
 * @since 2018/9/14
 */
@NoArgsConstructor
@Getter
@Setter
public class Rectangle extends Shape{

    public Rectangle(Shape shape){
        super(Color.RED, shape.getLines());
    }

}
