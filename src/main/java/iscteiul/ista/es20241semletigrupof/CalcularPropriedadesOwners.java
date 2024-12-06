package iscteiul.ista.es20241semletigrupof;

import java.util.*;
import java.util.stream.Collectors;

public class CalcularPropriedadesOwners {

    /**
     * Obtém os donos das propriedades em uma área específica.
     * Filtra as propriedades pela área fornecida  e retorna uma lista dos donos disponíveis nessa área.
     *
     * @param propriedades Lista de todas as propriedades disponíveis.
     * @param tipoArea     Tipo de área geográfica.
     * @param areaEscolhida A área geográfica específica
     * @return Lista de donos distintos.
     */
    public static List<String> obterDonosPorArea(List<DadosPropriedades> propriedades, String tipoArea, String areaEscolhida) {
        return propriedades.stream()
                .filter(propriedade -> {
                    switch (tipoArea.toLowerCase()) {
                        case "freguesia":
                            return  propriedade.getFreguesia().equals(areaEscolhida);
                        case "municipio":
                            return propriedade.getMunicipio().equals(areaEscolhida);
                        case "ilha":
                            return  propriedade.getIlha().equals(areaEscolhida);
                        default:
                            return false;
                    }
                })
                .map(DadosPropriedades::getOwner)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Calcula a área média das propriedades de um dono dentro de uma área geográfica específica.
     * Filtra as propriedades pela área e pelo dono especificado e calcula a área média das propriedades do dono.
     *
     * @param propriedades Lista de todas as propriedades disponíveis.
     * @param tipoArea     Tipo de área geográfica.
     * @param areaEscolhida A área geográfica específica .
     * @param donoEscolhido O nome do dono cujas propriedades serão consideradas para o cálculo da área média.
     * @return A área média das propriedades do dono na área especificada.
     */
    public static double calcularAreaMediaPorDono(List<DadosPropriedades> propriedades, String tipoArea, String areaEscolhida, String donoEscolhido) {
        List<DadosPropriedades> propriedadesFiltradas = propriedades.stream()
                .filter(propriedade -> {
                    switch (tipoArea.toLowerCase()) {
                        case "freguesia":
                            return propriedade.getFreguesia().equals(areaEscolhida);
                        case "municipio":
                            return  propriedade.getMunicipio().equals(areaEscolhida);
                        case "ilha":
                            return  propriedade.getIlha().equals(areaEscolhida);
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


    /**
     * Exibe a área média das propriedades de um dono em uma área geográfica específica,
     * com base na entrada do utilizador. O utilizador escolhe a área e o dono, e o método calcula e exibe a área média.
     *
     * @param propriedades Lista de todas as propriedades disponíveis.
     */
    public static void exibirAreaMediaPorDono(List<DadosPropriedades> propriedades) {
        Scanner scanner = new Scanner(System.in);

        // Escolher o tipo de área
        System.out.println("Escolha o tipo de área (freguesia, municipio, ilha): ");
        String tipoArea = scanner.nextLine();

        // Obter as áreas disponíveis
        List<String> areasDisponiveis = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, tipoArea);
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












