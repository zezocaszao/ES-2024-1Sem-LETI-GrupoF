package iscteiul.ista.es20241semletigrupof;

import java.util.*;

/**
 * Classe responsável por sugerir trocas de propriedades entre vizinhos com base em áreas geográficas.
 * A sugestão de trocas é gerada com base em um grafo de proprietários e a avaliação da melhoria da distribuição das propriedades.
 */
public class SugestaoTrocas {

    /**
     * Gera uma lista de sugestões de trocas de propriedades entre vizinhos com base em um grafo de proprietários.
     * A troca é sugerida entre dois proprietários com propriedades na área escolhida.
     * <p>
     * A sugestão de troca é calculada considerando a melhoria na média da área das propriedades dos donos após a troca.

     *
     * @param propriedades  A lista de todas as propriedades disponíveis.
     * @param tipoArea      O tipo de área para filtrar as propriedades (freguesia, município ou ilha).
     * @param areaEscolhida O nome da área escolhida para filtrar as propriedades .
     * @param grafo         O grafo de proprietários que contém as relações de vizinhança entre os proprietários.
     * @return Uma lista de sugestões de trocas de propriedades, onde cada sugestão é representada por um objeto
     *         {@link TrocaPropriedades}.
     */
    public static List<TrocaPropriedades> sugerirTrocas(List<DadosPropriedades> propriedades, String tipoArea,
                                                        String areaEscolhida, GrafoProprietarios grafo) {
        List<TrocaPropriedades> trocasSugeridas = new ArrayList<>();


        List<DadosPropriedades> propriedadesNaArea = new ArrayList<>();
        Set<String> proprietariosNaArea = new HashSet<>();
        for (DadosPropriedades propriedade : propriedades) {
            if (tipoArea.equalsIgnoreCase("freguesia") && propriedade.getFreguesia().equalsIgnoreCase(areaEscolhida) ||
                    tipoArea.equalsIgnoreCase("municipio") && propriedade.getMunicipio().equalsIgnoreCase(areaEscolhida) ||
                    tipoArea.equalsIgnoreCase("ilha") && propriedade.getIlha().equalsIgnoreCase(areaEscolhida)) {
                propriedadesNaArea.add(propriedade);
                proprietariosNaArea.add(propriedade.getOwner());
            }
        }


        Map<String, Set<String>> adjacencias = grafo.getAdjacencias();
        for (String proprietario1 : proprietariosNaArea) {

            if (!adjacencias.containsKey(proprietario1)) {
                continue;
            }


            List<DadosPropriedades> propriedades1 = new ArrayList<>();
            for (DadosPropriedades propriedade : propriedadesNaArea) {
                if (propriedade.getOwner().equals(proprietario1)) {
                    propriedades1.add(propriedade);
                }
            }


            for (String proprietario2 : adjacencias.get(proprietario1)) {
                // Verificar se o proprietário 2 está na área escolhida
                if (!proprietariosNaArea.contains(proprietario2)) {
                    continue;
                }


                List<DadosPropriedades> propriedades2 = new ArrayList<>();
                for (DadosPropriedades propriedade : propriedadesNaArea) {
                    if (propriedade.getOwner().equals(proprietario2)) {
                        propriedades2.add(propriedade);
                    }
                }


                for (DadosPropriedades prop1 : propriedades1) {
                    for (DadosPropriedades prop2 : propriedades2) {
                        List<DadosPropriedades> propriedadesSimuladas = new ArrayList<>(propriedadesNaArea);


                        propriedadesSimuladas.remove(prop1);
                        propriedadesSimuladas.remove(prop2);

                        DadosPropriedades prop1Trocada = new DadosPropriedades(prop2.getObjectId(), prop2.getParId(),
                                prop2.getParNum(), prop2.getShapeLength(), prop2.getShapeArea(),
                                prop2.getGeometry(), prop1.getOwner(), prop2.getFreguesia(),
                                prop2.getMunicipio(), prop2.getIlha());

                        DadosPropriedades prop2Trocada = new DadosPropriedades(prop1.getObjectId(), prop1.getParId(),
                                prop1.getParNum(), prop1.getShapeLength(), prop1.getShapeArea(),
                                prop1.getGeometry(), prop2.getOwner(), prop1.getFreguesia(),
                                prop1.getMunicipio(), prop1.getIlha());

                        propriedadesSimuladas.add(prop1Trocada);
                        propriedadesSimuladas.add(prop2Trocada);

                        // Calcular melhoria
                        double areaMediaAntes1 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedadesNaArea, tipoArea, areaEscolhida, proprietario1);
                        double areaMediaAntes2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedadesNaArea, tipoArea, areaEscolhida, proprietario2);
                        double areaMediaDepois1 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedadesSimuladas, tipoArea, areaEscolhida, proprietario1);
                        double areaMediaDepois2 = CalcularPropriedadesOwners.calcularAreaMediaPorDono(propriedadesSimuladas, tipoArea, areaEscolhida, proprietario2);

                        double melhoria1 = areaMediaDepois1 - areaMediaAntes1;
                        double melhoria2 = areaMediaDepois2 - areaMediaAntes2;
                        double potencialidade = Math.abs(prop1.getShapeArea() - prop2.getShapeArea());

                        if ( potencialidade < 10) {
                            TrocaPropriedades troca = new TrocaPropriedades(prop1, prop2, melhoria1, melhoria2, potencialidade);
                            trocasSugeridas.add(troca);
                        }
                    }
                }
            }
        }

        return trocasSugeridas;
    }

}