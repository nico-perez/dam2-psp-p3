package dev.el_nico.dam2_psp_p3;

public class HistorialDeMensajes {

    private final Object NO_ESTA_VACIO;
    private final Object NO_ESTA_LLENO;

    private final int MAX_ELEMENTOS;
    private final ContainerMsj[] mensajes;

    private Integer indicePrimerMensaje;
    private Integer indiceUltimoMensaje;
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

    protected void insertar(final Mensaje m) {

        ContainerMsj nuevo = new ContainerMsj(m);

        if (indiceUltimoMensaje == null) {
            // no hay ningun mensaje todavia
            mensajes[0] = nuevo;
            indicePrimerMensaje = indiceUltimoMensaje = 0;
            numeroElementos++;

        } else {
            // si que hay mensjaes!
            ContainerMsj ptr = mensajes[indiceUltimoMensaje];
            while (m.timestamp < ptr.getTimestamp()) {
                if (ptr.indiceAnterior != null) {
                    ptr = mensajes[ptr.indiceAnterior];
                } else {
                    ptr = null; // ptr aputnaba al primer mensaje
                    break;
                }
            }

            if (ptr == null) {
                // sustituri primero (OK)
                mensajes[indicePrimerMensaje].indiceAnterior = numeroElementos;
                nuevo.indiceSiguiente = indicePrimerMensaje;
                indicePrimerMensaje = numeroElementos;
            } else if (ptr.indiceSiguiente == null) {
                // añadir al final (OK)
                mensajes[indiceUltimoMensaje].indiceSiguiente = numeroElementos;
                nuevo.indiceAnterior = indiceUltimoMensaje;
                indiceUltimoMensaje = numeroElementos;
            } else {
                // agnadir entre medio (OK??)
                nuevo.indiceAnterior = mensajes[ptr.indiceSiguiente].indiceAnterior;
                nuevo.indiceSiguiente = ptr.indiceSiguiente;
                ptr.indiceSiguiente = mensajes[ptr.indiceSiguiente].indiceAnterior = numeroElementos;
            }
            mensajes[numeroElementos++] = nuevo;
        }
    }

    // no ok!
    protected void retirarMasAntiguo() {
        
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
/*
        mensajes[indicePrimerMensaje] = ultEnArray;
        mensajes[ultEnArray.indiceAnterior].indiceSiguiente = indicePrimerMensaje; // falla aqui
        if (ultEnArray.indiceSiguiente != null) {
            mensajes[ultEnArray.indiceSiguiente].indiceAnterior = indicePrimerMensaje;
        } else {
            indiceUltimoMensaje = indicePrimerMensaje;
        }

        numeroElementos--;

        if (numeroElementos == 0) {
            indicePrimerMensaje = indiceUltimoMensaje = null;
        } else {
            indicePrimerMensaje = indiceDelNuevoMasAntiguo;
            mensajes[indiceDelNuevoMasAntiguo].indiceAnterior = null;
        }
        
*/
    }

    protected class ContainerMsj {

        protected Integer indiceSiguiente;
        protected Integer indiceAnterior;
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
    }

    @Override
    public String toString() {
        String s = "";
        ContainerMsj m = mensajes[indicePrimerMensaje];
        while (m != null) {
            s += " " + m.mensaje.timestamp + " ";
            if (m.indiceSiguiente != null) { 
                m = mensajes[m.indiceSiguiente];
            } else { 
                m = null;
            }
        }
        return s;
    }
}
