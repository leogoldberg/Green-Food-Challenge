package team6.cmpt276.greenfoodchallenge.classes;

public class Meal {
    public String userID;
    public String mealName;
    public String mealDescription;
    public String protein;
    public CharSequence restaurantName;
    public CharSequence address;
    public CharSequence city;
    public float rating;

    public Meal(){}

    public Meal(String userID,
                String mealName,
                String protein,
                CharSequence restaurantName,
                CharSequence address,
                CharSequence city) {
        this.userID = userID;
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        this.address = address;
        this.restaurantName = restaurantName;
        this.city = city;
        this.rating = rating;
        this. protein = protein;
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
