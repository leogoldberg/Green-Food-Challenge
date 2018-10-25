package team6.cmpt276.greenfoodchallenge.classes;

import java.util.ArrayList;

import team6.cmpt276.greenfoodchallenge.R;

public class PlanPicker {
    public PlanPicker(){};

    public ArrayList<Integer> getResource() {
        ArrayList<Integer> displayOptionList = new ArrayList<Integer>();
        //Adding option 1
        addResource(displayOptionList,"bean");
        addResource(displayOptionList,"chicken");
        addResource(displayOptionList,"pork");
        return displayOptionList;
    }

    public void plantBased() {

    }

    public void meatEater() {

    }

    public void lowMeat(ConsumptionClass suggestedConsumption, int userInput) {
        float newConsumption = suggestedConsumption.getProteinPerMeal() * userInput /100;
        suggestedConsumption.setProteinPerMeal(newConsumption);

    }

    private void addResource (ArrayList<Integer> displayOptionList, String protein){
        if (protein == "chicken"){
            displayOptionList.add(R.drawable.chicken);
            displayOptionList.add(R.string.chicken);
        }
        else if (protein == "pork") {
            displayOptionList.add(R.drawable.pork);
            displayOptionList.add(R.string.pork);
        }
        else if (protein == "fish") {
            displayOptionList.add(R.drawable.fish);
            displayOptionList.add(R.string.fish);
        }
        else if (protein == "egg") {
            displayOptionList.add(R.drawable.egg);
            displayOptionList.add(R.string.egg);
        }
        else {
            displayOptionList.add(R.drawable.bean);
            displayOptionList.add(R.string.bean);
        }
    }

}
