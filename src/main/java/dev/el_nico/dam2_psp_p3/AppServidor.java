package dev.el_nico.dam2_psp_p3;

import java.io.IOException;
import java.net.ServerSocket;

public class AppServidor {

    public final int PUERTO = 6969;
    public final int MAX_USUARIOS = 3;
    public final int MAX_MENSAJES = 3829;

    private final ComunHilos monit = new ComunHilos(MAX_USUARIOS, MAX_MENSAJES);

    private AppServidor() {}

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
        new AppServidor().abrir(6969);
    }
}
