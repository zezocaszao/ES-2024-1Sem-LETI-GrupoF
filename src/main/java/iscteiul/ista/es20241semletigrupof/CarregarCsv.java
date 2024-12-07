package iscteiul.ista.es20241semletigrupof;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

/** Lê um ficheiro csv dado por um utilizador
*
 */
public class CarregarCsv {
    public static List<DadosPropriedades> carregarPropriedades(String caminhoArquivo) throws Exception {
        try (Reader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            return new CsvToBeanBuilder<DadosPropriedades>(reader)
                    .withType(DadosPropriedades.class)
                    .withSeparator(';')
                    .build()
                    .parse();
        } catch (Exception e) {
            throw new Exception("Erro ao carregar o arquivo CSV: " + caminhoArquivo + ". Verifique se o arquivo existe e está acessível.", e);
        }
    }
}