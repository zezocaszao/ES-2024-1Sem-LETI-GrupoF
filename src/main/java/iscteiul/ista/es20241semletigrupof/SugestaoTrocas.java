package iscteiul.ista.es20241semletigrupof;

import java.util.*;

public class SugestaoTrocas { // falta fazer a 4(area media) e a 5(para ter os vizinhos)

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
                            double areaMediaAntes1 = calcularAreaMedia(proprietario1, propriedadesPorProprietario.get(proprietario1));
                            double areaMediaAntes2 = calcularAreaMedia(proprietario2, propriedadesPorProprietario.get(proprietario2));


                            // Criar cópias das listas de propriedades para cada proprietário
                            List<DadosPropriedades> propriedadesTemp1 = new ArrayList<>(propriedades1);
                            List<DadosPropriedades> propriedadesTemp2 = new ArrayList<>(propriedades2);

                            // Trocar as propriedades
                            propriedadesTemp1.remove(prop1);
                            propriedadesTemp1.add(prop2);
                            propriedadesTemp2.remove(prop2);
                            propriedadesTemp2.add(prop1);

                            // Calcular as áreas médias após a troca
                            double areaMediaDepois1 = calcularAreaMedia(proprietario1, propriedadesTemp1);
                            double areaMediaDepois2 = calcularAreaMedia(proprietario2, propriedadesTemp2);

                            double melhoria1 = areaMediaDepois1 - areaMediaAntes1;
                            double melhoria2 = areaMediaDepois2 - areaMediaAntes2;

                            // Calcular a "potencialidade" da troca
                            double potencialidade = Math.abs(prop1.getShapeArea() - prop2.getShapeArea());

                            // Adicionar sugestão se houver melhoria para ambos os proprietários
                            if (((melhoria1 > 0  && areaMediaAntes2 > areaMediaAntes1)|| (melhoria2 > 0 &&  areaMediaAntes1 > areaMediaAntes2)) && potencialidade < 10) {
                                trocasSugeridas.add(new TrocaPropriedades(prop1, prop2, melhoria1, melhoria2, potencialidade));
                                System.out.println(new TrocaPropriedades(prop1, prop2, melhoria1, melhoria2, potencialidade));
                            }
                        }
                    }
                }
            }
        }

        // Passo 3: Ordenar as sugestões por melhoria e potencialidade
        /* trocasSugeridas.sort((t1, t2) -> {
            // Priorizar pela maior melhoria
            int comparacaoMelhoria = Double.compare(t2.getMelhoriaProprietario1() + t2.getMelhoriaProprietario2(),
                    t1.getMelhoriaProprietario1() + t1.getMelhoriaProprietario2());
            if (comparacaoMelhoria == 0) {
                // Em caso de empate, priorizar pela menor diferença de áreas
                return Double.compare(t1.getPotencialidadeTroca(), t2.getPotencialidadeTroca());
            }
            return comparacaoMelhoria;
        });
        */
        return trocasSugeridas;
    }

    // Método para calcular a área média de um proprietário
    private static double calcularAreaMedia(String proprietarioId, List<DadosPropriedades> propriedadesPorProprietario) {

        if (propriedadesPorProprietario == null || propriedadesPorProprietario.isEmpty()) {
            return 0;
        }
        double somaAreas = 0;
        for (DadosPropriedades prop : propriedadesPorProprietario) {
            somaAreas += prop.getShapeArea();
        }
        return somaAreas / propriedadesPorProprietario.size();
    }
}
