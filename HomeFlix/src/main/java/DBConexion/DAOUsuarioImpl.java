package DBConexion;

import Interfaces.DAOUsuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Usuario;
import view.ControllerLogin;
import view.ControllerPrincipal;

import java.sql.*;
import java.util.ArrayList;

public class DAOUsuarioImpl extends DBConexion implements DAOUsuario {

    /**
     * La funcion permite agregar usuarios a la base de datos
     * @author Michael Ng
     * @param usuario Recibe un parametro tipo Usuario y es para agregar el objeto a la base de datos
     * @throws Exception en caso de que el query tenga un error
     */
    @Override
    public void agregarUsuario(Usuario usuario) throws Exception {
        boolean bandera = true;
        try{
            this.conectar();
            PreparedStatement prs = this.conexion.prepareStatement("SELECT nombreUsuario from usuario");
            ResultSet resultSet = prs.executeQuery();
            while (resultSet.next()) {
                String retrievedUsername = resultSet.getString("nombreUsuario");
                if (retrievedUsername.equals(usuario.getNombreUsuario())) {
                    System.out.println("Usuario ya existe");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Usuario ya existe");
                    alert.show();
                    bandera = false;
                    break;
                }
            } if(bandera){
                PreparedStatement st = this.conexion.prepareStatement("INSERT INTO usuario (nombreUsuario, contrasena, nombre, identificacion, primerApellido, segundoApellido, avatar) VALUES (?,?,?,?,?,?,?)");
                st.setString(1,usuario.getNombreUsuario());
                st.setString(2,usuario.getContrasena());
                st.setString(3,usuario.getNombre());
                st.setString(4,usuario.getIdentifiacion());
                st.setString(5,usuario.getPrimerApellido());
                st.setString(6,usuario.getSegundoApellido());
                st.setString(7, usuario.getAvatar());
                st.executeUpdate();
            }

        }catch (Exception e){
            throw e;
        } finally {
            this.cerrar();
        }
    }

    /**
     * La funcion esta para listar todos los usuarios en la base de datos
     * @author Michael Ng
     * @return Retorna un dato de tipo ArrayList y funciona para listar a todos los usaurios en la base de datos
     * @throws Exception En caso de que el query tenga un error
     */
    @Override
    public ArrayList<Usuario> listarUsuarios() throws Exception {
        ArrayList<Usuario> listaUsuario = null;
        try{
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM usuario");
            listaUsuario = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPrimerApellido(rs.getString("primerApellido"));
                usuario.setSegundoApellido(rs.getString("segundoApellido"));
                usuario.setIdentifiacion(rs.getString("identificacion"));
                usuario.setNombreUsuario(rs.getString("nombreUsuario"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setAvatar(rs.getString("avatar"));
                listaUsuario.add(usuario);
            }
            rs.close();
            st.close();
        }catch (Exception e){
            throw e;
        } finally {
            this.cerrar();
        }
        return listaUsuario;
    }

    /**
     * La funcion permite a la aplicacion averiguar si el usuario que esta siendo ingresado existe en la base de datos
     * @author Michael NG
     * @param usuario recibe un dato de tipo String y es para averiguar si el usuario ingresado esta registrado en la base de datos
     * @return retorna un valor tipo boolean para averiguar si el usuario existe o no
     * @throws SQLException En caso de que el query tenga un error
     */
    @Override
    public boolean usuarioExiste(String usuario) throws SQLException{

            this.conectar();
            PreparedStatement prs = this.conexion.prepareStatement("SELECT * FROM usuario WHERE nombreUsuario = ?");
            prs.setString(1, usuario);
            ResultSet resultSet = prs.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return true;
            }

        return false;
    }

    /**
     * La funcion permite revisar si la contrasena que ingresa el usuario coincide con el usuario ingresado
     * @author Michael Ng
     * @param contrasenia recibe un dato de tipo String y es para revisar si la contrasena coincide con la contrasena del usuario ingresado
     * @param usuario recibe un dato de tipo String y es para revisar cual es el usuario que esta siendo ingresado en la aplicacion
     * @return retorna un tipo de dato boolean para averiguar si la contrasena es correcta o no
     * @throws SQLException En caso de que el query tenga un error
     */
    @Override
    public boolean revisarContrasenia(String contrasenia,String usuario) throws SQLException{

            this.conectar();
            PreparedStatement prs = this.conexion.prepareStatement("SELECT contrasena, idusuario, nombreUsuario FROM usuario WHERE nombreUsuario = ?");
            prs.setString(1, usuario);
            ResultSet resultSet = prs.executeQuery();
            while(resultSet.next()){
                String retrievedPassword = resultSet.getString("contrasena");
                if (retrievedPassword.equals(contrasenia)) {
                    ControllerLogin.setIdUsuarioIngresado(Integer.parseInt(resultSet.getString("idusuario")));
                    ControllerPrincipal.setUsername(resultSet.getString("nombreUsuario"));
                    return true;
                }
            }

        return false;
    }
}
