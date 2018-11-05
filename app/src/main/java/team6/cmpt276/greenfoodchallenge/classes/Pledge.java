package team6.cmpt276.greenfoodchallenge.classes;

public class Pledge {
    public double saveAmount;
    public String dietOption;
    public String municipality = null;

    public Pledge() {
    }

    public Pledge(double saveAmount, String dietOption) {
            this.saveAmount = saveAmount;
            this.dietOption = dietOption;
    }
}
