package view;

import DBConexion.DAOVideoImpl;
import Interfaces.DAOVideo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;

public class ControllerAgregarVideo {

    @FXML
    private TextField nombreTF;

    @FXML
    private TextField categoriaTF;

    @FXML
    private TextField descripcionTF;

    @FXML
    private Label videoLB;

    @FXML
    private Media mediaVideo;
    @FXML
    private MediaPlayer mediaPlayer;

    @FXML
    private Label videoDuracion;

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Cmabia la pantalla actual a la pantalla principal
     * @author Gabriel Porras Brenes
     * @param event un evento representando un tipo de action
     * @throws IOException signo de que algun tipo de excepcion ha ocurrido
     */
    public void volverPrincipal(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @author Gabriel Porras Brenes, Michael Ng
     * funcion que busca un documento .mp4 en el explorador de archivos y obtiene el nombre del archivo
     */
    public void buscarVideo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Video");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4", "*.mp4")
        );

        File vidFile = fileChooser.showOpenDialog(null);
        if (vidFile != null) {
            videoLB.setText(vidFile.toString());
            mediaVideo = new Media(new File(videoLB.getText()).toURI().toString());
            mediaPlayer = new MediaPlayer(mediaVideo);


            mediaPlayer.totalDurationProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    String tiempoVideo =  obtenerTiempo(mediaPlayer.getTotalDuration());
                    videoDuracion.setText(tiempoVideo);
                    System.out.println("VideoDuracion" + videoDuracion.getText());
                }
            });
        }
    }


    /**
     * Funcion que hace la conversion de tiempo para el video
     * @author Michael Ng
     * @param tiempo recibe como parametro el tiempo del video.
     * @return retorna un String del tiempo del Video
     */
    public String obtenerTiempo(Duration tiempo) {

        int horas = (int) tiempo.toHours();
        int minutos = (int) tiempo.toMinutes();
        int segundos = (int) tiempo.toSeconds();

        if (segundos > 59) {
            segundos = segundos % 60;
        }
        if (minutos > 59) {
            minutos = minutos % 60;
        }
        if (horas > 59) {
            horas = horas % 60;
        }

        if (horas > 0) {
            return String.format("%d:%02d:%02d",
                    horas,
                    minutos,
                    segundos);
        } else {
            return String.format("%02d:%02d",
                    minutos,
                    segundos);
        }
    }

    /**
     * @author Gabriel Porras Brenes
     *funcion que agrega un video a la base de datos
     */
    public void botonAgregar() {
        String nombre = nombreTF.getText();
        String categoria = categoriaTF.getText();
        String descripcion = descripcionTF.getText();
        String fechaAnadido = java.time.LocalDate.now().toString();
        String localizacion = videoLB.getText();
        String duracion = videoDuracion.getText() ;
        if (nombreTF.getText().isEmpty() || categoriaTF.getText().isEmpty() || descripcionTF.getText().isEmpty() || localizacion.isEmpty()) {
            System.out.println("Hay un espacio sin llenar o no hay video seleccionado");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Hay un espacio sin llenar o no hay video seleccionado");
            alert.show();
        } else {
            try {
                DAOVideo daoVideo = new DAOVideoImpl();
                daoVideo.agregarVideos(nombre, categoria, duracion, fechaAnadido, descripcion,localizacion );
                System.out.println("El video fue agregado exitosamente");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("El video fue agregado exitosamente");
                alert.show();
                ControllerBusqueda.accederBase = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
