package casual.canvas.presentation.fileui;

import casual.canvas.util.LoggerUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static casual.canvas.util.ConstantString.EXTENSION;

/**
 * @author miaomuzhi
 * @since 2018/9/17
 */
public class MakerController {

    @FXML
    private TextField inputField;

    private String fileName;//file name to return


    /**
     * initialize file maker window and get FileName as return value
     * @return file name that user inputs, null if user cancels.
     */
    public static String initFileMaker(){
        try {
            FXMLLoader loader = new FXMLLoader(MakerController.class.getResource("/fxml/filemaker.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(scene);

            MakerController controller = loader.getController();
            stage.showAndWait();
            return controller.fileName;
        } catch (IOException e){
            LoggerUtil.getLogger().info(e);
            return null;
        }
    }

    @FXML
    private void cancel(){
        ((Stage)inputField.getScene().getWindow()).close();
    }
    @FXML
    private void confirm(){
        String input = inputField.getText();
        if (input == null || input.isEmpty()){
            return;
        }

        if (!input.endsWith(EXTENSION)){
            input = input + EXTENSION;
        }

        this.fileName = input;
        ((Stage)inputField.getScene().getWindow()).close();
    }
}
