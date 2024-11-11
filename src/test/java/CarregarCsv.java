
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.CsvBindByName;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CarregarCsv {

    public static List<DadosPropriedades> carregarPropriedades(String caminhoArquivo) throws IOException {
        // Lê o arquivo CSV e mapeia para objetos da classe DadosPropriedades
        FileReader fileReader = new FileReader(caminhoArquivo);

        // Usando CsvToBeanBuilder para mapear os dados do CSV para a classe DadosPropriedades
        List<DadosPropriedades> propriedades = new CsvToBeanBuilder<DadosPropriedades>(fileReader)
                .withType(DadosPropriedades.class)  // Especifica a classe de destino
                .withSeparator(';')  // Define o separador de colunas como ponto e vírgula
                .build()
                .parse();

        fileReader.close();  // Fecha o arquivo após a leitura

        return propriedades;  // Retorna a lista de propriedades carregadas
    }

    public static void main(String[] args) {
        try {
            // Caminho do arquivo CSV (substitua pelo caminho real do seu arquivo)
            String caminhoArquivo = "Madeira-Moodle.csv";  // Exemplo: "caminho/para/o/arquivo/Madeira-Moodle.csv"

            // Carregar as propriedades do arquivo CSV
            List<DadosPropriedades> propriedades = carregarPropriedades(caminhoArquivo);

            // Exemplo de como imprimir os dados carregados
            for (DadosPropriedades propriedade : propriedades) {
                System.out.println("OBJECTID: " + propriedade.getObjectId());
                System.out.println("PAR_ID: " + propriedade.getParId());
                System.out.println("PAR_NUM: " + propriedade.getParNum());
                System.out.println("Shape_Length: " + propriedade.getShapeLength());
                System.out.println("Shape_Area: " + propriedade.getShapeArea());
                System.out.println("Geometry: " + propriedade.getGeometry());
                System.out.println("Owner: " + propriedade.getOwner());
                System.out.println("----------");  // Separador para facilitar a leitura
            }
        } catch (IOException e) {
            e.printStackTrace();  // Em caso de erro, imprime a exceção
        }
    }
}

