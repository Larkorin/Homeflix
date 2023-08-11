package view;

import DBConexion.DAOPlaylistImpl;
import Interfaces.DAOPlaylist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerAgregarPlaylist {

    @FXML
    private TextField nombrePlaylist;
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * @author Luis Diego Obando
     * Funcion que se encarga de volver a la pagina principal.
     * @param event parametro que recibe un objeti ActionEvent
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido.
     */
    public void volverPrincipal(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @author Michael Ng
     *funcion que agrega una playlist a la base de datos
     */
    public void botonAgregar(){
        String nombre = nombrePlaylist.getText();
        String fechaAnadido = java.time.LocalDate.now().toString();
        try {
            DAOPlaylist daoPlaylist = new DAOPlaylistImpl();
            daoPlaylist.agregarPlaylist(nombre, "00:00", fechaAnadido, ControllerLogin.getIdUsuarioIngresado());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("El playlist fue agregado exitosamente");
            alert.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
