package iscteiul.ista.es20241semletigrupof;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SugestaoTrocasTest {

    @Test
    void testSugerirTrocas() {
        // Criação de dados fictícios de propriedades
        List<DadosPropriedades> propriedades = new ArrayList<>();
        propriedades.add(new DadosPropriedades(1, "7343148.0", "2,99624E+12", 57.2469341921808,
                202.05981432070362,
                "MULTIPOLYGON (((299218.5203999998 3623637.4791, 299218.5033999998 3623637.4715, 299218.04000000004 3623638.4800000004, 299232.7400000002 3623644.6799999997, 299236.6233999999 3623637.1974, 299236.93709999975 3623636.7885999996, 299238.04000000004 3623633.4800000004, 299222.63999999966 3623627.1799999997, 299218.5203999998 3623637.4791)))\n",
                "93", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)"));

        propriedades.add(new DadosPropriedades(415,	"7345334.0","2,98622E+12", 61.742814667878356,	202.83229446573148,
                "MULTIPOLYGON (((298196.8984000003 3622886.5907000005, 298200.2159000002 3622896.3256, 298220.7023 3622892.431500001, 298218.26049999986 3622883.718800001, 298218.2572999997 3622883.7190000005, 298218.12370000035 3622883.7288000006, 298218.0981999999 3622883.7305999994, 298218.0987 3622883.7322000004, 298196.8984000003 3622886.5907000005)))",
                "49",	"Arco da Calheta",	"Calheta",	"Ilha da Madeira (Madeira)"));
        propriedades.add(new DadosPropriedades(3, "9315764.0", "2,98622E+12", 115.0, 419.0, "geom3", "A", "Freg1", "Mun1", "Ilha1"));

        // Criação do grafo de proprietários
        GrafoProprietarios grafo = new GrafoProprietarios();
        grafo.adicionarVizinho("93", "49");

        // Execução do método a ser testado
        List<TrocaPropriedades> trocas = SugestaoTrocas.sugerirTrocas(propriedades, "freguesia",
                "Arco da Calheta", grafo);

        // Validação do resultado
        assertFalse(trocas.isEmpty(), "Deve haver pelo menos uma troca sugerida.");
        assertEquals(2, trocas.size(), "Deve haver exatamente uma troca sugerida.");

        // Validar os detalhes da troca sugerida
        TrocaPropriedades troca = trocas.get(0);
        assertEquals("49", troca.getProp1().getOwner(), "O proprietário 1 da troca deve ser 'A'.");
        assertEquals("93", troca.getProp2().getOwner(), "O proprietário 2 da troca deve ser 'B'.");
        assertTrue(troca.getPotencialidadeTroca() < 10, "A potencialidade da troca deve ser menor que 10.");
    }
}
