package dev.el_nico.dam2_psp_p3;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class AtendedorDeCliente {

    private final Socket socket;
    private final BlockingQueue<Mensaje> bufferSalida;
    private final BlockingQueue<Mensaje> bufferEntrada;

    private Thread recibidorDeMensajes;
    private Thread enviadorDeMensajes;

    private AtomicBoolean conexionAbierta;

    public AtendedorDeCliente(Socket socket) {
        this.socket = socket;
        bufferSalida = new ConcurrentCircularBuffer<>(32);
        bufferEntrada = new ConcurrentCircularBuffer<>(32);

        try {
            recibidorDeMensajes = new Thread() {
                private DataInputStream inputDelSocket = new DataInputStream(socket.getInputStream());

                @Override
                public void run() {
                    while (conexionAbierta.get()) {
                        try {
                            String texto = inputDelSocket.readUTF(), usuario = inputDelSocket.readUTF();
                            long timestamp = inputDelSocket.readLong();
                        
                            bufferEntrada.put(new Mensaje(timestamp, usuario, texto));
                        } catch (InterruptedException | IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
