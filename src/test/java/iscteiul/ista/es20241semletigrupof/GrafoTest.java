package iscteiul.ista.es20241semletigrupof;

import javafx.geometry.BoundingBox;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class GrafoTest {

    @Test
    public void testAdicionarVizinho() {
        Grafo grafo = new Grafo();
        grafo.adicionarVizinho(1, 2);
        grafo.adicionarVizinho(2, 3);

        Map<Integer, Set<Integer>> adjacencias = grafo.getAdjacencias();

        assertTrue(adjacencias.containsKey(1));
        assertTrue(adjacencias.containsKey(2));
        assertTrue(adjacencias.containsKey(3));
        assertEquals(Set.of(2), adjacencias.get(1));
        assertEquals(Set.of(1, 3), adjacencias.get(2));
        assertEquals(Set.of(2), adjacencias.get(3));
    }

    @Test
    public void testCalcularBoundingBox() {
        Grafo grafo = new Grafo();

        String geometry = "MULTIPOLYGON (((10 20, 30 40, 50 60, 10 20)))";
        BoundingBox boundingBox = grafo.calcularBoundingBox(geometry);

        assertEquals(10, boundingBox.getMinX());
        assertEquals(20, boundingBox.getMinY());
        assertEquals(40, boundingBox.getWidth());
        assertEquals(40, boundingBox.getHeight());
    }

    @Test
    public void testCalcularBoundingBoxInvalidGeometry() {
        Grafo grafo = new Grafo();

        // Teste com geometria inválida
        String invalidGeometry = "EMPTY";
        BoundingBox boundingBox = grafo.calcularBoundingBox(invalidGeometry);
        assertEquals(0, boundingBox.getWidth());
        assertEquals(0, boundingBox.getHeight());
    }

    @Test
    public void testExibirGrafo() {
        Grafo grafo = new Grafo();
        grafo.adicionarVizinho(1, 2);
        grafo.adicionarVizinho(2, 3);

        // Capturar saída do console (opcional para verificar resultados manualmente)
        grafo.exibirGrafo();

        Map<Integer, Set<Integer>> adjacencias = grafo.getAdjacencias();
        assertEquals(2, adjacencias.get(2).size());
    }
}
