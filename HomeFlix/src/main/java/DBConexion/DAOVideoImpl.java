package DBConexion;


import Interfaces.DAOVideo;
import model.Usuario;
import model.Video;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOVideoImpl extends DBConexion implements DAOVideo {

    /**
     * La funcion permite agregar videos en la base de datos
     * @author Michael Ng
     * @param nombre El parametro es un tipo de dato String y es el nombre del video
     * @param categoria El parametro es un tipo de dato String y es el nombre de la categoria
     * @param duracion El parametro es un tipo de dato String y es la duracion del video
     * @param dia El parametro es un tipo de dato dia y es el dia en el que fue registrado el video
     * @param descripcion El parametro es un tipo de dato String y es la descripcion del video
     * @param localizacion El parametro es un tipo de dato String y es la localizacion de donde esta el video en la computadora
     * @throws Exception En caso de que el query o el sql tenga un error
     */
    @Override
    public void agregarVideos (String nombre, String categoria, String duracion, String dia, String descripcion, String localizacion) throws Exception {
        try {
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("INSERT INTO video (nombre, categoria, duracion, fechaAnadido, descripcion, localizacion) VALUES (?,?,?,?,?,?)");
            st.setString(1, nombre);
            st.setString(2, categoria);
            st.setString(3, duracion);
            st.setString(4, dia);
            st.setString(5, descripcion);
            st.setString(6, localizacion);
            st.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    /**
     * La funcion lista los videos guardados en la base de datos
     * @author Michael Ng
     * @return Retorna un valor tipo ArrayList y es para listar todos los videos que hay en la base de datos
     * @throws Exception En caso de que el queyr o el sql tenga un error
     */
    @Override
    public ArrayList<Video> listarVideos() throws Exception {
        ArrayList<Video> listarVideos;
        try{
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM video");
            listarVideos = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                Video video = new Video();
                video.setName(rs.getString("nombre"));
                video.setDuracion(rs.getString("duracion"));
                video.setCategory(rs.getString("categoria"));
                video.setDate(rs.getString("fechaAnadido"));
                video.setDescripcion(rs.getString("descripcion"));
                video.setLocalizacion(rs.getString("localizacion"));
                listarVideos.add(video);
            }
            rs.close();
            st.close();
        }catch (Exception e){
            throw e;
        } finally {
            this.cerrar();
        }
        return listarVideos;
    }

    /**
     * La funcion lista los videos que coincidan con el nombre y la categoria ingresada por el usuario
     * @author Michael NG
     * @param nombre Recibe un dato tipo String y es para filtrar los videos que coincidan con ese nombre
     * @param categoria Recibe un dato tipo String y es para filtrar los videos que coincidan con esa categoria
     * @return retorna un valor tipo ArrayList y es para listar los videos que coincidan con los valores ingresadps por los usuarios
     * @throws Exception En caso de que el query o el sql tenga un error
     */
    @Override
    public ArrayList<Video> listarVideoNC(String nombre, String categoria) throws Exception {
        ArrayList<Video> listarVideos;
        try{
            this.conectar();
            PreparedStatement st = this.conexion.prepareStatement("SELECT * FROM video WHERE nombre=? AND categoria=?");
            st.setString(1, nombre);
            st.setString(2, categoria);
            listarVideos = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                String retrivedNombre = rs.getString("nombre");
                String retrievedCategoria = rs.getString("categoria");
                if(retrivedNombre.equalsIgnoreCase(nombre) && retrievedCategoria.equalsIgnoreCase(categoria)){
                    Video video = new Video();
                    video.setName(rs.getString("nombre"));
                    video.setDuracion(rs.getString("duracion"));
                    video.setCategory(rs.getString("categoria"));
                    video.setDate(rs.getString("fechaAnadido"));
                    video.setDescripcion(rs.getString("descripcion"));
                    video.setLocalizacion(rs.getString("localizacion"));
                    listarVideos.add(video);
                }
            }
            rs.close();
            st.close();
        }catch (Exception e){
            throw e;
        } finally {
            this.cerrar();
        }
        return listarVideos;
    }

    /**
     * La funcion permite listar los videos del playlist
     * @author Michael Ng
     * @param nombre Recibe un String de parametro que es el nombre del playlist
     * @return retorna los videos de la playlist
     * @throws Exception En caso de que el query tenga un error
     */
    @Override
    public ArrayList<Video> listarVideoPlaylist(String nombre) throws Exception {
        ArrayList<Video> videosPlaylist;
        try{
            this.conectar();
            PreparedStatement prs = this.conexion.prepareStatement("SELECT * FROM playlist INNER JOIN playlist_has_video ON playlist.idplaylist = playlist_has_video.playlist_idplaylist INNER JOIN video on playlist_has_video.video_idvideo = video.idvideo WHERE playlist.nombre = ?");
            prs.setString(1, nombre);
            videosPlaylist = new ArrayList<>();
            ResultSet rs = prs.executeQuery();
            while(rs.next()){
                Video video = new Video();
                video.setName(rs.getString("video.nombre"));
                video.setDuracion(rs.getString("video.duracion"));
                video.setCategory(rs.getString("categoria"));
                video.setDate(rs.getString("fechaAnadido"));
                video.setDescripcion(rs.getString("descripcion"));
                video.setLocalizacion(rs.getString("localizacion"));
                videosPlaylist.add(video);
            }
            rs.close();
            prs.close();
        } catch (Exception e){
            throw e;
        } finally {
            this.cerrar();
        }
        return videosPlaylist;
    }


    /**
     * La funcion permite obtener el id del video deseado
     * @author Michael Ng
     * @param nombre recibe el nombre del video
     * @param categoria recibe la categoria del video
     * @param localizacion recibe la localizacion del video
     * @return retorna el id del video
     * @throws Exception En caso de que el query tenga un error
     */
    @Override
    public int obtenerIdVideo(String nombre, String categoria, String localizacion) throws Exception{
        int idVideo = 0;
        try {
            this.conectar();
            PreparedStatement prs = this.conexion.prepareStatement("SELECT idvideo FROM video WHERE nombre = ? AND categoria = ? AND localizacion = ?");
            prs.setString(1, nombre);
            prs.setString(2, categoria);
            prs.setString(3, localizacion);
            ResultSet rs = prs.executeQuery();
            while(rs.next()){
                idVideo = (rs.getInt("idvideo"));
            }

            prs.close();
            rs.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return idVideo;
    }
}
