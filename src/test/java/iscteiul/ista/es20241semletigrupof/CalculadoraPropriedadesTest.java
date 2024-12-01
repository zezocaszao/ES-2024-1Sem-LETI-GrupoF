package iscteiul.ista.es20241semletigrupof;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraPropriedadesTest {

    @Test
    void obterAreasDisponiveis() {
        // Configuração dos dados de teste
        DadosPropriedades prop1 = new DadosPropriedades();
        prop1.setFreguesia("Freguesia A");
        prop1.setMunicipio("Municipio X");
        prop1.setIlha("Ilha Norte");

        DadosPropriedades prop2 = new DadosPropriedades();
        prop2.setFreguesia("Freguesia B");
        prop2.setMunicipio("Municipio Y");
        prop2.setIlha("Ilha Sul");

        DadosPropriedades prop3 = new DadosPropriedades();
        prop3.setFreguesia("Freguesia A"); // Duplicado
        prop3.setMunicipio("Municipio X");
        prop3.setIlha("Ilha Norte");

        List<DadosPropriedades> propriedades = Arrays.asList(prop1, prop2, prop3);

        // Testar "freguesia"
        List<String> freguesias = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "freguesia");
        assertEquals(2, freguesias.size());
        assertTrue(freguesias.contains("Freguesia A"));
        assertTrue(freguesias.contains("Freguesia B"));

        // Testar "municipio"
        List<String> municipios = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "municipio");
        assertEquals(2, municipios.size());
        assertTrue(municipios.contains("Municipio X"));
        assertTrue(municipios.contains("Municipio Y"));

        // Testar "ilha"
        List<String> ilhas = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "ilha");
        assertEquals(2, ilhas.size());
        assertTrue(ilhas.contains("Ilha Norte"));
        assertTrue(ilhas.contains("Ilha Sul"));
    }

    @Test
    void calcularAreaMedia() {
        // Configuração dos dados de teste
        DadosPropriedades prop1 = new DadosPropriedades();
        prop1.setShapeArea(100.0);
        prop1.setFreguesia("Freguesia A");

        DadosPropriedades prop2 = new DadosPropriedades();
        prop2.setShapeArea(200.0);
        prop2.setFreguesia("Freguesia A");

        DadosPropriedades prop3 = new DadosPropriedades();
        prop3.setShapeArea(150.0);
        prop3.setFreguesia("Freguesia B");

        List<DadosPropriedades> propriedades = Arrays.asList(prop1, prop2, prop3);

        // Testar cálculo da área média para "Freguesia A"
        double mediaFreguesiaA = CalculadoraPropriedades.calcularAreaMedia(propriedades, "freguesia", "Freguesia A");
        assertEquals(150.0, mediaFreguesiaA);

        // Testar cálculo da área média para "Freguesia B"
        double mediaFreguesiaB = CalculadoraPropriedades.calcularAreaMedia(propriedades, "freguesia", "Freguesia B");
        assertEquals(150.0, mediaFreguesiaB);

        // Testar cálculo da área média para uma freguesia inexistente
        double mediaFreguesiaInexistente = CalculadoraPropriedades.calcularAreaMedia(propriedades, "freguesia", "Freguesia C");
        assertEquals(-1, mediaFreguesiaInexistente);

        // Testar cálculo da área média com tipo inválido
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CalculadoraPropriedades.calcularAreaMedia(propriedades, "tipoInvalido", "Freguesia A");
        });
        assertEquals("Tipo de área inválido. Use: freguesia, municipio ou ilha.", exception.getMessage());
    }
}
