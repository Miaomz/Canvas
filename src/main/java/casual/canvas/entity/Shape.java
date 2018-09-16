package casual.canvas.entity;

import casual.canvas.util.Color;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/13
 */
@Data
@NoArgsConstructor
public class Shape {

    protected Color color = Color.BLACK;//default

    protected List<Line> lines;

    //deep copying
    public Shape(Color color, List<Line> lines){
        this.color = color;
        this.lines = new ArrayList<>(lines.size());
        this.lines.addAll(lines);
    }

}
