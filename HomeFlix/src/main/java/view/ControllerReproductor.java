package view;

import Threads.ProcesoServidor;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.ValueAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

public class ControllerReproductor implements Initializable {

    @FXML
    private MediaView mvVideo;

    @FXML
    private VBox vBoxParent;

    private MediaPlayer mpVideo;

    private Media mediaVideo;

    @FXML
    private HBox hboxControls;

    @FXML
    private HBox hboxVolume;

    @FXML
    private Button buttonPPR;

    @FXML
    private Label labelCurrentTime;

    @FXML
    private Label labelTotalTime;

    @FXML
    private Label labelSpeed;

    @FXML
    private Label labelVolume;

    @FXML
    private Label labelFullScreen;

    @FXML
    private Slider sliderVolume;

    @FXML
    private Slider sliderTime;

    private boolean atEndOfVideo = false;

    private boolean isPlaying = true;

    private boolean isMuted = true;

    private ImageView ivPlay;

    private ImageView ivPause;

    private ImageView ivRestart;

    private ImageView ivVolume;

    private ImageView ivFullScreen;

    private ImageView ivMute;

    private ImageView ivExit;

    private static String path;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public static void setPath(String path) {
        ControllerReproductor.path = path;
    }

    public static String getPath() {
        return path;
    }

    /**
     *
     *  Funcion Inicializar que se encarga de inicializar objetos que se inicializan en el FXML.
     *
     * @param url Parametro URL que recibe un objeto url.
     * @param resourceBundle Parametro ResourceBundle que recibe un objeto ResourceBundle.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mediaVideo = new Media(new File(path).toURI().toString());
        mpVideo = new MediaPlayer(mediaVideo);
        mvVideo.setMediaPlayer(mpVideo);


        final int ivTamano = 25;

        Image imagePlay = new Image(new File("src/main/resources/view/play.png").toURI().toString());
        ivPlay = new ImageView(imagePlay);
        ivPlay.setFitHeight(ivTamano);
        ivPlay.setFitWidth(ivTamano);

        Image imageStop = new Image(new File("src/main/resources/view/pause.jpg").toURI().toString());
        ivPause = new ImageView(imageStop);
        ivPause.setFitHeight(ivTamano);
        ivPause.setFitWidth(ivTamano);

        Image imageRestart = new Image(new File("src/main/resources/view/restart.png").toURI().toString());
        ivRestart = new ImageView(imageRestart);
        ivRestart.setFitHeight(ivTamano);
        ivRestart.setFitWidth(ivTamano);

        Image imageVol = new Image(new File("src/main/resources/view/volumen.png").toURI().toString());
        ivVolume = new ImageView(imageVol);
        ivVolume.setFitHeight(ivTamano);
        ivVolume.setFitWidth(ivTamano);

        Image imageFull = new Image(new File("src/main/resources/view/FullScreen.png").toURI().toString());
        ivFullScreen = new ImageView(imageFull);
        ivFullScreen.setFitHeight(ivTamano);
        ivFullScreen.setFitWidth(ivTamano);

        Image imageMute = new Image(new File("src/main/resources/view/volumeMuted.jpg").toURI().toString());
        ivMute = new ImageView(imageMute);
        ivMute.setFitHeight(ivTamano);
        ivMute.setFitWidth(ivTamano);

        Image imageExit = new Image(new File("src/main/resources/view/exitfs.png").toURI().toString());
        ivExit = new ImageView(imageExit);
        ivExit.setFitHeight(ivTamano);
        ivExit.setFitWidth(ivTamano);

        buttonPPR.setGraphic(ivPause);
        labelVolume.setGraphic(ivMute);
        labelSpeed.setText("1X");
        labelFullScreen.setGraphic(ivFullScreen);

        buttonPPR.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * Funcion que se encarga de controlar el boton del play para otras funciones.
             * @author Gabriel Porras Brenes
             * @param actionEvent Recibe una accion dentro de la ventana del JavaFx
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                tiempoActualVideo();
                Button buttonPlay = (Button) actionEvent.getSource();
                if (atEndOfVideo) {
                    sliderTime.setValue(0);//para el video
                    atEndOfVideo = false;
                    isPlaying = false;
                }
                if (isPlaying) {//si esta en pausa
                    buttonPlay.setGraphic(ivPlay);//cuando este en play cambia la foto de play
                    mpVideo.pause();
                    isPlaying = false;
                } else {
                    buttonPlay.setGraphic(ivPause);
                    mpVideo.play();
                    isPlaying = true;
                }
            }
        });

        hboxVolume.getChildren().remove(sliderVolume);
        mpVideo.volumeProperty().bindBidirectional(sliderVolume.valueProperty());

        tiempoActualVideo();

        sliderVolume.valueProperty().addListener(new InvalidationListener() {

            /**
             * Funcion que se encarga del volumen y si el video tiene un volumen = 0 cambia la imagen a un icono de muteado.
             * @author Gabriel Porras Brenes
             * @param observable parametro que recibe un objeto Observable.
             */

