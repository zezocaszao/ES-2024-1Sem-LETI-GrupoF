package iscteiul.ista.es20241semletigrupof;

import java.util.*;

public class Grafo {

    private final Map<Integer, Set<Integer>> adjacencias; // Mapeia o ID de uma propriedade para seus vizinhos

    public Grafo() {
        this.adjacencias = new HashMap<>();
    }

    // Método para adicionar uma conexão entre duas propriedades
    public void adicionarVizinho(int idPropriedade1, int idPropriedade2) {
        adjacencias.computeIfAbsent(idPropriedade1, k -> new HashSet<>()).add(idPropriedade2);
        adjacencias.computeIfAbsent(idPropriedade2, k -> new HashSet<>()).add(idPropriedade1);
    }

    // Método para obter os vizinhos de uma propriedade
    public Set<Integer> obterVizinhos(int idPropriedade) {
        return adjacencias.getOrDefault(idPropriedade, Collections.emptySet());
    }

    // Método para construir o grafo a partir das propriedades
    public void construirGrafo(List<DadosPropriedades> propriedades) {
        for (int i = 0; i < propriedades.size(); i++) {
            DadosPropriedades prop1 = propriedades.get(i);
            Set<String> pontosProp1 = extrairPontos(prop1.getGeometry());

            for (int j = i + 1; j < propriedades.size(); j++) {
                DadosPropriedades prop2 = propriedades.get(j);
                Set<String> pontosProp2 = extrairPontos(prop2.getGeometry());

                // Verifica se existe interseção nos conjuntos de pontos
                if (!Collections.disjoint(pontosProp1, pontosProp2)) {
                    adicionarVizinho(prop1.getObjectId(), prop2.getObjectId());
                }
            }
        }
    }

    // Método para extrair os pontos de uma geometria no formato MULTIPOLYGON
    private Set<String> extrairPontos(String geometry) {
        Set<String> pontos = new HashSet<>();
        if (geometry.startsWith("MULTIPOLYGON")) {
            String coordenadas = geometry.replace("MULTIPOLYGON", "")
                    .replace("(", "")
                    .replace(")", "")
                    .trim();

            String[] pares = coordenadas.split(",");
            for (String par : pares) {
                pontos.add(par.trim());
            }
        }
        return pontos;
    }

    // Método para exibir o grafo (opcional, para depuração)
    public void exibirGrafo() {
        for (Map.Entry<Integer, Set<Integer>> entry : adjacencias.entrySet()) {
            System.out.println("Propriedade " + entry.getKey() + " é vizinha de: " + entry.getValue());
        }
    }
}
