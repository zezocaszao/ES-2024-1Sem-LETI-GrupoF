package iscteiul.ista.es20241semletigrupof;

public class TrocaPropriedades {
    private DadosPropriedades prop1;
    private DadosPropriedades prop2;
    private double melhoriaProprietario1;
    private double melhoriaProprietario2;
    private double potencialidadeTroca;

    // Construtor
    public TrocaPropriedades(DadosPropriedades prop1, DadosPropriedades prop2, double melhoriaProprietario1, double melhoriaProprietario2, double potencialidadeTroca) {
        this.prop1 = prop1;
        this.prop2 = prop2;
        this.melhoriaProprietario1 = melhoriaProprietario1;
        this.melhoriaProprietario2 = melhoriaProprietario2;
        this.potencialidadeTroca = potencialidadeTroca;
    }

    // Getters
    public DadosPropriedades getProp1() {
        return prop1;
    }

    public DadosPropriedades getProp2() {
        return prop2;
    }

    public double getMelhoriaProprietario1() {
        return melhoriaProprietario1;
    }

    public double getMelhoriaProprietario2() {
        return melhoriaProprietario2;
    }

    public double getPotencialidadeTroca() {
        return potencialidadeTroca;
    }

    // Sobrescrever toString para depuração (opcional)
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
