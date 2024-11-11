

import com.opencsv.bean.CsvBindByName;


public class DadosPropriedades {


    //String info = "Madeira-Moodle.csv";

    @CsvBindByName(column = "OBJECTID")
    private int objectId;

    @CsvBindByName(column = "PAR_ID")
    private double parId;

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

    public int getObjectId() {
        return objectId;
    }

    public double getParId() {
        return parId;
    }

    public void setParId(double parId) {
        this.parId = parId;
    }

    public String getParNum() {
        return parNum;
    }

    public void setParNum(String parNum) {
        this.parNum = parNum;
    }

    public double getShapeLength() {
        return shapeLength;
    }

    public void setShapeLength(double shapeLength) {
        this.shapeLength = shapeLength;
    }

    public double getShapeArea() {
        return shapeArea;
    }

    public void setShapeArea(double shapeArea) {
        this.shapeArea = shapeArea;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }
}
