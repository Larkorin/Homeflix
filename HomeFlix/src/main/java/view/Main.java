package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    /**
     *
     * @author Luis Diego Obando
     *
     * @param stage Recibe un escenario para ejecutar.
     * @throws IOException Signo de que algun tipo de excepcion ha ocurrido.
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @author Luis Diego Obando
     * 
     *  Funcion main del proyecto.
     *
     * @param args Recibe un array de tipo String.
     */

    public static void main(String[] args) {
        launch();
    }
}
