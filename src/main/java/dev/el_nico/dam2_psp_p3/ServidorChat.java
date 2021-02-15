package dev.el_nico.dam2_psp_p3;

import java.io.IOException;
import java.net.ServerSocket;

public class ServidorChat {

    public final int PUERTO = 6969;
    public final int MAX_USUARIOS = 100;
    public final int MAX_MENSAJES = 3829;

    private final MonitorMensajes monit = new MonitorMensajes(MAX_USUARIOS, MAX_MENSAJES);

    private ServidorChat() {}

    public void abrir(final int PUERTO) {
        System.out.println("Abriendo el servidor...");
        try (ServerSocket s = new ServerSocket(PUERTO)) {
        
            while (true) {
                monit.nuevoUsuario(s.accept());
            }
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServidorChat().abrir(6969);
    }
}
