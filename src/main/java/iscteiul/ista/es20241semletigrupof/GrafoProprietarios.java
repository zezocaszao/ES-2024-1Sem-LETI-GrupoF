package iscteiul.ista.es20241semletigrupof;

import javafx.geometry.BoundingBox;

import java.util.*;

/**
 * Classe que representa um grafo de proprietários, onde cada nó é um proprietário e
 * as arestas representam a conexão entre dois proprietários que têm propriedades adjacentes.
 */

public class GrafoProprietarios {
    private final Map<String, Set<String>> adjacencias;

    /**
     * Construtor da classe GrafoProprietarios. Inicializa o mapa de adjacências.
     */
    public GrafoProprietarios() {
        this.adjacencias = new HashMap<>();
    }


    /**
     * Adiciona uma conexão entre dois proprietários.
     * <p>
     * A conexão é bidirecional, ou seja, se o proprietário A é vizinho do proprietário B,
     * então o proprietário B é vizinho do proprietário A.
     *
     * @param owner1 O primeiro proprietário.
     * @param owner2 O segundo proprietário.
     */
    public void adicionarVizinho(String owner1, String owner2) {
        adjacencias.computeIfAbsent(owner1, k -> new HashSet<>()).add(owner2);
        adjacencias.computeIfAbsent(owner2, k -> new HashSet<>()).add(owner1);
    }

    /**
     * Retorna o mapa de adjacências que representa os vizinhos de cada proprietário.
     *
     */
    public Map<String, Set<String>> getAdjacencias() {
        return adjacencias;
    }

    /**
     * Constrói o grafo de proprietários a partir de uma lista de propriedades.
     * Para cada propriedade, o método calcula a BoundingBox e verifica se ela interseta com outras.
     * Se duas propriedades se intersectam,os seus respectivos proprietários são adicionados como vizinhos no grafo.
     *
     * @param propriedades A lista de propriedades para construir o grafo.
     */
    public void construirGrafoProprietarios(List<DadosPropriedades> propriedades) {
        Map<Integer, BoundingBox> boundingBoxes = new HashMap<>();
        Map<Integer, String> proprietarios = new HashMap<>();

        for (DadosPropriedades propriedade : propriedades) {
            Grafo g = new Grafo();
            BoundingBox boundingBox = g.calcularBoundingBox(propriedade.getGeometry());
            boundingBoxes.put(propriedade.getObjectId(), boundingBox);
            proprietarios.put(propriedade.getObjectId(), propriedade.getOwner());
        }

        for (Map.Entry<Integer, BoundingBox> entry1 : boundingBoxes.entrySet()) {
            for (Map.Entry<Integer, BoundingBox> entry2 : boundingBoxes.entrySet()) {
                if (entry1.getKey().equals(entry2.getKey())) {
                    continue;
                }
                if (entry1.getValue().intersects(entry2.getValue())) {
                    String owner1 = proprietarios.get(entry1.getKey());
                    String owner2 = proprietarios.get(entry2.getKey());

                    if (!owner1.equals(owner2)) { // Adicionar apenas se forem proprietários diferentes
                        adicionarVizinho(owner1, owner2);
                    }
                }
            }
        }
    }
    /**
     * Verifica se duas propriedades são vizinhas
     *
     * @param owner1  da primeira propriedade.
     * @param owner2  da segunda propriedade.
     * @return {@code true} se os owners forem vizinhos, caso contrário, {@code false}.
     */
    public boolean saoVizinhos(String owner1, String owner2) {
        return adjacencias.getOrDefault(owner1, new HashSet<>()).contains(owner2);
    }
    /**
     * Exibe as informações sobre o grafo de vizinhos, imprimindo cada proprietário
     * e os seus respectivos vizinhos.
     */
    public void exibirGrafo() {
        for (Map.Entry<String, Set<String>> entry : adjacencias.entrySet()) {
            System.out.println("Proprietário " + entry.getKey() + " é vizinho de: " + entry.getValue());
        }
    }
}