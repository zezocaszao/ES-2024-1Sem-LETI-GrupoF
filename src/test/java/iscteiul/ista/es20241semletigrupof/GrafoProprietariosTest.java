package iscteiul.ista.es20241semletigrupof;

import javafx.geometry.BoundingBox;
import java.util.*;

class DadosProprietariosTest {
    private final int objectId;
    private final String owner;
    private final String geometry;

    public DadosProprietariosTest(int objectId, String owner, String geometry) {
        this.objectId = objectId;
        this.owner = owner;
        this.geometry = geometry;
    }

    public int getObjectId() {
        return objectId;
    }

    public String getOwner() {
        return owner;
    }

    public String getGeometry() {
        return geometry;
    }
}

public class GrafoProprietariosTest {
    public static void main(String[] args) {
        // Cria uma lista de propriedades com dados fictícios
        List<DadosPropriedades> propriedades = new ArrayList<>();

        propriedades.add(new DadosPropriedades(1, "7343148.0", "2,99624E+12", 57.2469341921808,
                202.05981432070362,
                "MULTIPOLYGON (((299218.5203999998 3623637.4791, 299218.5033999998 3623637.4715, 299218.04000000004 3623638.4800000004, 299232.7400000002 3623644.6799999997, 299236.6233999999 3623637.1974, 299236.93709999975 3623636.7885999996, 299238.04000000004 3623633.4800000004, 299222.63999999966 3623627.1799999997, 299218.5203999998 3623637.4791)))\n",
                "93", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)"));

        // Inicializa o grafo
        GrafoProprietarios grafo = new GrafoProprietarios();

        // Constrói o grafo a partir da lista de propriedades
        grafo.construirGrafoProprietarios(propriedades);

        // Exibe o grafo no console
        System.out.println("Grafo de Proprietários:");
        grafo.exibirGrafo();

        // Testa se dois proprietários são vizinhos
        System.out.println("\nTestando vizinhança:");
        System.out.println("Alice e Bob são vizinhos? " + grafo.saoVizinhos("Alice", "Bob"));
        System.out.println("Alice e Charlie são vizinhos? " + grafo.saoVizinhos("Alice", "Charlie"));
        System.out.println("Bob e Charlie são vizinhos? " + grafo.saoVizinhos("Bob", "Charlie"));
    }
}
