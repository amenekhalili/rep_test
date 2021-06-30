package ir.fararayaneh.erp.data.models.middle;

import ir.fararayaneh.erp.data.models.IModels;

/**
 * use as a json array in GoodsTable
 */
public class GoodSUOMModel implements IModels {

    private int  numerator, deNominator;
    private String C5UnitId, weight, length, width, height,alloy,unitName,barCode;

    public GoodSUOMModel(int numerator, int deNominator, String c5UnitId, String weight, String length, String width, String height, String alloy, String unitName, String barCode) {
        this.numerator = numerator;
        this.deNominator = deNominator;
        C5UnitId = c5UnitId;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.alloy = alloy;
        this.unitName = unitName;
        this.barCode = barCode;
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDeNominator() {
        return deNominator;
    }

    public void setDeNominator(int deNominator) {
        this.deNominator = deNominator;
    }

    public String getC5UnitId() {
        return C5UnitId;
    }

    public void setC5UnitId(String c5UnitId) {
        C5UnitId = c5UnitId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAlloy() {
        return alloy;
    }

    public void setAlloy(String alloy) {
        this.alloy = alloy;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
