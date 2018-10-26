package team6.cmpt276.greenfoodchallenge.classes;

import java.io.Serializable;

/*
   stores data about frequency, emissions of co2 per gram,
   and amount of grams eaten per meal for each type of
   food item
 */
public class FoodData implements Serializable {
    private int frequency;
    private double co2PerGrams;
    private double gramsPerMeal;

    //basic constructor
    public FoodData(int frequency, double co2PerGrams, double gramsPerMeal) {
        this.frequency = frequency;
        this.co2PerGrams = co2PerGrams;
        this.gramsPerMeal = gramsPerMeal;
    }

    //constructor for non protein sources
    public FoodData(double co2PerGrams, double gramsPerMeal) {
        this.frequency = 0;
        this.co2PerGrams = co2PerGrams;
        this.gramsPerMeal = gramsPerMeal;
    }

    //cloning a food object
    public FoodData clone() {
        FoodData newObj = new FoodData(this.getFrequency(), this.getCo2PerGrams(),
                                        this.getGramsPerMeal());
        return newObj;
    }

    //grams of food item eaten per meal
    public double getGramsPerMeal() {
        return gramsPerMeal;
    }

    //set the amount of grams a user eats per meal of food item
    public void setGramsPerMeal(double gramsPerMeal) {
        this.gramsPerMeal = gramsPerMeal;
    }

    public double getCo2PerGrams() {
        return co2PerGrams;
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
        double co2 = frequency * co2permeal*2;
        return co2;
    }

}
