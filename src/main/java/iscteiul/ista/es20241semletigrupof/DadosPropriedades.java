package iscteiul.ista.es20241semletigrupof;

import com.opencsv.bean.CsvBindByName;

/**
 * Representa as propriedades de um objeto no contexto de um arquivo CSV.
 * <p>
 * Esta classe é usada para concerter os dados de um ficheiro CSV para um objeto. Os campos
 * são mapeados para as colunas do arquivo CSV com a ajuda da anotação {@link CsvBindByName}.
 * </p>
 */
public class DadosPropriedades {

    @CsvBindByName(column = "OBJECTID")
    private int objectId;

    @CsvBindByName(column = "PAR_ID")
    private String parId;

    @CsvBindByName(column = "PAR_NUM")
    private String parNum;

    @CsvBindByName(column = "Shape_Length")
    private double shapeLength;

    @CsvBindByName(column = "Shape_Area")
    private double shapeArea;

    @CsvBindByName(column = "geometry")
    private String geometry;

    @CsvBindByName(column = "OWNER")
    private String owner;

    @CsvBindByName(column = "Freguesia")
    private String freguesia;

    @CsvBindByName(column = "Municipio")
    private String municipio;

    @CsvBindByName(column = "Ilha")
    private String ilha;

    /**
     * Construtor sem argumentos, necessário para o OpenCSV.
     * <p>
     * Este construtor é utilizado para criar uma instância da classe {@code DadosPropriedades}
     * com valores padrão (não inicializados).
     * </p>
     */
    public DadosPropriedades() {
    }

    /**
     * Construtor que inicializa todos os campos com os valores fornecidos.
     *
     * @param objectId      O identificador da propriedade.
     * @param parId         Outro identificador.
     * @param parNum        Outro identificador.
     * @param shapeLength   O comprimento da geometria.
     * @param shapeArea     A área.
     * @param geometry      A descrição da geometria.
     * @param owner         O id do proprietário.
     * @param freguesia     A freguesia onde a propriedade está localizada.
     * @param municipio     O município onde a propriedade está localizada.
     * @param ilha          A ilha onde a propriedade está localizada.
     */
    public DadosPropriedades(int objectId, String parId, String parNum, double shapeLength, double shapeArea,
                             String geometry, String owner, String freguesia, String municipio, String ilha) {
        this.objectId = objectId;
        this.parId = parId;
        this.parNum = parNum;
        this.shapeLength = shapeLength;
        this.shapeArea = shapeArea;
        this.geometry = geometry;
        this.owner = owner;
        this.freguesia = freguesia;
        this.municipio = municipio;
        this.ilha = ilha;
    }

    /**
     * Obtém o id da propriedade.
     *
     * @return O id da propriedade.
     */
    public int getObjectId() {
        return objectId;
    }

    /**
     * Define o id da propriedade.
     *
     * @param objectId O id da propriedade.
     */
    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    /**
     * Obtém outro identificador..
     *
     * @return do id.
     */
    public String getParId() {
        return parId;
    }

    /**
     * Define o id.
     *
     * @param parId O id.
     */
    public void setParId(String parId) {
        this.parId = parId;
    }

    /**
     * Obtém o número da parcela.
     *
     * @return O número da parcela.
     */
    public String getParNum() {
        return parNum;
    }

    /**
     * Define o número da parcela.
     *
     * @param parNum O número da parcela.
     */
    public void setParNum(String parNum) {
        this.parNum = parNum;
    }

    /**
     * Obtém o comprimento da geometria.
     *
     * @return O comprimento da geometria.
     */
    public double getShapeLength() {
        return shapeLength;
    }

    /**
     * Define o comprimento da geometria.
     *
     * @param shapeLength O comprimento da geometria.
     */
    public void setShapeLength(double shapeLength) {
        this.shapeLength = shapeLength;
    }

    /**
     * Obtém a área .
     *
     * @return A área .
     */
    public double getShapeArea() {
        return shapeArea;
    }

    /**
     * Define a área .
     *
     * @param shapeArea A área .
     */
    public void setShapeArea(double shapeArea) {
        this.shapeArea = shapeArea;
    }

    /**
     * Obtém a descrição da geometria.
     *
     * @return A descrição da geometria.
     */
    public String getGeometry() {
        return geometry;
    }

    /**
     * Define a descrição da geometria.
     *
     * @param geometry A descrição da geometria.
     */
    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    /**
     * Obtém o proprietario
     *
     * @return O  proprietário.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Define o proprietário da propriedade.
     *
     * @param owner O  proprietário.
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Obtém o nome da freguesia.
     *
     * @return O nome da freguesia.
     */
    public String getFreguesia() {
        return freguesia;
    }

    /**
     * Define o nome da freguesia.
     *
     * @param freguesia O nome da freguesia.
     */
    public void setFreguesia(String freguesia) {
        this.freguesia = freguesia;
    }

    /**
     * Obtém o nome do município.
     *
     * @return O nome do município.
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Define o nome do município.
     *
     * @param municipio O nome do município.
     */
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    /**
     * Obtém o nome da ilha.
     *
     * @return O nome da ilha.
     */
    public String getIlha() {
        return ilha;
    }

    /**
     * Define o nome da ilha.
     *
     * @param ilha O nome da ilha.
     */
    public void setIlha(String ilha) {
        this.ilha = ilha;
    }

    /**
     * Retorna uma representação em formato de string dos dados da propriedade.
     * <p>
     * O método {@code toString()} é utilizado para gerar uma string legível que represente todos os campos
     * da classe {@code DadosPropriedades}.
     * </p>
     *
     * @return Uma string representando os dados da propriedade.
     */
    @Override
    public String toString() {
        return "DadosPropriedades{" +
                "objectId=" + objectId +
                ", parId='" + parId + '\'' +
                ", parNum='" + parNum + '\'' +
                ", shapeLength=" + shapeLength +
                ", shapeArea=" + shapeArea +
                ", geometry='" + geometry + '\'' +
                ", owner='" + owner + '\'' +
                ", freguesia='" + freguesia + '\'' +
                ", municipio='" + municipio + '\'' +
                ", ilha='" + ilha + '\'' +
                '}';
    }
}
