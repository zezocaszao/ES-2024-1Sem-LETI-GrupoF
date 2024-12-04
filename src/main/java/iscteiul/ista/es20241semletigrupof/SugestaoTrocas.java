package iscteiul.ista.es20241semletigrupof;

import java.util.*;

public class SugestaoTrocas {

    // Método para gerar sugestões de troca
    public static List<TrocaPropriedades> sugerirTrocas(List<DadosPropriedades> propriedades, String tipoArea, String areaEscolhida) {
        List<TrocaPropriedades> trocasSugeridas = new ArrayList<>();

        // Filtrar propriedades pela área escolhida
        List<DadosPropriedades> propriedadesNaArea = new ArrayList<>();
        for (DadosPropriedades propriedade : propriedades) {
            if (tipoArea.equalsIgnoreCase("freguesia") && propriedade.getFreguesia().equalsIgnoreCase(areaEscolhida) ||
                    tipoArea.equalsIgnoreCase("municipio") && propriedade.getMunicipio().equalsIgnoreCase(areaEscolhida) ||
                    tipoArea.equalsIgnoreCase("ilha") && propriedade.getIlha().equalsIgnoreCase(areaEscolhida)) {
                propriedadesNaArea.add(propriedade);
            }
        }

        // Agrupar propriedades por proprietário dentro da área escolhida
        Map<String, List<DadosPropriedades>> propriedadesPorProprietario = new HashMap<>();
        for (DadosPropriedades propriedade : propriedadesNaArea) {
            propriedadesPorProprietario
                    .computeIfAbsent(propriedade.getOwner(), k -> new ArrayList<>())
                    .add(propriedade);
        }

        // Avaliar trocas possíveis entre pares de proprietários
        for (Map.Entry<String, List<DadosPropriedades>> entry1 : propriedadesPorProprietario.entrySet()) {
            String proprietario1 = entry1.getKey();
            List<DadosPropriedades> propriedades1 = entry1.getValue();

            for (Map.Entry<String, List<DadosPropriedades>> entry2 : propriedadesPorProprietario.entrySet()) {
                String proprietario2 = entry2.getKey();
                List<DadosPropriedades> propriedades2 = entry2.getValue();

                // Evitar comparar o mesmo proprietário
                if (!proprietario1.equals(proprietario2)) {
                    // Avaliar todas as combinações de trocas possíveis
                    for (DadosPropriedades prop1 : propriedades1) {
                        for (DadosPropriedades prop2 : propriedades2) {
                            // Criar uma cópia da lista de propriedades para simular a troca
                            List<DadosPropriedades> propriedadesSimuladas = new ArrayList<>(propriedadesNaArea);

                            // Remover as propriedades originais
                            propriedadesSimuladas.remove(prop1);
                            propriedadesSimuladas.remove(prop2);

                            // Adicionar as propriedades trocadas
                            //DadosPropriedades prop1Trocada = new DadosPropriedades(prop2.getObjectId(), prop1.getOwner(), prop2.getShapeArea(), prop2.getFreguesia(), prop2.getMunicipio(), prop2.getIlha());
                            //DadosPropriedades prop2Trocada = new DadosPropriedades(prop1.getId(), prop2.getOwner(), prop1.getShapeArea(), prop1.getFreguesia(), prop1.getMunicipio(), prop1.getIlha());
                            //propriedadesSimuladas.add(prop1Trocada);
                            //propriedadesSimuladas.add(prop2Trocada);

                            // Calcular a área média antes da troca
                            double areaMediaAntes1 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedadesNaArea, tipoArea, areaEscolhida, proprietario1);
                            double areaMediaAntes2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedadesNaArea, tipoArea, areaEscolhida, proprietario2);

                            // Calcular a área média após a troca
                            double areaMediaDepois1 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedadesSimuladas, tipoArea, areaEscolhida, proprietario1);
                            double areaMediaDepois2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedadesSimuladas, tipoArea, areaEscolhida, proprietario2);

                            // Calcular a melhoria
                            double melhoria1 = areaMediaDepois1 - areaMediaAntes1;
                            double melhoria2 = areaMediaDepois2 - areaMediaAntes2;

                            // Calcular a "potencialidade" da troca
                            double potencialidade = Math.abs(prop1.getShapeArea() - prop2.getShapeArea());

                            // Adicionar sugestão se houver melhoria para ambos os proprietários
                            if (melhoria1 > 0 && melhoria2 > 0 && potencialidade < 10) {
                                TrocaPropriedades troca = new TrocaPropriedades(prop1, prop2, melhoria1, melhoria2, potencialidade);
                                trocasSugeridas.add(troca);
                                System.out.println(troca);
                            }
                        }
                    }
                }
            }
        }
        return trocasSugeridas;
    }
}
