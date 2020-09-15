import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.Charset;


public class MainUI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

    /*    FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.showOpenDialog(primaryStage);*/

        TextField beforeTextField = new TextField();
        Button beforeButton = new Button("ä¯ÀÀ");
        StackPane stackPane = new StackPane();
        HBox beforeHBox = new HBox();
        ObservableList observableList =  FXCollections.observableArrayList(Charset.availableCharsets().keySet().toArray())        ;
        ChoiceBox afterChoiceBox = new ChoiceBox(observableList);

        ChoiceBox beforeChoiceBox = new ChoiceBox(observableList);
        beforeHBox.getChildren().addAll(beforeTextField, beforeButton, beforeChoiceBox);

        // Êä³ö
        TextField afterTextField = new TextField();
        Button afterButton = new Button("ä¯ÀÀ");
        HBox afterHBox = new HBox();

        afterHBox.getChildren().addAll(afterTextField, afterButton, afterChoiceBox);

        // ×ª»»°´Å¥
        Button convertButton = new Button("×ª»»");
        convertButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String beforePath = beforeTextField.getText();
                String afterPath = afterTextField.getText();
                String beforeEncode = (String) beforeChoiceBox.getSelectionModel().getSelectedItem();
                String afterEncode = (String) afterChoiceBox.getSelectionModel().getSelectedItem();

                File beforeFile = new File(beforePath);
                File afterFile = new File(afterPath);
                if (!afterFile.exists()){
                    afterFile.mkdirs();
                }
                if (beforeFile.isDirectory()) {
                    for (File file : beforeFile.listFiles()) {
                        afterFile = new File(afterPath+File.separator+file.getName());
                        fileConvert(file, afterFile, beforeEncode, afterEncode);
                    }
                } else if (beforeFile.isFile()) {
                    fileConvert(beforeFile, afterFile, beforeEncode, afterEncode);
                }
            }
        });
        convertButton.setAlignment(Pos.CENTER);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(beforeHBox, afterHBox, convertButton);
        stackPane.getChildren().add(vBox);
        primaryStage.setScene(new Scene(stackPane, 300, 200));
        primaryStage.show();
    }

    void fileConvert(File beforeFile, File afterFile, String beforeEncode, String afterEncode) {
        try (LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(new FileInputStream(beforeFile), beforeEncode));
             PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(afterFile), afterEncode));) {
            String line = "";
            while ((line = lineNumberReader.readLine()) != null) {
                printWriter.write(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}
