package casual.canvas.presentation;

import casual.canvas.bl.BlFactory;
import casual.canvas.bl.BlService;
import casual.canvas.entity.Line;
import casual.canvas.entity.Shape;
import casual.canvas.util.Color;
import casual.canvas.util.LoggerUtil;
import casual.canvas.util.PathUtil;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/14
 */
public class MainController {

    private DisplayedData displayedData = DisplayedData.getInstance();
    private BlService blService = BlFactory.getInstance().getBlService();

    private Drawer drawer = new Drawer();

    @FXML
    private Canvas canvas;


    /**
     * initialize function called by javafx
     */
    public void initialize(){
        canvas.getGraphicsContext2D().setStroke(Color.BLACK.transform());
        canvas.getGraphicsContext2D().setLineWidth(1);
        displayedData.getDisplayedShapes().addListener(new ListChangeListener<Shape>() {
            @Override
            public void onChanged(Change<? extends Shape> c) {
                sync();
            }
        });
    }

    /**
     * synchronize the canvas with displayed data
     */
    private void sync(){
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());//clear the canvas first

        ObservableList<Shape> shapes = displayedData.getDisplayedShapes();
        if (shapes == null || shapes.isEmpty()){
            return;
        }

        GraphicsContext context = canvas.getGraphicsContext2D();
        for (Shape shape : shapes) {
            if (shape.getLines() == null || shape.getLines().isEmpty()){//check
                continue;
            }

            context.setStroke(shape.getColor().transform());
            for (Line line : shape.getLines()) {
                context.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            }
        }
    }

    @FXML
    private void beginDraw(MouseEvent event){
        drawer.beginDraw(event);
    }

    @FXML
    private void draw(MouseEvent event){
        Line line = drawer.draw(event);
        if (line != null){
            canvas.getGraphicsContext2D().strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
    }

    @FXML
    private void stopDraw(){
        displayedData.getDisplayedShapes().add(drawer.stopDraw());
    }

    @FXML
    private void open(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("MyCanvas Files","*.mcv"));//mcv is ext of this app
        fileChooser.setInitialDirectory(new File(PathUtil.getFilePath()));
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());

        if (file != null){
            List<Shape> shapes = blService.loadPainting(file.getName());
            displayedData.getDisplayedShapes().clear();
            displayedData.getDisplayedShapes().addAll(FXCollections.observableArrayList(shapes));
            displayedData.setFileName(file.getName());
        }
    }

    @FXML
    private void save(){
        if (displayedData == null || displayedData.getFileName() == null || displayedData.getFileName().isEmpty()){
            LoggerUtil.getLogger().info(new Exception("no certain file to be saved"));
            return;
        }

        ObservableList<Shape> displayedShapes = displayedData.getDisplayedShapes();
        List<Shape> shapes = new ArrayList<>(displayedShapes);
        blService.savePainting(shapes, displayedData.getFileName());
    }

    @FXML
    private void saveAs(){
        //TODO
    }

    @FXML
    private void create(){

    }

    @FXML
    private void revert(){
        displayedData.getDisplayedShapes().clear();
    }

    @FXML
    private void quit(){
        System.exit(0);
    }
}
