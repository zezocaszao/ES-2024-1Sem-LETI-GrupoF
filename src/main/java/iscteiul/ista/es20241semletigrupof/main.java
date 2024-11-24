package iscteiul.ista.es20241semletigrupof;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";
        /*
        try {
            // Carregar propriedades do CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);

            // Exibir cada propriedade carregada
            for (DadosPropriedades dados : propriedades) {
                System.out.println(dados);
            }

        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
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

        /*
        try {
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);
            Grafo grafo = new Grafo();
            grafo.construirGrafo(propriedades);

            grafo.exibirGrafo();
        } catch (Exception e) {
            System.err.println("Erro ao executar o programa: " + e.getMessage());
            e.printStackTrace();
        }
        */

        try {
            // Carregar propriedades do CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);

            // Exibir as propriedades carregadas
            for (DadosPropriedades dados : propriedades) {
                System.out.println(dados);
            }

            // Solicitar ao utilizador o tipo de área geográfica e o valor correspondente
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o tipo de área geográfica (freguesia, municipio, ilha): ");
            String tipoArea = scanner.nextLine();

            System.out.print("Digite o valor da área (ex.: 'Arco da Calheta' ou 'Calheta'): ");
            String valorArea = scanner.nextLine();

            // Calcular a área média
            double areaMedia = CalculadoraPropriedades.calcularAreaMedia(propriedades, tipoArea, valorArea);

            if (areaMedia == -1) {
                System.out.println("Nenhuma propriedade encontrada para a área especificada.");
            } else {
                System.out.printf("A área média das propriedades em %s (%s) é: %.2f\n", valorArea, tipoArea, areaMedia);
            }

        } catch (Exception e) {
            System.err.println("Erro ao executar o programa: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
