package model;

import java.util.ArrayList;

public class Playlist {
    private ArrayList<Video> listaReproduccion;
    private String nombre;
    private Usuario creador;
    private String fechaCreacion;
    private double duracion;

    public Playlist(ArrayList<Video> listaReproduccion, String nombre, Usuario creador, String fechaCreacion, double duracion) {
        this.listaReproduccion = listaReproduccion;
        this.nombre = nombre;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
        this.duracion = duracion;
    }
    public Playlist(String nombre, Usuario creador, String fechaCreacion, double duracion) {
        this.listaReproduccion = new ArrayList<Video>();
        this.nombre = nombre;
        this.creador = creador;
        this.fechaCreacion = fechaCreacion;
        this.duracion = duracion;
    }

    public Playlist(){

    }

    public ArrayList<Video> getListaReproduccion() {
        return listaReproduccion;
    }

    public void setListaReproduccion(ArrayList<Video> listaReproduccion) {
        this.listaReproduccion = listaReproduccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "listaReproduccion=" + listaReproduccion +
                ", nombre='" + nombre + '\'' +
                ", creador=" + creador +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", duracion=" + duracion +
                '}';
    }
}
