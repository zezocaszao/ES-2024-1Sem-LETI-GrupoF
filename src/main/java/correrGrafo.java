import java.io.IOException;
import java.util.List;

public class correrGrafo {
    public static void main(String[] args) {
        try {
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades();
            Grafo grafo = new Grafo();
            for (DadosPropriedades propriedade : propriedades) {
                grafo.adicionarPropriedade(propriedade);
            }


            if (propriedades.size() > 1) {
                grafo.adicionarAdjacencia(propriedades.get(0), propriedades.get(1));
                grafo.adicionarAdjacencia(propriedades.get(1), propriedades.get(2));
            }

            grafo.exibirGrafo();

        } catch (IOException e) {
            System.out.println("Erro ao carregar propriedades: " + e.getMessage());
        }
    }
}
