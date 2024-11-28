package iscteiul.ista.es20241semletigrupof;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";


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
        /*
        try {
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);

            // Solicitar ao utilizador o tipo de área geográfica
            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o tipo de área geográfica (freguesia, municipio, ilha): ");
            String tipoArea = scanner.nextLine().toLowerCase();

            // Verificar se o tipo de área é válido
            if (!List.of("freguesia", "municipio", "ilha").contains(tipoArea)) {
                System.out.println("Tipo de área inválido. Use: freguesia, municipio ou ilha.");
                return;
            }

            // Obter e exibir as opções disponíveis para o tipo de área escolhido
            List<String> areasDisponiveis = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, tipoArea);
            if (areasDisponiveis.isEmpty()) {
                System.out.println("Não há áreas disponíveis para o tipo especificado.");
                return;
            }

            System.out.println("Áreas disponíveis:");
            areasDisponiveis.forEach(area -> System.out.println("- " + area));

            // Solicitar o valor da área ao usuário
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
        */

        // Executar a aplicação JavaFX
        HelloApplication.main(args);

    }
}
