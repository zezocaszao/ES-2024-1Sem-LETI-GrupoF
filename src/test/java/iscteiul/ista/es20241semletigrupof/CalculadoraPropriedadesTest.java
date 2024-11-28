package iscteiul.ista.es20241semletigrupof;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraPropriedadesTest {

    @Test
    void testObterAreasDisponiveis() {
        // Cria uma lista de propriedades para teste
        List<DadosPropriedades> propriedades = Arrays.asList(
                new DadosPropriedades(1, "MULTIPOLYGON(((0 0, 0 10, 10 10, 10 0, 0 0)))"),
                new DadosPropriedades(2, "MULTIPOLYGON(((0 0, 0 10, 10 10, 10 0, 0 0)))")
        );
        propriedades.get(1).setMunicipio("Area");

        propriedades.get(1).setMunicipio("Value");
    }
}
