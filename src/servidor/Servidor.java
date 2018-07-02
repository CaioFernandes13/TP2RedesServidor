package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import modelo.Jogador;
import modelo.Mensagem;

public class Servidor {
    
    private ServerSocket serverSocket;
    private Socket socket1;
    private Socket socket2;
    Jogador player1, player2;
    ForcaControle controle;
    
    public void criarConexao(int porta) throws IOException {
        serverSocket = new ServerSocket(porta);
        controle = new ForcaControle();
    }
    
    public void esperaConexao() throws IOException {
        
        socket1 = serverSocket.accept();
        player1 = new Jogador(socket1, "0");
        trataPrimeiraConexao(socket1);
        estadoInicialJogo(socket1);
        System.out.println("1/2 Clientes conectados");
        socket2 = serverSocket.accept();
        player2 = new Jogador(socket2, "1");
        trataPrimeiraConexao(socket2);
        estadoInicialJogo(socket2);
        System.out.println("2/2 Clientes conectados");
        
    }
    
    private void trataPrimeiraConexao(Socket socket) throws IOException {
        System.out.println("Primeira Mensgem: ");
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        System.out.println("Recebendo mensagem...");

        System.out.println("Mensagem : " + input.readUTF() + " recebida.");
        System.out.println("Enviando Mensagem : ");
        if (player1.getCliente().equals(socket)) {
            output.writeUTF("0");
            System.out.println("Mensagem : 0 enviada.");
        } else {
            output.writeUTF("1");
            System.out.println("Mensagem: 1 envidada.");
        }
        output.flush();
    }
    
    private void estadoInicialJogo(Socket socket) throws IOException {
        System.out.println("Enviando Estado Inicial do Jogo...");
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        Mensagem m = new Mensagem();
        String codigo = m.codificarMensagem(0, controle.getPalavraOculta(), 0, 0, 0, 0, 0, 0, 0, "p");
        output.writeUTF(codigo);
        System.out.println("Mensage: " + codigo + " Enviado.");
        output.flush();
        
    }
    
    public String receberMensagem(Socket socket) throws IOException {
        System.out.println("Recebendo mensagem...");
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        String s = input.readUTF();
        System.out.println("Mensagem : " + s + " recebida.");
        return  s;
    }
    
    public void enviarMensagem(Socket socket, String msg) throws IOException {
        System.out.println("Enviadno Mensagem");
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        output.writeUTF(msg);
        System.out.println("Mensagem: " + msg + " enviado");
        output.flush();
        
    }
    
    public void trataConexao(Socket socket) throws IOException {
        System.out.println("Tratando conexão");
        String letra = receberMensagem(socket);
        System.out.println("Realizando a Jogada...");
        String out = " ";
        if (player1.getCliente().equals(socket)) {
            out = realizarJogada(letra, player1);
        } else {
            out = realizarJogada(letra, player2);
        }
        System.out.println("Jogada realizada...");
        enviarMensagem(socket1, out);
        enviarMensagem(socket2, out);
        System.out.println("Tratamento Encerrado...");
        
    }
    
