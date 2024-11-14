import java.io.IOException;
import java.util.List;

public class correrGrafo {
    public static void main(String[] args) {
        try {
            // Carrega as propriedades do arquivo CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades();

            // Cria o grafo
            Grafo grafo = new Grafo();

            // Adiciona as propriedades ao grafo
            for (DadosPropriedades propriedade : propriedades) {
                grafo.adicionarPropriedade(propriedade);
            }

            // Adiciona algumas adjacências manualmente (ou você pode definir a lógica de acordo com proximidade real)
            if (propriedades.size() > 1) {
                grafo.adicionarAdjacencia(propriedades.get(0), propriedades.get(1));
                grafo.adicionarAdjacencia(propriedades.get(1), propriedades.get(2));
            }

            // Exibe o grafo com apenas as vizinhanças
            grafo.exibirGrafo();

        } catch (IOException e) {
            System.out.println("Erro ao carregar propriedades: " + e.getMessage());
        }
    }
}
