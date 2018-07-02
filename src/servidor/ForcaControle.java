/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import modelo.Forca;
import java.util.Arrays;
import java.util.Random;

public class ForcaControle {

    Forca forca;
    private char[] palavraOculta;
    private int letrasInvalidas = 0; // se o tamanho for igual as partes do corpo é empate
    private int rodada = 0;
    private int pontosPlayer1 = 0;
    private int pontosPlayer2 = 0;

    public ForcaControle() {
        this.forca = new Forca();
        novaRodada();
    }

    public int sortearJogador() {
        Random gerador = new Random();
        return gerador.nextInt(2);
    }

    public void novaRodada() {
        this.forca.gerarPalavra();
        palavraOculta = new char[forca.getTamanho()];
        for (int z = 0; z < forca.getTamanho(); z++) {
            palavraOculta[z] = '_';
        }
        letrasInvalidas = 0;
    }

    public String getPalavraOculta() {
        return String.valueOf(palavraOculta);
    }

    public int getTamanho() {
        return forca.getTamanho();
    }

    public boolean isAcerto(String letra) {
        boolean isAcerto = false;
        for (int z = 0; z < getTamanho(); z++) {
            if (forca.getPalavra().charAt(z) == letra.charAt(0)) {
                palavraOculta[z] = letra.charAt(0);
                isAcerto = true;
            }
        }
        if (!isAcerto) {
            letrasInvalidas++;
        }

        return isAcerto;

    }

    public int getNumeroDeErros() {
        return letrasInvalidas;
    }

    public boolean isCompleto() {
        return !Arrays.toString(palavraOculta).contains("_");
    }

    public int getRodada() {
        return rodada;
    }

    public void setRodada() {
        this.rodada++;
    }

    public int getPontosPlayer2() {
        return pontosPlayer2;
    }

    public void setPontosPlayer2() {
        this.pontosPlayer2++;
    }

    public int getPontosPlayer1() {
        return pontosPlayer1;
    }

    public void setPontosPlayer1() {
        this.pontosPlayer1++;
    }
}
