package casual.canvas.entity;

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

}
