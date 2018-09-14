package casual.canvas.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shape {

    protected List<Line> lines;

}
