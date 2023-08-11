package model;

import java.util.ArrayList;
import model.Usuario;

public class Video {

    private String name;

    private String category;

    private String duracion;

    private String date;

    private String descripcion;

    private String localizacion;

    private ArrayList<Usuario> likes;

    public Video(String name, String category, String duracion, String date, String descripcion, String localizacion, ArrayList<Usuario> likes) {
        this.name = name;
        this.category = category;
        this.duracion = duracion;
        this.date = date;
        this.descripcion = descripcion;
        this.localizacion = localizacion;
        this.likes = likes;
    }
    public Video(){

    }
    public Video(String name, String category, String duracion, String date, String descripcion, String localizacion) {
        this.name = name;
        this.category = category;
        this.duracion = duracion;
        this.date = date;
        this.descripcion = descripcion;
        this.localizacion = localizacion;
        this.likes = new ArrayList<Usuario>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Usuario> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Usuario> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Video{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", duracion=" + duracion +
                ", date='" + date + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", localizacion='" + localizacion + '\'' +
                ", likes=" + likes +
                '}';
    }
}
