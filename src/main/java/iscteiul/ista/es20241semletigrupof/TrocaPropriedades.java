package iscteiul.ista.es20241semletigrupof;

/**
 * Representa uma sugestão de troca de propriedades entre dois proprietários.
 * A troca inclui as propriedades envolvidas, a melhoria obtida por cada proprietário após a troca
 * e a potencialidade da troca, que é uma medida da diferença entre as áreas das propriedades trocadas.
 */

public class TrocaPropriedades {
    private DadosPropriedades prop1;
    private DadosPropriedades prop2;
    private double melhoriaProprietario1;
    private double melhoriaProprietario2;
    private double potencialidadeTroca;


    /**
     *Construtor de TrocaPropriedades
     *
     * @param prop1                 propriedade comparada.
     * @param prop2                 propriedade comparada.
     * @param melhoriaProprietario1 A melhoria do primeiro proprietário após a troca.
     * @param melhoriaProprietario2 A melhoria do segundo proprietário após a troca.
     * @param potencialidadeTroca   A potencialidade da troca, que mede a diferença de área entre as propriedades trocadas.
     */
    public TrocaPropriedades(DadosPropriedades prop1, DadosPropriedades prop2, double melhoriaProprietario1, double melhoriaProprietario2, double potencialidadeTroca) {
        this.prop1 = prop1;
        this.prop2 = prop2;
        this.melhoriaProprietario1 = melhoriaProprietario1;
        this.melhoriaProprietario2 = melhoriaProprietario2;
        this.potencialidadeTroca = potencialidadeTroca;
    }

    /**
     * Retorna a primeira propriedade da troca.
     *
     * @return A primeira propriedade.
     */
    public DadosPropriedades getProp1() {
        return prop1;
    }


    /**
     * Retorna a segunda propriedade da troca.
     *
     * @return A segunda propriedade.
     */

    public DadosPropriedades getProp2() {
        return prop2;
    }


    /**
     * Retorna a melhoria do primeiro proprietário após a troca.
     *
     * @return A melhoria do primeiro proprietário.
     */

    public double getMelhoriaProprietario1() {
        return melhoriaProprietario1;
    }
    /**
     * Retorna a melhoria do segundo proprietário após a troca.
     *
     * @return A melhoria do segundo proprietário.
     */

    public double getMelhoriaProprietario2() {
        return melhoriaProprietario2;
    }

    /**
     * Retorna a potencialidade da troca, que é uma medida da diferença entre as áreas das propriedades trocadas.
     *
     * @return A potencialidade da troca.
     */
    public double getPotencialidadeTroca() {
        return potencialidadeTroca;
    }

    /**
     * Retorna uma representação em string da sugestão de troca de propriedades.
     *
     * @return Uma string representando os detalhes da troca.
     */
    @Override
    public String toString() {
        return "TrocaPropriedades{" +
                "prop1=" + prop1 +
                ", prop2=" + prop2 +
                ", melhoriaProprietario1=" + melhoriaProprietario1 +
                ", melhoriaProprietario2=" + melhoriaProprietario2 +
                ", potencialidadeTroca=" + potencialidadeTroca +
                '}';
    }
}