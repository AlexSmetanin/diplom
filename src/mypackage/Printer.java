package mypackage;

public class Printer {
    private Integer id;
    private String printerModel;
    private Integer userID;

    public Printer(String printerModel, Integer userID) {
        this.printerModel = printerModel;
        this.userID = userID;
    }

    public Printer(Integer id, String printerModel, Integer userID) {
        this.id = id;
        this.printerModel = printerModel;
        this.userID = userID;
    }

    public String getPrinterModel() {
        return printerModel;
    }

    public Integer getId() {
        return id;
    }

    public void setPrinterModel(String printerModel) {
        this.printerModel = printerModel;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