    private String realizarJogada(String letra, Jogador player) throws IOException {
        int id = 2;
        if (player.getId().equalsIgnoreCase("0")) {
            id = 1;
        } else {
            id = 0;
        }
        Mensagem mensagem = new Mensagem();
        String out = "";
        if (letra.length() == 1) {
            if (this.controle.isAcerto(letra)) {
                if (this.controle.isCompleto()) {
                    if (player.getId().equals("0")) {
                        if (this.controle.getPontosPlayer1() == 1) {
                            out = jogador1GanhouPartida(mensagem, letra, id);
                        } else {
                            out = jogador1GanhouRodada(mensagem, letra, id);
                        }
                    } else {
                        if (this.controle.getPontosPlayer2() == 1) {
                            out = jogador2GanhouPartida(mensagem, letra, id);
                        } else {
                            out = jogador2GanhouRodada(mensagem, letra, id);
                        }
                    }
                } else {
                    out = jogadoresContinuamJogando(mensagem, letra, id);
                }
            } else {
                if (this.controle.getNumeroDeErros() == 6) {
                    out = jogadoresEmpataram(mensagem, letra, id);
                } else {
                    out = jogadoresContinuamJogando(mensagem, letra, id);
                }
            }
        } else {
            if (letra.equalsIgnoreCase("desistir")) {
                if (player.getId().equals("0")) {
                    if (this.controle.getPontosPlayer2() == 1) {
                        out = jogador2GanhouPartida(mensagem, letra, id);
                    } else {
                        out = jogador2GanhouRodada(mensagem, letra, id);
                    }
                } else {
                    if (this.controle.getPontosPlayer1() == 1) {
                        out = jogador1GanhouPartida(mensagem, letra, id);
                    } else {
                        out = jogador1GanhouRodada(mensagem, letra, id);
                    }
                }
            } else {
                if (this.controle.forca.getPalavra().equalsIgnoreCase(letra)) {
                    if (player.getId().equals("0")) {
                        if (this.controle.getPontosPlayer1() == 1) {
                            out = jogador1GanhouPartida(mensagem, letra, id);
                        } else {
                            out = jogador1GanhouRodada(mensagem, letra, id);
                        }
                    } else {
                        if (this.controle.getPontosPlayer2() == 1) {
                            out = jogador2GanhouPartida(mensagem, letra, id);
                        } else {
                            out = jogador2GanhouRodada(mensagem, letra, id);
                        }
                    }
                } else {
                    if (player.getId().equals("0")) {
                        if (this.controle.getPontosPlayer2() == 1) {
                            out = jogador2GanhouPartida(mensagem, letra, id);
                        } else {
                            out = jogador2GanhouRodada(mensagem, letra, id);
                        }
                    } else {
                        if (this.controle.getPontosPlayer1() == 1) {
                            out = jogador1GanhouPartida(mensagem, letra, id);
                        } else {
                            out = jogador1GanhouRodada(mensagem, letra, id);
                        }
                    }
                }
                
            }
        }
        return out;
    }
    
    private String jogador1GanhouRodada(Mensagem msg, String letra, int id) throws IOException {
        controle.novaRodada();
        this.controle.setPontosPlayer1();
        String out = msg.codificarMensagem(id, controle.forca.getPalavra(),
                1, 0, 1, 0, controle.getPontosPlayer1(), controle.getPontosPlayer2(), controle.getNumeroDeErros(), letra);
        return out;
    }
    
    private String jogador2GanhouRodada(Mensagem msg, String letra, int id) throws IOException {
        controle.novaRodada();
        this.controle.setPontosPlayer2();
        String out = msg.codificarMensagem(id, controle.forca.getPalavra(),
                2, 0, 1, 0, controle.getPontosPlayer1(), controle.getPontosPlayer2(), controle.getNumeroDeErros(), letra);
        return out;
    }
    
    private String jogador1GanhouPartida(Mensagem msg, String letra, int id) throws IOException {
        this.controle.setPontosPlayer1();
        String out = msg.codificarMensagem(id, controle.forca.getPalavra(),
                1, 1, 1, 1, controle.getPontosPlayer1(), controle.getPontosPlayer2(), controle.getNumeroDeErros(), letra);
        return out;
    }
    
    private String jogador2GanhouPartida(Mensagem msg, String letra, int id) throws IOException {
        this.controle.setPontosPlayer2();
        String out = msg.codificarMensagem(id, controle.forca.getPalavra(),
                2, 2, 1, 1, controle.getPontosPlayer1(), controle.getPontosPlayer2(), controle.getNumeroDeErros(), letra);
        return out;
    }
    
    private String jogadoresEmpataram(Mensagem msg, String letra, int id) throws IOException {
        controle.novaRodada();
        String out = msg.codificarMensagem(id, controle.forca.getPalavra(),
                0, 0, 1, 0, controle.getPontosPlayer1(), controle.getPontosPlayer2(), controle.getNumeroDeErros(), letra);
        return out;
    }
    
    private String jogadoresContinuamJogando(Mensagem msg, String letra, int id) throws IOException {
        String out = msg.codificarMensagem(id, controle.getPalavraOculta(),
                0, 0, 0, 0, controle.getPontosPlayer1(), controle.getPontosPlayer2(), controle.getNumeroDeErros(), letra);
        return out;
    }
    
    public Socket getSocket1() {
        return socket1;
    }
    
    public void setSocket1(Socket socket1) {
        this.socket1 = socket1;
    }
    
    public Socket getSocket2() {
        return socket2;
    }
    
    public void setSocket2(Socket socket2) {
        this.socket2 = socket2;
    }
    
}
