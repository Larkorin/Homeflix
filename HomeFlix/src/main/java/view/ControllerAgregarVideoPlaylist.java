package view;

import DBConexion.DAOPlaylistImpl;
import DBConexion.DAOVideoImpl;
import Interfaces.DAOPlaylist;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Video;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerAgregarVideoPlaylist implements Initializable {

    @FXML
    private TextField nombreVideoTF;

    @FXML
    private TextField categoriaTF;

    @FXML
    private TableColumn<Video, String> nombreCO;

    @FXML
    private TableColumn<Video, String> categoriaCO;

    @FXML
    private TableColumn<Video, Double> duracionCO;

    @FXML
    private Label nombrePlaylistLB;
    @FXML
    private TableColumn<Video, Double> localizacionCO;

    @FXML
    private TableView<Video> tblVideos;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<Video> videosList;
    public static String nombrePlaylist;

    public static String getNombrePlaylist() {
        return nombrePlaylist;
    }
    public static void setNombrePlaylist(String nombrePlaylist) {
        ControllerAgregarVideoPlaylist.nombrePlaylist = nombrePlaylist;
    }

    /**
     * @author Luis Diego Obando
     * Funcion que se encarga de volver a la pagina principal.
     * @param event parametro que recibe un objeti ActionEvent
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido.
     */
    public void volverPlaylist(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("SeleccionarVideoPlaylist.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * La funcion permite al usuario seleccionar un video de la tabla
     * @author luis Diego Obando
     * @param event recibe un parametro de evento.
     * @throws Exception signo de que algun tipo de excepcion ha ocurrido.
     */
    public void seleccionarVideo(ActionEvent event) throws Exception {
        ObservableList<Video> videoSeleccionado;
        videoSeleccionado = tblVideos.getSelectionModel().getSelectedItems();
        String path = videoSeleccionado.get(0).getLocalizacion();
        String nombre = videoSeleccionado.get(0).getName();
        String categoria = videoSeleccionado.get(0).getCategory();

        DAOVideo daoVideo = new DAOVideoImpl();
        DAOPlaylist daoPlaylist = new DAOPlaylistImpl();

        int idVideo = daoVideo.obtenerIdVideo(nombre, categoria, path);
        int idplaylist = daoPlaylist.obtenerIdPlaylist(ControllerAgregarVideoPlaylist.getNombrePlaylist(),ControllerLogin.getIdUsuarioIngresado());
        daoPlaylist.agregarVideoPlaylist(idplaylist, idVideo);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("El video fue agregado exitosamente a la playlist");
        alert.show();
    }

    /**
     * La funcion permite inicializar todas las tablas que se encuentran en el FXML
     * @author Gabriel Porras
     * @param url Recibe un objeto tipo url
     * @param resourceBundle recibe un objeto tipo resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nombrePlaylistLB.setText("Agregar Video a " + ControllerAgregarVideoPlaylist.getNombrePlaylist() + " playlist");
        DAOVideo daoVideo = new DAOVideoImpl();
        videosList = FXCollections.observableArrayList();
        ArrayList<Video> videosSeleccionados = null;
        try {
            videosSeleccionados = daoVideo.listarVideos();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        videosList.setAll(videosSeleccionados);
        this.nombreCO.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.categoriaCO.setCellValueFactory(new PropertyValueFactory<>("category"));
        this.duracionCO.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        this.localizacionCO.setCellValueFactory(new PropertyValueFactory<>("localizacion"));

        tblVideos.setItems(videosList);
    }
}
