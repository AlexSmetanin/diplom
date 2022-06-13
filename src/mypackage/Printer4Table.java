package mypackage;

public class Printer4Table {
    private Integer id;
    private String printerModel;
    private String user;

    public Printer4Table(Integer id, String printerModel, String user) {
        this.id = id;
        this.printerModel = printerModel;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrinterModel() {
        return printerModel;
    }

    public void setPrinterModel(String printerModel) {
        this.printerModel = printerModel;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
