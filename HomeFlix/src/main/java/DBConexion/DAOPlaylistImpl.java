package DBConexion;

import Interfaces.DAOPlaylist;
import model.Playlist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOPlaylistImpl extends DBConexion implements DAOPlaylist {

    /**
     * @author Michael NG
     * @param idUsuario recibe un parametro de tipo integer para poder identificar cual es el usuario ingresado
     * @return retorna una ArrayList de tipo playlist para poder ver cuales son los playlists del usuario ingresaod
     * @throws Exception En caso de que el query tenga un error
     */
    @Override
    public ArrayList<Playlist> listarPlaylists(int idUsuario) throws Exception {
        ArrayList<Playlist> listarPlaylists;
        try{
            this.conectar();
            PreparedStatement prs = this.conexion.prepareStatement("SELECT * FROM playlist WHERE usuario_idusuario = ?");
            prs.setInt(1, idUsuario);
            listarPlaylists = new ArrayList<>();
            ResultSet rs = prs.executeQuery();
            while(rs.next()) {
                Playlist playlist = new Playlist();
                String retrievedNombre = rs.getString("nombre");
                String retrievedFechaCreacion = rs.getString("fechaCreacion");
                playlist.setNombre(retrievedNombre);
                playlist.setFechaCreacion(retrievedFechaCreacion);
                listarPlaylists.add(playlist);
            }
        } catch (Exception e){
            throw e;
        } finally {
            this.cerrar();
        }
        return listarPlaylists;
    }

    /**
     * @author Michael Ng
     * @param nombre recibe un parametro de tipo String y es el nombre de la playlist a agregar
     * @param duracion recibe un parametro de tipo String y es la duracion de la playlist
     * @param fechaCreacion recibe un parametro de tipo String y es la fecha de creacion de la playlist
     * @param idUsuario recibe un parametro de tipo int y es para identificar cual es el usuario que esta creando la playlist
     * @throws Exception En caso de que el query tenga un error
     */
    @Override
    public void agregarPlaylist(String nombre, String duracion, String fechaCreacion, int idUsuario) throws Exception {
        try{
            this.conectar();
            PreparedStatement prs = this.conexion.prepareStatement("INSERT INTO playlist (nombre, duracion, fechaCreacion, usuario_idusuario) VALUES(?,?,?,?) ");
            prs.setString(1, nombre);
            prs.setString(2, duracion);
            prs.setString(3, fechaCreacion);
            prs.setInt(4, idUsuario);
            prs.executeUpdate();
        } catch(Exception e){
            throw e;
        } finally {
            this.cerrar();
        }
    }

    /**
     * @author Michael NG
     * @param idPlaylist recibe un parametro de tipo int y es para identificar cual es la playlist seleccionada
     * @param idVideo recibe un parametro de tipo int y es para identificar cual es el video seleccionado
     * @throws Exception En caso de que el query tenga un error
     */
    @Override
    public void agregarVideoPlaylist(int idPlaylist, int idVideo) throws Exception {

    try{
        this.conectar();
        PreparedStatement prs = this.conexion.prepareStatement("INSERT INTO playlist_has_video (playlist_idplaylist, video_idvideo) VALUES (?,?)");
        prs.setInt(1, idPlaylist);
        prs.setInt(2, idVideo);
        prs.executeUpdate();
    } catch (Exception e){
        throw e;
    } finally {
        this.cerrar();
    }
    }

    /**
     * La funcion permite obtener el id del playlist deseado
     * @author Michael Ng
     * @param nombre recibe el nombre del playlist
     * @param idUsuario recibe el id del usuario
     * @return retorna el id del playlist seleccionado
     * @throws Exception En caso de que el query tenga un error
     */
    @Override
    public int obtenerIdPlaylist(String nombre, int idUsuario) throws Exception{
        int idPlaylist = 0;
        try{
            this.conectar();
            PreparedStatement prs = this.conexion.prepareStatement("SELECT idplaylist FROM playlist WHERE nombre = ? AND usuario_idusuario = ?");
            prs.setString(1, nombre);
            prs.setInt(2, idUsuario);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                idPlaylist = rs.getInt("idplaylist");
            }


            prs.close();
            rs.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return idPlaylist;
    }

}
