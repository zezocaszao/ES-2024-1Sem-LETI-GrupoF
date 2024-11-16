import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CarregarCsv {

    public static List<DadosPropriedades> carregarPropriedades() throws IOException {
        String urlArquivo = "https://raw.githubusercontent.com/zezocaszao/ES-2024-1Sem-LETI-GrupoF/2c9d2cb2588670acaf561e2099dba05d9b6f4c72/Madeira-Moodle.csv";


        URL url = new URL(urlArquivo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));//BufferedReader le ficheiros online, como o comando File


        List<DadosPropriedades> propriedades = new CsvToBeanBuilder<DadosPropriedades>(reader) // CsvToBeanBuilder le csvs
                .withType(DadosPropriedades.class)
                .withSeparator(';')
                .build()
                .parse();
        reader.close();

        return propriedades;
    }
}



