package team6.cmpt276.greenfoodchallenge.classes;

public class Pledge {
    public double saveAmount;
    public String dietOption;
    private String name;
    private String photo;
    public String municipality;

    public Pledge() {
    }

    public Pledge(double saveAmount, String dietOption) {
            this.saveAmount = saveAmount;
            this.dietOption = dietOption;
            this.municipality = "Undefined";
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public Pledge(double saveAmount, String dietOption, String municipality) {
        this.saveAmount = saveAmount;
        this.dietOption = dietOption;
        this.municipality = municipality;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
