import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CarregarCsv {

    public static List<DadosPropriedades> carregarPropriedades() throws IOException {
        // URL do arquivo bruto no GitHub
        String urlArquivo = "https://raw.githubusercontent.com/zezocaszao/ES-2024-1Sem-LETI-GrupoF/2c9d2cb2588670acaf561e2099dba05d9b6f4c72/Madeira-Moodle.csv";

        // Conecta à URL e cria um BufferedReader para ler o arquivo
        URL url = new URL(urlArquivo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        // Usa o CsvToBeanBuilder para mapear os dados do CSV para objetos DadosPropriedades
        List<DadosPropriedades> propriedades = new CsvToBeanBuilder<DadosPropriedades>(reader)
                .withType(DadosPropriedades.class)  // Define a classe de destino
                .withSeparator(';')  // Define o separador de colunas como ponto e vírgula
                .build()
                .parse();

        // Fecha o leitor após o mapeamento
        reader.close();

        return propriedades;  // Retorna a lista de propriedades carregadas
    }

public static void main(String[] args) {
    try {
        // Chama o método carregarPropriedades e armazena o resultado
        List<DadosPropriedades> propriedades = carregarPropriedades();

        // Exibe o conteúdo carregado
        for (DadosPropriedades dados : propriedades) {
            System.out.println(dados);
        }
    } catch (IOException e) {
        System.out.println("Erro ao carregar propriedades: " + e.getMessage());
    }
}
}