            @Override
            public void invalidated(Observable observable) {
                mpVideo.setVolume(sliderVolume.getValue());
                if (mpVideo.getVolume() != 0.0) {//si el volumen es distinto a 0 la imagen del volumen cambia a muteado
                    labelVolume.setGraphic(ivVolume);
                    isMuted = false;
                } else {
                    labelVolume.setGraphic(ivMute);
                    isMuted = true;
                }
            }
        });

        labelSpeed.setOnMouseClicked(new EventHandler<MouseEvent>() {

            /**
             * Funcion que se encarga de acelerar el video.
             * @author Gabriel Porras Brenes
             * @param mouseEvent parametro que recibe una accion en javaFx
             */

            @Override
            public void handle(MouseEvent mouseEvent) {
                if(labelSpeed.getText().equalsIgnoreCase("1X")) {
                    labelSpeed.setText("2X");
                    mpVideo.setRate(2.0);
                } else {
                    labelSpeed.setText("1X");
                    mpVideo.setRate(1.0);
                }
            }
        });

        labelVolume.setOnMouseClicked(new EventHandler<MouseEvent>() {//modifica la imagen del volumen y el nivel del volumen

            /**
             *
             * Funcion que se encarga de verificar si el volumen es igual  a 0 y mutea el video.
             * @author Gabriel Porras Brenes
             * @param mouseEvent recibe una accion en el javaFx.
             */

            @Override
            public void handle(MouseEvent mouseEvent) {
                if (isMuted) {//cuando modifique el volumen si el volumen esta en 0.0 cambiara a no muteado
                    labelVolume.setGraphic(ivVolume);
                    sliderVolume.setValue(0.2);
                    isMuted = false;
                } else {//cuando modifique el volumen si esta en 0.0 cambiara a muteado
                    labelVolume.setGraphic(ivMute);
                    sliderVolume.setValue(0);
                    isMuted = true;
                }
            }
        });

        labelVolume.setOnMouseEntered(new EventHandler<MouseEvent>() {
            /**
             * La funcion permite mover el slider del volumen
             * @author Michael Ng
             * @param mouseEvent
             */
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (hboxVolume.lookup("#sliderVolume") == null) {
                    hboxVolume.getChildren().add(sliderVolume);
                    sliderVolume.setValue(mpVideo.getVolume());
                }
            }
        });

        hboxVolume.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hboxVolume.getChildren().remove(sliderVolume);
            }
        });

        vBoxParent.sceneProperty().addListener(new ChangeListener<Scene>() {

            /**
             * Funcion que se encarga de cambia rel tamaño de la escena y evita que los controles se sobrepongan.
             * @author Gabriel Porras Brenes
             * @param observableValue Recibe un objeto observableValue
             * @param escenaVieja Recibe una escena.
             * @param nuevaEscena Recibe otra escena.
             */
            @Override
            public void changed(ObservableValue<? extends Scene> observableValue, Scene escenaVieja, Scene nuevaEscena) {//esta funcion se llama cuando cambia la escena de tamanio, utiliza la vieja escena con la nueva
                if (escenaVieja == null && nuevaEscena != null) {
                    mvVideo.fitHeightProperty().bind(nuevaEscena.heightProperty().subtract(hboxControls.heightProperty().add(20)));//cambia el tamanio del scene
                    //le quitamos el div de los controles para que nuevaEscena no se ponga encima de los controles que hemos puesto
                }
            }
        });

        labelFullScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {

            /**
             * Funcion que se encarga de que a la hora de ser clickeado el label cambie el tamaño del video.
             * @author Gabriel Porras Brenes
             * @param mouseEvent Recibe como parametro una accion en JavaFx.
             */
            @Override
            public void handle(MouseEvent mouseEvent) {
                Label label = (Label) mouseEvent.getSource();
                Stage stage = (Stage) label.getScene().getWindow();
                if (stage.isFullScreen()) {
                    stage.setFullScreen(false);
                    labelFullScreen.setGraphic(ivFullScreen);
                } else {
                    stage.setFullScreen(true);
                    labelFullScreen.setGraphic(ivExit);
                }
                stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode() == KeyCode.ESCAPE) {
                            labelFullScreen.setGraphic(ivFullScreen);
                        }
                    }
                });
            }
        });

        mpVideo.totalDurationProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration viejaDuracion, Duration nuevaDuracion) {
                tiempoActualVideo();
                sliderTime.setMax(nuevaDuracion.toSeconds());
                labelTotalTime.setText(obtenerTiempo(nuevaDuracion));
            }
        });

        sliderTime.valueChangingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean cambiaba, Boolean estaCambiando) {
                tiempoActualVideo();
                if (!estaCambiando) {
                    mpVideo.seek(Duration.seconds(sliderTime.getValue()));
                }
            }
        });

        sliderTime.valueProperty().addListener(new ChangeListener<Number>() {

            /**
             *
             * Funcion que se encarga de de sincornizar el slider y el tiempo del video.
             * @author Gabriel Porras Brenes
             * @param observableValue Recibe un parametro observableValue
             * @param viejoNum  Recibe un numero.
             * @param nuevoNum  Recibe otro numero.
             */
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number viejoNum, Number nuevoNum) {
                tiempoActualVideo();
                double tiempoActual = mpVideo.getCurrentTime().toSeconds();
                if (Math.abs(tiempoActual - nuevoNum.doubleValue()) > 0.5) {
                    mpVideo.seek(Duration.seconds(nuevoNum.doubleValue()));
                }
                labelEsFinalVideo(labelCurrentTime.getText(), labelTotalTime.getText());
            }
        });

        mpVideo.currentTimeProperty().addListener(new ChangeListener<Duration>() {


            /**
             * Funcion que se encarga de comparar el tiempo con el Slider.
             * @author Gabriel Porras Brenes
             * @param observableValue  parametro de objeto Observable.
             * @param viejaDuracion  Recibe como parametro una duracion.
             * @param nuevaDuracion Recibe como parametro otra duracion.
             */
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration viejaDuracion, Duration nuevaDuracion) {
                tiempoActualVideo();
                if (sliderTime.isValueChanging()) {
                    sliderTime.setValue(nuevaDuracion.toSeconds());
                }
                labelEsFinalVideo(labelCurrentTime.getText(), labelTotalTime.getText());
            }
        });

        mpVideo.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                buttonPPR.setGraphic(ivRestart);
                atEndOfVideo = true;
                if(!labelCurrentTime.textProperty().equals(labelTotalTime.textProperty())){
                    labelCurrentTime.textProperty().unbind();
                    labelCurrentTime.setText(obtenerTiempo(mpVideo.getTotalDuration()) + " / ");
                }
            }
        });
    }

    /**
     *  Funcion que se encarga de obtener el tiempo actual del video.
     */
    public void tiempoActualVideo() {

        labelCurrentTime.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return obtenerTiempo(mpVideo.getCurrentTime()) + " / ";
            }
        }, mpVideo.currentTimeProperty()));

    }

    /**
     *
     * Funcion que se encarga de obtener el tiempo del video.
     * @author Gabriel Porras Brenes
     * @param tiempo recibe como parametro el tiempo del video.
     * @return retorna un tring en minutos con segundos.
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
     *
     * Funcion que se encarga de verificar si el video ya se terino.
     * @author Gabriel Porras Brenes
     * @param labelTiempo  recibe un label del JavaFx
     * @param labelTiempoTotal recibe un label del JavaFx
     */
    public void labelEsFinalVideo(String labelTiempo, String labelTiempoTotal) {
        for (int i = 0; i < labelTiempoTotal.length(); i++) {
            if (labelTiempo.charAt(i) != labelTiempoTotal.charAt(i)) {
                atEndOfVideo = false;
                if (isPlaying) {
                    buttonPPR.setGraphic(ivPause);
                } else {
                    buttonPPR.setGraphic(ivPlay);
                    break;
                }
            } else {
                atEndOfVideo = true;
                buttonPPR.setGraphic(ivRestart);
            }
        }
    }

    /**
     * Funcion que se encarga de volver a la pagina principal con un boton.
     * @author Michael Ng
     * @param event recibe un evento en Javafx
     * @throws IOException Recibe un parametro IOException
     */
    public void volverBusqueda(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
