package casual.canvas.entity;

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
public class Triangle {

    protected Line lineOne;

    protected Line lineTwo;

    protected Line lineThree;
}
