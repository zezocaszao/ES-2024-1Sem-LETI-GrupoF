package iscteiul.ista.es20241semletigrupof;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class CalculadoraPropriedades {

    /**
     * Calcula a área média das propriedades de uma área geográfica/administrativa.
     *
     * @param propriedades Lista de todas as propriedades disponíveis.
     * @param tipoArea      Tipo de área geográfica (freguesia, concelho ou distrito).
     * @param valorArea     Valor específico da área geográfica.
     * @return Área média das propriedades ou -1 se não houver propriedades correspondentes.
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

        // Calcular a área média das propriedades filtradas
        OptionalDouble media = propriedadesFiltradas.stream()
                .mapToDouble(DadosPropriedades::getShapeArea)
                .average();

        return media.orElse(-1); // Retorna -1 se não houver propriedades correspondentes
    }
}
