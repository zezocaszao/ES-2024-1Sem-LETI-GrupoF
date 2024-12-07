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
/**
Relaciona todas as funcionalidades com a respetiva interface gráfica
 */
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

/**Implementação do carregamento do ficheiro associado à interface gráfica
*
 */
    public void onCarregarCSVClick() {



        FileChooser fileChooser = new FileChooser();


        FileChooser.ExtensionFilter filtroCSV = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        fileChooser.getExtensionFilters().add(filtroCSV);


        Stage stage = new Stage();
        File arquivoSelecionado = fileChooser.showOpenDialog(stage);


        if (arquivoSelecionado != null) {

            arquivoCSV = arquivoSelecionado;


            nomeArquivoLabel.setText(arquivoSelecionado.getName());
        } else {

            nomeArquivoLabel.setText("Nenhum arquivo selecionado");
        }
    }
    /**Implementação do ponto face à interface gráfica
     *
     */
    @FXML
    protected void onExercicio1Click() {
        try {

            if (arquivoCSV == null) {
                showAlert("Erro", "Nenhum arquivo CSV foi carregado.");
                return;
            }


            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());


            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setPrefHeight(600);
            textArea.setPrefWidth(800);


            StringBuilder stringBuilder = new StringBuilder();
            for (DadosPropriedades propriedade : propriedades) {
                stringBuilder.append(propriedade.toString()).append("\n");
            }
            textArea.setText(stringBuilder.toString());


            Stage novaJanela = new Stage();
            novaJanela.setTitle("Exercício 1 - Dados Carregados");


            VBox root = new VBox(textArea);
            root.setPadding(new Insets(10));
            Scene scene = new Scene(root, 850, 650);
            novaJanela.setScene(scene);


            novaJanela.show();

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível carregar os dados: " + e.getMessage());
        }
    }

    /**Implementação gráfica do grafo que tem as propriedades como nós
     * e as adjacências como arestas
     */
    @FXML
    protected void onExercicio2Click() {
        try {

            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());



            Grafo grafo = new Grafo();
            grafo.construirGrafo(propriedades);


            Pane pane = new Pane();
            Map<Integer, Circle> nos = new HashMap<>();

            // Ajustar a posição dos nós
            double xInicio = 100;
            double yInicio = 100;
            double distanciaHorizontal = 200;
            double distanciaVertical = 200;


            int linhaAzul = 0;

            double maxX = 0;
            double maxY = 0;


            for (Map.Entry<Integer, Set<Integer>> entry : grafo.getAdjacencias().entrySet()) {
                Integer idPropriedade = entry.getKey();
                Set<Integer> vizinhos = entry.getValue();


                double xPos = xInicio;
                double yPos = yInicio + linhaAzul * distanciaVertical;


                Circle circulo = new Circle(20);
                circulo.setFill(Color.BLUE);
                circulo.setStroke(Color.BLACK);
                circulo.setCenterX(xPos);
                circulo.setCenterY(yPos);


                Text texto = new Text(String.valueOf(idPropriedade));
                texto.setX(circulo.getCenterX() - 10);
                texto.setY(circulo.getCenterY() + 5);


                pane.getChildren().addAll(circulo, texto);
                nos.put(idPropriedade, circulo);


                int offset = 1;
                for (Integer vizinhoId : vizinhos) {

                    Circle circuloVizinho = new Circle(20);
                    circuloVizinho.setFill(Color.GREEN);
                    circuloVizinho.setStroke(Color.BLACK);


                    circuloVizinho.setCenterX(xPos + offset * distanciaHorizontal);
                    circuloVizinho.setCenterY(yPos + distanciaVertical);


                    Text textoVizinho = new Text(String.valueOf(vizinhoId));
                    textoVizinho.setX(circuloVizinho.getCenterX() - 10);
                    textoVizinho.setY(circuloVizinho.getCenterY() + 5);


                    pane.getChildren().addAll(circuloVizinho, textoVizinho);
                    nos.put(vizinhoId, circuloVizinho);


                    Line linha = new Line();
                    linha.setStartX(circulo.getCenterX());
                    linha.setStartY(circulo.getCenterY());
                    linha.setEndX(circuloVizinho.getCenterX());
                    linha.setEndY(circuloVizinho.getCenterY());
                    linha.setStroke(Color.BLACK);


                    pane.getChildren().add(linha);

                    offset++;
                }

                maxX = Math.max(maxX, xPos + distanciaHorizontal * offset);
                maxY = Math.max(maxY, yPos + distanciaVertical);
                linhaAzul++;
            }

            pane.setMinSize(maxX + 100, maxY + 100);
            pane.setPrefSize(maxX + 100, maxY + 100);


            ScrollPane scrollPane = new ScrollPane(pane);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


            Stage novaJanela = new Stage();
            novaJanela.setTitle("Exibição do GrafoProprietarios");
            Scene novaCena = new Scene(scrollPane, 800, 600);
            novaJanela.setScene(novaCena);


            novaJanela.show();

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível carregar os dados ou desenhar o grafo: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**Implementação gráfica do ponto 3
    *
     */

    @FXML
    protected void onExercicio3Click() {
        try {

            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());


            List<String> tiposArea = List.of("freguesia", "municipio", "ilha");
            ChoiceDialog<String> tipoDialog = new ChoiceDialog<>(tiposArea.get(0), tiposArea);
            tipoDialog.setTitle("Seleção de Tipo de Área");
            tipoDialog.setHeaderText("Escolha o tipo de área geográfica:");
            tipoDialog.setContentText("Tipo de área:");

            Optional<String> tipoAreaOpt = tipoDialog.showAndWait();
            if (!tipoAreaOpt.isPresent()) {
                showAlert("Erro", "O tipo de área geográfica é obrigatório.");
                return;
            }

            String tipoArea = tipoAreaOpt.get().trim().toLowerCase();
            List<String> areasDisponiveis = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, tipoArea);
            if (areasDisponiveis.isEmpty()) {
                showAlert("Erro", "Não há áreas disponíveis para o tipo especificado: " + tipoArea);
                return;
            }


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


            double areaMedia = CalculadoraPropriedades.calcularAreaMedia(propriedades, tipoArea, areaEscolhida);


            if (areaMedia == -1) {
                showAlert("Resultado", "Nenhuma propriedade encontrada para a área especificada: " + areaEscolhida + " (" + tipoArea + ").");
            } else {
                showAlert("Resultado", String.format("A área média das propriedades em %s (%s) é: %.2f", areaEscolhida, tipoArea, areaMedia));
            }

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível calcular a área média: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**Implementação gráfica do ponto 4
     *
     */
    @FXML
    protected void onExercicio4Click() {
        try {

            if (arquivoCSV == null) {
                showAlert("Erro", "Nenhum arquivo CSV foi carregado.");
                return;
            }

            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());


            List<String> tiposArea = List.of("freguesia", "municipio", "ilha");
            ChoiceDialog<String> tipoDialog = new ChoiceDialog<>(tiposArea.get(0), tiposArea);
            tipoDialog.setTitle("Seleção de Tipo de Área");
            tipoDialog.setHeaderText("Escolha o tipo de área geográfica:");
            tipoDialog.setContentText("Tipo de área:");

            Optional<String> tipoAreaOpt = tipoDialog.showAndWait();
            if (!tipoAreaOpt.isPresent()) {
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


            List<String> donosDisponiveis = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, tipoArea, areaEscolhida);
            if (donosDisponiveis.isEmpty()) {
                showAlert("Erro", "Não há donos disponíveis na área " + areaEscolhida);
                return;
            }


            donosDisponiveis = donosDisponiveis.stream()
                    .sorted()
                    .collect(Collectors.toList());


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


            double areaMedia = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, tipoArea, areaEscolhida, donoEscolhido);


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


    /** Implementação gráfica do grafo pedido no ponto 5 que
     * devolve um grafo onde os nós são os proprietários e as arestas
     * são a relação de vizinhança com outros proprietários
     */

    @FXML
    protected void onExercicio5Click() {
        try {

            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());


            GrafoProprietarios grafo = new GrafoProprietarios();
            grafo.construirGrafoProprietarios(propriedades);


            Pane pane = new Pane();
            Map<String, Circle> nos = new HashMap<>();


            double xInicio = 100;
            double yInicio = 100;
            double distanciaHorizontal = 200;
            double distanciaVertical = 200;


            int linhaAzul = 0;

            double maxX = 0;
            double maxY = 0;


            for (Map.Entry<String, Set<String>> entry : grafo.getAdjacencias().entrySet()) {
                String dono = entry.getKey();
                Set<String> vizinhos = entry.getValue();


                double xPos = xInicio;
                double yPos = yInicio + linhaAzul * distanciaVertical;


                Circle circulo = new Circle(20);
                circulo.setFill(Color.BLUE);
                circulo.setStroke(Color.BLACK);
                circulo.setCenterX(xPos);
                circulo.setCenterY(yPos);


                Text texto = new Text(dono);
                texto.setX(circulo.getCenterX() - 10);
                texto.setY(circulo.getCenterY() + 5);


                pane.getChildren().addAll(circulo, texto);
                nos.put(dono, circulo);


                int offset = 1;
                for (String vizinho : vizinhos) {

                    Circle circuloVizinho = new Circle(20);
                    circuloVizinho.setFill(Color.GREEN);
                    circuloVizinho.setStroke(Color.BLACK);


                    circuloVizinho.setCenterX(xPos + offset * distanciaHorizontal);
                    circuloVizinho.setCenterY(yPos + distanciaVertical);


                    Text textoVizinho = new Text(vizinho);
                    textoVizinho.setX(circuloVizinho.getCenterX() - 10);
                    textoVizinho.setY(circuloVizinho.getCenterY() + 5);


                    pane.getChildren().addAll(circuloVizinho, textoVizinho);
                    nos.put(vizinho, circuloVizinho);


                    Line linha = new Line();
                    linha.setStartX(circulo.getCenterX());
                    linha.setStartY(circulo.getCenterY());
                    linha.setEndX(circuloVizinho.getCenterX());
                    linha.setEndY(circuloVizinho.getCenterY());
                    linha.setStroke(Color.BLACK);


                    pane.getChildren().add(linha);

                    offset++;
                }


                maxX = Math.max(maxX, xPos + distanciaHorizontal * offset);
                maxY = Math.max(maxY, yPos + distanciaVertical);
                linhaAzul = linhaAzul+2;
            }


            pane.setMinSize(maxX + 100, maxY + 100);
            pane.setPrefSize(maxX + 100, maxY + 100);


            ScrollPane scrollPane = new ScrollPane(pane);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


            Stage novaJanela = new Stage();
            novaJanela.setTitle("Exibição do Grafo de Proprietários");
            Scene novaCena = new Scene(scrollPane, 800, 600);
            novaJanela.setScene(novaCena);


            novaJanela.show();

        } catch (Exception e) {
            showAlert("Erro", "Não foi possível carregar os dados ou desenhar o grafo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /** Implementação gráfica do ponto 6 que consiste em sugestoes de troca
    * entre propriedades
     */

    @FXML
    public void onExercicio6Click(ActionEvent event) throws Exception {
        Stage newStage = new Stage();
        newStage.setTitle("Sugestão de Trocas");


        List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(arquivoCSV.getAbsolutePath());


        List<String> tiposArea = List.of("freguesia", "municipio", "ilha");
        ChoiceDialog<String> tipoDialog = new ChoiceDialog<>(tiposArea.get(0), tiposArea);
        tipoDialog.setTitle("Seleção de Tipo de Área");
        tipoDialog.setHeaderText("Selecione o tipo de área geográfica:");
        tipoDialog.setContentText("Tipo de área:");
        Optional<String> tipoAreaOpt = tipoDialog.showAndWait();

        if (!tipoAreaOpt.isPresent()) {
            showAlert("Erro", "O tipo de área geográfica é obrigatório.");
            return;
        }

        String tipoArea = tipoAreaOpt.get().trim().toLowerCase();


        List<String> areasDisponiveis = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, tipoArea);
        if (areasDisponiveis.isEmpty()) {
            showAlert("Erro", "Não há áreas disponíveis para o tipo especificado: " + tipoArea);
            return;
        }


        ChoiceDialog<String> valorDialog = new ChoiceDialog<>(areasDisponiveis.get(0), areasDisponiveis);
        valorDialog.setTitle("Seleção de Valor da Área");
        valorDialog.setHeaderText("Selecione o valor da área geográfica:");
        valorDialog.setContentText("Valor da área:");
        Optional<String> valorAreaOpt = valorDialog.showAndWait();

        if (!valorAreaOpt.isPresent()) {
            showAlert("Erro", "O valor da área é obrigatório.");
            return;
        }

        String areaEscolhida = valorAreaOpt.get().trim();


        GrafoProprietarios grafo = new GrafoProprietarios();
        grafo.construirGrafoProprietarios(propriedades);


        List<TrocaPropriedades> trocas = SugestaoTrocas.sugerirTrocas(propriedades, tipoArea, areaEscolhida, grafo);


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
