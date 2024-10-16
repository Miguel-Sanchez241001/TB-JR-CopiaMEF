package Util;

import javafx.scene.control.Alert;

public class ViewUtils {


    public static void showAlert(String title, String message, Alert.AlertType information) {
        Alert alert = new Alert(information);
        alert.setTitle(title);
        alert.setHeaderText(null); // Sin encabezado
        alert.setContentText(message);
        alert.showAndWait(); // Espera hasta que el usuario cierre la alerta
    }
}
