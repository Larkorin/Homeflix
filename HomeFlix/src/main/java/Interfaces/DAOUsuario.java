package Interfaces;

import model.Usuario;

import java.util.ArrayList;

public interface DAOUsuario {
    /**
     * La funcion permite agregar un usuario a la base de datos
     * @author Michael Ng
     * @param usuario Recibe un parametro tipo Usuario y es para agregar el objeto a la base de datos
     * @throws Exception En caso de que el query tenga un error
     */
    public void agregarUsuario(Usuario usuario) throws Exception;

    /**
     * La funcion esta para listar todos los usuarios en la base de datos
     * @author Michael Ng
     * @return Retorna un dato de tipo ArrayList y funciona para listar a todos los usaurios en la base de datos
     * @throws Exception En caso de que el query tenga un error
     */
    public ArrayList<Usuario> listarUsuarios() throws Exception;

    /**
     * La funcion permite revisar si la contrasena que ingresa el usuario coincide con el usuario ingresado
     * @author Michael Ng
     * @param contrasenia recibe un dato de tipo String y es para revisar si la contrasena coincide con la contrasena del usuario ingresado
     * @param usuario recibe un dato de tipo String y es para revisar cual es el usuario que esta siendo ingresado en la aplicacion
     * @return retorna un tipo de dato boolean para averiguar si la contrasena es correcta o no
     * @throws Exception En caso de que el query tenga un error
     */
    public boolean revisarContrasenia(String contrasenia,String usuario) throws Exception;

    /**
     * La funcion permite a la aplicacion averiguar si el usuario que esta siendo ingresado existe en la base de datos
     * @author Michael NG
     * @param usuario recibe un dato de tipo String y es para averiguar si el usuario ingresado esta registrado en la base de datos
     * @return retorna un valor tipo boolean para averiguar si el usuario existe o no
     * @throws Exception En caso de que el query tenga un error
     */
    public boolean usuarioExiste(String usuario) throws Exception;
}
