import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CalcController implements Initializable {

    @FXML
    public TextField tab;
    private int left, result;

    public CalcController() {
        System.out.println(tab);
    }

    public void process(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        String type = button.getText();
        switch (type) {
            case "0":
                changeScene(actionEvent);
                break;
            case "1":
                tab.appendText(type);
                break;
            case "+":
                left = Integer.parseInt(tab.getText());
                tab.clear();
                break;
            case "=":
                result = left + Integer.parseInt(tab.getText());
                tab.setText(String.valueOf(result));
        }
    }

    private void changeScene(ActionEvent actionEvent) throws IOException {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("ok.fxml"));
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(tab);
        System.out.println(location);
        System.out.println(resources);
    }
}
