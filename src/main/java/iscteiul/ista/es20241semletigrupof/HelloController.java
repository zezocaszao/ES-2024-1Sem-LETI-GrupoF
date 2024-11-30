package iscteiul.ista.es20241semletigrupof;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        try {
            // Caminho do arquivo CSV
            String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

            // Carregar os dados do CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);

            // Solicitar ao utilizador o tipo de área geográfica
            TextInputDialog tipoDialog = new TextInputDialog();
            tipoDialog.setTitle("Seleção de Tipo de Área");
            tipoDialog.setHeaderText("Digite o tipo de área geográfica (freguesia, municipio, ilha):");
            tipoDialog.setContentText("Tipo de área:");
            Optional<String> tipoAreaOpt = tipoDialog.showAndWait();

            if (!tipoAreaOpt.isPresent() || tipoAreaOpt.get().trim().isEmpty()) {
                showAlert("Erro", "O tipo de área geográfica é obrigatório.");
                return;
            }

            String tipoArea = tipoAreaOpt.get().trim().toLowerCase();

            // Verificar se o tipo de área é válido
            if (!List.of("freguesia", "municipio", "ilha").contains(tipoArea)) {
                showAlert("Erro", "Tipo de área inválido. Use: freguesia, municipio ou ilha.");
                return;
            }

            // Obter e exibir as áreas disponíveis
            List<String> areasDisponiveis = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, tipoArea);
            if (areasDisponiveis.isEmpty()) {
                showAlert("Erro", "Não há áreas disponíveis para o tipo especificado: " + tipoArea);
                return;
            }

            StringBuilder areasTexto = new StringBuilder("Áreas disponíveis:\n");
            for (String area : areasDisponiveis) {
                areasTexto.append("- ").append(area).append("\n");
            }

            // Mostrar as áreas disponíveis ao utilizador
            TextArea areasDisponiveisArea = new TextArea(areasTexto.toString());
            areasDisponiveisArea.setEditable(false);
            Alert areasDialog = new Alert(Alert.AlertType.INFORMATION);
            areasDialog.setTitle("Áreas Disponíveis");
            areasDialog.setHeaderText("Selecione uma área a partir da lista abaixo:");
            areasDialog.getDialogPane().setContent(areasDisponiveisArea);
            areasDialog.showAndWait();

            // Solicitar o valor da área
            TextInputDialog valorDialog = new TextInputDialog();
            valorDialog.setTitle("Seleção de Valor da Área");
            valorDialog.setHeaderText("Digite o valor da área geográfica (ex.: 'Arco da Calheta' ou 'Calheta'):");
            valorDialog.setContentText("Valor da área:");
            Optional<String> valorAreaOpt = valorDialog.showAndWait();

            if (!valorAreaOpt.isPresent() || valorAreaOpt.get().trim().isEmpty()) {
                showAlert("Erro", "O valor da área é obrigatório.");
                return;
            }

            String valorArea = valorAreaOpt.get().trim();

            // Calcular a área média
            double areaMedia = CalculadoraPropriedades.calcularAreaMedia(propriedades, tipoArea, valorArea);

            // Mostrar o resultado
            if (areaMedia == -1) {
                showAlert("Resultado", "Nenhuma propriedade encontrada para a área especificada: " + valorArea + " (" + tipoArea + ").");
            } else {
                showAlert("Resultado", String.format("A área média das propriedades em %s (%s) é: %.2f", valorArea, tipoArea, areaMedia));
            }

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível calcular a área média: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void onExercicio4Click() {
        try {
            // Caminho do arquivo CSV
            String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

            // Carregar os dados do CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);

            // Solicitar ao utilizador o tipo de área geográfica
            TextInputDialog tipoDialog = new TextInputDialog();
            tipoDialog.setTitle("Seleção de Tipo de Área");
            tipoDialog.setHeaderText("Digite o tipo de área geográfica (freguesia, municipio, ilha):");
            tipoDialog.setContentText("Tipo de área:");
            Optional<String> tipoAreaOpt = tipoDialog.showAndWait();

            if (!tipoAreaOpt.isPresent() || tipoAreaOpt.get().trim().isEmpty()) {
                showAlert("Erro", "O tipo de área geográfica é obrigatório.");
                return;
            }

            String tipoArea = tipoAreaOpt.get().trim().toLowerCase();

            // Verificar se o tipo de área é válido
            if (!List.of("freguesia", "municipio", "ilha").contains(tipoArea)) {
                showAlert("Erro", "Tipo de área inválido. Use: freguesia, municipio ou ilha.");
                return;
            }

            // Obter e exibir as áreas disponíveis
            List<String> areasDisponiveis = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, tipoArea);
            if (areasDisponiveis.isEmpty()) {
                showAlert("Erro", "Não há áreas disponíveis para o tipo especificado: " + tipoArea);
                return;
            }

            StringBuilder areasTexto = new StringBuilder("Áreas disponíveis:\n");
            for (String area : areasDisponiveis) {
                areasTexto.append("- ").append(area).append("\n");
            }

            // Mostrar as áreas disponíveis ao utilizador
            TextArea areasDisponiveisArea = new TextArea(areasTexto.toString());
            areasDisponiveisArea.setEditable(false);
            Alert areasDialog = new Alert(Alert.AlertType.INFORMATION);
            areasDialog.setTitle("Áreas Disponíveis");
            areasDialog.setHeaderText("Selecione uma área a partir da lista abaixo:");
            areasDialog.getDialogPane().setContent(areasDisponiveisArea);
            areasDialog.showAndWait();

            // Solicitar o valor da área
            TextInputDialog valorDialog = new TextInputDialog();
            valorDialog.setTitle("Seleção de Valor da Área");
            valorDialog.setHeaderText("Digite o valor da área geográfica (ex.: 'Arco da Calheta' ou 'Calheta'):");
            valorDialog.setContentText("Valor da área:");
            Optional<String> valorAreaOpt = valorDialog.showAndWait();

            if (!valorAreaOpt.isPresent() || valorAreaOpt.get().trim().isEmpty()) {
                showAlert("Erro", "O valor da área é obrigatório.");
                return;
            }

            String valorArea = valorAreaOpt.get().trim();

            // Construir o grafo
            Grafo grafo = new Grafo();
            grafo.construirGrafo(propriedades);

            // Calcular a área média considerando propriedades adjacentes do mesmo proprietário
            double areaMedia = CalcularPropiedadesOwners.averageAreaOwner(propriedades, grafo, tipoArea, valorArea);

            // Mostrar o resultado
            if (areaMedia == -1) {
                showAlert("Resultado", "Nenhuma propriedade encontrada para a área especificada: " + valorArea + " (" + tipoArea + ").");
            } else {
                showAlert("Resultado", String.format("A área média das propriedades em %s (%s) é: %.2f", valorArea, tipoArea, areaMedia));
            }

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível calcular a área média: " + e.getMessage());
            e.printStackTrace();
        }
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
