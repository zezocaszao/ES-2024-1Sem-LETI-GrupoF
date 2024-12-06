package iscteiul.ista.es20241semletigrupof;

import javafx.geometry.BoundingBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.*;

/**
 * A classe Grafo representa um grafo não direcionado onde cada nó corresponde a uma propriedade
 * e as arestas representam a vizinhança entre as propriedades, baseando-se nas suas bounding boxes.
 *
 * A classe permite calcular vizinhanças entre as propriedades com base na interseção de suas bounding boxes
 * e fornece funcionalidades para exibir e verificar vizinhanças entre as propriedades.
 */
public class Grafo {
/** Mapa de adjacências que armazena as conexões entre as propriedades. */
    private final Map<Integer, Set<Integer>> adjacencias;

    /**
     * Construtor da classe Grafo, inicializando o mapa de adjacências.
     */
    public Grafo() {
        this.adjacencias = new HashMap<>();
    }

    /**
     * Método para adicionar uma conexão entre duas propriedades.
     * Adiciona uma aresta entre duas propriedades no grafo, indicando que são vizinhas.
     *
     * @param idPropriedade1 ID da primeira propriedade.
     * @param idPropriedade2 ID da segunda propriedade.
     */
    public void adicionarVizinho(int idPropriedade1, int idPropriedade2) {
        adjacencias.computeIfAbsent(idPropriedade1, k -> new HashSet<>()).add(idPropriedade2);
        adjacencias.computeIfAbsent(idPropriedade2, k -> new HashSet<>()).add(idPropriedade1);
    }

    /**
     * Método para obter as adjacências  de uma propriedade.
     *
     * @return O mapa de adjacências, onde as chaves são os IDs das propriedades e os valores são conjuntos de IDs de propriedades vizinhas.
     */
    public Map<Integer, Set<Integer>> getAdjacencias() {
        return adjacencias;
    }

    /**
     * Método para calcular a bounding box de uma geometria, que é uma caixa delimitadora que envolve todos os pontos de uma geometria.
     * A geometria é uma string representada por um "MULTIPOLYGON" com coordenadas de pontos.
     *
     * @param geometry A string que representa a geometria, no formato "MULTIPOLYGON"
     * @return A BoundingBox calculada para a geometria.
     */
   public BoundingBox calcularBoundingBox(String geometry) {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;

        if (geometry == null || geometry.isEmpty() || geometry.contains("EMPTY") || !geometry.startsWith("MULTIPOLYGON")) {
            return new BoundingBox(0, 0, 0, 0); // Retorna uma bounding box nula
        }
        try {
            String coordenadas = geometry.replace("MULTIPOLYGON", "")
                    .replace("(", "")
                    .replace(")", "")
                    .trim();

            String[] pares = coordenadas.split(",");
            for (String par : pares) {
                String[] ponto = par.trim().split(" ");
                if (ponto.length == 2) {
                    double x = Double.parseDouble(ponto[0]);
                    double y = Double.parseDouble(ponto[1]);

                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                } else {
                    System.err.println("Par inválido ignorado: " + par);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar a geometria: " + e.getMessage());
            return new BoundingBox(0, 0, 0, 0);
        }

        return new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }


    /**
     * Método para construir o grafo de vizinhança a partir de uma lista de propriedades.
     * Para cada par de propriedades, verifica se as suas bounding boxes se intersectam e, se sim,
     * adiciona uma aresta entre elas.
     *
     * @param propriedades Lista de propriedades.
     */

    public void construirGrafo(List<DadosPropriedades> propriedades) {
        Map<Integer, BoundingBox> boundingBoxes = new HashMap<>();

        for (DadosPropriedades propriedade : propriedades) {
            BoundingBox boundingBox = calcularBoundingBox(propriedade.getGeometry());
            boundingBoxes.put(propriedade.getObjectId(), boundingBox);
        }

        for (Map.Entry<Integer, BoundingBox> entry1 : boundingBoxes.entrySet()) {
            for (Map.Entry<Integer, BoundingBox> entry2 : boundingBoxes.entrySet()) {
                if (entry1.getKey().equals(entry2.getKey())) {
                    continue;
                }
                if (entry1.getValue().intersects(entry2.getValue())) {
                    adicionarVizinho(entry1.getKey(), entry2.getKey());
                }
            }
        }
    }
    /**
     * Verifica se duas propriedades são vizinhas
     *
     * @param idPropriedade1 ID da primeira propriedade.
     * @param idPropriedade2 ID da segunda propriedade.
     * @return {@code true} se as propriedades forem vizinhas, caso contrário, {@code false}.
     */
    public boolean saoVizinhos(int idPropriedade1, int idPropriedade2) {
        return adjacencias.getOrDefault(idPropriedade1, new HashSet<>()).contains(idPropriedade2);
    }

    /**
     * Exibe as conexões (vizinhanças) de todas as propriedades no grafo.
     * Para cada propriedade, imprime os vizinhos.
     */
    public void exibirGrafo() {
        for (Map.Entry<Integer, Set<Integer>> entry : adjacencias.entrySet()) {
            System.out.println("Propriedade " + entry.getKey() + " é vizinha de: " + entry.getValue());
        }
    }


}
