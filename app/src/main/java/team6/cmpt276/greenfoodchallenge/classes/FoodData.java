package team6.cmpt276.greenfoodchallenge.classes;

import java.io.Serializable;

public class FoodData implements Serializable {
    private int frequency;
    private double co2PerGrams;
    private double gramsPerMeal;

    public FoodData(int frequency, double co2PerGrams, double gramsPerMeal) {
        this.frequency = frequency;
        this.co2PerGrams = co2PerGrams;
        this.gramsPerMeal=gramsPerMeal;
    }

    public FoodData(double co2PerGrams, double gramsPerMeal) { //constructor for non protein sources
        this.frequency = 0;
        this.co2PerGrams = co2PerGrams;
        this.gramsPerMeal=gramsPerMeal;
    }

    public double getGramsPerMeal() {
        return gramsPerMeal;
    }

    public void setGramsPerMeal(double gramsPerMeal) {
        this.gramsPerMeal = gramsPerMeal;
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

    //returns weekly co2e due to this food item
    public double getco2(){
        double co2permeal = gramsPerMeal*co2PerGrams;
        double co2=frequency*co2permeal*2; //assuming 2 meals a day
        return co2;
    }

    public double getco2PerYear(){
        return getco2()*52;
    }

}
