package casual.canvas.presentation;

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
import java.util.List;

import static casual.canvas.util.ConstantString.EXTENSION;

/**
 * @author miaomuzhi
 * @since 2018/9/17
 */
public class FileController {

    @FXML
    private TextField inputField;

    private List<String> strings;//space to store the file


    public static void initFileMaker(List<String> memorySpace){
        try {
            FXMLLoader loader = new FXMLLoader(FileController.class.getResource("/fxml/filemaker.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(scene);

            loader.<FileController>getController().setStrings(memorySpace);

            stage.showAndWait();
        } catch (IOException e){
            LoggerUtil.getLogger().info(e);
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
        strings.add(input);
        ((Stage)inputField.getScene().getWindow()).close();
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }
}
