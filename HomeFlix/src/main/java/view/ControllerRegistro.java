package view;

import DBConexion.DAOUsuarioImpl;
import Interfaces.DAOUsuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Usuario;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class ControllerRegistro {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView avatar;
    @FXML
    private TextField nombreTF;
    @FXML
    private TextField identificacionTF;
    @FXML
    private TextField primerApellidoTF;
    @FXML
    private TextField segundoApellidoTF;
    @FXML
    private TextField usuarioTF;
    @FXML
    private TextField contrasenaTF;
    @FXML
    private TextField confirmarContrasenaTF;

    /**
     * Cambia la pantalla actual por la pantalla Login
     * @author Gabriel Porras Brenes
     * @param event un evento representando un tipo de action
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido
     */
    public void volverLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *Crea un usuario nuevo
     * @author Gabriel Porras Brenes
     * @param event un evento representando un tipo de action
     * @throws Exception signo de que algun tipo de excepcion ha ocurrido
     */
    public void botonCrear(ActionEvent event) throws Exception {
        String nombre = nombreTF.getText();
        String identificacion = identificacionTF.getText();
        String primerApellido = primerApellidoTF.getText();
        String segundoApellido = segundoApellidoTF.getText();
        String usuario = usuarioTF.getText();
        String contrasena = contrasenaTF.getText();
        String confirmarContrasena = confirmarContrasenaTF.getText();
        String avatar = "temp";
        boolean validarContrasenia = true;
        String upperCaseChars = "(.*[A-Z].*)";
        String lowerCaseChars = "(.*[a-z].*)";
        String specialChars = "(.*[@,#,$,%].*$)";
        if(contrasena.equals(confirmarContrasena)){
            if(contrasena.length() < 6){
                System.out.println("La contrasenia contiene menos de 6 caracteres");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("La contraseña contiene menos de 6 caracteres");
                alert.show();
                validarContrasenia = false;
            } else if(!contrasena.matches(upperCaseChars)){
                System.out.println("La contrasenia no contiene ninguna mayuscula");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("La contraseña no contiene ninguna mayuscula");
                alert.show();
                validarContrasenia = false;
            } else if(!contrasena.matches(lowerCaseChars)){
                System.out.println("La contrasenia no contiene ninguna minuscula");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("La contraseña no contiene ninguna minuscula");
                alert.show();
                validarContrasenia = false;
            } else if(!contrasena.matches(specialChars)){
                System.out.println("La contrasenia no contiene ningun caracter especial");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("La contraseña tiene que tener almenos uno de los siguientes caracteres especiales: @#$%");
                alert.show();
                validarContrasenia = false;
            }
            if(validarContrasenia){
                Usuario usuarioTemp = new Usuario(nombre,primerApellido,segundoApellido,identificacion,usuario,contrasena,avatar);
                try {
                    DAOUsuario dao = new DAOUsuarioImpl();
                    dao.agregarUsuario(usuarioTemp);
                    root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println("La contraseñas no coinciden");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("La contraseñas no coinciden");
            alert.show();
        }
    }

    /**
     * Abre el explorador de archivos para que el usuario pueda elegir un avatar en el perfil personal
     * @author Gabriel Porras Brenes
     */
    public void buscarAvatar(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File imgFile = fileChooser.showOpenDialog(null);
        if (imgFile != null){
            avatar.setImage(new Image(imgFile.toURI().toString()));
        }
    }
}
