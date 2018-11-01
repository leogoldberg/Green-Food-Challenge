package team6.cmpt276.greenfoodchallenge.classes;

public class User {
    private String firstName;
    private String lastName;
    private boolean isAdmin;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        isAdmin = false;
    }
}
