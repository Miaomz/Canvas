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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Triangle extends Shape{

    protected Line lineOne;

    protected Line lineTwo;

    protected Line lineThree;

    public Triangle(Shape shape){
        super(Color.GREEN, shape.getLines());

        //TODO
    }
}
