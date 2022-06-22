package mypackage;

public class User {
    private Integer id;
    private String login;
    private String password;
    private String userName;
    private String otdel;
    private String role;

    public User(String login, String password, String userName, String otdel, String role) {
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.otdel = otdel;
        this.role = role;
    }
    public User(Integer id, String login, String password, String userName, String otdel, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.otdel = otdel;
        this.role = role;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOtdel() {
        return otdel;
    }

    public void setOtdel(String otdel) {
        this.otdel = otdel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }
}
