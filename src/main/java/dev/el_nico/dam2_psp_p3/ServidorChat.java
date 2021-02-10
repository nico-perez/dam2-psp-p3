package dev.el_nico.dam2_psp_p3;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ServidorChat {

    public final int PUERTO = 6969;
    public final int MAX_USUARIOS = 20;
    public final int MAX_MENSAJES = 100;

    BlockingQueue<Mensaje> historial = new ConcurrentCircularBuffer<>(MAX_MENSAJES);
    BlockingQueue<Thread> usuarios = new ConcurrentCircularBuffer<>(MAX_USUARIOS);

    public ServidorChat(int puerto) {

        try (ServerSocket s = new ServerSocket(PUERTO)) {

            while (true) {
                Thread hiloUsuario = new Thread()
                {
                    private Socket socket = s.accept();

                    @Override
                    public void run() {
                        try {
                            DataInputStream dis = new DataInputStream(socket.getInputStream());
                            
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                
                usuarios.put(hiloUsuario);
                hiloUsuario.start();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void atender() {

    }
}
