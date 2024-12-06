package iscteiul.ista.es20241semletigrupof;

import javafx.geometry.BoundingBox;

import java.util.*;

public class GrafoProprietarios {
    private final Map<String, Set<String>> adjacencias;

    public GrafoProprietarios() {
        this.adjacencias = new HashMap<>();
    }

    // Método para adicionar uma conexão entre dois proprietários
    public void adicionarVizinho(String owner1, String owner2) {
        adjacencias.computeIfAbsent(owner1, k -> new HashSet<>()).add(owner2);
        adjacencias.computeIfAbsent(owner2, k -> new HashSet<>()).add(owner1);
    }

    public Map<String, Set<String>> getAdjacencias() {
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

    public void construirGrafoProprietarios(List<DadosPropriedades> propriedades) {
        Map<Integer, BoundingBox> boundingBoxes = new HashMap<>();
        Map<Integer, String> proprietarios = new HashMap<>();

        for (DadosPropriedades propriedade : propriedades) {
            BoundingBox boundingBox = calcularBoundingBox(propriedade.getGeometry());
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

    public boolean saoVizinhos(String owner1, String owner2) {
        System.out.println(adjacencias.getOrDefault(1, new HashSet<>()).contains(44));
        return adjacencias.getOrDefault(owner1, new HashSet<>()).contains(owner2);
    }

    public void exibirGrafo() {
        for (Map.Entry<String, Set<String>> entry : adjacencias.entrySet()) {
            System.out.println("Proprietário " + entry.getKey() + " é vizinho de: " + entry.getValue());
        }
    }
}
