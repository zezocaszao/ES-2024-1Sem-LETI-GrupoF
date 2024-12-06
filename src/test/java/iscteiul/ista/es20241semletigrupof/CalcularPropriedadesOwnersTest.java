package iscteiul.ista.es20241semletigrupof;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalcularPropriedadesOwnersTest {

    private List<DadosPropriedades> criarPropriedadesTeste() {
        return Arrays.asList(
                new DadosPropriedades(1, "123456", "7343148.0", 100.0, 200.0,
                        "MULTIPOLYGON (((...)))", "Dono 1", "Freguesia A", "Município X", "Ilha 1"),
                new DadosPropriedades(2, "123457", "7343149.0", 150.0, 250.0,
                        "MULTIPOLYGON (((...)))", "Dono 2", "Freguesia B", "Município X", "Ilha 1"),
                new DadosPropriedades(3, "123458", "7343150.0", 200.0, 300.0,
                        "MULTIPOLYGON (((...)))", "Dono 1", "Freguesia A", "Município Y", "Ilha 2"),
                new DadosPropriedades(4, "123459", "7343151.0", 300.0, 400.0,
                        "MULTIPOLYGON (((...)))", "Dono 3", "Freguesia C", "Município Y", "Ilha 2")
        );
    }

    @Test
    public void testObterAreasDisponiveis() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        // Testa áreas disponíveis por "freguesia"
        List<String> freguesias = CalcularPropriedadesOwners.obterAreasDisponiveis(propriedades, "freguesia");
        assertEquals(Arrays.asList("Freguesia A", "Freguesia B", "Freguesia C"), freguesias);

        // Testa áreas disponíveis por "municipio"
        List<String> municipios = CalcularPropriedadesOwners.obterAreasDisponiveis(propriedades, "municipio");
        assertEquals(Arrays.asList("Município X", "Município Y"), municipios);

        // Testa áreas disponíveis por "ilha"
        List<String> ilhas = CalcularPropriedadesOwners.obterAreasDisponiveis(propriedades, "ilha");
        assertEquals(Arrays.asList("Ilha 1", "Ilha 2"), ilhas);
    }

    @Test
    public void testObterDonosPorArea() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        // Testa donos na "Freguesia A"
        List<String> donosFreguesiaA = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, "freguesia", "Freguesia A");
        assertEquals(Arrays.asList("Dono 1"), donosFreguesiaA);

        // Testa donos no "Município X"
        List<String> donosMunicipioX = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, "municipio", "Município X");
        assertEquals(Arrays.asList("Dono 1", "Dono 2"), donosMunicipioX);

        // Testa donos na "Ilha 2"
        List<String> donosIlha2 = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, "ilha", "Ilha 2");
        assertEquals(Arrays.asList("Dono 1", "Dono 3"), donosIlha2);
    }

    @Test
    public void testCalcularAreaMediaPorDono() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        // Testa cálculo da área média do "Dono 1" na "Freguesia A"
        double areaMediaDono1FreguesiaA = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, "freguesia", "Freguesia A", "Dono 1");
        assertEquals(100.0, areaMediaDono1FreguesiaA);

        // Testa cálculo da área média do "Dono 1" na "Ilha 2"
        double areaMediaDono1Ilha2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, "ilha", "Ilha 2", "Dono 1");
        assertEquals(200.0, areaMediaDono1Ilha2);

        // Testa cálculo da área média do "Dono 2" no "Município X"
        double areaMediaDono2MunicipioX = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, "municipio", "Município X", "Dono 2");
        assertEquals(150.0, areaMediaDono2MunicipioX);

        // Testa cálculo da área média do "Dono 3" na "Ilha 2"
        double areaMediaDono3Ilha2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, "ilha", "Ilha 2", "Dono 3");
        assertEquals(300.0, areaMediaDono3Ilha2);
    }

    @Test
    public void testTipoAreaInvalido() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        // Testa exceção para tipo de área inválido
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                CalcularPropriedadesOwners.obterAreasDisponiveis(propriedades, "tipo_invalido")
        );

        // Valida mensagem de erro
        assertEquals("Tipo de área inválido: tipo_invalido", exception.getMessage());
    }
}
