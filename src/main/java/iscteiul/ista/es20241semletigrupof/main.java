package iscteiul.ista.es20241semletigrupof;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args) {
        String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

      /*
      HelloApplication.main(args);

      */

        try {
            // Carregar dados das propriedades usando a classe CarregarCsv
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);

            // Criar o grafo de vizinhança entre os proprietários
            GrafoProprietarios grafo = new GrafoProprietarios();
            grafo.construirGrafoProprietarios(propriedades);
            Scanner scanner = new Scanner(System.in);

            // Exibir o grafo de vizinhança (opcional para depuração)
            //grafo.exibirGrafo();

            // Escolher o tipo de área
            System.out.println("Escolha o tipo de área (freguesia, município ou ilha):");
            String tipoArea;
            while (true) {
                tipoArea = scanner.nextLine().trim().toLowerCase();
                if (tipoArea.equals("freguesia") || tipoArea.equals("municipio") || tipoArea.equals("ilha")) {
                    break;
                }
                System.out.println("Opção inválida. Escolha entre 'freguesia', 'municipio' ou 'ilha':");
            }

            // Determinar as opções disponíveis com base no tipo de área
            Set<String> opcoesDisponiveis;
            switch (tipoArea) {
                case "freguesia":
                    opcoesDisponiveis = propriedades.stream()
                            .map(DadosPropriedades::getFreguesia)
                            .collect(Collectors.toSet());
                    break;
                case "municipio":
                    opcoesDisponiveis = propriedades.stream()
                            .map(DadosPropriedades::getMunicipio)
                            .collect(Collectors.toSet());
                    break;
                case "ilha":
                    opcoesDisponiveis = propriedades.stream()
                            .map(DadosPropriedades::getIlha)
                            .collect(Collectors.toSet());
                    break;
                default:
                    opcoesDisponiveis = new HashSet<>();
            }

            // Exibir opções disponíveis
            System.out.println("Opções disponíveis para " + tipoArea + ":");
            opcoesDisponiveis.forEach(System.out::println);

            // Escolher a área
            String areaEscolhida;
            while (true) {
                System.out.println("Escolha uma opção para a área:");
                areaEscolhida = scanner.nextLine().trim();
                if (opcoesDisponiveis.contains(areaEscolhida)) {
                    break;
                }
                System.out.println("Opção inválida. Escolha uma das áreas disponíveis:");
            }


            // Gerar sugestões de troca com base nas propriedades e no grafo
            List<TrocaPropriedades> trocasSugeridas = SugestaoTrocas.sugerirTrocas(propriedades, tipoArea, areaEscolhida, grafo);

            // Exibir as sugestões de troca
            System.out.println("Sugestões de Troca:");
            for (TrocaPropriedades troca : trocasSugeridas) {
                System.out.println(troca);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}