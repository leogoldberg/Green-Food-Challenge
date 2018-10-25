package team6.cmpt276.greenfoodchallenge.classes;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import team6.cmpt276.greenfoodchallenge.R;

import static org.junit.Assert.*;

public class PlanPickerTest {


    @Test
    public void isVegetarian() {
        UserData testData = new UserData(250,250);
        testData.setFoodFrequency("Beef",0);
        testData.setFoodFrequency("Chicken",0);
        testData.setFoodFrequency("Pork",0);
        testData.setFoodFrequency("Fish",0);
        testData.setFoodFrequency("Eggs",0);
        testData.setFoodFrequency("Beans",5);

        PlanPicker testPlan= new PlanPicker(testData);
        assertTrue("Test is not vegetarian when data is",testPlan.isVegetarian());

        testData.setFoodFrequency("Eggs", 5);
        testPlan = new PlanPicker(testData);
        assertTrue("Test is not vegetarian when data is",testPlan.isVegetarian());

        testData.setFoodFrequency("Chicken", 5);
        testPlan = new PlanPicker(testData);
        assertFalse("Test Data is vegeratian when not",testPlan.isVegetarian());
    }

    @Test
    public void isVegan() {
        UserData testData = new UserData(250,250);
        testData.setFoodFrequency("Beef",0);
        testData.setFoodFrequency("Chicken",0);
        testData.setFoodFrequency("Pork",0);
        testData.setFoodFrequency("Fish",0);
        testData.setFoodFrequency("Eggs",0);
        testData.setFoodFrequency("Beans",5);

        PlanPicker testPlan= new PlanPicker(testData);
        assertTrue("Test is not vegan when data is",testPlan.isVegetarian());

        testData.setFoodFrequency("Eggs", 5);
        testPlan = new PlanPicker(testData);
        assertFalse("Test is not vegan when data is",testPlan.isVegetarian());
    }

    @Test
    public void getResource() {
        UserData testData = new UserData(250,250);
        testData.setFoodFrequency("Beef",0);
        testData.setFoodFrequency("Pork",0);
        testData.setFoodFrequency("Chicken",3);
        testData.setFoodFrequency("Fish",0);
        testData.setFoodFrequency("Eggs",0);
        testData.setFoodFrequency("Beans",5);

        PlanPicker testPlan= new PlanPicker(testData);
        ArrayList<Integer> displayListTest = testPlan.getResource();

        assertTrue("Length of display list wrong", displayListTest.size() == 9);
        assertTrue("First Option Choice is wrong", displayListTest.get(0) == 3);
        assertTrue("First Image Resource is wrong", displayListTest.get(1) == R.drawable.fish);
        assertTrue("First Text Resource is wrong", displayListTest.get(2) == R.string.fish);

        assertTrue("Second Option Choice is wrong", displayListTest.get(3) == 4);
        assertTrue("Second Image Resource is wrong", displayListTest.get(4) == R.drawable.egg);
        assertTrue("Second Text Resource is wrong", displayListTest.get(5) == R.string.egg);

        assertTrue("First Option Choice is wrong", displayListTest.get(6) == 5);
        assertTrue("First Image Resource is wrong", displayListTest.get(7) == R.drawable.bean);
        assertTrue("First Text Resource is wrong", displayListTest.get(8) == R.string.bean);


        testData.setFoodFrequency("Chicken", 0);
        testData.setFoodFrequency("Fish", 3);
        testPlan= new PlanPicker(testData);
        displayListTest = testPlan.getResource();
        assertTrue("Length of display list 2 wrong", displayListTest.size() == 6);
        assertTrue("First Option Choice is wrong", displayListTest.get(0) == 4);
        assertTrue("First Image Resource is wrong", displayListTest.get(1) == R.drawable.egg);
        assertTrue("First Text Resource is wrong", displayListTest.get(2) == R.string.egg);

        assertTrue("Second Option Choice is wrong", displayListTest.get(3) == 5);
        assertTrue("Second Image Resource is wrong", displayListTest.get(4) == R.drawable.bean);
        assertTrue("Second Text Resource is wrong", displayListTest.get(5) == R.string.bean);



    }

    @Test
    public void plantBased() {
        UserData testData = new UserData(250,250);
        testData.setFoodFrequency("Beef",8);
        testData.setFoodFrequency("Pork",7);
        testData.setFoodFrequency("Chicken",3);
        testData.setFoodFrequency("Fish",0);
        testData.setFoodFrequency("Eggs",0);
        testData.setFoodFrequency("Beans",5);

        PlanPicker testPlan= new PlanPicker(testData);
        testPlan.plantBased(testData);
        assertEquals("Beef frequency wrong", 0,testData.getUserFrequency("Beef"));
        assertEquals("Pork frequency wrong", 0,testData.getUserFrequency("Pork"));
        assertEquals("Chicken frequency wrong", 0,testData.getUserFrequency("Chicken"));
        assertEquals("Fish frequency wrong", 0,testData.getUserFrequency("Fish"));
        assertEquals("Egg frequency wrong", 0,testData.getUserFrequency("Eggs"));
        assertEquals("Beans frequency wrong", 23,testData.getUserFrequency("Beans"));

    }

    @Test
    public void meatEater() {
        UserData testData = new UserData(250,250);
        testData.setFoodFrequency("Beef",1);
        testData.setFoodFrequency("Pork",2);
        testData.setFoodFrequency("Chicken",3);
        testData.setFoodFrequency("Fish",0);
        testData.setFoodFrequency("Eggs",0);
        testData.setFoodFrequency("Beans",5);

        PlanPicker testPlan= new PlanPicker(testData);
        testPlan.meatEater(testData,2);
        assertEquals("Beef frequency wrong", 0,testData.getUserFrequency("Beef"));
        assertEquals("Pork frequency wrong", 0,testData.getUserFrequency("Pork"));
        assertEquals("Chicken frequency wrong", 6, testData.getUserFrequency("Chicken"));
        assertEquals("Fish frequency wrong", 0,testData.getUserFrequency("Fish"));
        assertEquals("Egg frequency wrong", 0,testData.getUserFrequency("Eggs"));
        assertEquals("Beans frequency wrong", 5,testData.getUserFrequency("Beans"));
    }
}