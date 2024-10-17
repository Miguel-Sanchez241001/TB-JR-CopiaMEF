package view;

import Servicie.ServiceXlsToTxt;
import Util.StringUtiles;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.MefTrama;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static Util.ViewUtils.showAlert;

public class MainActivity extends Application {

    private File archivo = null;
    private File rutaArchivoGenerado = null;  // Variable para almacenar la ruta del archivo generado

    @Override
    public void start(Stage primaryStage) throws Exception {


        Label label1 = new Label("Seleccione el archivo Excel");
        Label label2 = new Label("Archivo :");
        TextArea textArea = new TextArea();
        textArea.setPromptText("");
        textArea.setEditable(true); // Hacer el TextArea no editable
        textArea.setWrapText(true); // Permitir que el texto se ajuste
        textArea.setPrefHeight(200); // Altura preferida para permitir redimensionar

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
        // Botón para descargar el contenido del TextArea
        Button downloadButton = new Button("Guardar en Carpeta");
        downloadButton.setOnAction(event -> {
            if (textArea.getText().isEmpty()) {
                showAlert("Advertencia", "El TextArea está vacío", Alert.AlertType.WARNING);
            } else {
                // Usar DirectoryChooser para seleccionar una carpeta
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Seleccionar carpeta de destino");
                File selectedDirectory = directoryChooser.showDialog(primaryStage);
                if (selectedDirectory != null) {
                    String fechaActual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                    String nombreArchivo = "TTPHAB_" + fechaActual + "_01_MEF.TXT";
                    rutaArchivoGenerado = new File(selectedDirectory, nombreArchivo);  // Guardar la ruta del archivo generado
                    try (FileWriter writer = new FileWriter(rutaArchivoGenerado)) {
                        writer.write(textArea.getText());
                        showAlert("Éxito", "Archivo guardado correctamente en: " + rutaArchivoGenerado.getAbsolutePath(), Alert.AlertType.INFORMATION);
                    } catch (IOException e) {
                        showAlert("Error", "No se pudo guardar el archivo: " + e.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            }
        });
        // Botón para abrir el archivo guardado
        Button openFileButton = new Button("Abrir Archivo");
        openFileButton.setOnAction(event -> {
            if (rutaArchivoGenerado != null && rutaArchivoGenerado.exists()) {
                try {
                    Desktop.getDesktop().open(rutaArchivoGenerado);
                } catch (IOException e) {
                    showAlert("Error", "No se pudo abrir el archivo: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                showAlert("Advertencia", "No se ha generado ningún archivo", Alert.AlertType.WARNING);
            }
        });


        // Botón para limpiar los campos
        Button clearButton = new Button("Limpiar");
        clearButton.setOnAction(event -> {
            textField.clear();
            textArea.clear();
            label2.setText("Archivo :");
            this.archivo = null;
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
        // Añadir los nuevos botones
        gridPane.add(downloadButton, 0, 12);
        gridPane.add(openFileButton, 1, 12);  // Botón para abrir archivo
        gridPane.add(clearButton, 2, 12);  // Botón para limpiar


        // Crear una escena
        Scene scene = new Scene(gridPane, 500, 250);

        // Configurar el escenario
        primaryStage.setTitle("SIMULADOR TRAMAS MEF - BN");
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
