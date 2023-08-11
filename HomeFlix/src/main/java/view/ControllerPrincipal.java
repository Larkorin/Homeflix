package view;

import DBConexion.DAOPlaylistImpl;
import Interfaces.DAOPlaylist;
import Threads.ProcesoServidor;
import com.almasb.fxgl.entity.action.Action;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Playlist;
import model.Video;

import java.io.*;
import java.net.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerPrincipal implements Initializable {

    @FXML
    private TableView<Video> tblVideoReciente;

    @FXML
    private TableColumn<Video, String> nombreVidCO;

    @FXML
    private TableColumn<Video, String> localizacionCO;

    @FXML
    private TableColumn<Video, String> categoriaCO;

    @FXML
    private TableColumn<Video, String> duracionCO;

    public static ArrayList<Video> videosRecientes = new ArrayList<Video>();

    public static ArrayList<Video> getVideosRecientes() {
        return videosRecientes;
    }

    public static void agregarVideoReciente(Video v){
        if(ControllerPrincipal.getVideosRecientes().size() < 6) {
            videosRecientes.add(v);
        } else {
            videosRecientes.add(v);
            videosRecientes.remove(0);
        }
    }

    @FXML
    private TableView<Playlist> tblPlaylist;

    @FXML
    private TableColumn<Playlist, String> nombreCO;

    @FXML
    private TableColumn<Playlist, String> fechaCO;

    @FXML
    private Label usuario;

    @FXML
    private Button startHost;

    @FXML
    private Button joinServer;

    private ObservableList<Playlist> playlistList;

    private ObservableList<Video> videoReciente;

    public static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ControllerPrincipal.username = username;
    }
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Cambia la pantalla actual por la pantalla de busqueda
     * @author Gabriel Porras Brenes
     * @param event un evento representando un tipo de action
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido
     */
    public void botonBusqueda(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Busqueda.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * funcion para volver a la pantalla de Login
     * @author Gabriel Porras Brenes
     * @param event un evento representando un tipo de action
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido
     */
    public void botonVolver(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * funcion que cambia la pantalla actual a la pantalla agregar video
     * @author Gabriel Porras Brenes
     * @param event un evento representando un tipo de action
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido
     */
    public void botonAgregarVideo(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("AgregarVideo.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * La funcion permite al usuario viajar hacia el FXML AgregarPlaylist
     * @author Michael Ng
     * @param event recibe un parametro de evento.
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido.
     */
    public void botonAgregarPlaylist(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("AgregarPlaylist.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * La funcion permite al usuario viajar hacia el FXML ConectarCliente
     * @author Gabriel Porras
     * @param event recibe un parametro de evento.
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido.
     */
    public void botonJoinServer(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ConectarCliente.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * La funcion permite al usuario viajar hacia el FXML HostBusqueda
     * @author Gabriel Porras
     * @param event recibe un parametro de evento.
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido.
     */
    public void botonStartHost(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("HostBusqueda.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * La funcion permite al usuario seleccionar un video de la tabla de videos
     * @author Michael Ng
     * @param event recibe un parametro de evento.
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido.
     */
    public void seleccionarVideo(ActionEvent event) throws IOException {
        ObservableList<Video> videoSeleccionado;
        videoSeleccionado = tblVideoReciente.getSelectionModel().getSelectedItems();
        String path = videoSeleccionado.get(0).getLocalizacion();
        ControllerReproductor.setPath(path);

        root = FXMLLoader.load(getClass().getResource("Reproductor.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * La funcion permite al usuario seleccionar una playlist de las playlists que ha creado el usuario
     * @author Michael Ng
     * @param event recibe un parametro de evento.
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido.
     */
    public void seleccionarPlaylist(ActionEvent event) throws IOException {
        ObservableList<Playlist> playlistSeleccionado;
        playlistSeleccionado = tblPlaylist.getSelectionModel().getSelectedItems();
        String nombrePlaylist = playlistSeleccionado.get(0).getNombre();
        ControllerSeleccionarVideoPlaylist.setNombrePlaylist(nombrePlaylist);

        root = FXMLLoader.load(getClass().getResource("SeleccionarVideoPlaylist.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * La funcion permite inicializar las tablas en el FXML Principal
     * @author Michael Ng
     * @param url Recibe un objeto tipo url
     * @param resourceBundle recibe un objeto tipo resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuario.setText("Bienvenido " + username);
        DAOPlaylist daoPlaylist = new DAOPlaylistImpl();
        playlistList = FXCollections.observableArrayList();
        ArrayList<Playlist> playlistUsuario = null;
        try {
            playlistUsuario = daoPlaylist.listarPlaylists(ControllerLogin.getIdUsuarioIngresado());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        playlistList.setAll(playlistUsuario);
        this.nombreCO.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.fechaCO.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));

        tblPlaylist.setItems(playlistList);

        videoReciente = FXCollections.observableArrayList();
        videoReciente.setAll(ControllerPrincipal.getVideosRecientes());
        this.nombreVidCO.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.categoriaCO.setCellValueFactory(new PropertyValueFactory<>("category"));
        this.duracionCO.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        this.localizacionCO.setCellValueFactory(new PropertyValueFactory<>("localizacion"));

        tblVideoReciente.setItems(videoReciente);

    }
}
