package team6.cmpt276.greenfoodchallenge.classes;

public class Meal {
    public String userID;
    public String mealName;
    public String mealDescription;
    public String protein;
    public String fileName;
    public CharSequence restaurantName;
    public CharSequence address;
    public CharSequence city;
    public float rating;

    public Meal(){}

    public Meal(String userID, String mealName, String protein,
                CharSequence restaurantName, CharSequence address, CharSequence city,
                String fileName) {
        this.userID = userID;
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.address = address;
        this.restaurantName = restaurantName;
        this.city = city;
        this.rating = rating;
        this.protein = protein;
        this.fileName = fileName;
    }

    public String getProtein(){
        return protein;
    }

    public void setProtein(String protein){
        this.protein = protein;
    }

    public CharSequence getRestaurantName(){
        return restaurantName;
    }

    public void setRestaurantName(CharSequence restaurantName){
        this.restaurantName = restaurantName;
    }

    public CharSequence getAddress(){
        return address;
    }

    public void setAddress(CharSequence address){
        this.address = address;
    }

    public String getMealName(){
        return mealName;
    }

    public void setMealName(String mealName){
        this.mealName = mealName;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }
}
