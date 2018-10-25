package team6.cmpt276.greenfoodchallenge.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class ConsumptionClass implements Serializable{
//    private ArrayList<Integer> frequency;
//    private ArrayList<Integer> cO2Consumption;
    private float proteinPerMeal;
    private float vegPerMeal;

    public ConsumptionClass(){
        proteinPerMeal = 100;
        vegPerMeal = 100;
    }

    public float getProteinPerMeal() {
        return proteinPerMeal;
    }

    public void setProteinPerMeal(float proteinPerMeal) {
        this.proteinPerMeal = proteinPerMeal;
    }

    public float getVegPerMeal() {
        return vegPerMeal;
    }

    public void setVegPerMeal(float vegPerMeal) {
        this.vegPerMeal = vegPerMeal;
    }
}
