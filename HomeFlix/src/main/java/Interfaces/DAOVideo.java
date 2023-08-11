package Interfaces;


import model.Video;

import java.util.ArrayList;

public interface DAOVideo {
    /**
     * La funcion agrega videos a la base de datos
     * @author Michael NG
     * @param nombre El parametro es un tipo de dato String y es el nombre del video
     * @param categoria El parametro es un tipo de dato String y es el nombre de la categoria
     * @param duracion El parametro es un tipo de dato String y es la duracion del video
     * @param dia El parametro es un tipo de dato dia y es el dia en el que fue registrado el video
     * @param descripcion El parametro es un tipo de dato String y es la descripcion del video
     * @param localizacion El parametro es un tipo de dato String y es la localizacion de donde esta el video en la computadora
     * @throws Exception En caso de que el query tenga un error
     */
    public void agregarVideos(String nombre, String categoria, String duracion, String dia, String descripcion, String localizacion) throws Exception;

    /**
     * La funcion lista los videos guardados en la base de datos
     * @author Michael Ng
     * @return Retorna un valor tipo ArrayList y es para listar todos los videos que hay en la base de datos
     * @throws Exception En caso de que el query tenga un error
     */
    public ArrayList<Video> listarVideos() throws Exception;

    /**
     * La funcion lista los videos que coincidan con el nombre y la categoria ingresada por el usuario
     * @author Michael NG
     * @param nombre Recibe un dato tipo String y es para filtrar los videos que coincidan con ese nombre
     * @param categoria Recibe un dato tipo String y es para filtrar los videos que coincidan con esa categoria
     * @return retorna un valor tipo ArrayList y es para listar los videos que coincidan con los valores ingresadps por los usuarios
     * @throws Exception En caso de que el query tenga un error
     */
    public ArrayList<Video> listarVideoNC(String nombre, String categoria) throws Exception;

    /**
     * La funcion permite listar los videos del playlist
     * @author Michael Ng
     * @param nombre Recibe un String de parametro que es el nombre del playlist
     * @return retorna los videos de la playlist
     * @throws Exception En caso de que el query tenga un error
     */
    public ArrayList<Video> listarVideoPlaylist(String nombre) throws Exception;

    /**
     * La funcion permite obtener el id del video deseado
     * @author Michael Ng
     * @param nombre recibe el nombre del video
     * @param categoria recibe la categoria del video
     * @param localizacion recibe la localizacion del video
     * @return retorna el id del video
     * @throws Exception En caso de que el query tenga un error
     */
    public int obtenerIdVideo(String nombre, String categoria, String localizacion) throws Exception;
}
