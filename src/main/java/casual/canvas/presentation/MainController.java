package casual.canvas.presentation;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * @author miaomuzhi
 * @since 2018/9/14
 */
public class MainController {
    @FXML
    private Canvas canvas;

    private double startX;
    private double startY;
    private boolean isDrawing = false;

    public void initialize(){
        canvas.getGraphicsContext2D().setStroke(Color.BLACK);
    }

    @FXML
    private void beginDraw(MouseEvent event){
        isDrawing = true;
        startX = event.getX();
        startY = event.getY();
        System.out.println("begin");
    }

    @FXML
    private void draw(MouseEvent event){
        double x = event.getX();
        double y = event.getY();
        if (isDrawing){
            canvas.getGraphicsContext2D().setStroke(Color.BLACK);
            canvas.getGraphicsContext2D().setLineWidth(2);
            canvas.getGraphicsContext2D().strokeLine(startX, startY, x, y);
        }
        startX = x;
        startY = y;

    }

    @FXML
    private void stopDraw(){
        isDrawing = false;
        System.out.println("done");
    }
}
