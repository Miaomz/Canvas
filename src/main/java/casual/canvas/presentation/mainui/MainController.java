package casual.canvas.presentation.mainui;

import casual.canvas.bl.BlFactory;
import casual.canvas.bl.BlService;
import casual.canvas.entity.Line;
import casual.canvas.entity.Shape;
import casual.canvas.presentation.fileui.MakerController;
import casual.canvas.presentation.fileui.SaverController;
import casual.canvas.presentation.preferenceui.PreferenceController;
import casual.canvas.presentation.utilui.Popup;
import casual.canvas.util.Color;
import casual.canvas.util.LoggerUtil;
import casual.canvas.util.PathUtil;
import casual.canvas.util.ResultMessage;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


/**
 * To bind the code with fxml
 * main controller contains a lot of instances and actions, but most responsibilities will be assigned to other objects
 * @author miaomuzhi
 * @since 2018/9/14
 */
public class MainController {

    private final DisplayedData displayedData = DisplayedData.getInstance();
    private final BlService blService = BlFactory.getInstance().getBlService();

    //objects to which MainController will assign responsibilities to
    private final LineDrawer lineDrawer = new LineDrawer();
    private final ShapeDrawer shapeDrawer = new ShapeDrawer();
    private Mediator mediator;//will be build with all-arg constructor
    private final Command command = new Command();

    private double strokeWidth = 1;//default value

    @FXML
    private Canvas canvas;

    @FXML
    private Menu openRecent;

    @FXML
    private CheckMenuItem transformItem;

    //these menu items will be managed by other classes
    @FXML
    private MenuItem createItem;
    @FXML
    private MenuItem openItem;
    @FXML
    private MenuItem saveItem;
    @FXML
    private MenuItem saveAsItem;
    @FXML
    private MenuItem revertItem;
    @FXML
    private MenuItem undoItem;
    @FXML
    private MenuItem redoItem;

    /**
     * initialize function called by javafx
     */
    public void initialize(){
        canvas.getGraphicsContext2D().setStroke(Color.BLACK.transform());
        canvas.getGraphicsContext2D().setLineWidth(strokeWidth);

        displayedData.getDisplayedShapes().addListener((ListChangeListener.Change<? extends Shape> c) -> {
            sync();
            mediator.canvasChanged();
        });

        mediator = new Mediator(canvas, displayedData, command, createItem, openItem, saveItem, saveAsItem, revertItem, undoItem, redoItem);
    }

    /**
     * synchronize the canvas with displayed data
     * be only method to draw a shape(not including line)
     */
    private void sync(){
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());//clear the canvas first

        ObservableList<Shape> shapes = displayedData.getDisplayedShapes();
        if (shapes == null || shapes.isEmpty()){
            return;
        }

