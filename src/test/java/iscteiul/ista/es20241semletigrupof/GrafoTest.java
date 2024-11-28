package iscteiul.ista.es20241semletigrupof;

import javafx.geometry.BoundingBox;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GrafoTest {

    @Test
    void testAdicionarVizinho() {
        Grafo grafo = new Grafo();
        grafo.adicionarVizinho(1, 2);

        Map<Integer, Set<Integer>> adjacencias = grafo.getAdjacencias();

        assertTrue(adjacencias.containsKey(1));
        assertTrue(adjacencias.containsKey(2));
        assertTrue(adjacencias.get(1).contains(2));
        assertTrue(adjacencias.get(2).contains(1));
    }

    @Test
    void testCalcularBoundingBox() {
        Grafo grafo = new Grafo();

        // Teste direto do método privado
        BoundingBox bb = grafo.calcularBoundingBox("MULTIPOLYGON(((0 0, 0 10, 10 10, 10 0, 0 0)))");
        assertEquals(0, bb.getMinX());
        assertEquals(0, bb.getMinY());
        assertEquals(10, bb.getWidth());
        assertEquals(10, bb.getHeight());
    }

    @Test
    void testConstruirGrafo() {
        Grafo grafo = new Grafo();

        List<DadosPropriedades> propriedades = new ArrayList<>();
        propriedades.add(new DadosPropriedades(1, "MULTIPOLYGON(((0 0, 0 10, 10 10, 10 0, 0 0)))"));
        propriedades.add(new DadosPropriedades(2, "MULTIPOLYGON(((5 5, 5 15, 15 15, 15 5, 5 5)))"));
        propriedades.add(new DadosPropriedades(3, "MULTIPOLYGON(((20 20, 20 30, 30 30, 30 20, 20 20)))"));

        grafo.construirGrafo(propriedades);

        Map<Integer, Set<Integer>> adjacencias = grafo.getAdjacencias();

        assertTrue(adjacencias.containsKey(1));
        assertTrue(adjacencias.containsKey(2));
        assertFalse(adjacencias.containsKey(3));

        assertTrue(adjacencias.get(1).contains(2));
        assertTrue(adjacencias.get(2).contains(1));
        assertFalse(adjacencias.get(1).contains(3));
    }
}
