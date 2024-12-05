package iscteiul.ista.es20241semletigrupof;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraPropriedadesTest {

    private List<DadosPropriedades> propriedades;

    @BeforeEach
    void setUp() {
        propriedades = new ArrayList<>();
        propriedades.add(new DadosPropriedades(1, "7343148.0", "2,99624E+12",
                57.2469341921808,
                202.05981432070362,
                "MULTIPOLYGON (((299218.5203999998 3623637.4791, 299218.5033999998 3623637.4715," +
                        " 299218.04000000004 3623638.4800000004, 299232.7400000002 3623644.6799999997, " +
                        "299236.6233999999 3623637.1974, 299236.93709999975 3623636.7885999996, " +
                        "299238.04000000004 3623633.4800000004, 299222.63999999966 3623627.1799999997, " +
                        "299218.5203999998 3623637.4791)))\n",
                "93", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)"));
        propriedades.add(new DadosPropriedades(2, "7344660.0\n", "2,99622E+12\n",
                55.63800662596267,
                151.76387471712783,
                "MULTIPOLYGON (((298724.1991999997 3623192.6094000004, 298724.3200000003 3623192.619999999" +
                        ", 298724.26999999955 3623185.7200000007, 298723.8854 3623185.681500001, 298723.8854 3623185.6338," +
                        " 298717.2167999996 3623184.6405999996, 298716.2909000004 3623184.495100001, 298716.1699999999 " +
                        "3623184.5700000003, 298711.51999999955 3623184.17, 298709.1414000001 3623183.7961999997," +
                        " 298708.48000000045 3623183.3200000003, 298705.6799999997 3623183.2200000007, " +
                        "298704.5800000001 3623183.3200000003, 298703.98000000045 3623184.119999999, 298703.48000000045" +
                        " 3623190.7200000007, 298704.0525000002 3623190.7905, 298704.0488999998 3623190.8441000003," +
                        " 298705.574 3623190.9777000006, 298709.98000000045 3623191.5199999996," +
                        " 298710.0937999999 3623191.3737000003, 298724.1991999997 3623192.6094000004)))",
                "68\n", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)"));
    }

    @Test
    void testObterAreasDisponiveisFreguesia() {
        List<String> areas = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "freguesia");
        assertEquals(1, areas.size());
        assertTrue(areas.contains("Arco da Calheta"));
    }

    @Test
    void testObterAreasDisponiveisMunicipio() {
        List<String> areas = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "municipio");
        assertEquals(1, areas.size());
        assertTrue(areas.contains("Calheta"));
    }

    @Test
    void testObterAreasDisponiveisIlha() {
        List<String> areas = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "ilha");
        assertEquals(1, areas.size());
        assertTrue(areas.contains("Ilha da Madeira (Madeira)"));
    }

    @Test
    void testCalcularAreaMediaFreguesia() {
        double media = CalculadoraPropriedades.calcularAreaMedia(propriedades, "freguesia", "Arco da Calheta");
        assertEquals(176.91184451891573, media, 0.001);
    }

    @Test
    void testCalcularAreaMediaMunicipio() {
        double media = CalculadoraPropriedades.calcularAreaMedia(propriedades, "municipio", "Calheta");
        assertEquals(176.91184451891573, media, 0.001); // Média ponderada pelos valores adicionados
    }

    @Test
    void testCalcularAreaMediaSemResultados() {
        double media = CalculadoraPropriedades.calcularAreaMedia(propriedades, "freguesia", "Freguesia inexistente");
        assertEquals(-1, media);
    }

    @Test
    void testObterAreasDisponiveisTipoInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "tipoInvalido")
        );
        assertEquals("Tipo de área inválido. Use: freguesia, municipio ou ilha.", exception.getMessage());
    }

    @Test
    void testCalcularAreaMediaTipoInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                CalculadoraPropriedades.calcularAreaMedia(propriedades, "tipoInvalido", "Ilha da Madeira (Madeira)")
        );
        assertEquals("Tipo de área inválido. Use: freguesia, municipio ou ilha.", exception.getMessage());
    }
}
