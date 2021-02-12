package dev.el_nico.dam2_psp_p3;


public class Mensaje {
    public final long timestamp;
    public final String usuario;
    public final String texto;

    public Mensaje(long timestamp) {
        this.timestamp = timestamp;
        usuario = texto = null;
    }

    public Mensaje(long timestamp, String usuario, String texto) {
        this.timestamp = timestamp;
        this.usuario = usuario;
        this.texto = texto;
    }
}
