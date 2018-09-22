package casual.canvas.presentation.preferenceui;

import casual.canvas.presentation.fileui.MakerController;
import casual.canvas.util.LoggerUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * @author miaomuzhi
 * @since 2018/9/22
 */
public class PreferenceController {

    @FXML
    private Slider strokeWidth;

    private Integer modifiedWidth = null;

    /**
     * allow user to modify stroke width and path of workspace
     * @return modified stroke width, null if it fails to load the page
     */
    public static Integer initPreferenceController(){
        try {
            FXMLLoader loader = new FXMLLoader(MakerController.class.getResource("/fxml/preference.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(scene);

            PreferenceController controller = loader.getController();
            stage.showAndWait();
            return controller.modifiedWidth;
        } catch (IOException e){
            LoggerUtil.getLogger().info(e);
            return null;
        }
    }


    @FXML
    private void cancel(){
        ((Stage)strokeWidth.getScene().getWindow()).close();
    }

    @FXML
    private void confirm(){
        this.modifiedWidth = (int)strokeWidth.getValue();
        ((Stage)strokeWidth.getScene().getWindow()).close();
    }
}
