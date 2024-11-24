package iscteiul.ista.es20241semletigrupof;

import java.io.IOException;
import java.util.List;

public class main {
    public static void main(String[] args) {

        /*
         try { //ponto1
            List<DadosPropriedades> propriedades = carregarPropriedades();
            for (DadosPropriedades dados : propriedades) {
                System.out.println(dados);
            }
        } catch (
        IOException e) {
            System.out.println("Erro" + e.getMessage());
        }
        */

        /*
        try {//ponto 2
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades();
            Grafo grafo = new Grafo();
            for (DadosPropriedades propriedade : propriedades) {
                grafo.adicionarPropriedade(propriedade);
            }


            if (propriedades.size() > 1) {
                grafo.adicionarAdjacencia(propriedades.get(0), propriedades.get(1));
                grafo.adicionarAdjacencia(propriedades.get(1), propriedades.get(2));
            }

            grafo.exibirGrafo();

        } catch (IOException e) {
            System.out.println("Erro ao carregar propriedades: " + e.getMessage());
        }
        */
        String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

        try {
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);
            Grafo grafo = new Grafo();
            grafo.construirGrafo(propriedades);

            grafo.exibirGrafo();
        } catch (Exception e) {
            System.err.println("Erro ao executar o programa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
