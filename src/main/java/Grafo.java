// Classe Grafo adaptada para exibir apenas vizinhanças
import java.util.*;

public class Grafo {
    private Map<Integer, DadosPropriedades> propriedades; // Usando DadosPropriedades como valores
    private Map<DadosPropriedades, List<DadosPropriedades>> adjacencias; // Lista de adjacências para cada propriedade

    public Grafo() {
        propriedades = new HashMap<>();
        adjacencias = new HashMap<>();
    }

    // Adiciona uma propriedade ao grafo
    public void adicionarPropriedade(DadosPropriedades propriedade) {
        propriedades.put(propriedade.getObjectId(), propriedade); // Usando objectId como chave
        adjacencias.put(propriedade, new ArrayList<>());
    }

    // Adiciona uma relação de adjacência entre duas propriedades
    public void adicionarAdjacencia(DadosPropriedades p1, DadosPropriedades p2) {
        adjacencias.get(p1).add(p2);
        adjacencias.get(p2).add(p1); // Grafo não direcionado
    }

    // Exibe apenas as propriedades que possuem vizinhos
    public void exibirGrafo() {
        for (DadosPropriedades propriedade : propriedades.values()) {
            List<DadosPropriedades> vizinhos = adjacencias.get(propriedade);
            if (!vizinhos.isEmpty()) {
                System.out.print("Propriedade " + propriedade.getParId() + " - Vizinhos: ");
                for (DadosPropriedades vizinho : vizinhos) {
                    System.out.print(vizinho.getParId() + ", ");
                }
                System.out.println(); // Linha para cada propriedade com vizinhos
            }
        }
    }
}
