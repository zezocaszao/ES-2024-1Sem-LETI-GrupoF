package iscteiul.ista.es20241semletigrupof;

import java.util.*;

public class SugestaoTrocas { // falta fazer  a 5(para ter os vizinhos)

    // Método para gerar sugestões de troca
    public static List<TrocaPropriedades> sugerirTrocas(List<DadosPropriedades> propriedades) {
        List<TrocaPropriedades> trocasSugeridas = new ArrayList<>();

        // Passo 1: Agrupar propriedades por proprietário
        Map<String, List<DadosPropriedades>> propriedadesPorProprietario = new HashMap<>();

        for (DadosPropriedades propriedade : propriedades) {
            propriedadesPorProprietario
                    .computeIfAbsent(propriedade.getOwner(), k -> new ArrayList<>())
                    .add(propriedade);
        }

        // Passo 2: Avaliar trocas possíveis entre pares de proprietários
        for (Map.Entry<String, List<DadosPropriedades>> entry1 : propriedadesPorProprietario.entrySet()) {
            String proprietario1 = entry1.getKey();
            List<DadosPropriedades> propriedades1 = entry1.getValue();

            for (Map.Entry<String, List<DadosPropriedades>> entry2 : propriedadesPorProprietario.entrySet()) {
                String proprietario2 = entry2.getKey();
                List<DadosPropriedades> propriedades2 = entry2.getValue();

                if (!proprietario1.equals(proprietario2)) {
                    // Avaliar todas as combinações de trocas possíveis
                    for (DadosPropriedades prop1 : propriedades1) {
                        for (DadosPropriedades prop2 : propriedades2) {
                            // Calcular a melhoria na área média
                            String tipoArea = "freguesia"; // Ou "municipio", "ilha", dependendo do caso
                            String areaEscolhida = prop1.getFreguesia(); // Supondo que a área escolhida seja a freguesia

                            // Chama o método da outra classe para calcular a área média
                            double areaMediaAntes1 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, tipoArea, areaEscolhida, proprietario1);
                            double areaMediaAntes2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, tipoArea, areaEscolhida, proprietario2);

                            // Criar cópias das listas de propriedades para cada proprietário
                            List<DadosPropriedades> propriedadesTemp1 = new ArrayList<>(propriedades1);
                            List<DadosPropriedades> propriedadesTemp2 = new ArrayList<>(propriedades2);

                            // Trocar as propriedades
                            propriedadesTemp1.remove(prop1);
                            propriedadesTemp1.add(prop2);
                            propriedadesTemp2.remove(prop2);
                            propriedadesTemp2.add(prop1);

                            // Calcular as áreas médias após a troca
                            double areaMediaDepois1 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, tipoArea, areaEscolhida, proprietario1);
                            double areaMediaDepois2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedades, tipoArea, areaEscolhida, proprietario2);

                            double melhoria1 = areaMediaDepois1 - areaMediaAntes1;
                            double melhoria2 = areaMediaDepois2 - areaMediaAntes2;

                            // Calcular a "potencialidade" da troca
                            double potencialidade = Math.abs(prop1.getShapeArea() - prop2.getShapeArea());

                            // Adicionar sugestão se houver melhoria para ambos os proprietários
                            if (((melhoria1 > 0 && areaMediaAntes2 > areaMediaAntes1) || (melhoria2 > 0 && areaMediaAntes1 > areaMediaAntes2)) && potencialidade < 10) {
                                trocasSugeridas.add(new TrocaPropriedades(prop1, prop2, melhoria1, melhoria2, potencialidade));
                                System.out.println(new TrocaPropriedades(prop1, prop2, melhoria1, melhoria2, potencialidade));
                            }
                        }
                    }
                }
            }
        }
        return trocasSugeridas;
    }
}
