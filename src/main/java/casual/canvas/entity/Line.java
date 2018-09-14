package casual.canvas.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author miaomuzhi
 * @since 2018/9/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Line {

    private double startX;

    private double startY;

    private double endX;

    private double endY;

}
