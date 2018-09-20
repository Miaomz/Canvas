package casual.canvas.entity;

import casual.canvas.util.Color;
import javafx.scene.canvas.GraphicsContext;
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
        if (lines == null){//it doesn't matter
            lines = new ArrayList<>();
        }

        this.color = color;
        this.lines = new ArrayList<>(lines.size());
        this.lines.addAll(lines);
    }

    public void draw(GraphicsContext context){
        if (lines == null || lines.isEmpty()){//check
            return;
        }

        context.setStroke(color.transform());
        for (Line line : lines) {
            context.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
    }
}
