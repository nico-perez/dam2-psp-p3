package dev.el_nico.dam2_psp_p3;

import org.junit.jupiter.api.Test;

public class RetirarDeHistorialTest {
    
    @Test 
    public void hole() {
        HistorialDeMensajes h = new HistorialDeMensajes(4);
        h.insertar(new Mensaje(2));
        h.insertar(new Mensaje(0));
        h.insertar(new Mensaje(3));
        h.insertar(new Mensaje(1));
        
        h.retirarMasAntiguo();
        System.out.println(h);
    }

}
