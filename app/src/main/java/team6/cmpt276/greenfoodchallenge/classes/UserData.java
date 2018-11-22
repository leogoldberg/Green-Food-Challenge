package team6.cmpt276.greenfoodchallenge.classes;

import android.content.Context;
import android.util.TypedValue;

import java.io.Serializable;
import java.util.*;

import team6.cmpt276.greenfoodchallenge.R;

/*
    Class is a collection of food data objects which records the
    frequency that the user eats each food item and the amount
    of protein/veg the user eats per meal

    Calculates the total co2 the user emits due to food consumption
    based off food objects list
 */

public class UserData {
    private double proteinPerMeal = 0;
    private double vegPerMeal = 0;
    private int beefFrequency = 0;
    private int porkFrequency = 0;
    private int chickenFrenquency = 0;
    private int fishFrequency = 0;
    private int eggFrequency = 0;
    private int beanFrequency = 0;
    private int vegFrequency = 0;


    //Normal Constructor
    public UserData(){

    }

    //Copy constructor
    public UserData(UserData userData) {
        beanFrequency = userData.getBeanFrequency();
        porkFrequency = userData.getPorkFrequency();
        chickenFrenquency = userData.getChickenFrenquency();
        fishFrequency = userData.getFishFrequency();
        eggFrequency = userData.getEggFrequency();
        beanFrequency = userData.getBeanFrequency();
        vegFrequency = userData.getVegFrequency();
        vegPerMeal = userData.getVegPerMeal();
        proteinPerMeal = userData.getProteinPerMeal();
    }



    //yearly co2
    public double getTotalco2perYear(){
        //Total consumption per week is the aggregrated CO2 consumption per gram times the consumption in gram per meal times the frequency of the food in a week times 2 (assuming 2 meals a week).
        //Total consumption per year is the consumption per week times 52 weeks.
        double total = beefFrequency*27*proteinPerMeal;
        total += porkFrequency*12.1*proteinPerMeal;
        total += chickenFrenquency*6.9*proteinPerMeal;
        total += fishFrequency*6.1*proteinPerMeal;
        total += eggFrequency*4.8*proteinPerMeal;
        total += beanFrequency*2*proteinPerMeal;
        total +=vegFrequency*2*vegFrequency;
        total = total*10;
        return total;
    }

    private double categoryCalculation(double categoryCo2, int categoryFrequency) {
        int totalFrequency = getTotalFrequency();

        return categoryCo2 * (categoryFrequency / totalFrequency);
    }

    public void setFoodFrequency (String foodName, int frequency) {
        ArrayList<String> foodNames = getFoodNames();
        int index = foodNames.indexOf(foodName);
        switch (index){
            case 0:
                setBeefFrequency(frequency);
                break;
            case 1:
                setPorkFrequency(frequency);
                break;
            case 2:
                setChickenFrenquency(frequency);
                break;
            case 3:
                setFishFrequency(frequency);
                break;
            case 4:
                setEggFrequency(frequency);
                break;
            case 5:
                setBeanFrequency(frequency);
                break;
            case 6:
                setVegFrequency(frequency);
                break;
        }
    }



    public ArrayList<String> getFoodNames() {
        ArrayList<String> foodNames = new ArrayList<>();
        foodNames.add("Beef");
        foodNames.add("Pork");
        foodNames.add("Chicken");
        foodNames.add("Fish");
        foodNames.add("Eggs");
        foodNames.add("Beans");
        foodNames.add("Vegetable");
        return foodNames;
    }
    public ArrayList<Integer> getUserFoodData() {
        ArrayList<Integer> foodData = new ArrayList<>();
        foodData.add(getBeefFrequency());
        foodData.add(getPorkFrequency());
        foodData.add(getChickenFrenquency());
        foodData.add(getFishFrequency());
        foodData.add(getEggFrequency());
        foodData.add(getBeanFrequency());
        foodData.add(getVegFrequency());
        return foodData;
    }


    public double getProteinPerMeal() {
        return proteinPerMeal;
    }

    public void setProteinPerMeal(double proteinPerMeal){
        this.proteinPerMeal = proteinPerMeal;
    }

    public double getVegPerMeal() {
        return vegPerMeal;
    }

    public void setVegPerMeal(double vegPerMeal) {
        this.vegPerMeal = vegPerMeal;
    }

    public int getTotalFrequency() {
        int total = 0;
        total += beefFrequency + porkFrequency + chickenFrenquency + fishFrequency + eggFrequency + beanFrequency + vegFrequency;
        return total;
    }



    public int getBeefFrequency() {
        return beefFrequency;
    }

    public void setBeefFrequency(int beefFrequency) {
        this.beefFrequency = beefFrequency;
    }

    public int getPorkFrequency() {
        return porkFrequency;
    }

    public void setPorkFrequency(int porkFrequency) {
        this.porkFrequency = porkFrequency;
    }

    public int getChickenFrenquency() {
        return chickenFrenquency;
    }

    public void setChickenFrenquency(int chickenFrenquency) {
        this.chickenFrenquency = chickenFrenquency;
    }

    public int getFishFrequency() {
        return fishFrequency;
    }

    public void setFishFrequency(int fishFrequency) {
        this.fishFrequency = fishFrequency;
    }

    public int getEggFrequency() {
        return eggFrequency;
    }

    public void setEggFrequency(int eggFrequency) {
        this.eggFrequency = eggFrequency;
    }

    public int getBeanFrequency() {
        return beanFrequency;
    }

    public void setBeanFrequency(int beanFrequency) {
        this.beanFrequency = beanFrequency;
    }

    public int getVegFrequency() {
        return vegFrequency;
    }

    public void setVegFrequency(int vegFrequency) {
        this.vegFrequency = vegFrequency;
    }
}
