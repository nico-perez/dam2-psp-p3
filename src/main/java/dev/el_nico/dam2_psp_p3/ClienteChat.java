package dev.el_nico.dam2_psp_p3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClienteChat {

    public static void nuevo(final String IP, final int PUERTO) {
        
        try (Socket socketTcp = new Socket(IP, PUERTO); Scanner s = new Scanner(System.in)) {

            // Obtenemos los canales de entrada de datos y de salida
            DataInputStream outputServidor = new DataInputStream(socketTcp.getInputStream());
            DataOutputStream inputServidor = new DataOutputStream(socketTcp.getOutputStream());

            // Enviamos un mensaje y esperamos la respuesta del servidor
            String linea;
            while (!(linea = s.nextLine()).equals("*")) {
                inputServidor.writeUTF(linea);
                String mensajeDelServidor = outputServidor.readUTF();
                System.out.println("Recibido mensaje del servidor: " + mensajeDelServidor);
            }
            
            // cerrar ocnexion
            inputServidor.writeUTF(linea);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ClienteChat.nuevo("localhost", 6969);
    }

}
