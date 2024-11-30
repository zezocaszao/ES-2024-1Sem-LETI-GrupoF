package iscteiul.ista.es20241semletigrupof;

import javafx.geometry.BoundingBox;
import java.util.*;

public class Grafo {
    private final Map<Integer, Set<Integer>> adjacencias;

    public Grafo() {
        this.adjacencias = new HashMap<>();
    }

    // Método para adicionar uma conexão entre duas propriedades
    public void adicionarVizinho(int idPropriedade1, int idPropriedade2) {
        adjacencias.computeIfAbsent(idPropriedade1, k -> new HashSet<>()).add(idPropriedade2);
        adjacencias.computeIfAbsent(idPropriedade2, k -> new HashSet<>()).add(idPropriedade1);
    }
    public Map<Integer, Set<Integer>> getAdjacencias() {
        return adjacencias;
    }

    // Método para calcular a bounding box de uma geometria
    private BoundingBox calcularBoundingBox(String geometry) {
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

    public boolean saoVizinhos(int idPropriedade1, int idPropriedade2) {
        return adjacencias.getOrDefault(idPropriedade1, new HashSet<>()).contains(idPropriedade2);
    }

    public void exibirGrafo() {
        for (Map.Entry<Integer, Set<Integer>> entry : adjacencias.entrySet()) {
            System.out.println("Propriedade " + entry.getKey() + " é vizinha de: " + entry.getValue());
        }
    }
}
