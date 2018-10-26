package team6.cmpt276.greenfoodchallenge.classes;

import org.junit.Test;

import static org.junit.Assert.*;

public class FoodDataTest {

    @Test
    public void cloneTest() {
      FoodData food=new FoodData(10,3);
      FoodData foodClone=food.clone();
      assertEquals(foodClone.getGramsPerMeal(), food.getGramsPerMeal(), 0.000001);
        assertEquals(foodClone.getCo2PerGrams(), food.getCo2PerGrams(), 0.000001);
    }

    @Test
    public void getGramsPerMeal() {
       FoodData food=new FoodData(10,3);
       assertEquals(3 , food.getGramsPerMeal(), 0.000001);
    }

    @Test
    public void setGramsPerMeal() {
        FoodData food=new FoodData(10,3);
        assertEquals(3 , food.getGramsPerMeal(), 0.000001);
        food.setGramsPerMeal(531);
        assertEquals(531 , food.getGramsPerMeal(), 0.000001);
        food.setGramsPerMeal(0);
        assertEquals(0 , food.getGramsPerMeal(), 0.000001);
    }

    @Test
    public void getCo2PerGrams() {
        FoodData food=new FoodData(10,3);
        assertEquals(10 , food.getCo2PerGrams(), 0.000001);
    }

    @Test
    public void getFrequency() {
        FoodData food=new FoodData(11, 10,3);
        assertEquals(11 , food.getFrequency());
    }

    @Test
    public void setFrequency() {
        FoodData food=new FoodData(11, 10,3);
        assertEquals(11 , food.getFrequency());
        food.setFrequency(200);
        assertEquals(200 , food.getFrequency());
    }

    @Test
    public void getco2() {
        FoodData food=new FoodData(11, 10,3);
        assertEquals(660 , food.getco2(),0.000001);
        food.setFrequency(0);
        assertEquals(0 , food.getco2(),0.000001);
    }
}