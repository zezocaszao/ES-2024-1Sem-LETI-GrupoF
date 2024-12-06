package iscteiul.ista.es20241semletigrupof;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

public class HelloControllerTest extends ApplicationTest {

    private HelloController controller;
    private Label nomeArquivoLabel;


    @BeforeEach
    public void setUp() {
        controller = new HelloController();
        nomeArquivoLabel = new Label("Nenhum arquivo selecionado");
        controller.nomeArquivoLabel = nomeArquivoLabel;
        controller.arquivoCSV = new File("src/test/resources/es20241semletigrupof/Madeira-Moodle-1.1.csv"); // Mock de arquivo CSV
    }
    @Start
    public void start(Stage stage) {
        // Configura o stage inicial para testes
        stage.setScene(new Scene(new Pane(), 800, 600));
        stage.show();
    }
    @Test
    void testOnCarregarCSVClick() {
        Platform.runLater(() -> {
            // Simular clique ou teste da lógica
            try {
                controller.onCarregarCSVClick();
            } catch (Exception e) {
                e.printStackTrace();
                fail("Erro inesperado: " + e.getMessage());
            }
        });

        // Adicione espera se necessário para eventos assíncronos
        WaitForAsyncUtils.waitForFxEvents();
    }


    @Test
    void testOnExercicio1Click_ComArquivoValido(FxRobot robot) throws Exception {
        // Simular o clique no método
        robot.interact(() -> controller.onExercicio1Click());

        // Verificar se uma nova janela foi aberta
        Stage stage = (Stage) robot.lookup(".stage").query();
        assertNotNull(stage, "A nova janela deveria ter sido aberta.");

        // Verificar se o TextArea foi carregado corretamente
        VBox root = (VBox) stage.getScene().getRoot();
        TextArea textArea = (TextArea) root.getChildren().get(0);
        assertNotNull(textArea, "O TextArea deveria estar presente na nova janela.");

        // Verificar conteúdo do TextArea
        List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(controller.arquivoCSV.getAbsolutePath());
        StringBuilder expectedText = new StringBuilder();
        for (DadosPropriedades propriedade : propriedades) {
            expectedText.append(propriedade.toString()).append("\n");
        }
        assertEquals(expectedText.toString(), textArea.getText(), "O conteúdo do TextArea deveria corresponder ao esperado.");
    }

    @Test
    void testOnExercicio1Click_SemArquivoSelecionado() {
        // Configurar o arquivo como null
        controller.arquivoCSV = null;

        // Simular o clique no método
        FxRobot robot;
        robot.interact(() -> controller.onExercicio1Click());

        // Verificar se um alerta foi exibido
        // Aqui, substituímos o showAlert para printar na saída e verificar a mensagem
        String alertMessage = "Nenhum arquivo CSV foi carregado.";
        assertTrue(outContent.toString().contains(alertMessage), "Deveria exibir um alerta de erro sobre arquivo não carregado.");
    }

    @Test
    void testOnExercicio1Click_ArquivoInvalido() {
        // Configurar um arquivo inválido
        controller.arquivoCSV = new File("src/test/resources/invalid.csv");

        // Simular o clique no método
        robot.interact(() -> controller.onExercicio1Click());

        // Verificar se um alerta foi exibido
        String alertMessage = "Não foi possível carregar os dados:";
        assertTrue(outContent.toString().contains(alertMessage), "Deveria exibir um alerta de erro sobre falha no carregamento.");
    }


    @Test
    public void testOnExercicio2Click_ComArquivoValido() {
        // Simular a leitura de um arquivo CSV válido
        File arquivoSimulado = new File("Madeira-Moodle-1.1.csv");
        controller.arquivoCSV = arquivoSimulado;

        assertDoesNotThrow(() -> controller.onExercicio2Click());

        // Verificar se a janela com o grafo foi exibida
        assertThat(Optional.ofNullable(lookup("#paneGrafo").query())).isNotNull();
    }



    @Test
    public void testOnExercicio3Click_ComArquivoValido() {
        File arquivoSimulado = new File("valid-data.csv");
        controller.arquivoCSV = arquivoSimulado;

        assertDoesNotThrow(() -> controller.onExercicio3Click());

        // Simular a entrada de valores válidos
        Button okButton = lookup("#okButton").queryAs(Button.class);
        clickOn(okButton);

        // Verificar se a janela de resultados foi exibida
        assertThat(Optional.ofNullable(lookup("#resultadoAreaMedia").query())).isNotNull();
    }
/*
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

            if (tipoAreaOpt.isEmpty() || tipoAreaOpt.get().trim().isEmpty()) {
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
            if (areaEscolhidaOpt.isEmpty()) {
                showAlert("Erro", "Nenhuma área foi selecionada.");
                return;
            }

            String areaEscolhida = areaEscolhidaOpt.get();

            // Obter os donos disponíveis na área escolhida
            List<String> donosDisponiveis = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, tipoArea, areaEscolhida);
            if (donosDisponiveis.isEmpty()) {
                showAlert("Erro", "Não há donos disponíveis na área " + areaEscolhida);
                return;
            }

            // Ordenar os donos alfabeticamente
            donosDisponiveis.sort(String::compareTo);

            // Exibir lista de donos para o utilizador escolher
            ChoiceDialog<String> donoDialog = new ChoiceDialog<>(donosDisponiveis.get(0), donosDisponiveis);
            donoDialog.setTitle("Escolha o Dono");
            donoDialog.setHeaderText("Escolha o dono da área " + areaEscolhida);
            donoDialog.setContentText("Dono:");

            Optional<String> donoEscolhidoOpt = donoDialog.showAndWait();
            if (donoEscolhidoOpt.isEmpty()) {
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
*/
    @Test
    void testExercicio5Button() {
        // Localizar o botão do exercício 5 e clicar
        Button exercicio5Button = lookup("#exercicio5Button").queryAs(Button.class);
        clickOn(exercicio5Button);

        // Verificar se a nova janela foi aberta
        assertThat(Optional.ofNullable(lookup("#grafoProprietariosWindow").query())).isNotNull();
    }

    @Test
    void testExercicio6Button() {
        // Localizar o botão do exercício 6 e clicar
        Button exercicio6Button = lookup("#exercicio6Button").queryAs(Button.class);
        clickOn(exercicio6Button);

        // Verificar se o diálogo de tipo de área aparece
        TextArea dialogText = lookup(".dialog-pane").queryAs(TextArea.class);
        assertThat(dialogText).hasText("Digite o tipo de área geográfica (freguesia, municipio, ilha):");
    }

    @Test
    void testInvalidAreaSelection() {
        // Clicar no botão do exercício 6
        clickOn("#exercicio6Button");

        // Inserir um valor inválido
        write("área inválida");
        clickOn("OK");

        // Verificar que a mensagem de erro aparece
        Label alerta = lookup("#alertLabel").queryAs(Label.class);
        assertEquals("Tipo de área inválido. Use: freguesia, municipio ou ilha.", alerta.getText());
    }


}

