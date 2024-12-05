package iscteiul.ista.es20241semletigrupof;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javafx.geometry.BoundingBox;
import java.util.*;

public class GrafoTest {

    private Grafo grafo;

    @BeforeEach
    public void setUp() {
        grafo = new Grafo();
    }

    @Test
    public void testAdicionarVizinho() {
        grafo.adicionarVizinho(1, 2);
        grafo.adicionarVizinho(2, 3);

        assertTrue(grafo.saoVizinhos(1, 2));
        assertTrue(grafo.saoVizinhos(2, 1)); // Testa a bidirecionalidade
        assertTrue(grafo.saoVizinhos(2, 3));
        assertFalse(grafo.saoVizinhos(1, 3)); // Não há conexão direta
    }

    @Test
    public void testCalcularBoundingBox() {
        String geometry = "MULTIPOLYGON(((0 0, 10 0, 10 10, 0 10, 0 0)))";
        BoundingBox box = grafo.calcularBoundingBox(geometry);

        assertEquals(0, box.getMinX());
        assertEquals(0, box.getMinY());
        assertEquals(10, box.getWidth());
        assertEquals(10, box.getHeight());

        // Testa geometria inválida
        BoundingBox emptyBox = grafo.calcularBoundingBox("EMPTY");
        assertEquals(0, emptyBox.getWidth());
        assertEquals(0, emptyBox.getHeight());
    }

    @Test
    public void testConstruirGrafo() {
        // Cria objetos de DadosPropriedades simulados
        DadosPropriedades prop1 =new DadosPropriedades(1, "7343148.0", "2,99624E+12", 57.2469341921808,
                202.05981432070362,
                "MULTIPOLYGON (((299218.5203999998 3623637.4791, 299218.5033999998 3623637.4715, 299218.04000000004 3623638.4800000004, 299232.7400000002 3623644.6799999997, 299236.6233999999 3623637.1974, 299236.93709999975 3623636.7885999996, 299238.04000000004 3623633.4800000004, 299222.63999999966 3623627.1799999997, 299218.5203999998 3623637.4791)))\n",
                "93", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)");

        DadosPropriedades prop2 = new DadosPropriedades(178,	"7343170.0",	"2,99624E+12",	201.8209596197191,	616.0341240613293	,"MULTIPOLYGON (((299211.5703999996 3623638.663899999, 299217.26999999955 3623640.4299999997, 299218.04000000004 3623638.4800000004, 299218.5033999998 3623637.4715, 299218.5203999998 3623637.4791, 299222.63999999966 3623627.1799999997, 299222.8076999998 3623626.7882000003, 299226.76999999955 3623617.5299999993, 299233.6699999999 3623594.7300000004, 299237.2229000004 3623581.2423, 299239.0700000003 3623574.2300000004, 299243.3700000001 3623553.33, 299239.8499999996 3623553.9299999997, 299239.8487 3623553.9338000007, 299236.1286000004 3623553.7356000002, 299236.0700000003 3623554.130000001, 299229.1699999999 3623586.4299999997, 299226.3700000001 3623597.0299999993, 299218.1699999999 3623621.0299999993, 299213.8700000001 3623629.9299999997, 299213.0700000003 3623629.630000001, 299207.8010999998 3623631.5562999994, 299213.2348999996 3623633.3674999997, 299211.5703999996 3623638.663899999)))",
                "45",	"Arco da Calheta",	"Calheta",	"Ilha da Madeira (Madeira)");
        DadosPropriedades prop3 = new DadosPropriedades(2,	"7344660.0",	"2,99622E+12",	55.63800662596267,
                151.76387471712783,	"MULTIPOLYGON (((298724.1991999997 3623192.6094000004, 298724.3200000003 3623192.619999999, 298724.26999999955 3623185.7200000007, 298723.8854 3623185.681500001, 298723.8854 3623185.6338, 298717.2167999996 3623184.6405999996, 298716.2909000004 3623184.495100001, 298716.1699999999 3623184.5700000003, 298711.51999999955 3623184.17, 298709.1414000001 3623183.7961999997, 298708.48000000045 3623183.3200000003, 298705.6799999997 3623183.2200000007, 298704.5800000001 3623183.3200000003, 298703.98000000045 3623184.119999999, 298703.48000000045 3623190.7200000007, 298704.0525000002 3623190.7905, 298704.0488999998 3623190.8441000003, 298705.574 3623190.9777000006, 298709.98000000045 3623191.5199999996, 298710.0937999999 3623191.3737000003, 298724.1991999997 3623192.6094000004)))",
                "68",	"Arco da Calheta",	"Calheta",	"Ilha da Madeira (Madeira)");

        List<DadosPropriedades> propriedades = Arrays.asList(prop1, prop2, prop3);

        grafo.construirGrafo(propriedades);

        // Testa vizinhanças
        assertTrue(grafo.saoVizinhos(1, 178));
        assertFalse(grafo.saoVizinhos(1, 2));
        assertFalse(grafo.saoVizinhos(2, 178));
    }

    @Test
    public void testExibirGrafo() {
        grafo.adicionarVizinho(1, 2);
        grafo.adicionarVizinho(2, 3);

        grafo.exibirGrafo();
        // Teste visual: Verificar a saída do console manualmente
    }
}
