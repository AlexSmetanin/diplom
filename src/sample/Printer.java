package sample;

public class Printer {
    private String printerModel;
    private Integer userID;

    public Printer(String printerModel, Integer userID) {
        this.printerModel = printerModel;
        this.userID = userID;
    }

    public String getPrinterModel() {
        return printerModel;
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
