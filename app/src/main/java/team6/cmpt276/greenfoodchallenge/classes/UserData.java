package team6.cmpt276.greenfoodchallenge.classes;

import java.io.Serializable;
import java.util.*;


public class UserData implements Serializable {
    private List<FoodData> userFoodData;
    private List<String> foodNames;
    private double proteinPerMeal;
    private double vegPerMeal;

    //Normal Constructor
    public UserData(int proteinPerMeal, int vegPerMeal){
        this.userFoodData = new ArrayList<>();
        this.foodNames = new ArrayList<>();
        this.proteinPerMeal = proteinPerMeal;
        this.vegPerMeal=vegPerMeal;

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
        userFoodData = userData.getUserFoodData();
        foodNames = userData.getFoodNames();
        vegPerMeal = userData.getVegPerMeal();
        proteinPerMeal = userData.getProteinPerMeal();
    }

    //calculates total weekly co2
    public double getTotalco2(){
        FoodData food;
        double total=0;
        for(int i=0; i<userFoodData.size(); i++){
            food = userFoodData.get(i);
            total += food.getco2();
        }
        return total;
    }

    //get co2 due to that food item
    public double getFoodco2(String foodName){
        int index=foodNames.indexOf(foodName);
        FoodData food=userFoodData.get(index);
        return food.getco2();
    }

    public double getTotalco2perYear(){
        return getTotalco2()*52;
    }

    //calculates proportion of weekly co2 due to foodName
    public double getProportion(String foodName){
        int index=foodNames.indexOf(foodName);
        double totalco2= getTotalco2();
        double co2;
        FoodData food=userFoodData.get(index);
        co2= food.getco2();
        double proportion=co2/totalco2;
        return proportion;
    }

    public int getUserFrequency(String foodName){
        int index=foodNames.indexOf(foodName);
        int frequency=userFoodData.get(index).getFrequency();
        return frequency;
    }

    public void setFoodFrequency(String foodName, int frequency){
        int index=foodNames.indexOf(foodName);
        userFoodData.get(index).setFrequency(frequency);
    }

    //adders
    public void add(String foodName, FoodData foodObject) {
        userFoodData.add(foodObject);
        foodNames.add(foodName);
    }

    public FoodData getFoodObject(String foodName) {
        int index = foodNames.indexOf(foodName);
        return userFoodData.get(index);
    }

    public void setUserFoodData(List<FoodData> userFoodData) {
        this.userFoodData = userFoodData;
    }

    public List<String> getFoodNames() {
        return foodNames;
    }
    public List<FoodData> getUserFoodData(){return userFoodData;}

    public void setFoodNames(List<String> foodNames) {
        this.foodNames = foodNames;
    }

    public void setProteinPerMeal(double proteinPerMeal) {
        FoodData food;
        for(int i=0; i<userFoodData.size(); i++){
            if(foodNames.get(i)!="vegetables"){
                userFoodData.get(i).setGramsPerMeal(proteinPerMeal);
            }
        }
    }

    public void setVegPerMeal(double vegPerMeal){
        FoodData food;
        int index=foodNames.indexOf("vegetables");
        food=userFoodData.get(index);
        food.setGramsPerMeal(vegPerMeal);
    }

    public double getProteinPerMeal() {
        return proteinPerMeal;
    }

    public double getVegPerMeal() {
        return vegPerMeal;
    }

}
