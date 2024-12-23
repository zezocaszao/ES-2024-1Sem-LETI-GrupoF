package iscteiul.ista.es20241semletigrupof;

import javafx.geometry.BoundingBox;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class GrafoProprietariosTest {

    @Test
    void testConstruirGrafoProprietarios() {
        // Cria uma lista de propriedades com dados fictícios
        List<DadosPropriedades> propriedades = new ArrayList<>();

        propriedades.add(new DadosPropriedades(1, "7343148.0", "2,99624E+12", 57.2469341921808,
                202.05981432070362,
                "MULTIPOLYGON (((299218.5203999998 3623637.4791, 299218.5033999998 3623637.4715," +
                        " 299218.04000000004 3623638.4800000004, 299232.7400000002 3623644.6799999997, " +
                        "299236.6233999999 3623637.1974, 299236.93709999975 3623636.7885999996, " +
                        "299238.04000000004 3623633.4800000004, 299222.63999999966 3623627.1799999997, " +
                        "299218.5203999998 3623637.4791)))",
                "93", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)"));

        propriedades.add(new DadosPropriedades(199, "21861648.0", "2,99622E+12", 31.367737038514523, 60.08014000591071,
                "MULTIPOLYGON (((298824.9874 3621782.0117000006, 298826.4199999999 3621779.5, " +
                        "298825.67090000026 3621779.0276999995, 298819.51999999955 3621775.1500000004," +
                        " 298815.71999999974 3621780.0500000007, 298820.01999999955 3621783.9000000004," +
                        " 298822.5700000003 3621786.25, 298824.9874 3621782.0117000006)))", "44",
                "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)"));
        propriedades.add(new DadosPropriedades(267, "9315764.0", "2,98622E+12",
                115.6305701339722, 419.6198330076224,
                "MULTIPOLYGON (((297976.86000000034 3622701.619999999, 297976.2599999998 3622703.2200000007, " +
                        "297976.2737999996 3622703.3791000005, 297974.54420000035 3622709.8256, 297964.0895999996 3622708.1623," +
                        " 297961.68960000016 3622723.6622, 297961.8130999999 3622723.6691999994," +
                        " 297961.7599999998 3622723.7200000007, 297973.95999999996 3622724.7699999996," +
                        " 297977.5599999996 3622720.0700000003, 297977.4223999996 3622720.0021," +
                        " 297977.66640000045 3622720.0720000006, 297980.8099999996 3622716.119999999," +
                        " 297993.7873999998 3622721.2662000004, 297993.7894000001 3622721.2621999998," +
                        " 297993.8897000002 3622721.2113000005, 297993.8278999999 3622721.1822999995," +
                        " 297993.9759999998 3622720.8303999994, 297997.51970000006 3622712.4042000007," +
                        " 297984.3099999996 3622708.67, 297982.70999999996 3622711.7200000007," +
                        " 297980.66000000015 3622710.8200000003, 297982.43620000035 3622705.320699999," +
                        " 297982.37739999965 3622705.2984999996, 297982.8099999996 3622703.5199999996," +
                        " 297976.86000000034 3622701.619999999)))", "1", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)"));

        // Inicializa o grafo
        GrafoProprietarios grafo = new GrafoProprietarios();

        // Constrói o grafo a partir da lista de propriedades
        grafo.construirGrafoProprietarios(propriedades);

        // Testa as conexões
        assertFalse(grafo.saoVizinhos("93", "44"), "Proprietários 93 e 44 não deveriam ser vizinhos.");
    }
}
