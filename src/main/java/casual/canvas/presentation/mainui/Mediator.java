package casual.canvas.presentation.mainui;

import casual.canvas.presentation.fileui.MakerController;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * mediator class, responsible for the interaction of menus
 * @author miaomuzhi
 * @since 2018/9/16
 */

@Getter
@Setter
@AllArgsConstructor
class Mediator {

    private Canvas canvas;

    private DisplayedData displayedData;
    private Command command;

    private MenuItem createItem;
    private MenuItem openItem;
    private MenuItem saveItem;
    private MenuItem saveAsItem;
    private MenuItem revertItem;
    private MenuItem undoItem;
    private MenuItem redoItem;

    private static boolean hasFileName = false;

    private static void fileNameInputted(){
        hasFileName = true;
    }

    public void canvasChanged(){
        boolean isEmpty = displayedData.getDisplayedShapes().isEmpty();//get state

        saveItem.setDisable(isEmpty);
        saveAsItem.setDisable(isEmpty);
        revertItem.setDisable(isEmpty);
        undoItem.setDisable(isEmpty);
    }

    public void saveMade(){
        if (!displayedData.getFileName().equals("unnamed.mcv")){//precondition
            fileNameInputted();
            return;
        }

        if (!hasFileName){
            String fileName = MakerController.initFileMaker();
            if (fileName != null){
                changeFileName(fileName);
            }
        }
    }

    public void commandExecuted(){
        redoItem.setDisable(command.getMemos().isEmpty());
    }

    /**
     * in order to make sure that the following manipulations will be done after change file name,
     * no other setFileName should be applied, make the file virtually observable
     * @param s file name
     */
    public void changeFileName(String s){
        displayedData.setFileName(s);
        ((Stage)canvas.getScene().getWindow()).setTitle(s);
        fileNameInputted();
    }
}
