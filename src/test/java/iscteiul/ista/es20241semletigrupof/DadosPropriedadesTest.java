package iscteiul.ista.es20241semletigrupof;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a classe {@link DadosPropriedades}.
 */
class DadosPropriedadesTest {

    /**
     * Testa o construtor sem argumentos da classe {@link DadosPropriedades}.
     */

    @Test
    void testConstrutorSemArgumentos() {
        DadosPropriedades dados = new DadosPropriedades();
        assertNotNull(dados, "O construtor sem argumentos deve criar um objeto não nulo.");
    }

    /**
     * Testa o construtor com argumentos da classe {@link DadosPropriedades}.
     */

    @Test
    void testConstrutorComArgumentos() {
        DadosPropriedades dados = new DadosPropriedades(1, "7343148.0", "2,99624E+12", 57.2469341921808,
                202.05981432070362,
                "MULTIPOLYGON (((299218.5203999998 3623637.4791, 299218.5033999998 3623637.4715, 299218.04000000004 3623638.4800000004, 299232.7400000002 3623644.6799999997, 299236.6233999999 3623637.1974, 299236.93709999975 3623636.7885999996, 299238.04000000004 3623633.4800000004, 299222.63999999966 3623627.1799999997, 299218.5203999998 3623637.4791)))\n",
                "93", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)");

        assertEquals(1, dados.getObjectId());
        assertEquals("7343148.0", dados.getParId());
        assertEquals("2,99624E+12", dados.getParNum());
        assertEquals(57.2469341921808, dados.getShapeLength());
        assertEquals(202.05981432070362, dados.getShapeArea());
        assertEquals("MULTIPOLYGON (((299218.5203999998 3623637.4791, 299218.5033999998 3623637.4715, 299218.04000000004 3623638.4800000004, 299232.7400000002 3623644.6799999997, 299236.6233999999 3623637.1974, 299236.93709999975 3623636.7885999996, 299238.04000000004 3623633.4800000004, 299222.63999999966 3623627.1799999997, 299218.5203999998 3623637.4791)))\n", dados.getGeometry());
        assertEquals("93", dados.getOwner());
        assertEquals("Arco da Calheta", dados.getFreguesia());
        assertEquals("Calheta", dados.getMunicipio());
        assertEquals("Ilha da Madeira (Madeira)", dados.getIlha());
    }

    /**
     * Testa os métodos getters e setters da classe {@link DadosPropriedades}.
     *
     * <p>Este teste verifica se os métodos getters e setters funcionam corretamente, ou seja, se os valores
     * atribuídos através dos setters podem ser recuperados corretamente pelos getters.</p>
     */

    @Test
    void testGettersAndSetters() {
        DadosPropriedades dados = new DadosPropriedades();

        dados.setObjectId(2);
        dados.setParId("PAR002");
        dados.setParNum("456");
        dados.setShapeLength(75.3);
        dados.setShapeArea(150.7);
        dados.setGeometry("POINT(...)");
        dados.setOwner("Outro Proprietário");
        dados.setFreguesia("Outra Freguesia");
        dados.setMunicipio("Outro Município");
        dados.setIlha("Outra Ilha");

        assertEquals(2, dados.getObjectId());
        assertEquals("PAR002", dados.getParId());
        assertEquals("456", dados.getParNum());
        assertEquals(75.3, dados.getShapeLength());
        assertEquals(150.7, dados.getShapeArea());
        assertEquals("POINT(...)", dados.getGeometry());
        assertEquals("Outro Proprietário", dados.getOwner());
        assertEquals("Outra Freguesia", dados.getFreguesia());
        assertEquals("Outro Município", dados.getMunicipio());
        assertEquals("Outra Ilha", dados.getIlha());
    }


    /**
     * Testa o método {@code toString()} da classe {@link DadosPropriedades}.
     *
     * <p>Este teste verifica se o método {@code toString()} gera a representação em string da classe
     * de acordo com o formato esperado.</p>
     */

    @Test
    void testToString() {
        DadosPropriedades dados = new DadosPropriedades(1, "7343148.0", "2,99624E+12", 57.2469341921808,
                202.05981432070362,
                "MULTIPOLYGON (((299218.5203999998 3623637.4791, 299218.5033999998 3623637.4715, 299218.04000000004 3623638.4800000004, 299232.7400000002 3623644.6799999997, 299236.6233999999 3623637.1974, 299236.93709999975 3623636.7885999996, 299238.04000000004 3623633.4800000004, 299222.63999999966 3623627.1799999997, 299218.5203999998 3623637.4791)))\n",
                "93", "Arco da Calheta", "Calheta", "Ilha da Madeira (Madeira)");

        String resultadoEsperado = "DadosPropriedades{" +
                "objectId=1, parId='7343148.0', parNum='2,99624E+12', shapeLength=57.2469341921808, " +
                "shapeArea=202.05981432070362, geometry='MULTIPOLYGON (((299218.5203999998 3623637.4791, 299218.5033999998 3623637.4715, 299218.04000000004 3623638.4800000004, 299232.7400000002 3623644.6799999997, 299236.6233999999 3623637.1974, 299236.93709999975 3623636.7885999996, 299238.04000000004 3623633.4800000004, 299222.63999999966 3623627.1799999997, 299218.5203999998 3623637.4791)))\n'," +
                " owner='93', " +
                "freguesia='Arco da Calheta', municipio='Calheta', ilha='Ilha da Madeira (Madeira)'}";

        assertEquals(resultadoEsperado, dados.toString());
    }
}
