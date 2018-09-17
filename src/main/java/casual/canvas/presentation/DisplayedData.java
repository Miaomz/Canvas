package casual.canvas.presentation;

import casual.canvas.entity.Shape;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import static casual.canvas.util.ConstantString.EXTENSION;


/**
 * @author miaomuzhi
 * @since 2018/9/15
 */
@Getter
@Setter
public class DisplayedData {

    private static DisplayedData displayedData;
    private final ObservableList<Shape> displayedShapes = FXCollections.observableArrayList();
    private String fileName =  "unnamed" + EXTENSION;

    private DisplayedData(){}

    public static DisplayedData getInstance() {
        if (displayedData == null){
            displayedData = new DisplayedData();
        }
        return displayedData;
    }
}
