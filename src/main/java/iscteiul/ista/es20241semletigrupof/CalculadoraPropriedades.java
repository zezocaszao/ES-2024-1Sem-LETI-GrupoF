package iscteiul.ista.es20241semletigrupof;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Calcula a área média das propriedades de uma área geográfica/administrativa.
 */
public class CalculadoraPropriedades {

/**
 * Obtém as áreas geográficas disponíveis em uma lista de propriedades.
 * A área pode ser uma freguesia, município ou ilha, dependendo do tipo de área especificado pelo utilizador.
 *
 * @param propriedades Lista de todas as propriedades disponíveis.
 * @param tipoArea     Tipo de área geográfica que pode ser "freguesia", "municipio" ou "ilha".
 * @return Lista de áreas distintas, ordenadas alfabeticamente, de acordo com o tipo de área.
 */

    public static List<String> obterAreasDisponiveis(List<DadosPropriedades> propriedades, String tipoArea) {
        return propriedades.stream()
                .map(propriedade -> {
                    switch (tipoArea.toLowerCase()) {
                        case "freguesia":
                            return propriedade.getFreguesia();
                        case "municipio":
                            return propriedade.getMunicipio();
                        case "ilha":
                            return propriedade.getIlha();
                        default:
                            throw new IllegalArgumentException("Tipo de área inválido. Use: freguesia, municipio ou ilha.");
                    }
                })
                .distinct() // Remover duplicatas
                .sorted()   // Ordenar alfabeticamente
                .collect(Collectors.toList());
    }

    /**
     * Calcula a área média das propriedades dentro de uma área geográfica especificada.
     * @param propriedades Lista de todas as propriedades disponíveis.
     * @param tipoArea     Tipo de área geográfica.
     * @param valorArea    O valor específico da área  para calcular a média.
     * @return A área média das propriedades dentro da área especificada.
     */
    public static double calcularAreaMedia(List<DadosPropriedades> propriedades, String tipoArea, String valorArea) {
        // Filtrar as propriedades de acordo com o tipo de área e valor especificado
        List<DadosPropriedades> propriedadesFiltradas = propriedades.stream()
                .filter(propriedade -> {
                    switch (tipoArea.toLowerCase()) {
                        case "freguesia":
                            return propriedade.getFreguesia().equalsIgnoreCase(valorArea);
                        case "municipio":
                            return propriedade.getMunicipio().equalsIgnoreCase(valorArea);
                        case "ilha":
                            return propriedade.getIlha().equalsIgnoreCase(valorArea);
                        default:
                            throw new IllegalArgumentException("Tipo de área inválido. Use: freguesia, municipio ou ilha.");
                    }
                })
                .collect(Collectors.toList());


        OptionalDouble media = propriedadesFiltradas.stream()
                .mapToDouble(DadosPropriedades::getShapeArea)
                .average();

        return media.orElse(-1);
    }
}
