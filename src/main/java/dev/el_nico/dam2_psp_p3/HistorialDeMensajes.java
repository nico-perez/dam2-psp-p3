package dev.el_nico.dam2_psp_p3;

public class HistorialDeMensajes {

    private final Object NO_ESTA_VACIO;
    private final Object NO_ESTA_LLENO;

    private final int MAX_ELEMENTOS;
    private final ContainerMsj[] mensajes;

    private Integer primero;
    private Integer ultimo;
    private int numeroElementos;

    public HistorialDeMensajes(final int MAX_ELEMENTOS) {
        if (MAX_ELEMENTOS < 1) {
            throw new IllegalArgumentException("La capacidad máxima tiene que ser de, al menos, una unidad");
        }
        this.MAX_ELEMENTOS = MAX_ELEMENTOS;

        NO_ESTA_VACIO = new Object();
        NO_ESTA_LLENO = new Object();

        mensajes = new ContainerMsj[MAX_ELEMENTOS];
    }

    public boolean estaLleno() {
        return numeroElementos == MAX_ELEMENTOS;
    }

    public void insertar(Mensaje m) {

        ContainerMsj nuevo = new ContainerMsj(m);

        if (ultimo == null) {
            // no hay ningun mensaje todavia
            mensajes[0] = nuevo;
            primero = ultimo = 0;
            numeroElementos++;

        } else {
            // si que hay mensjaes!
            ContainerMsj ptr = mensajes[ultimo];
            while (m.timestamp < ptr.getTimestamp()) {
                if (ptr.anterior != null) {
                    ptr = mensajes[ptr.anterior];
                } else {
                    ptr = null; // ptr aputnaba al primer mensaje
                    break;
                }
            }

            if (ptr == null) {
                // sustituri primero (OK)
                mensajes[primero].anterior = numeroElementos;
                nuevo.siguiente = primero;
                primero = numeroElementos;
            } else if (ptr.siguiente == null) {
                // añadir al final (OK)
                mensajes[ultimo].siguiente = numeroElementos;
                nuevo.anterior = ultimo;
                ultimo = numeroElementos;
            } else {
                // agnadir entre medio (OK??)
                nuevo.anterior = mensajes[ptr.siguiente].anterior;
                nuevo.siguiente = ptr.siguiente;
                ptr.siguiente = mensajes[ptr.siguiente].anterior = numeroElementos;
            }
            mensajes[numeroElementos++] = nuevo;
        }
    }

    // no ok!

    public void retirarMasAntiguo() {
        
        if (numeroElementos == 1) {

            primero = ultimo = null;
            numeroElementos = 0;

        } else if (numeroElementos > 1) {

            int siguienteAlPrimero = mensajes[primero].siguiente;
            ContainerMsj ultEnArray = mensajes[numeroElementos - 1];           

            if (siguienteAlPrimero == numeroElementos - 1) {
                // el mensaje a desplazar es tambien el que pasara a ser el primero otrav ez 

                ultEnArray.anterior = null;
                mensajes[ultEnArray.siguiente].anterior = primero;
                mensajes[primero] = ultEnArray;

            } else if (ultimo == numeroElementos - 1) {
                // el obj a desplazar es a la vez el ultimo de la lista

                mensajes[ultEnArray.anterior].siguiente = primero;
                mensajes[primero] = ultEnArray;
                ultimo = primero;
                mensajes[siguienteAlPrimero].anterior = null;
                primero = siguienteAlPrimero;

            } else {
                // caso normal no se

                mensajes[ultEnArray.siguiente].anterior = mensajes[ultEnArray.anterior].siguiente = primero;
                mensajes[primero] = ultEnArray;
            }

            if (--numeroElementos == 0) {
                primero = ultimo = null;
            }
        }
    }

    /*protected void retirarMasAntiguo() {
        
        if (numeroElementos == 1) {

            indicePrimerMensaje = indiceUltimoMensaje = null;
            numeroElementos = 0;

        } else if (numeroElementos > 1) {

            int indiceDelNuevoMasAntiguo = mensajes[indicePrimerMensaje].indiceSiguiente;
            ContainerMsj ultEnArray = mensajes[numeroElementos - 1];

            mensajes[indicePrimerMensaje] = ultEnArray;

            if (indiceDelNuevoMasAntiguo == numeroElementos - 1) {
                
                mensajes[ultEnArray.indiceSiguiente].indiceAnterior = indicePrimerMensaje;
                indicePrimerMensaje = indiceDelNuevoMasAntiguo;
            } else {

            }
        }

        mensajes[indicePrimerMensaje] = ultEnArray;
        mensajes[ultEnArray.indiceAnterior].indiceSiguiente = indicePrimerMensaje; // falla aqui
        if (ultEnArray.indiceSiguiente != null) {
            mensajes[ultEnArray.indiceSiguiente].indiceAnterior = indicePrimerMensaje;
        } else {
            indiceUltimoMensaje = indicePrimerMensaje;
        }

        numeroElementos--;

        if (numeroElementos == 0) {
            
        } else {
            indicePrimerMensaje = indiceDelNuevoMasAntiguo;
            mensajes[indiceDelNuevoMasAntiguo].indiceAnterior = null;
        }
    }*/

    protected class ContainerMsj {

        protected Integer siguiente;
        protected Integer anterior;
        protected Mensaje mensaje;

        protected ContainerMsj(Mensaje mensaje) { this.mensaje = mensaje; }

        public long getTimestamp() {
            return mensaje.timestamp;
        }

        public String getUsuario() {
            return mensaje.usuario;
        }

        public String getTexto() {
            return mensaje.texto;
        }

        @Override
        public String toString() {
            return "timestamp: " + mensaje.timestamp + "  siguiente: " + siguiente + "  anterior: " + anterior;
        }
    }

    @Override
    public String toString() {
        String s = "";
        ContainerMsj m = mensajes[primero];
        while (m != null) {
            s += " " + m.mensaje.timestamp + " ";
            if (m.siguiente != null) { 
                m = mensajes[m.siguiente];
            } else { 
                m = null;
            }
        }
        return s;
    }
}
