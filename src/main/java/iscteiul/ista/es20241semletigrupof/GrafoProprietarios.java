package iscteiul.ista.es20241semletigrupof;

import java.util.*;

public class GrafoProprietarios {
    private final Map<Integer, Set<Integer>> grafoProprietarios;

    public GrafoProprietarios() {
        this.grafoProprietarios = new HashMap<>();
    }

    // Adicionar uma relação de vizinhança entre dois proprietários
    public void adicionarVizinhos(int idProprietario1, int idProprietario2) {
        grafoProprietarios.computeIfAbsent(idProprietario1, k -> new HashSet<>()).add(idProprietario2);
        grafoProprietarios.computeIfAbsent(idProprietario2, k -> new HashSet<>()).add(idProprietario1);
    }

    // Obter vizinhos de um proprietário
    public Set<Integer> getVizinhos(int idProprietario) {
        return grafoProprietarios.getOrDefault(idProprietario, new HashSet<>());
    }

    // Construir grafo de proprietários a partir do grafo de propriedades
    public void construirGrafo(Grafo grafoPropriedades, Map<Integer, Integer> mapaPropriedadeParaProprietario) {
        Map<Integer, Set<Integer>> adjacenciasPropriedades = grafoPropriedades.getAdjacencias();

        for (Map.Entry<Integer, Set<Integer>> entry : adjacenciasPropriedades.entrySet()) {
            int propriedade1 = entry.getKey();
            int proprietario1 = mapaPropriedadeParaProprietario.get(propriedade1);

            for (int propriedade2 : entry.getValue()) {
                int proprietario2 = mapaPropriedadeParaProprietario.get(propriedade2);

                if (proprietario1 != proprietario2) { // Apenas adiciona vizinhos se os proprietários forem diferentes
                    adicionarVizinhos(proprietario1, proprietario2);
                }
            }
        }
    }

    // Exibir o grafo de proprietários
    public void exibirGrafo() {
        for (Map.Entry<Integer, Set<Integer>> entry : grafoProprietarios.entrySet()) {
            System.out.println("Proprietário " + entry.getKey() + " é vizinho de: " + entry.getValue());
        }
    }
}