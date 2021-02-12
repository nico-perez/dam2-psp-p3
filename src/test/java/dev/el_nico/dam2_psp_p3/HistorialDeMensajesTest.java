package dev.el_nico.dam2_psp_p3;

import org.junit.jupiter.api.Test;

public class HistorialDeMensajesTest {

    public static class Historial extends HistorialDeMensajes {
        public Historial() {
            super(32);
        }
    }
    
    Historial historial = new Historial();

    @Test
    public void meteSaca() {
        
        insertarEnHistorial(0);
        insertarEnHistorial(1);
        insertarEnHistorial(3);
        insertarEnHistorial(4);
        insertarEnHistorial(2);
        retirarDeHistorial();
        insertarEnHistorial(5);
        insertarEnHistorial(4);
        insertarEnHistorial(6);
        retirarDeHistorial();
        retirarDeHistorial();
        insertarEnHistorial(1);
        insertarEnHistorial(4);
        retirarDeHistorial();
        retirarDeHistorial();
        retirarDeHistorial();
        retirarDeHistorial();
        retirarDeHistorial();
        retirarDeHistorial();
        retirarDeHistorial();

    }

    public void insertarEnHistorial(int i) {
        historial.insertar(new Mensaje(i));
        System.out.println(historial);
    }

    public void retirarDeHistorial() {
        historial.retirarMasAntiguo();
        System.out.println(historial);
    }
}
