package casual.canvas.presentation.fileui;

import casual.canvas.util.LoggerUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * @author miaomuzhi
 * @since 2018/9/21
 */
public class SaverController {
    @FXML
    private Label info;

    private boolean toBeSaved = false;

    /**
     * initialize file saver window and get toBeSaved as return value
     * @return whether temp work will be saved, false by default
     */
    public static boolean initFileSaver(){
        try {
            FXMLLoader loader = new FXMLLoader(MakerController.class.getResource("/fxml/filesaver.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(scene);

            SaverController controller = loader.getController();
            stage.showAndWait();
            return controller.toBeSaved;
        } catch (IOException e){
            LoggerUtil.getLogger().info(e);
            return false;
        }
    }

    @FXML
    private void cancel(){
        ((Stage)info.getScene().getWindow()).close();
    }

    @FXML
    private void confirm(){
        toBeSaved = true;
        ((Stage)info.getScene().getWindow()).close();
    }
}
