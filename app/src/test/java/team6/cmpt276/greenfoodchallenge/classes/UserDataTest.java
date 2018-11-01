package team6.cmpt276.greenfoodchallenge.classes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserDataTest {

    @Test
    public void getTotalco2perYear() {
        UserData userTest=new UserData(3,  5);
        assertEquals(0,userTest.getTotalco2perYear(), 0.000001);
        userTest.setFoodFrequency("Chicken", 5);

        userTest.setFoodFrequency("Pork", 7);
    }

    @Test
    public void getProportion() {
        UserData userTest=new UserData(3,  5);
        userTest.setFoodFrequency("Pork", 2);
        assertEquals(1.0, userTest.getProportion("Pork"),0.000001);
        userTest.setFoodFrequency("Chicken", 2);
        assertEquals(0.63684210, userTest.getProportion("Pork"),0.000001);
    }

    @Test
    public void getUserFrequency() {
        UserData userTest=new UserData(3,  5);
        assertEquals(0,userTest.getUserFrequency("Chicken"));
        userTest.setFoodFrequency("Pork", 2);
        assertEquals(2,userTest.getUserFrequency("Pork"));
        userTest.setFoodFrequency("Chicken", 3);
        assertEquals(3,userTest.getUserFrequency("Chicken"));
    }

    @Test
    public void setFoodFrequency() {
        UserData userTest=new UserData(3,  5);
        assertEquals(0,userTest.getUserFrequency("Pork"));
        userTest.setFoodFrequency("Pork", 2);
        assertEquals(2,userTest.getUserFrequency("Pork"));
    }

    @Test
    public void add() {
        UserData userTest=new UserData();
        userTest.add("Chicken", new FoodData(3, 5));
        List<FoodData> foodDataList= userTest.getUserFoodData();
        assertEquals(3,foodDataList.get(0).getCo2PerGrams(), 0.00001);
        assertEquals("Chicken", userTest.getFoodNames().get(0));
    }

    @Test
    public void getFoodNames() {
        UserData userTest=new UserData();
        userTest.add("Chicken", new FoodData(3, 5));
        assertEquals("Chicken", userTest.getFoodNames().get(0));
    }


    @Test
    public void getUserFoodData() {
        UserData userTest=new UserData();
        userTest.add("Chicken", new FoodData(3, 5));
        List<FoodData> foodDataList= userTest.getUserFoodData();
        assertEquals(3,foodDataList.get(0).getCo2PerGrams(), 0.00001);
    }

    @Test
    public void setProteinPerMeal() {
        UserData userTest=new UserData();
        userTest.setProteinPerMeal(30.5);
        assertEquals(30.5, userTest.getProteinPerMeal(),0.000001);
    }

    @Test
    public void getProteinPerMeal() {
        UserData userTest=new UserData();
        userTest.setProteinPerMeal(11.353);
        assertEquals(11.353, userTest.getProteinPerMeal(),0.000001);
    }

    @Test
    public void getVegPerMeal() {
        UserData userTest=new UserData(11.3, 15);
        assertEquals(15, userTest.getVegPerMeal(),0.000001);
    }

    @Test
    public void getTotalFrequency() {
        UserData userTest=new UserData(11.3, 15);
        userTest.setFoodFrequency("Pork", 2);
        userTest.setFoodFrequency("Chicken", 3);
        assertEquals(5, userTest.getTotalFrequency());
    }

    @Test
    public void copyFoodData() {
        List<FoodData> testFood=new ArrayList<>();
        testFood.add(new FoodData(3,4));
        testFood.add(new FoodData(3,4));
        UserData userTest=new UserData();
        List<FoodData> listFood=userTest.copyFoodData(testFood);
        assertEquals(testFood.get(0).getCo2PerGrams(), listFood.get(0).getCo2PerGrams(),0.0001);
    }
}