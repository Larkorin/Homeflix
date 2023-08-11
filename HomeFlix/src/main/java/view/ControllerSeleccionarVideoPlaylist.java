package view;

import DBConexion.DAOVideoImpl;
import Interfaces.DAOVideo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Playlist;
import model.Video;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerSeleccionarVideoPlaylist implements Initializable {

    @FXML
    private TableView<Video> tblVideos;

    @FXML
    private TableColumn<Video, String> nombreCO;

    @FXML
    private TableColumn<Video, String> categoriaCO;

    @FXML
    private TableColumn<Video, String> localizacionCO;

    @FXML
    private TableColumn<Video, String> duracionCO;

    @FXML
    private Label namePlaylist;

    private ObservableList<Video> videosList;

    public static String nombrePlaylist;

    public static String getNombrePlaylist() {
        return nombrePlaylist;
    }

    public static void setNombrePlaylist(String nombrePlaylist) {
        ControllerSeleccionarVideoPlaylist.nombrePlaylist = nombrePlaylist;
    }
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * @author Michael Ng
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
     * Funcion que se encarga de permitir al usuario seleccionar un video.
     * @param event recibe un parametro de evento.
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido.
     */
    public void seleccionarVideo(ActionEvent event) throws IOException {
        ObservableList<Video> videoSeleccionado;
        videoSeleccionado = tblVideos.getSelectionModel().getSelectedItems();
        String path = videoSeleccionado.get(0).getLocalizacion();
        ControllerReproductor.setPath(path);
        ControllerPrincipal.agregarVideoReciente(videoSeleccionado.get(0));

        root = FXMLLoader.load(getClass().getResource("Reproductor.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * La funcion permite agregar un video al playlist elegido
     * @author Gabriel Porras
     * @param event recibe un parametro de evento.
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido.
     */
    public void agregarVideo(ActionEvent event) throws IOException {
        ControllerAgregarVideoPlaylist.setNombrePlaylist(nombrePlaylist);
        root = FXMLLoader.load(getClass().getResource("AgregarVideoPlaylist.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @author Luis Diego Obando
     * Funcion inicializar que se encarga de inicializar objetos dentro de ella para poder ser utilizados y ejecutados
     * @param url Recibe un objeto tipo url
     * @param resourceBundle recibe un objeto tipo resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        namePlaylist.setText("Playlist " + nombrePlaylist);
        DAOVideo daoVideo = new DAOVideoImpl();
        videosList = FXCollections.observableArrayList();
        ArrayList<Video> videosPlaylist;
        try {
            videosPlaylist = daoVideo.listarVideoPlaylist(ControllerSeleccionarVideoPlaylist.getNombrePlaylist());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        videosList.setAll(videosPlaylist);
        this.nombreCO.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.categoriaCO.setCellValueFactory(new PropertyValueFactory<>("category"));
        this.duracionCO.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        this.localizacionCO.setCellValueFactory(new PropertyValueFactory<>("localizacion"));

        tblVideos.setItems(videosList);
    }
}
