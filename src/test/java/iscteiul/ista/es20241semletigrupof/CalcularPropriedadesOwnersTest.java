package iscteiul.ista.es20241semletigrupof;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalcularPropriedadesOwnersTest {

    private List<DadosPropriedades> criarPropriedadesTeste() {
        return Arrays.asList(
                new DadosPropriedades(1, "7343148.0", "2,99624E+12",
                        57.2469341921808,
                        202.05981432070362,
                        "MULTIPOLYGON (((299218.5203999998 3623637.4791, 299218.5033999998 3623637.4715," +
                                " 299218.04000000004 3623638.4800000004, 299232.7400000002 3623644.6799999997, " +
                                "299236.6233999999 3623637.1974, 299236.93709999975 3623636.7885999996, " +
                                "299238.04000000004 3623633.4800000004, 299222.63999999966 3623627.1799999997, " +
                                "299218.5203999998 3623637.4791)))\n",
                        "93", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)"));
                new DadosPropriedades(2, "Freguesia B", "Município X", "Ilha 1", "Dono 2", 200.0, "MULTIPOLYGON(((0 0, 3 3, 4 4, 0 0)))"),
                new DadosPropriedades(3, "Freguesia A", "Município Y", "Ilha 2", "Dono 1", 150.0, "MULTIPOLYGON(((1 1, 2 2, 3 3, 1 1)))"),
                new DadosPropriedades(4, "Freguesia C", "Município Y", "Ilha 2", "Dono 3", 300.0, "MULTIPOLYGON(((2 2, 3 3, 4 4, 2 2)))")
        );
    }

    @Test
    public void testObterAreasDisponiveis() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        List<String> freguesias = CalcularPropriedadesOwners.obterAreasDisponiveis(propriedades, "freguesia");
        assertEquals(Arrays.asList("Freguesia A", "Freguesia B", "Freguesia C"), freguesias);

        List<String> municipios = CalcularPropriedadesOwners.obterAreasDisponiveis(propriedades, "municipio");
        assertEquals(Arrays.asList("Município X", "Município Y"), municipios);

        List<String> ilhas = CalcularPropriedadesOwners.obterAreasDisponiveis(propriedades, "ilha");
        assertEquals(Arrays.asList("Ilha 1", "Ilha 2"), ilhas);
    }

    @Test
    public void testObterDonosPorArea() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        List<String> donosFreguesiaA = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, "freguesia", "Freguesia A");
        assertEquals(Arrays.asList("Dono 1"), donosFreguesiaA);

        List<String> donosMunicipioX = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, "municipio", "Município X");
        assertEquals(Arrays.asList("Dono 1", "Dono 2"), donosMunicipioX);

        List<String> donosIlha2 = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, "ilha", "Ilha 2");
        assertEquals(Arrays.asList("Dono 1", "Dono 3"), donosIlha2);
    }

    @Test
    public void testCalcularAreaMediaPorDono() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        double areaMediaDono1FreguesiaA = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, "freguesia", "Freguesia A", "Dono 1");
        assertEquals(100.0, areaMediaDono1FreguesiaA);

        double areaMediaDono1Ilha2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, "ilha", "Ilha 2", "Dono 1");
        assertEquals(150.0, areaMediaDono1Ilha2);

        double areaMediaDono2MunicipioX = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, "municipio", "Município X", "Dono 2");
        assertEquals(200.0, areaMediaDono2MunicipioX);

        double areaMediaDono3Ilha2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, "ilha", "Ilha 2", "Dono 3");
        assertEquals(300.0, areaMediaDono3Ilha2);
    }

    @Test
    public void testTipoAreaInvalido() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        assertThrows(IllegalArgumentException.class, () ->
                CalcularPropriedadesOwners.obterAreasDisponiveis(propriedades, "invalido")
        );
    }
}
