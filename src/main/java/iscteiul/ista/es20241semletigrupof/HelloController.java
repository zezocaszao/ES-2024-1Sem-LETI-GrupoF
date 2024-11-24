package iscteiul.ista.es20241semletigrupof;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class HelloController {


    @FXML
    private VBox container; // VBox do layout FXML para adicionar o TextArea dinamicamente.

    @FXML
    protected void onExercicio1Click() {
        try {
            // Caminho do arquivo CSV
            String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

            // Carregar os dados do CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);

            // Mostrar os dados na interface
            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setPrefHeight(400);
            textArea.setPrefWidth(600);

            StringBuilder stringBuilder = new StringBuilder();
            for (DadosPropriedades propriedade : propriedades) {
                stringBuilder.append(propriedade.toString()).append("\n");
            }

            textArea.setText(stringBuilder.toString());
            container.getChildren().add(textArea); // Adiciona o TextArea ao layout

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível carregar os dados: " + e.getMessage());
        }
    }

    @FXML
    protected void onExercicio2Click() {
        try {
            // Caminho do arquivo CSV
            String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

            // Carregar os dados do CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);

            // Construir o grafo
            Grafo grafo = new Grafo();
            grafo.construirGrafo(propriedades);

            // Capturar a saída do método exibirGrafo
            StringBuilder grafoTexto = new StringBuilder();
            for (Map.Entry<Integer, Set<Integer>> entry : grafo.getAdjacencias().entrySet()) {
                grafoTexto.append("Propriedade ").append(entry.getKey())
                        .append(" é vizinha de: ").append(entry.getValue())
                        .append("\n");
            }

            // Criar o TextArea para exibir o grafo
            TextArea textArea = new TextArea();
            textArea.setText(grafoTexto.toString());
            textArea.setEditable(false);
            textArea.setPrefHeight(400);
            textArea.setPrefWidth(600);

            // Adicionar o TextArea ao layout
            container.getChildren().clear(); // Limpa o conteúdo anterior
            container.getChildren().add(textArea);

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível carregar os dados: " + e.getMessage());
        }
    }

    @FXML
    protected void onExercicio3Click() {
        showAlert("Exercício 3", "Você selecionou o Exercício 3.");
    }

    @FXML
    protected void onExercicio4Click() {
        showAlert("Exercício 4", "Você selecionou o Exercício 4.");
    }

    @FXML
    protected void onExercicio5Click() {
        showAlert("Exercício 5", "Você selecionou o Exercício 5.");
    }

    @FXML
    protected void onExercicio6Click() {
        showAlert("Exercício 6", "Você selecionou o Exercício 6.");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
