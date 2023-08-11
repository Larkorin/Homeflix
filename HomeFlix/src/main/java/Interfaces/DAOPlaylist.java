package Interfaces;

import model.Playlist;
import java.util.ArrayList;

public interface DAOPlaylist {
    /**
     * @author Michael NG
     * @param idUsuario recibe un parametro de tipo integer para poder identificar cual es el usuario ingresado
     * @return retorna una ArrayList de tipo playlist para poder ver cuales son los playlists del usuario ingresaod
     * @throws Exception En caso de que el query tenga un error
     */
    public ArrayList<Playlist> listarPlaylists(int idUsuario) throws Exception;

    /**
     * @author Michael Ng
     * @param nombre recibe un parametro de tipo String y es el nombre de la playlist a agregar
     * @param duracion recibe un parametro de tipo String y es la duracion de la playlist
     * @param fechaCreacion recibe un parametro de tipo String y es la fecha de creacion de la playlist
     * @param idUsuario recibe un parametro de tipo int y es para identificar cual es el usuario que esta creando la playlist
     * @throws Exception En caso de que el query tenga un error
     */
    public void agregarPlaylist(String nombre, String duracion, String fechaCreacion, int idUsuario) throws Exception;

    /**
     * @author Michael NG
     * @param idPlaylist recibe un parametro de tipo int y es para identificar cual es la playlist seleccionada
     * @param idVideo recibe un parametro de tipo int y es para identificar cual es el video seleccionado
     * @throws Exception En caso de que el query tenga un error
     */
    public void agregarVideoPlaylist(int idPlaylist, int idVideo) throws Exception;

    /**
     * La funcion permite obtener el id del playlist deseado
     * @author Michael Ng
     * @param nombre recibe el nombre del playlist
     * @param idUsuario recibe el id del usuario
     * @return retorna el id del playlist seleccionado
     * @throws Exception En caso de que el query tenga un error
     */
    public int obtenerIdPlaylist(String nombre, int idUsuario) throws Exception;

}
