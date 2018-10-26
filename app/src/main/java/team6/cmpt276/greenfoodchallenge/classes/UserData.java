package team6.cmpt276.greenfoodchallenge.classes;

import java.io.Serializable;
import java.util.*;

/*
    Class is a collection of food data objects which records the
    frequency that the user eats each food item and the amount
    of protein/veg the user eats per meal

    Calculates the total co2 the user emits due to food consumption
    based off food objects list
 */

public class UserData implements Serializable {
    private List<FoodData> userFoodData;
    private List<String> foodNames;
    private double proteinPerMeal;
    private double vegPerMeal;

    //Empty constructor
    public UserData() {
        userFoodData = new ArrayList<>();
        foodNames = new ArrayList<>();
        vegPerMeal =0;
        proteinPerMeal =0;
    }

    //HardCoded Constructor
    public UserData(double proteinPerMeal, double vegPerMeal) {
        this.userFoodData = new ArrayList<>();
        this.foodNames = new ArrayList<>();
        this.proteinPerMeal = proteinPerMeal;
        this.vegPerMeal = vegPerMeal;

        add("Beef", new FoodData(27, proteinPerMeal));
        add("Pork", new FoodData(12.1, proteinPerMeal));
        add("Chicken", new FoodData(6.9, proteinPerMeal));
        add("Fish", new FoodData(6.1, proteinPerMeal));
        add("Eggs",  new FoodData(4.8, proteinPerMeal));
        add("Beans", new FoodData(2, proteinPerMeal));
        add("Vegetables", new FoodData(2, vegPerMeal));
    }

    //Copy constructor
    public UserData(UserData userData) {
        userFoodData = copyFoodData(userData.userFoodData);
        foodNames = userData.getFoodNames();
        vegPerMeal = userData.getVegPerMeal();
        proteinPerMeal = userData.getProteinPerMeal();
    }


    //calculates total weekly co2
    private double getTotalco2(){
        FoodData food;
        double total = 0;
        for(int i = 0; i < userFoodData.size(); i++){
            food = userFoodData.get(i);
            total += food.getco2();
        }
        return total;
    }

    //yearly co2
    public double getTotalco2perYear(){
        return getTotalco2()*52;
    }

    //calculates proportion of weekly co2 due to food item
    public double getProportion(String foodName){
        int index = foodNames.indexOf(foodName);
        double totalco2 = getTotalco2();

        FoodData food = userFoodData.get(index);
        double co2 = food.getco2();
        double proportion = co2 / totalco2;

        return proportion;
    }


    public int getUserFrequency(String foodName){
        int index = foodNames.indexOf(foodName);
        int frequency = userFoodData.get(index).getFrequency();
        return frequency;
    }

    public void setFoodFrequency(String foodName, int frequency){
        int index = foodNames.indexOf(foodName);
        userFoodData.get(index).setFrequency(frequency);
    }

    //adders
    public void add(String foodName, FoodData foodObject) {
        userFoodData.add(foodObject);
        foodNames.add(foodName);
    }

    public List<String> getFoodNames() {
        return foodNames;
    }
    public List<FoodData> getUserFoodData(){return userFoodData;}

    public void setProteinPerMeal(double proteinPerMeal) {
        this.proteinPerMeal=proteinPerMeal;
        for(int i = 0; i < userFoodData.size(); i++){
            FoodData food = userFoodData.get(i);
            String curFoodName = foodNames.get(i);

            if(curFoodName != "Vegetables") {
                food.setGramsPerMeal(proteinPerMeal);
            }
        }
    }
    public double getProteinPerMeal() {
        return proteinPerMeal;
    }

    public double getVegPerMeal() {
        return vegPerMeal;
    }

    public int getTotalFrequency() {
        int total = 0;

        for(int i = 0; i < this.userFoodData.size(); i++) {
            FoodData curFoodData = userFoodData.get(i);

            total += curFoodData.getFrequency();
        }

        return total;
    }

    public List<FoodData> copyFoodData(List<FoodData> copyUserData) {
        List<FoodData> returnFoodData = new ArrayList<>();

        for (FoodData item : copyUserData) {
            returnFoodData.add(item.clone());
        }

        return returnFoodData;
    }

}
