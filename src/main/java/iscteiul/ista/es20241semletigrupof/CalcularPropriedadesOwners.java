package iscteiul.ista.es20241semletigrupof;

import java.util.*;
import java.util.stream.Collectors;

public class CalcularPropriedadesOwners {

    // Método para obter as áreas disponíveis
    public static List<String> obterAreasDisponiveis(List<DadosPropriedades> propriedades, String tipoArea) {
        return propriedades.stream()
                .map(propriedade -> {
                    switch (tipoArea.toLowerCase()) {
                        case "freguesia":
                            return propriedade.getFreguesia();
                        case "municipio":
                            return propriedade.getMunicipio();
                        case "ilha":
                            return propriedade.getIlha();
                        default:
                            throw new IllegalArgumentException("Tipo de área inválido. Use: freguesia, municipio ou ilha.");
                    }
                })
                .distinct() // Remover duplicatas
                .sorted()   // Ordenar alfabeticamente
                .collect(Collectors.toList());
    }

    // Método para obter os donos disponíveis em uma área específica
    public static List<String> obterDonosPorArea(List<DadosPropriedades> propriedades, String tipoArea, String areaEscolhida) {
        return propriedades.stream()
                .filter(propriedade -> {
                    switch (tipoArea.toLowerCase()) {
                        case "freguesia":
                            return areaEscolhida != null && propriedade.getFreguesia() != null && propriedade.getFreguesia().equals(areaEscolhida);
                        case "municipio":
                            return areaEscolhida != null && propriedade.getMunicipio() != null && propriedade.getMunicipio().equals(areaEscolhida);
                        case "ilha":
                            return areaEscolhida != null && propriedade.getIlha() != null && propriedade.getIlha().equals(areaEscolhida);
                        default:
                            return false;
                    }
                })
                .map(DadosPropriedades::getOwner)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    // Método para calcular a área média das propriedades de um dono dentro de uma área específica
    public static double calcularAreaMediaPorDono(List<DadosPropriedades> propriedades, String tipoArea, String areaEscolhida, String donoEscolhido) {
        List<DadosPropriedades> propriedadesFiltradas = propriedades.stream()
                .filter(propriedade -> {
                    switch (tipoArea.toLowerCase()) {
                        case "freguesia":
                            return areaEscolhida != null && propriedade.getFreguesia() != null && propriedade.getFreguesia().equals(areaEscolhida);
                        case "municipio":
                            return areaEscolhida != null && propriedade.getMunicipio() != null && propriedade.getMunicipio().equals(areaEscolhida);
                        case "ilha":
                            return areaEscolhida != null && propriedade.getIlha() != null && propriedade.getIlha().equals(areaEscolhida);
                        default:
                            return false;
                    }
                })
                .filter(propriedade -> donoEscolhido != null && propriedade.getOwner() != null && propriedade.getOwner().equals(donoEscolhido))
                .collect(Collectors.toList());

        if (propriedadesFiltradas.isEmpty()) {
            return 0; // Ou outro comportamento desejado para o caso de não haver propriedades
        }

        double areaTotal = propriedadesFiltradas.stream()
                .mapToDouble(DadosPropriedades::getShapeArea)
                .sum();
        return areaTotal / propriedadesFiltradas.size();
    }


    // Exemplo de uso para escolher a área, o dono e calcular a média das áreas
    public static void exibirAreaMediaPorDono(List<DadosPropriedades> propriedades) {
        Scanner scanner = new Scanner(System.in);

        // Escolher o tipo de área
        System.out.println("Escolha o tipo de área (freguesia, municipio, ilha): ");
        String tipoArea = scanner.nextLine();

        // Obter as áreas disponíveis
        List<String> areasDisponiveis = obterAreasDisponiveis(propriedades, tipoArea);
        System.out.println("Áreas disponíveis: ");
        areasDisponiveis.forEach(System.out::println);

        System.out.println("Escolha uma área: ");
        String areaEscolhida = scanner.nextLine();

        // Obter os donos disponíveis na área escolhida
        List<String> donosDisponiveis = obterDonosPorArea(propriedades, tipoArea, areaEscolhida);
        System.out.println("Donos disponíveis: ");
        donosDisponiveis.forEach(System.out::println);

        System.out.println("Escolha um dono: ");
        String donoEscolhido = scanner.nextLine();

        // Calcular a área média do dono na área escolhida
        double areaMedia = calcularAreaMediaPorDono(propriedades, tipoArea, areaEscolhida, donoEscolhido);
        System.out.println("A área média das propriedades do dono " + donoEscolhido + " na área " + areaEscolhida + " é: " + areaMedia);
    }
}
