package casual.canvas.entity;

import casual.canvas.util.Color;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author miaomuzhi
 * @since 2018/9/14
 */
@Getter
@Setter
@NoArgsConstructor
public class Triangle extends Shape{

    public Triangle(Shape shape){
        super(Color.GREEN, shape.getLines());
    }

}
