package team6.cmpt276.greenfoodchallenge.classes;

import java.util.ArrayList;
import java.util.*;


public class UserData {
    private List<FoodData> userFoodData;
    private List<String> foodNames;
    private int proteinPerMeal;

    //important methods
    public double getTotalco2(){ //calculates total weekly co2
        FoodData food;
        double total=0;
        for(int i=0; i<userFoodData.size(); i++){
            food=userFoodData.get(i);
            total+=food.getco2(proteinPerMeal);
        }
        return total;
    }

    public double getProportion(String foodName){ //calculates proportion of weekly co2 due to foodName
        int index=foodNames.indexOf(foodName);
        double totalco2= getTotalco2();
        double co2=userFoodData.get(index).getco2(proteinPerMeal);
        double proportion=co2/totalco2;
        return proportion;
    }

    public int getUserFrequency(String foodName){
        int index=foodNames.indexOf(foodName);
        int frequency=userFoodData.get(index).getFrequency();
        return frequency;
    }

    public boolean foodExists(String foodName){ //checks if a food item is in the user data object
        return !(foodNames.indexOf(foodName)==-1);
    }

    public void printFoods(){ //just for programmer to check what food items are present
        for(int i=0; i<foodNames.size(); i++){
            System.out.println(foodNames.get(i));
        }
    }
    //adders
    public void add(String foodName, FoodData foodObject) {
        userFoodData.add(foodObject);
        foodNames.add(foodName);

    }

    //get,set, constructor

    public UserData(List<FoodData> userFoodData, List<String> foodNames, int proteinPerMeal) {
        this.userFoodData = userFoodData;
        this.foodNames = foodNames;
        this.proteinPerMeal=proteinPerMeal;
    }


    public FoodData getFoodObject(String foodName) {
        int index=foodNames.indexOf(foodName);
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


}
