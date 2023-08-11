package view;

import DBConexion.DAOUsuarioImpl;
import DBConexion.DAOVideoImpl;
import Interfaces.DAOUsuario;
import Interfaces.DAOVideo;
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
import java.sql.*;

public class ControllerLogin {
    @FXML
    private TextField usuarioTF;
    @FXML
    private TextField contrasenaTF;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static int idUsuarioIngresado;

    public static int getIdUsuarioIngresado() {
        return idUsuarioIngresado;
    }

    public static void setIdUsuarioIngresado(int idUsuarioIngresado) {
        ControllerLogin.idUsuarioIngresado = idUsuarioIngresado;
    }

    /**
     * Cambia la pantalla actual hacia la pantalla de registro
     * @author Luis Diego Obando
     * @param event un evento representando un tipo de action
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido
     */
    public void cambiarEscenaRegistro(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Registro.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Se asegura con la base de datos que el usuario y la contrasena existan para ingresar a la pagina principal
     * @author Michael Ng
     * @param event un evento representando un tipo de action
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido
     */
    public void botonLogin(ActionEvent event) throws Exception {
        String usuario = usuarioTF.getText();
        String contrasenia = contrasenaTF.getText();
        DAOUsuario daoUsuario = new DAOUsuarioImpl();
        if(daoUsuario.usuarioExiste(usuario)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("El usuario no existe");
            alert.show();
        } else {
            if(daoUsuario.revisarContrasenia(contrasenia,usuario)) {
                root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("La contraseña no coincide con el usuario");
                alert.show();
            }
        }

    }
}
