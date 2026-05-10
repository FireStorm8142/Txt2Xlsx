import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Select TXT File");
        Button button = new Button("Choose File");
        button.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select TXT File");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = chooser.showOpenDialog(stage);
            if (file == null) return;
            try {
                TxtReader reader = new TxtReader();
                Parser parser = new Parser();
                ExcelGenerator generator = new ExcelGenerator();
                List<String> lines = reader.reader(file.getAbsolutePath());
                List<Student> students = parser.parser(lines);
                generator.excelGenerator(students);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success");
                alert.setContentText("Excel file generated successfully!");
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(label, button);
        Scene scene = new Scene(root, 350, 120);
        stage.setTitle("Txt2Xlsx");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}