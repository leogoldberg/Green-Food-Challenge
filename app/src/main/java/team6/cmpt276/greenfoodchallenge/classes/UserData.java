package team6.cmpt276.greenfoodchallenge.classes;

import java.util.ArrayList;
import java.util.*;


public class UserData {
    private List<FoodData> userFoodData;
    private List<String> foodNames;

    //important methods
    public double getTotalco2(){
        FoodData food;
        double total=0;
        for(int i=0; i<userFoodData.size(); i++){
            food=userFoodData.get(i);
            total+=food.getco2();
        }
        return total;
    }

    public double getProportion(String food_name){
        int index=foodNames.indexOf(food_name);
        double totalco2= getTotalco2();
        double co2=userFoodData.get(index).getco2();
        double proportion=co2/totalco2;
        return proportion;
    }

    //get,set, construct
    public UserData(List<FoodData> userFoodData, List<String> foodNames) {
        this.userFoodData = userFoodData;
        this.foodNames = foodNames;
    }

    public List<FoodData> getUserFoodData() {
        return userFoodData;
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
