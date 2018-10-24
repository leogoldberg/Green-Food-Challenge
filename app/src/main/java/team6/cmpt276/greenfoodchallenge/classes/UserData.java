package team6.cmpt276.greenfoodchallenge.classes;

import java.util.*;


public class UserData {
    private List<FoodData> userFoodData;
    private List<String> foodNames;
    private int proteinPerMeal;
    private int vegPerMeal;

    //important methods
    public double getTotalco2(){ //calculates total weekly co2
        FoodData food;
        double total=0;
        for(int i=0; i<userFoodData.size(); i++){
            food=userFoodData.get(i);
            if(food.isProtein()) {
                total += food.getco2(proteinPerMeal);
            }
            else{
                total += food.getco2(vegPerMeal);
            }
        }
        return total;
    }

    public double getProportion(String foodName){ //calculates proportion of weekly co2 due to foodName
        int index=foodNames.indexOf(foodName);
        double totalco2= getTotalco2();
        double co2;
        FoodData food=userFoodData.get(index);
        if(food.isProtein()) {
            co2= food.getco2(proteinPerMeal);
        }
        else{
            co2= food.getco2(vegPerMeal);
        }
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

    public UserData(int proteinPerMeal, int vegPerMeal){  //hardcoded constructor as requested
        this.userFoodData=new ArrayList<>();
        this.proteinPerMeal=proteinPerMeal;
        this.vegPerMeal=vegPerMeal;
        add("beef", new FoodData(27, proteinPerMeal));
        add("pork", new FoodData(12.1, proteinPerMeal));
        add("chicken", new FoodData(6.9, proteinPerMeal));
        add("fish", new FoodData(6.1, proteinPerMeal));
        add("eggs",  new FoodData(4.8, proteinPerMeal));
        add("beans", new FoodData(2, proteinPerMeal));
        add("vegetables", new FoodData(2, vegPerMeal));
    }

    public UserData(List<FoodData> userFoodData, List<String> foodNames, int proteinPerMeal, int vegPerMeal) {
        this.userFoodData = userFoodData;
        this.foodNames = foodNames;
        this.proteinPerMeal=proteinPerMeal;
        this.vegPerMeal=vegPerMeal;
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