        GraphicsContext context = canvas.getGraphicsContext2D();
        for (Shape shape : shapes) {//draw shape
            shapeDrawer.drawShape(context, shape);
        }
    }

    /**
     * begin drawing
     * @param event press event
     */
    @FXML
    private void beginDraw(MouseEvent event){
        lineDrawer.beginDraw(event);
    }

    /**
     * drawing
     * @param event dragged event
     */
    @FXML
    private void draw(MouseEvent event){
        Line line = lineDrawer.draw(event);
        if (line != null){
            canvas.getGraphicsContext2D().strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        }
    }

    /**
     * stop drawing and manipulate the displayedData
     */
    @FXML
    @SuppressWarnings("unchecked")
    private void stopDraw(){
        Shape shape = lineDrawer.stopDraw();
        Class subType = blService.recognizeShape(shape);
        try {
          Shape reshaped = (Shape)subType.getConstructor(Shape.class).newInstance(shape);
          displayedData.getDisplayedShapes().add(reshaped);
        } catch (NoSuchMethodException|IllegalAccessException|InstantiationException|InvocationTargetException e){
            displayedData.getDisplayedShapes().add(shape);
        }
    }

    /**
     * create new file
     * ask the user if they need to save the temporary painting
     */
    @FXML
    private void create(){
        //determine if or not to save the temp work
        if (!displayedData.getDisplayedShapes().isEmpty()
                && SaverController.initFileSaver()){//return toBeSaved
            save();
        }

        revert();
        String fileName = MakerController.initFileMaker();
        if (fileName != null){
            mediator.changeFileName(fileName);
        }
    }

    /**
     * open a file and rewrite the model
     */
    @FXML
    private void open(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("MyCanvas Files","*.mcv"));//ext of this app is '.mcv'
        fileChooser.setInitialDirectory(new File(PathUtil.getFilePath()));
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        loadIntoCanvas(file);
    }

    /**
     * choose recent files and then open it
     */
    @FXML
    private void openRecent(){
        List<File> fileList = blService.getRecentFiles();
        for (File file : fileList) {//preconditions
            if (file == null || file.getName().isEmpty()){
                LoggerUtil.getLogger().warning(new Exception("bad files contained"));
                return;
            }
        }

        List<MenuItem> menuItems = new ArrayList<>(fileList.size());
        fileList.forEach(file -> {
            MenuItem item = new MenuItem(file.getName());
            item.setOnAction((ActionEvent event) -> loadIntoCanvas(file));
            menuItems.add(item);
        });
        openRecent.getItems().clear();
        openRecent.getItems().addAll(menuItems);
        openRecent.show();
    }


    /**
     * save a file
     * preconditions: there are shapes in canvas
     * if user do not input the file name, it will alert
     */
    @FXML
    private void save(){
        mediator.saveMade();//try to get file name as input

        if (displayedData == null || displayedData.getFileName() == null || displayedData.getFileName().isEmpty()){
            LoggerUtil.getLogger().info(new Exception("no certain file to be saved"));
            return;
        }

        ObservableList<Shape> displayedShapes = displayedData.getDisplayedShapes();
        List<Shape> shapes = new ArrayList<>(displayedShapes);
        ResultMessage resultMessage = blService.savePainting(shapes, displayedData.getFileName());
        switch (resultMessage){
            case SUCCESS://do nothing
                break;
            case FAILURE:
                Popup.showPopup("File doesn't exist or data is broken", Color.RED);
                break;
            case ARG_ABSENT:
                Popup.showPopup("no input", Color.BLACK);
                break;
            case WRONG_EXT:
                Popup.showPopup("wrong data format", Color.BLACK);
                break;

                default:
                    break;
        }
    }

    @FXML
    private void saveAs(){
        String fileName = MakerController.initFileMaker();
        if (fileName != null){
            mediator.changeFileName(fileName);
        }

        save();
    }

    /**
     * clean all the temporary data
     */
    @FXML
    private void revert(){
        displayedData.getDisplayedShapes().clear();
    }

    @FXML
    private void preference(){
        Integer width = PreferenceController.initPreferenceController();
        if (width != null){
            this.strokeWidth = width;
            canvas.getGraphicsContext2D().setLineWidth(strokeWidth);
            sync();
        }
    }

    @FXML
    private void quit(){
        System.exit(0);
    }

    @FXML
    private void changeTransformMode(){
        shapeDrawer.changeState(transformItem.isSelected());
        sync();//rewrite the shapes
    }

    @FXML
    private void undo(){
        command.undo();
        mediator.commandExecuted();
    }

    @FXML
    private void redo(){
        command.redo();
        mediator.commandExecuted();
    }

    @FXML
    private void showAboutInfo(){
        Popup.showPopup("Mycanvas supports image painting and automatic Detection.\n" +
                "All paint must be drawn without any interruption.", Color.BLACK);
    }


    /**
     * utility function
     * @param file .mcv file
     */
    private void loadIntoCanvas(File file){
        if (file != null){
            List<Shape> shapes = blService.loadPainting(file);
            displayedData.getDisplayedShapes().clear();
            displayedData.getDisplayedShapes().addAll(FXCollections.observableArrayList(shapes));
            mediator.changeFileName(file.getName());
        }
    }
}
