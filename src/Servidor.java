
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author caio
 */
public class Servidor {
    private ServerSocket server;
    private Socket cliente1, cliente2, clienteAtual;
    private boolean isFechado;
    
    
    public Servidor(){
        isFechado = true;
        cliente1 = new Socket();
        cliente2 = new Socket();
    }
    
    public boolean ligaServidor(int porta){
        try {
            server = new ServerSocket(porta);
            isFechado = false;
            return true;
        } catch (IOException ex) {
           System.err.printf("Erro ao ligar servidor!");
           return false;
        }
    }
    
    public boolean desligaServidor(){
        try {
            server.close();
            isFechado = true;
            return true;
        } catch (IOException ex) {
           System.err.printf("Erro ao desligar servidor!");
           return false;
        }
    }
    
    public boolean isFechado(){
        return isFechado;
    }
    
    public int quantConexao(){
        int cont = 0;
        if(cliente1 != null) cont++;
        if(cliente2 != null) cont++;
        return cont;
    }
    
    public boolean aceitarConexao(){
        if (!isFechado()){
            try {
                switch(quantConexao()){
                    case 0:
                        cliente1 = server.accept();
                        return true;
                    case 1:
                        cliente2 = server.accept();
                        return true;
                    case 2:
                        System.out.println("Servidor cheio!");
                        return false;
                }
            }
            catch (IOException ex){
                System.err.println("Erro ao aceitar conexao!");
                return false;
            }
        }
        System.out.println("Servidor desligado!");
        return false; 
    }
}
