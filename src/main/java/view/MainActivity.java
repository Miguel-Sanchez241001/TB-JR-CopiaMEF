package view;

import Servicie.ServiceXlsToTxt;
import Util.StringUtiles;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.MefTrama;

import java.io.File;
import java.util.List;

import static Util.ViewUtils.showAlert;

public class MainActivity extends Application {

    private File archivo = null;

    @Override
    public void start(Stage primaryStage) throws Exception {


        Label label1 = new Label("Seleccione el archivo Excel");
        Label label2 = new Label("Archivo :");
        TextArea textArea = new TextArea();
        textArea.setPromptText("");
        textArea.setEditable(true); // Hacer el TextArea no editable
         // Crear un TextField (campo de texto)
        TextField textField = new TextField();
        textField.setPromptText("Escribe algo aquí");

        // Crear un botón
        Button button = new Button("Generar TXT MEF");

        // Configurar la acción del botón
        button.setOnAction(event -> {
            String inputText = textField.getText();
            System.out.println("Texto ingresado: " + inputText);
            if (archivo != null) {

                List<MefTrama> objectos = ServiceXlsToTxt.ObtenerDatOfXls(archivo);
                try {
                    StringBuilder  sbMain = StringUtiles.GenerarTrama(objectos);
                    textArea.setText(sbMain.toString());
                } catch (Exception e) {
                    showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);

                    throw new RuntimeException(e);
                }

            } else {
                // Mostrar mensaje flotante si no se selecciona un archivo
                showAlert("Advertencia", "No se seleccionó ningún archivo", Alert.AlertType.WARNING);
            }
        });




        // Crear otro botón para abrir el FileChooser
        Button fileButton = new Button("Busca");

        // Crear un FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona un archivo");

        // Configurar la acción del botón del FileChooser
        fileButton.setOnAction(event -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                showAlert("Archivo seleccionado", "Ruta: " + selectedFile.getAbsolutePath(), Alert.AlertType.INFORMATION);
                label2.setText("Archivo :" + selectedFile.getName());
                this.setArchivo(selectedFile);

            } else {
                // Mostrar mensaje flotante si no se selecciona un archivo
                showAlert("Advertencia", "No se seleccionó ningún archivo", Alert.AlertType.WARNING);
                this.setArchivo(null);
                label2.setText("Archivo :" );
            }
        });


        // Crear un layout (VBox)
        VBox layout = new VBox(10); // Espacio de 10 píxeles entre elementos
        layout.getChildren().addAll(textField, button, fileButton);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10)); // Margen alrededor del grid
        gridPane.setHgap(10); // Espacio horizontal entre columnas
        gridPane.setVgap(10); // Espacio vertical entre filas

        // Añadir los elementos al GridPane
        gridPane.add(label1, 0, 0);  // Columna 0, Fila 0
        gridPane.add(fileButton, 1, 0);  // Columna 1, Fila 0
        gridPane.add(label2, 0, 1);  // Columna 0, Fila 0
        GridPane.setColumnSpan(button, 2);  // El botón ocupará las dos columnas
        gridPane.add(button, 0, 3);  // Colocamos el botón en la primera columna (0), fila 2
        // Añadir el TextArea que ocupe 4 columnas y 4 filas
        GridPane.setColumnSpan(textArea, 6);  // Ocupará 4 columnas
        GridPane.setRowSpan(textArea, 6);     // Ocupará 4 filas
        gridPane.add(textArea, 0, 6);         // Colocamos el TextArea en la primera columna y fila 4


        // Crear una escena
        Scene scene = new Scene(gridPane, 500, 250);

        // Configurar el escenario
        primaryStage.setTitle("Ventana con JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    public File getArchivo() {
        return archivo;
    }

    public void setArchivo(File archivo) {
        this.archivo = archivo;
    }
}
