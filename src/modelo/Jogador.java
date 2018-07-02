package modelo;

import java.net.Socket;

public class Jogador {

    private final String id;
    private final Socket cliente;

    public Jogador(Socket cliente, String id) {
        this.cliente = cliente;
        this.id = id;
    }

    public Socket getCliente() {
        return cliente;
    }


    public String getId() {
        return id;
    }

}
