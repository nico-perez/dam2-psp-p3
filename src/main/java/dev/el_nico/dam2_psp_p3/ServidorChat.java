package dev.el_nico.dam2_psp_p3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ServidorChat {

    public final int PUERTO = 6969;
    public final int MAX_USUARIOS = 20;
    public final int MAX_MENSAJES = 100;

    private BlockingQueue<Mensaje> historial = new ConcurrentCircularBuffer<>(MAX_MENSAJES);
    private BlockingQueue<Thread> usuarios = new ConcurrentCircularBuffer<>(MAX_USUARIOS);

    private ServidorChat() {}

    public void abrir(final int PUERTO) {
        try (ServerSocket s = new ServerSocket(PUERTO)) {
        
            while (true) {
                Socket cliente = s.accept();
                usuarios.add(new Thread() {

                    private BlockingQueue<Mensaje> buffer = new ConcurrentCircularBuffer<>(32);
                    
                    

                });
            }
            
            DataInputStream outCliente = new DataInputStream(cliente.getInputStream());
            DataOutputStream inputCliente = new DataOutputStream(cliente.getOutputStream());
            String linea;
            while (!(linea = outCliente.readUTF()).equals("*")) {
                inputCliente.writeUTF("recibido stirmg: " + linea);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ServidorChat().abrir(6969);
    }
}
