package mypackage;

public class Cartridge4Table {
    private Integer id;
    private Integer nomer;
    private String model;
    private String printer;

    public Cartridge4Table(Integer nomer, String model, String printer) {
        this.nomer = nomer;
        this.model = model;
        this.printer = printer;
    }

    public Cartridge4Table(Integer id, Integer nomer, String model, String printer) {
        this.id = id;
        this.nomer = nomer;
        this.model = model;
        this.printer = printer;
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

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Integer getId() {
        return id;
    }
}
