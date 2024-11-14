import java.util.*;

public class Grafo {
    private Map<Integer, DadosPropriedades> propriedades;
    private Map<DadosPropriedades, List<DadosPropriedades>> adjacencias;

    public Grafo() {
        propriedades = new HashMap<>();
        adjacencias = new HashMap<>();
    }

    // Adiciona uma propriedade ao grafo
    public void adicionarPropriedade(DadosPropriedades propriedade) {
        propriedades.put(propriedade.getObjectId(), propriedade); // Usa objectId como key
        adjacencias.put(propriedade, new ArrayList<>());
    }

    public void adicionarAdjacencia(DadosPropriedades p1, DadosPropriedades p2) {
        adjacencias.get(p1).add(p2);
        adjacencias.get(p2).add(p1);
    }

    public void exibirGrafo() {
        for (DadosPropriedades propriedade : propriedades.values()) {
            List<DadosPropriedades> vizinhos = adjacencias.get(propriedade);
            if (!vizinhos.isEmpty()) {
                System.out.print("Propriedade " + propriedade.getParId() + " - Vizinhos: ");
                for (DadosPropriedades vizinho : vizinhos) {
                    System.out.print(vizinho.getParId() + ", ");
                }
                System.out.println();
            }
        }
    }
}
