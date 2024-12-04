package iscteiul.ista.es20241semletigrupof;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args) {

       String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";



        HelloApplication.main(args);
       /*
        String caminhoCSV = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv"; // Atualize para o caminho correto do arquivo
        List<DadosPropriedades> propriedades;

        // Ler propriedades usando a classe CarregarCsv
        try {
            propriedades = CarregarCsv.carregarPropriedades(caminhoCSV);
        } catch (Exception e) {
            System.err.println("Erro ao carregar o arquivo CSV: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        // Passo 1: Escolha do tipo de área
        System.out.println("Escolha o tipo de área (freguesia, municipio ou ilha): ");
        String tipoArea = scanner.nextLine().toLowerCase();

        // Passo 2: Obter lista de áreas disponíveis com base no tipo de área
        Set<String> areasDisponiveis = obterAreasDisponiveis(propriedades, tipoArea);

        if (areasDisponiveis.isEmpty()) {
            System.out.println("Tipo de área inválido ou nenhuma área encontrada.");
            return;
        }

        System.out.println("\nÁreas disponíveis:");
        List<String> listaAreas = new ArrayList<>(areasDisponiveis);
        for (int i = 0; i < listaAreas.size(); i++) {
            System.out.println((i + 1) + ". " + listaAreas.get(i));
        }

        System.out.println("\nEscolha o número correspondente à área desejada: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Consumir o newline

        if (escolha < 1 || escolha > listaAreas.size()) {
            System.out.println("Escolha inválida.");
            return;
        }

        String areaEscolhida = listaAreas.get(escolha - 1);

        // Passo 3: Gerar sugestões de troca
        List<TrocaPropriedades> trocasSugeridas = SugestaoTrocas.sugerirTrocas(propriedades, tipoArea, areaEscolhida);

        // Passo 4: Exibir resultados
        System.out.println("\nSugestões de trocas encontradas:");
        if (trocasSugeridas.isEmpty()) {
            System.out.println("Nenhuma troca possível foi encontrada para os critérios fornecidos.");
        } else {
            for (TrocaPropriedades troca : trocasSugeridas) {
                System.out.println(troca);
            }
        }

        scanner.close();
    }

    // Método para obter a lista de áreas disponíveis com base no tipo de área
    private static Set<String> obterAreasDisponiveis(List<DadosPropriedades> propriedades, String tipoArea) {
        switch (tipoArea) {
            case "freguesia":
                return propriedades.stream()
                        .map(DadosPropriedades::getFreguesia)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
            case "municipio":
                return propriedades.stream()
                        .map(DadosPropriedades::getMunicipio)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
            case "ilha":
                return propriedades.stream()
                        .map(DadosPropriedades::getIlha)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
            default:
                return Collections.emptySet();
        }
        */
    }
}
