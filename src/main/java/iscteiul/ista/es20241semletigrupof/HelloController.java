package iscteiul.ista.es20241semletigrupof;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.*;

public class HelloController {


    @FXML
    private VBox container;

    @FXML
    private Pane paneGrafo;

    // Variáveis para armazenar a posição do mouse
    private double mouseX = 0;
    private double mouseY = 0;



    @FXML
    protected void onExercicio1Click() {
        try {

            String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);
            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setPrefHeight(400);
            textArea.setPrefWidth(600);

            StringBuilder stringBuilder = new StringBuilder();
            for (DadosPropriedades propriedade : propriedades) {
                stringBuilder.append(propriedade.toString()).append("\n");
            }

            textArea.setText(stringBuilder.toString());
            container.getChildren().add(textArea);

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível carregar os dados: " + e.getMessage());
        }
    }

    @FXML
    protected void onExercicio2Click() {
        try {
            // Carregar as propriedades a partir do arquivo CSV
            String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);

            // Criar o grafo
            Grafo grafo = new Grafo();
            grafo.construirGrafo(propriedades); // Construir o grafo com as vizinhanças

            // Criar um painel para desenhar
            Pane pane = new Pane();
            Map<Integer, Circle> nos = new HashMap<>(); // Armazenar os círculos que representam as propriedades

            // Ajustar a posição dos nós
            double xInicio = 100; // Posição inicial horizontal para os nós azuis
            double yInicio = 100; // Posição inicial vertical para os nós azuis
            double distanciaHorizontal = 200; // Distância horizontal entre os nós azuis (apenas um por linha)
            double distanciaVertical = 200;   // Distância vertical entre as linhas de nós azuis

            // Variável para controlar a linha dos nós azuis
            int linhaAzul = 0;

            double maxX = 0;  // Para controlar o tamanho máximo em X (largura)
            double maxY = 0;  // Para controlar o tamanho máximo em Y (altura)

            // Para cada conjunto de vizinhos, desenhar os nós e conexões
            for (Map.Entry<Integer, Set<Integer>> entry : grafo.getAdjacencias().entrySet()) {
                Integer idPropriedade = entry.getKey();
                Set<Integer> vizinhos = entry.getValue();

                // Ajustar a posição do nó azul em sua linha
                double xPos = xInicio;  // Um único nó azul por linha, então a posição horizontal é fixa
                double yPos = yInicio + linhaAzul * distanciaVertical; // Cada linha vai para baixo

                // Criar um círculo para o nó azul (propriedade)
                Circle circulo = new Circle(20);
                circulo.setFill(Color.BLUE);
                circulo.setStroke(Color.BLACK);
                circulo.setCenterX(xPos);
                circulo.setCenterY(yPos);

                // Adicionar o texto com o identificador da propriedade
                Text texto = new Text(String.valueOf(idPropriedade));
                texto.setX(circulo.getCenterX() - 10);
                texto.setY(circulo.getCenterY() + 5);

                // Adicionar o nó azul (propriedade) ao painel
                pane.getChildren().addAll(circulo, texto);
                nos.put(idPropriedade, circulo);

                // Desenhar as conexões (linhas) para os vizinhos (nós verdes)
                int offset = 1; // Para controlar a disposição dos vizinhos ao longo de uma linha
                for (Integer vizinhoId : vizinhos) {
                    // Criar um círculo para o vizinho (nó verde)
                    Circle circuloVizinho = new Circle(20);
                    circuloVizinho.setFill(Color.GREEN);
                    circuloVizinho.setStroke(Color.BLACK);

                    // Posicionar o vizinho ligeiramente abaixo do nó azul
                    circuloVizinho.setCenterX(xPos + offset * distanciaHorizontal); // Ajuste horizontal dos vizinhos
                    circuloVizinho.setCenterY(yPos + distanciaVertical);            // Ajuste vertical dos vizinhos

                    // Adicionar o texto para o vizinho
                    Text textoVizinho = new Text(String.valueOf(vizinhoId));
                    textoVizinho.setX(circuloVizinho.getCenterX() - 10);
                    textoVizinho.setY(circuloVizinho.getCenterY() + 5);

                    // Adicionar o vizinho ao painel
                    pane.getChildren().addAll(circuloVizinho, textoVizinho);
                    nos.put(vizinhoId, circuloVizinho);

                    // Desenhar a linha conectando o nó azul ao vizinho (nó verde)
                    Line linha = new Line();
                    linha.setStartX(circulo.getCenterX());
                    linha.setStartY(circulo.getCenterY());
                    linha.setEndX(circuloVizinho.getCenterX());
                    linha.setEndY(circuloVizinho.getCenterY());
                    linha.setStroke(Color.BLACK);

                    // Adicionar a linha ao painel
                    pane.getChildren().add(linha);

                    offset++; // Incrementa para posicionar o próximo vizinho em uma posição à direita
                }
                // Atualizar os limites máximos do painel
                maxX = Math.max(maxX, xPos + distanciaHorizontal * offset);
                maxY = Math.max(maxY, yPos + distanciaVertical);
                linhaAzul++; // Incrementar para a próxima linha de nós azuis
            }
            // Ajustar o tamanho preferido do painel com base no conteúdo
            pane.setMinSize(maxX + 100, maxY + 100);  // Ajuste automático baseado no conteúdo
            pane.setPrefSize(maxX + 100, maxY + 100);  // Adicionar margens para o conteúdo

            // Criar um ScrollPane para permitir o scroll dentro do painel
            ScrollPane scrollPane = new ScrollPane(pane);
            scrollPane.setFitToHeight(true); // Ajustar automaticamente à altura
            scrollPane.setFitToWidth(true);  // Ajustar automaticamente à largura
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Sempre mostrar barra de rolagem vertical

            // Criar uma nova janela (Stage) para exibir o conteúdo
            Stage novaJanela = new Stage();
            novaJanela.setTitle("Exibição do Grafo");
            Scene novaCena = new Scene(scrollPane, 800, 600);
            novaJanela.setScene(novaCena);

            // Mostrar a nova janela
            novaJanela.show();

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível carregar os dados ou desenhar o grafo: " + e.getMessage());
            e.printStackTrace();
        }
    }




    @FXML
    protected void onExercicio3Click() {
        try {
            String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);
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
            if (!List.of("freguesia", "municipio", "ilha").contains(tipoArea)) {
                showAlert("Erro", "Tipo de área inválido. Use: freguesia, municipio ou ilha.");
                return;
            }

            List<String> areasDisponiveis = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, tipoArea);
            if (areasDisponiveis.isEmpty()) {
                showAlert("Erro", "Não há áreas disponíveis para o tipo especificado: " + tipoArea);
                return;
            }

            StringBuilder areasTexto = new StringBuilder("Áreas disponíveis:\n");
            for (String area : areasDisponiveis) {
                areasTexto.append("- ").append(area).append("\n");
            }

            TextArea areasDisponiveisArea = new TextArea(areasTexto.toString());
            areasDisponiveisArea.setEditable(false);
            Alert areasDialog = new Alert(Alert.AlertType.INFORMATION);
            areasDialog.setTitle("Áreas Disponíveis");
            areasDialog.setHeaderText("Selecione uma área a partir da lista abaixo:");
            areasDialog.getDialogPane().setContent(areasDisponiveisArea);
            areasDialog.showAndWait();
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
            double areaMedia = CalculadoraPropriedades.calcularAreaMedia(propriedades, tipoArea, valorArea);

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
