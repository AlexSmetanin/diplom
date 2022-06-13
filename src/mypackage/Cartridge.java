package mypackage;

public class Cartridge {
    private Integer id;
    private Integer nomer;
    private String model;
    private Integer printerID;

    public Cartridge(Integer nomer, String model, Integer printerID) {
        this.nomer = nomer;
        this.model = model;
        this.printerID = printerID;
    }

    public Cartridge(Integer id, Integer nomer, String model, Integer printerID) {
        this.id = id;
        this.nomer = nomer;
        this.model = model;
        this.printerID = printerID;
    }

    public Integer getNomer() {
        return nomer;
    }

    public void setNomer(Integer nomer) {
        this.nomer = nomer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPrinterID() {
        return printerID;
    }

    public void setPrinterID(Integer printerID) {
        this.printerID = printerID;
    }

    public Integer getId() {
        return id;
    }
}
