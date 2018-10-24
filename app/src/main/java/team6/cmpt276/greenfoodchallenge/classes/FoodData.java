package team6.cmpt276.greenfoodchallenge.classes;

public class FoodData {
    private int frequency;
    private double co2PerGrams;
    private boolean isProtein;

    public FoodData(int frequency, double co2PerGrams) {
        this.frequency = frequency;
        this.co2PerGrams = co2PerGrams;
    }

    public FoodData(int frequency, double co2PerGrams, int gramsPerMeal, boolean isProtein) { //constructor for non protein sources
        this.frequency = frequency;
        this.co2PerGrams = co2PerGrams;
        this.isProtein=isProtein;
    }

    public double getCo2PerGrams() {
        return co2PerGrams;
    }

    public void setCo2PerGrams(double co2PerGrams) {
        this.co2PerGrams = co2PerGrams;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public double getco2(int gramsPerMeal){ //returns weekly co2e due to this food item
        double co2permeal=gramsPerMeal*co2PerGrams;
        double co2=frequency*co2permeal;
        return co2;
    }

    public boolean isProtein() {
        return isProtein;
    }
}
