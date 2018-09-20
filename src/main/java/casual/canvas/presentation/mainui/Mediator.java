package casual.canvas.presentation.mainui;

import casual.canvas.presentation.fileui.FileController;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/9/16
 */

@Getter
@Setter
@AllArgsConstructor
class Mediator {

    private Canvas canvas;

    private static boolean hasFileName = false;
    private DisplayedData displayedData;
    private MenuItem createItem;
    private MenuItem openItem;
    private MenuItem saveItem;
    private MenuItem saveAsItem;
    private MenuItem revertItem;
    private MenuItem undoItem;
    private MenuItem redoItem;

    private static void fileNameInputted(){
        hasFileName = true;
    }

    public void canvasChanged(){
        boolean isEmpty = displayedData.getDisplayedShapes().isEmpty();//get state

        saveItem.setDisable(isEmpty);
        saveAsItem.setDisable(isEmpty);
        revertItem.setDisable(isEmpty);
    }

    public void saveMade(){
        if (!displayedData.getFileName().equals("unnamed.mcv")){//precondition
            fileNameInputted();
            return;
        }

        if (!hasFileName){
            List<String> stringList = new ArrayList<>(1);
            FileController.initFileMaker(stringList);
            if (!stringList.isEmpty()){
                changeFileName(stringList.get(0));
            }
        }
    }

    /**
     * in order to make sure that the following manipulations will be done after change file name,
     * no other setFileName should be applied
     * @param s file name
     */
    public void changeFileName(String s){
        displayedData.setFileName(s);
        fileNameInputted();
    }
}
