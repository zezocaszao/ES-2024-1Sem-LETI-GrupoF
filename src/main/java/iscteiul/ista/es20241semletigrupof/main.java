package iscteiul.ista.es20241semletigrupof;

import java.io.IOException;
import java.util.*;

public class main {
    public static void main(String[] args) {

      /* String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";



        HelloApplication.main(args);

  */
        // Caminho para o arquivo CSV
        String caminhoArquivo = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

        try {
            // Passo 1: Carregar dados do CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoArquivo);

            // Passo 2: Construir o grafo de propriedades
            Grafo grafoPropriedades = new Grafo();

            for (int i = 0; i < propriedades.size(); i++) {
                DadosPropriedades prop1 = propriedades.get(i);
                for (int j = i + 1; j < propriedades.size(); j++) {
                    DadosPropriedades prop2 = propriedades.get(j);

                    // Verificar interseção usando bounding boxes
                    if (grafoPropriedades.calcularBoundingBox(prop1.getGeometry())
                            .intersects(grafoPropriedades.calcularBoundingBox(prop2.getGeometry()))) {
                        grafoPropriedades.adicionarVizinho(prop1.getObjectId(), prop2.getObjectId());
                        System.out.println("Proprietário " + prop1.getOwner() + " e Proprietário" + prop2.getOwner() + " são vizinhas.");
                    }
                }
            }

            // Passo 3: Criar o mapeamento de IDs únicos para os proprietários
            Map<String, Integer> nomeParaIdProprietario = new HashMap<>();
            Map<Integer, Integer> mapaPropriedadeParaProprietario = new HashMap<>();
            int idAtual = 1;

            for (DadosPropriedades propriedade : propriedades) {
                String nomeProprietario = propriedade.getOwner();
                if (!nomeParaIdProprietario.containsKey(nomeProprietario)) {
                    nomeParaIdProprietario.put(nomeProprietario, idAtual++);
                }
                mapaPropriedadeParaProprietario.put(propriedade.getObjectId(), nomeParaIdProprietario.get(nomeProprietario));
            }

            // Passo 4: Construir o grafo de proprietários
            GrafoProprietarios grafoProprietarios = new GrafoProprietarios();
            grafoProprietarios.construirGrafo(grafoPropriedades, mapaPropriedadeParaProprietario);

            // Passo 5: Exibir os vizinhos de cada proprietário
            System.out.println("\nVizinhos dos Proprietários:");
            grafoProprietarios.exibirGrafo(); // Usamos diretamente o método existente
        } catch (Exception e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}