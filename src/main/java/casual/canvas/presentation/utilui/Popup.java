package casual.canvas.presentation.utilui;

import casual.canvas.util.Color;
import casual.canvas.util.LoggerUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * @author miaomuzhi
 * @since 2018/9/17
 */
public class Popup {

    private Popup(){}

    /**
     * show a pop up to show a label
     * @param info text of label
     * @param color color of label
     */
    public static void showPopup(String info, Color color){
        try {
            FXMLLoader loader = new FXMLLoader(Popup.class.getResource("/fxml/popup.fxml"));
            AnchorPane root = loader.load();
            for (Node node : root.getChildren()) {
                if (node.getId().equals("info")){//label's id
                    Label label = (Label) node;
                    label.setText(info);
                    label.setTextFill(color.transform());

                }
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }catch (IOException e){
            LoggerUtil.getLogger().info(e);
        }
    }
}
