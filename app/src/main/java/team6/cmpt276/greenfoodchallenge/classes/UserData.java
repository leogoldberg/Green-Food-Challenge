package team6.cmpt276.greenfoodchallenge.classes;

import java.io.Serializable;
import java.util.*;


public class UserData implements Serializable {
    private List<FoodData> userFoodData;
    private List<String> foodNames;
    private double proteinPerMeal;
    private double vegPerMeal;

    //important methods
    public double getTotalco2(){ //calculates total weekly co2
        FoodData food;
        double total=0;
        for(int i=0; i<userFoodData.size(); i++){
            food=userFoodData.get(i);
            total += food.getco2();
        }
        return total;
    }

    public double getFoodco2(String foodName){ //get co2 due to that food item
        int index=foodNames.indexOf(foodName);
        FoodData food=userFoodData.get(index);
        return food.getco2();
    }

    public double getTotalco2perYear(){
        return getTotalco2()*52;
    }

    public double getProportion(String foodName){ //calculates proportion of weekly co2 due to foodName
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
        FoodData temp= userFoodData.get(index); //question: is this temp variable necessary? can i modify the actual element?
        temp.setFrequency(frequency);
        userFoodData.set(index, temp);
    }

    //adders
    public void add(String foodName, FoodData foodObject) {
        userFoodData.add(foodObject);
        foodNames.add(foodName);
    }

    //get,set, constructor
    public UserData(int proteinPerMeal, int vegPerMeal){  //hardcoded constructor as requested
        this.userFoodData = new ArrayList<>();
        this.foodNames = new ArrayList<>();
        this.vegPerMeal=vegPerMeal;

        add("Beef", new FoodData(27, proteinPerMeal));
        add("Pork", new FoodData(12.1, proteinPerMeal));
        add("Chicken", new FoodData(6.9, proteinPerMeal));
        add("Fish", new FoodData(6.1, proteinPerMeal));
        add("Eggs",  new FoodData(4.8, proteinPerMeal));
        add("Beans", new FoodData(2, proteinPerMeal));
        add("Vegetables", new FoodData(2, vegPerMeal));
    }

    public UserData(List<FoodData> userFoodData, List<String> foodNames, int proteinPerMeal, int vegPerMeal) {
        this.userFoodData = userFoodData;
        this.foodNames = foodNames;
        this.vegPerMeal=vegPerMeal;
        this.proteinPerMeal=proteinPerMeal;
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

    public void setFoodNames(List<String> foodNames) {
        this.foodNames = foodNames;
    }

    public void setProteinPerMeal(double proteinPerMeal) {
        FoodData food;
        for(int i=0; i<userFoodData.size(); i++){
            if(foodNames.get(i)!="vegetables"){
                food=userFoodData.get(i);
                food.setGramsPerMeal(proteinPerMeal);
                userFoodData.set(i, food);
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
