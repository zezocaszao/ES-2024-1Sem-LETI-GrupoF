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
                        "93", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)"),


                new DadosPropriedades(138,	"7350213.0",	"2,97622E+12",	77.88483961099904,	235.38846544564066,
                        "MULTIPOLYGON (((297438.841 3622000.3060999997, 297445.55709999986 3621998.4744000006," +
                                " 297443.96999999974 3621985.4800000004, 297441.96999999974 3621976.58, " +
                                "297423.5700000003 3621988.380000001, 297425.3700000001 3621992.1799999997, " +
                                "297434.76999999955 3621988.4800000004, 297438.46999999974 3621998.08, " +
                                "297438.841 3622000.3060999997)))",
                        "93",	"Arco da Calheta",	"Calheta",	"Ilha da Madeira (Madeira)"),
                new DadosPropriedades(17483,	"20424655.0",	"3,0462E+12",
                        134.65136668356857,
                        1123.7614206059889,
                        "MULTIPOLYGON (((303637.1189000001 3620643.0599000007, 303638.89740000013 3620644.8649000004, " +
                                "303639.52720000036 3620644.043400001, 303639.7609000001 3620643.7386000007," +
                                " 303640.21999999974 3620644.1799999997, 303648.3200000003 3620633.58," +
                                " 303651.5599999996 3620628.3484000005, 303658.58050000016 3620617.012599999," +
                                " 303658.67169999983 3620616.8652999997, 303659.21999999974 3620615.9800000004," +
                                " 303657.9784000004 3620614.9038999993, 303657.71999999974 3620614.6799999997, " +
                                "303657.5917999996 3620614.695800001, 303632.68290000036 3620594.1666, " +
                                "303616.95529999956 3620621.8320000004, 303617.0374999996 3620621.9186000004, " +
                                "303616.6974999998 3620622.665100001, 303624.17069999967 3620629.9185000006, " +
                                "303637.1189000001 3620643.0599000007)))",
                        "497",	"Ponta do Sol",	"Ponta do Sol",	"Ilha da Madeira (Madeira)"));
               // new DadosPropriedades(2, "Freguesia B", "Município X", "Ilha 1", "Dono 2", 200.0, "MULTIPOLYGON(((0 0, 3 3, 4 4, 0 0)))");
                //new DadosPropriedades(3, "Freguesia A", "Município Y", "Ilha 2", "Dono 1", 150.0, "MULTIPOLYGON(((1 1, 2 2, 3 3, 1 1)))");
                //new DadosPropriedades(4, "Freguesia C", "Município Y", "Ilha 2", "Dono 3", 300.0, "MULTIPOLYGON(((2 2, 3 3, 4 4, 2 2)))");
    }

    @Test
    public void testObterAreasDisponiveis() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        List<String> freguesias = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "freguesia");
        assertEquals(Arrays.asList("Arco da Calheta", "Ponto do Sol"), freguesias);

        List<String> municipios = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "municipio");
        assertEquals(Arrays.asList("Calheta", "Ponta do Sol"), municipios);

        List<String> ilhas = CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "ilha");
        assertEquals(Arrays.asList("Ilha da Madeira (Madeira)"), ilhas);
    }

    @Test
    public void testObterDonosPorArea() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        List<String> donosFreguesia = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, "freguesia",
                "Arco da Calheta");
        assertEquals(Arrays.asList("93"), donosFreguesia);

        List<String> donosMunicipio = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, "municipio",
                "Calheta");
        assertEquals(Arrays.asList("93"), donosMunicipio);

        List<String> donosIlha2 = CalcularPropriedadesOwners.obterDonosPorArea(propriedades, "ilha",
                "Ilha da Madeira (Madeira)");
        assertEquals(Arrays.asList("497","93" ), donosIlha2);
    }

    @Test
    public void testCalcularAreaMediaPorDono() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        double areaMediaDono1FreguesiaA = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades,
                "freguesia", "Arco da Calheta", "93");
        assertEquals(218.72413988317214, areaMediaDono1FreguesiaA);

        double areaMediaDono1Ilha2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades,
                "ilha", "Ilha da Madeira (Madeira)", "497");
        assertEquals(1123.7614206059889, areaMediaDono1Ilha2);
    }

    @Test
    public void testTipoAreaInvalido() {
        List<DadosPropriedades> propriedades = criarPropriedadesTeste();

        assertThrows(IllegalArgumentException.class, () ->
                CalculadoraPropriedades.obterAreasDisponiveis(propriedades, "invalido")
        );
    }
}
