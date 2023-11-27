package poo.scrabblejavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApplication extends Application {

    /**
     * Método principal de aplicación de JavaFX llamado por el main para cargar la aplicación en pantalla.
     * @param primaryStage el contenedor de la ventana que va a cargarse, en JavaFX se llaman "Stages".
     * @throws Exception en caso de no encontrar el archivo recurso a cargar arrojaría una excepción.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("RummikubStartScreen.fxml"));
        primaryStage.setTitle("Rummikub - Pantalla de Inicio");
        primaryStage.setScene(new Scene(fxmlLoader.load(), 450, 400));
        primaryStage.show();
    }

    /**
     * Método principal absoluto del proyecto. Se encarga de ejecutar la aplicación al momento que se ejecuta el archivo.
     * @param args se carga automáticamente como los argumentos del main la aplicación y el método start.
     */
    public static void main(String[] args) {
        launch();
    }
}
