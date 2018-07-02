package modelo;

import java.util.Random;

public class Forca {

    private String palavra;
    private String categoria;

    public Forca() {
        gerarPalavra();
    }

    public void gerarPalavra() {
        String[] palavras = null;
        Random gerador = new Random();
        int cat = gerador.nextInt(10);
        switch (cat) {
            case 0:
                categoria = "Computador";
                palavras = new String[]{"teclado", "monitor", "gabinete", "impressora"};
                break;
            case 1:
                categoria = "frutas";
                palavras = new String[]{"melancia", "abacate", "tomate", "quiabo"};
                break;
            case 2:
                categoria = "comida";
                palavras = new String[]{"lasanha", "pizza", "sanduiche", "strogonoff"};
                break;
            case 3:
                categoria = "corpo";
                palavras = new String[]{"intestino", "bexiga", "tendao", "cabeca"};
                break;
            case 4:
                categoria = "futebol";
                palavras = new String[]{"palmeiras", "cruzeiro", "Paysandu", "flamengo"};
                break;
            case 5:
                categoria = "casa";
                palavras = new String[]{"geladeira", "estante", "quarto", "armario"};
                break;
            case 6:
                categoria = "mamifero";
                palavras = new String[]{"orca", "humano", "bezerro", "leite"};
                break;
            case 7:
                categoria = "inseto";
                palavras = new String[]{"largata", "mosquito", "pernilongo", "bezouro"};
                break;
            case 8:
                categoria = "dispositivos";
                palavras = new String[]{"celular", "notebook", "televisor", "tablet"};
                break;
            case 9:
                categoria = "aleatório";
                palavras = new String[]{"beringela", "cortina", "pescador", "serenidade"};
                break;
        }
        int i = gerador.nextInt(4);
        this.palavra = palavras[i];
    }

    public String getPalavra() {
        return palavra;
    }

    public int getTamanho() {
        return palavra.length();
    }

}
