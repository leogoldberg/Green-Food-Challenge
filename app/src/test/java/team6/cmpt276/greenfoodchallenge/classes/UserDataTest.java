package team6.cmpt276.greenfoodchallenge.classes;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserDataTest {

    @Test
    public void getTotalco2perYear() {
        UserData userTest = new UserData();
        userTest.setVegPerMeal(250);
        userTest.setProteinPerMeal(250);
        assertEquals(0,userTest.getTotalco2perYear(), 0.000001);
        userTest.setFoodFrequency("Chicken", 5);
        assertEquals(897000,userTest.getTotalco2perYear(), 0.000001);
    }


    @Test
    public void setFoodFrequency() {
        UserData userTest=new UserData();
        assertEquals(0,userTest.getBeefFrequency());
        assertEquals(0,userTest.getPorkFrequency());
        assertEquals(0,userTest.getChickenFrenquency());
        assertEquals(0,userTest.getFishFrequency());
        assertEquals(0,userTest.getEggFrequency());
        userTest.setFoodFrequency("Pork", 2);
        assertEquals("Beef got set to 2",0,userTest.getBeefFrequency());
        assertEquals(2,userTest.getPorkFrequency());
        assertEquals(0,userTest.getChickenFrenquency());
        assertEquals(0,userTest.getFishFrequency());
        assertEquals(0,userTest.getEggFrequency());
        userTest.setFoodFrequency("Eggs", 5);
        assertEquals(5,userTest.getEggFrequency());
    }


    @Test
    public void getFoodNames() {
        UserData userTest=new UserData();
        assertEquals("Beef", userTest.getFoodNames().get(0));
    }


    @Test
    public void getUserFoodData() {
        UserData userTest=new UserData();

        List<Integer> foodDataList= userTest.getUserFoodData();
        assertEquals(0,foodDataList.get(0), 0.00001);

        userTest.setBeanFrequency(3);
        foodDataList= userTest.getUserFoodData();
        assertEquals(3,foodDataList.get(5), 0.00001);
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
    public void setVegPerMeal() {
        UserData userTest=new UserData();
        userTest.setVegPerMeal(15);
        assertEquals(15, userTest.getVegPerMeal(),0.000001);
    }



}