package model;

import java.util.Objects;

public class Usuario {

    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String identifiacion;
    private String nombreUsuario;
    private String contrasena;
    private String avatar;

    public Usuario(String nombre, String primerApellido, String segundoApellido, String identifiacion, String nombreUsuario, String contrasena, String avatar) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.identifiacion = identifiacion;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.avatar = avatar;
    }
    public Usuario(){

    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getIdentifiacion() {
        return identifiacion;
    }

    public void setIdentifiacion(String identifiacion) {
        this.identifiacion = identifiacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre) && Objects.equals(primerApellido, usuario.primerApellido) && Objects.equals(segundoApellido, usuario.segundoApellido) && Objects.equals(identifiacion, usuario.identifiacion) && Objects.equals(nombreUsuario, usuario.nombreUsuario) && Objects.equals(contrasena, usuario.contrasena) && Objects.equals(avatar, usuario.avatar);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", primerApellido='" + primerApellido + '\'' +
                ", segundoApellido='" + segundoApellido + '\'' +
                ", identifiacion='" + identifiacion + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
