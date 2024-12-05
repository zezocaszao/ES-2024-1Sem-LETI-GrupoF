package iscteiul.ista.es20241semletigrupof;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarregarCsvTest {

    @Test
    void testCarregarPropriedadesArquivoValido() throws Exception {
        // Caminho do arquivo CSV para o teste (certifique-se de que o arquivo existe)
        String caminhoArquivo = "src/test/resources/es20241semletigrupof/Madeira-Moodle-1.1.csv";

        // Executa o método
        List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoArquivo);

        // Verifica se o retorno não é nulo
        assertNotNull(propriedades, "A lista de propriedades não deve ser nula.");

        // Verifica se há elementos na lista
        assertFalse(propriedades.isEmpty(), "A lista de propriedades não deve estar vazia.");

        // Verifica se os dados foram carregados corretamente (exemplo: verifica o primeiro elemento)
        DadosPropriedades primeiraPropriedade = propriedades.get(0);
        assertNotNull(primeiraPropriedade.getFreguesia(), "O campo 'freguesia' da primeira propriedade não deve ser nulo.");
    }

    @Test
    void testCarregarPropriedadesArquivoInvalido() {
        String caminhoInvalido = "caminho/invalido.csv";

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            CarregarCsv.carregarPropriedades(caminhoInvalido);
        });

        String mensagemEsperada = "Erro ao carregar o arquivo CSV";
        Assertions.assertTrue(exception.getMessage().contains(mensagemEsperada),
                "A mensagem de erro deve indicar que o arquivo não foi encontrado.");
    }


    @Test
    void testCarregarPropriedadesArquivoVazio() throws Exception {
        // Cria temporariamente um arquivo vazio para o teste
        Path caminhoArquivoVazio = Files.createTempFile("propriedades_vazio", ".csv");

        try {
            // Executa o método
            List<DadosPropriedades> propriedades = CarregarCsv.carregarPropriedades(caminhoArquivoVazio.toString());

            // Verifica se a lista está vazia
            assertNotNull(propriedades, "A lista de propriedades não deve ser nula.");
            assertTrue(propriedades.isEmpty(), "A lista de propriedades deve estar vazia.");
        } finally {
            // Apaga o arquivo temporário
            Files.delete(caminhoArquivoVazio);
        }
    }
}
