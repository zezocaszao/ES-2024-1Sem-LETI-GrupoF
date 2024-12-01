package iscteiul.ista.es20241semletigrupof;

import javafx.geometry.BoundingBox;

import java.util.*;
import java.util.stream.Collectors;

public class CalcularPropriedadesOwners {

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

    // Método para agrupar propriedades por dono e área
    public static Map<String, List<DadosPropriedades>> agruparPropriedadesPorDono(List<DadosPropriedades> propriedades) {
        return propriedades.stream()
                .collect(Collectors.groupingBy(DadosPropriedades::getOwner));
    }

    // Método para verificar vizinhança
    public static boolean saoVizinhas(DadosPropriedades p1, DadosPropriedades p2, Grafo grafo) {
        // Criar as BoundingBoxes para cada propriedade
        BoundingBox bbox1 = grafo.calcularBoundingBox(p1.getGeometry());
        BoundingBox bbox2 = grafo.calcularBoundingBox(p2.getGeometry());

        // Verifica se as BoundingBoxes se sobrepõem, indicando que as propriedades são vizinhas
        return bbox1.intersects(bbox2);
    }

    // Método para calcular a área total de propriedades vizinhas pertencentes ao mesmo dono
    public static double calcularAreaTotal(List<DadosPropriedades> propriedades, Grafo grafo) {
        // Agrupar propriedades por dono
        Map<String, List<DadosPropriedades>> propriedadesPorDono = agruparPropriedadesPorDono(propriedades);

        double areaTotal = 0;

        for (Map.Entry<String, List<DadosPropriedades>> entry : propriedadesPorDono.entrySet()) {
            List<DadosPropriedades> propriedadesDono = entry.getValue();
            Set<DadosPropriedades> propriedadesCombinadas = new HashSet<>();

            for (DadosPropriedades p1 : propriedadesDono) {
                // Verifica todas as propriedades adjacentes e agrupa as áreas
                for (DadosPropriedades p2 : propriedadesDono) {
                    if (p1 != p2 && saoVizinhas(p1, p2, grafo)) {
                        // Combina as áreas das propriedades vizinhas
                        propriedadesCombinadas.add(p1);
                        propriedadesCombinadas.add(p2);
                    }
                }
            }

            // Soma as áreas das propriedades vizinhas (todas as propriedades combinadas)
            areaTotal += propriedadesCombinadas.stream()
                    .mapToDouble(DadosPropriedades::getShapeArea)
                    .sum();
        }

        return areaTotal;
    }

    // Método para calcular a área média, considerando as áreas combinadas
    public static double calcularAreaMedia(List<DadosPropriedades> propriedades, Grafo grafo) {
        double areaTotal = calcularAreaTotal(propriedades, grafo);
        int quantidadePropriedades = propriedades.size();
        return areaTotal / quantidadePropriedades;
    }

    // Método para exibir a área média por área (freguesia, municipio, ilha)
    public static Map<String, Double> calcularAreaMediaPorArea(List<DadosPropriedades> propriedades, String tipoArea, Grafo grafo) {
        Map<String, Double> areaMediaPorArea = new HashMap<>();

        List<String> areasDisponiveis = obterAreasDisponiveis(propriedades, tipoArea);

        for (String area : areasDisponiveis) {
            // Filtrar propriedades por área
            List<DadosPropriedades> propriedadesPorArea = propriedades.stream()
                    .filter(propriedade -> {
                        switch (tipoArea.toLowerCase()) {
                            case "freguesia":
                                return propriedade.getFreguesia().equals(area);
                            case "municipio":
                                return propriedade.getMunicipio().equals(area);
                            case "ilha":
                                return propriedade.getIlha().equals(area);
                            default:
                                return false;
                        }
                    })
                    .collect(Collectors.toList());

            // Calcular a área média para a área filtrada
            double areaMedia = calcularAreaMedia(propriedadesPorArea, grafo); // Passando o grafo aqui
            areaMediaPorArea.put(area, areaMedia);
        }

        return areaMediaPorArea;
    }

}
