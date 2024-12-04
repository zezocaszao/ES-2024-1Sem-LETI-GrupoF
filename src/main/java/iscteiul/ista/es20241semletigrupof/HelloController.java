package iscteiul.ista.es20241semletigrupof;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class HelloController {


    @FXML
    private VBox container;

    @FXML
    private Pane paneGrafo;

    @FXML
    private Label nomeArquivoLabel; // Variável para o Label

    private File arquivoCSV; // Variável para armazenar o arquivo CSV carregado

    @FXML
    private ChoiceBox<String> tipoAreaChoiceBox;

    // Variáveis para armazenar a posição do mouse
    private double mouseX = 0;
    private double mouseY = 0;

    public void onCarregarCSVClick() {
        // Cria uma instância do FileChooser
        FileChooser fileChooser = new FileChooser();

        // Filtra os tipos de ficheiros para mostrar apenas CSV
        FileChooser.ExtensionFilter filtroCSV = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        fileChooser.getExtensionFilters().add(filtroCSV);

        // Mostra a caixa de diálogo para o usuário escolher o arquivo
        Stage stage = new Stage();
        File arquivoSelecionado = fileChooser.showOpenDialog(stage);

        // Se um arquivo foi selecionado
        if (arquivoSelecionado != null) {
            // Armazena o arquivo
            arquivoCSV = arquivoSelecionado;

            // Atualiza o texto do Label para mostrar o nome do arquivo selecionado
            nomeArquivoLabel.setText(arquivoSelecionado.getName());
        } else {
            // Caso nenhum arquivo seja selecionado, reseta o nome do arquivo
            nomeArquivoLabel.setText("Nenhum arquivo selecionado");
        }
    }

    @FXML
    protected void onExercicio1Click() {
        try {
            // Verifica se um arquivo foi carregado
            if (arquivoCSV == null) {
                showAlert("Erro", "Nenhum arquivo CSV foi carregado.");
                return;
            }

            // Carregar os dados do CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());

            // Criar um TextArea para exibir os dados
            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setPrefHeight(400);
            textArea.setPrefWidth(600);

            // Construir o texto a partir das propriedades
            StringBuilder stringBuilder = new StringBuilder();
            for (DadosPropriedades propriedade : propriedades) {
                stringBuilder.append(propriedade.toString()).append("\n");
            }
            textArea.setText(stringBuilder.toString());

            // Criar uma nova janela
            Stage novaJanela = new Stage();
            novaJanela.setTitle("Exercício 1 - Dados Carregados");

            // Adicionar o TextArea a um Scene e exibir na nova janela
            VBox root = new VBox(textArea);
            root.setPadding(new Insets(10));
            Scene scene = new Scene(root, 650, 450);
            novaJanela.setScene(scene);

            // Exibir a nova janela
            novaJanela.show();

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível carregar os dados: " + e.getMessage());
        }
    }


    @FXML
    protected void onExercicio2Click() {
        try {
            // Carregar as propriedades a partir do arquivo CSV
            //String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";
           // List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());


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
            novaJanela.setTitle("Exibição do GrafoProprietarios");
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
            //String caminhoCsv = "src/main/resources/iscteiul/ista/es20241semletigrupof/Madeira-Moodle-1.1.csv";

            //List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoCsv);
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());

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
        try {
            // Verifica se um arquivo CSV foi carregado
            if (arquivoCSV == null) {
                showAlert("Erro", "Nenhum arquivo CSV foi carregado.");
                return;
            }

            // Carregar as propriedades a partir do arquivo CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());

            // Exibir diálogo para o tipo de área
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

            // Obter as áreas disponíveis
            List<String> areasDisponiveis = CalcularPropriedadesOwners.obterAreasDisponiveis(propriedades, tipoArea);
            if (areasDisponiveis.isEmpty()) {
                showAlert("Erro", "Não há áreas disponíveis para o tipo especificado: " + tipoArea);
                return;
            }

            // Exibir lista de áreas para o utilizador escolher
            ChoiceDialog<String> areaDialog = new ChoiceDialog<>(areasDisponiveis.get(0), areasDisponiveis);
            areaDialog.setTitle("Escolha a Área");
            areaDialog.setHeaderText("Escolha uma área de " + tipoArea);
            areaDialog.setContentText("Área:");

            Optional<String> areaEscolhidaOpt = areaDialog.showAndWait();
            if (!areaEscolhidaOpt.isPresent()) {
                showAlert("Erro", "Nenhuma área foi selecionada.");
                return;
            }

            String areaEscolhida = areaEscolhidaOpt.get();

            // Obter os donos disponíveis na área escolhida e ordená-los
            List<String> donosDisponiveis = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, tipoArea, areaEscolhida);
            if (donosDisponiveis.isEmpty()) {
                showAlert("Erro", "Não há donos disponíveis na área " + areaEscolhida);
                return;
            }

            // Ordenar os donos alfabeticamente
            donosDisponiveis = donosDisponiveis.stream()
                    .sorted()
                    .collect(Collectors.toList());

            // Exibir lista de donos para o utilizador escolher
            ChoiceDialog<String> donoDialog = new ChoiceDialog<>(donosDisponiveis.get(0), donosDisponiveis);
            donoDialog.setTitle("Escolha o Dono");
            donoDialog.setHeaderText("Escolha o dono da área " + areaEscolhida);
            donoDialog.setContentText("Dono:");

            Optional<String> donoEscolhidoOpt = donoDialog.showAndWait();
            if (!donoEscolhidoOpt.isPresent()) {
                showAlert("Erro", "Nenhum dono foi selecionado.");
                return;
            }

            String donoEscolhido = donoEscolhidoOpt.get();

            // Calcular a área média das propriedades do dono na área escolhida
            double areaMedia = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, tipoArea, areaEscolhida, donoEscolhido);

            // Exibir o resultado da área média
            if (areaMedia == -1) {
                showAlert("Resultado", "Nenhuma propriedade encontrada para o dono " + donoEscolhido + " na área " + areaEscolhida + ".");
            } else {
                showAlert("Resultado", String.format("A área média das propriedades do dono %s na área %s é: %.2f", donoEscolhido, areaEscolhida, areaMedia));
            }

        } catch (Exception e) {
            showAlert("Erro", "Ocorreu um erro ao calcular a área média: " + e.getMessage());
            e.printStackTrace();
        }
    }




    @FXML
    protected void onExercicio5Click() {
        try {
            // Carregar as propriedades a partir do arquivo CSV
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());

            // Criar o grafo
            GrafoProprietarios grafo = new GrafoProprietarios();
            grafo.construirGrafoProprietarios(propriedades); // Construir o grafo com as vizinhanças

            // Criar um painel para desenhar
            Pane pane = new Pane();
            Map<String, Circle> nos = new HashMap<>(); // Mapear os donos aos círculos criados

            // Ajustar a posição dos nós
            double xInicio = 100; // Posição inicial horizontal para os nós azuis
            double yInicio = 100; // Posição inicial vertical para os nós azuis
            double distanciaHorizontal = 200; // Distância horizontal entre os nós azuis
            double distanciaVertical = 200;   // Distância vertical entre as linhas de nós azuis

            // Variável para controlar a linha dos nós azuis
            int linhaAzul = 0;

            double maxX = 0;  // Para controlar o tamanho máximo em X (largura)
            double maxY = 0;  // Para controlar o tamanho máximo em Y (altura)

            // Para cada conjunto de vizinhos, desenhar os nós e conexões
            for (Map.Entry<String, Set<String>> entry : grafo.getAdjacencias().entrySet()) {
                String dono = entry.getKey();  // O dono é uma String
                Set<String> vizinhos = entry.getValue();

                // Ajustar a posição do nó azul em sua linha
                double xPos = xInicio;
                double yPos = yInicio + linhaAzul * distanciaVertical;

                // Criar um círculo para o nó azul (dono da propriedade)
                Circle circulo = new Circle(20);
                circulo.setFill(Color.BLUE);
                circulo.setStroke(Color.BLACK);
                circulo.setCenterX(xPos);
                circulo.setCenterY(yPos);

                // Adicionar o texto com o nome do dono
                Text texto = new Text(dono);
                texto.setX(circulo.getCenterX() - 10);
                texto.setY(circulo.getCenterY() + 5);

                // Adicionar o nó azul (dono) ao painel
                pane.getChildren().addAll(circulo, texto);
                nos.put(dono, circulo);

                // Desenhar as conexões (linhas) para os vizinhos
                int offset = 1; // Para posicionar os vizinhos na linha
                for (String vizinho : vizinhos) {
                    // Criar um círculo para o vizinho (nó verde)
                    Circle circuloVizinho = new Circle(20);
                    circuloVizinho.setFill(Color.GREEN);
                    circuloVizinho.setStroke(Color.BLACK);

                    // Posicionar o vizinho ligeiramente abaixo do nó azul
                    circuloVizinho.setCenterX(xPos + offset * distanciaHorizontal);
                    circuloVizinho.setCenterY(yPos + distanciaVertical);

                    // Adicionar o texto para o vizinho
                    Text textoVizinho = new Text(vizinho);
                    textoVizinho.setX(circuloVizinho.getCenterX() - 10);
                    textoVizinho.setY(circuloVizinho.getCenterY() + 5);

                    // Adicionar o vizinho ao painel
                    pane.getChildren().addAll(circuloVizinho, textoVizinho);
                    nos.put(vizinho, circuloVizinho);

                    // Desenhar a linha conectando o nó azul ao vizinho (nó verde)
                    Line linha = new Line();
                    linha.setStartX(circulo.getCenterX());
                    linha.setStartY(circulo.getCenterY());
                    linha.setEndX(circuloVizinho.getCenterX());
                    linha.setEndY(circuloVizinho.getCenterY());
                    linha.setStroke(Color.BLACK);

                    // Adicionar a linha ao painel
                    pane.getChildren().add(linha);

                    offset++; // Incrementa para posicionar o próximo vizinho
                }

                // Atualizar os limites máximos do painel
                maxX = Math.max(maxX, xPos + distanciaHorizontal * offset);
                maxY = Math.max(maxY, yPos + distanciaVertical);
                linhaAzul = linhaAzul+2; // Incrementar para a próxima linha de nós azuis
            }

            // Ajustar o tamanho preferido do painel com base no conteúdo
            pane.setMinSize(maxX + 100, maxY + 100);
            pane.setPrefSize(maxX + 100, maxY + 100);

            // Criar um ScrollPane para permitir o scroll dentro do painel
            ScrollPane scrollPane = new ScrollPane(pane);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

            // Criar uma nova janela (Stage) para exibir o conteúdo
            Stage novaJanela = new Stage();
            novaJanela.setTitle("Exibição do Grafo de Proprietários");
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
    public void onExercicio6Click(ActionEvent event) throws Exception {
        Stage newStage = new Stage();
        newStage.setTitle("Sugestão de Trocas");

        // Carregar as propriedades do CSV
        List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());

        // Dialog para selecionar o tipo de área geográfica
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

        // Obter as áreas disponíveis
        List<String> areasDisponiveis = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, tipoArea);
        if (areasDisponiveis.isEmpty()) {
            showAlert("Erro", "Não há áreas disponíveis para o tipo especificado: " + tipoArea);
            return;
        }

        // Exibir as áreas disponíveis
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

        // Dialog para escolher uma área específica
        TextInputDialog valorDialog = new TextInputDialog();
        valorDialog.setTitle("Seleção de Valor da Área");
        valorDialog.setHeaderText("Digite o valor da área geográfica (ex.: 'Arco da Calheta' ou 'Calheta'):");
        valorDialog.setContentText("Valor da área:");
        Optional<String> valorAreaOpt = valorDialog.showAndWait();

        if (!valorAreaOpt.isPresent() || valorAreaOpt.get().trim().isEmpty()) {
            showAlert("Erro", "O valor da área é obrigatório.");
            return;
        }

        String areaEscolhida = valorAreaOpt.get().trim();

        // Criar o grafo de proprietários (aqui é necessário que você tenha a implementação do grafo)
        GrafoProprietarios grafo = new GrafoProprietarios(); // Isso pode ser um grafo carregado de alguma forma
        grafo.construirGrafoProprietarios(propriedades);

        // Chamar o método para sugerir trocas de propriedades
        List<TrocaPropriedades> trocas = SugestaoTrocas.sugerirTrocas(propriedades, tipoArea, areaEscolhida, grafo);

        // Exibir os resultados
        StringBuilder resultadoTexto = new StringBuilder("Sugestões de Troca:\n");
        if (trocas.isEmpty()) {
            resultadoTexto.append("Nenhuma troca sugerida.");
        } else {
            for (TrocaPropriedades troca : trocas) {
                resultadoTexto.append("Troca entre:\n")
                        .append("Propriedade 1: ").append(troca.getProp1().getObjectId()).append("\n")
                        .append("Propriedade 2: ").append(troca.getProp2().getObjectId()).append("\n")
                        .append("Melhoria Proprietário 1: ").append(troca.getMelhoriaProprietario1()).append("\n")
                        .append("Melhoria Proprietário 2: ").append(troca.getMelhoriaProprietario2()).append("\n")
                        .append("Potencialidade de troca: ").append(troca.getPotencialidadeTroca()).append("\n\n");
            }
        }

        // Mostrar as sugestões de troca
        TextArea resultadoArea = new TextArea(resultadoTexto.toString());
        resultadoArea.setEditable(false);
        StackPane newRoot = new StackPane();
        newRoot.getChildren().add(resultadoArea);

        Scene newScene = new Scene(newRoot, 600, 400);
        newStage.setScene(newScene);
        newStage.show();
    }



    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();


    }
}
