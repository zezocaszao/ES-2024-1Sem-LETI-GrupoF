package iscteiul.ista.es20241semletigrupof;

import java.util.List;
import javafx.geometry.BoundingBox;
import java.util.*;
import java.util.stream.Collectors;

public class CalcularPropiedadesOwners {


    public static boolean sameOwnership(List<DadosPropriedades> propriedades, int prop1, int prop2) {

        Grafo g = new Grafo();


        if (propriedades == null || propriedades.isEmpty() || g == null) {
            throw new IllegalArgumentException("Alguma coisa mal");
        }
        DadosPropriedades propriedade1 = null;
        DadosPropriedades propriedade2 = null;

        for (DadosPropriedades propriedade : propriedades) {
            if (propriedade.getObjectId() == prop1) {
                propriedade1 = propriedade;
            }
            if (propriedade.getObjectId() == prop2) {
                propriedade2 = propriedade;
            }

            // Verifica se as propriedades foram encontradas
            if (propriedade1 == null || propriedade2 == null) {
                throw new IllegalArgumentException("Propriedades encontradas");
            }
        }
        return g.saoVizinhos(prop1, prop2) &&
                propriedade1.getOwner().equals(propriedade2.getOwner());
        }

    public static int propsNotNeighor (List<DadosPropriedades> propriedades) {

        String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

        try {
            propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int num = 0;
        for (DadosPropriedades propriedade : propriedades) {
            for (DadosPropriedades propriedade2 : propriedades) {

                if (!sameOwnership(propriedades, propriedade.getObjectId(), propriedade2.getObjectId()) ) {
                    num++;
                }
            }
        }
        return num;
    }



    public static Map<String, Integer> contarPropriedadesComVizinhosMesmoOwner(List<DadosPropriedades> propriedades, Grafo grafo) {
        Map<String, Integer> ocorrenciasPorOwner = new HashMap<>();

        for (DadosPropriedades propriedade : propriedades) {
            String owner = propriedade.getOwner();
            if (owner == null || owner.isEmpty()) continue;

            int count = ocorrenciasPorOwner.getOrDefault(owner, 0);


            for (DadosPropriedades vizinha : propriedades) {
                for (DadosPropriedades vizinha2 : propriedades) {
                if (sameOwnership(propriedades, vizinha.getObjectId(), vizinha2.getObjectId())) {
                    count++;
                    break;
                }
            }
}
            ocorrenciasPorOwner.put(owner, count);
        }

        return ocorrenciasPorOwner;
    }




    public static double averageAreaOwner(List<DadosPropriedades> propriedades, Grafo grafo, String tipoArea, String valorArea) {
        if (propriedades == null || propriedades.isEmpty() || grafo == null) {
            throw new IllegalArgumentException("Lista de propriedades ou grafo inválido.");
        }

        // Filtrar as propriedades pela área geográfica
        List<DadosPropriedades> propriedadesFiltradas = propriedades.stream()
                .filter(propriedade -> {
                    switch (tipoArea.toLowerCase()) {
                        case "freguesia":
                            return propriedade.getFreguesia().equalsIgnoreCase(valorArea);
                        case "municipio":
                            return propriedade.getMunicipio().equalsIgnoreCase(valorArea);
                        case "ilha":
                            return propriedade.getIlha().equalsIgnoreCase(valorArea);
                        default:
                            throw new IllegalArgumentException("Tipo de área inválido. Use: freguesia, municipio ou ilha.");
                    }
                })
                .collect(Collectors.toList());

        if (propriedadesFiltradas.isEmpty()) {
            return -1; // Nenhuma propriedade encontrada
        }

        // Agrupar propriedades adjacentes do mesmo proprietário
        Map<String, Set<Integer>> agrupamentoPorOwner = new HashMap<>();

        for (DadosPropriedades propriedade : propriedadesFiltradas) {
            String owner = propriedade.getOwner();
            if (owner == null || owner.isEmpty()) continue;

            Set<Integer> propriedadesDoOwner = agrupamentoPorOwner.getOrDefault(owner, new HashSet<>());

            // Adicionar a propriedade atual e vizinhas que pertencem ao mesmo proprietário
            propriedadesDoOwner.add(propriedade.getObjectId());
            for (DadosPropriedades outraPropriedade : propriedadesFiltradas) {
                if (grafo.saoVizinhos(propriedade.getObjectId(), outraPropriedade.getObjectId()) &&
                        propriedade.getOwner().equals(outraPropriedade.getOwner())) {
                    propriedadesDoOwner.add(outraPropriedade.getObjectId());
                }
            }

            agrupamentoPorOwner.put(owner, propriedadesDoOwner);
        }

        // Calcular a área total para cada proprietário
        Map<String, Double> areaTotalPorOwner = new HashMap<>();
        for (Map.Entry<String, Set<Integer>> entry : agrupamentoPorOwner.entrySet()) {
            String owner = entry.getKey();
            Set<Integer> idsPropriedades = entry.getValue();

            double areaTotal = 0;
            for (Integer id : idsPropriedades) {
                DadosPropriedades propriedade = propriedadesFiltradas.stream()
                        .filter(p -> p.getObjectId() == id)
                        .findFirst()
                        .orElse(null);

                if (propriedade != null) {
                    areaTotal += propriedade.getShapeArea(); // Assumindo que getShapeArea retorna a área
                }
            }

            areaTotalPorOwner.put(owner, areaTotal);
        }

        // Calcular a área média
        double somaDasAreas = areaTotalPorOwner.values().stream().mapToDouble(Double::doubleValue).sum();
        int numOwners = areaTotalPorOwner.size();

        if (numOwners == 0) {
            return 0;
        } else {
            return somaDasAreas / numOwners;
        }
    }

}