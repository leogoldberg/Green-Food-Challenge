package team6.cmpt276.greenfoodchallenge.classes;

public class User {
    public String name;
    public boolean isAdmin;
    public String icon;
    public String email;
    public String municipality;

    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        isAdmin = false;
        icon = "cherry";
        municipality = "undefined";
    }

}
