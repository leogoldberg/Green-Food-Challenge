package team6.cmpt276.greenfoodchallenge.classes;

import java.util.ArrayList;

import team6.cmpt276.greenfoodchallenge.R;

/**
 * This class will give dynamic resources to populate the front end based on previous user input
 * It will also calculate the new diet based and return the new suggested consumption for user
 */
public class PlanPicker {

    private ArrayList<String> proteinList;
    private ArrayList<Integer> proteinFrequency;

    /**
     * Default constructor for PlanPicker Class, take in the frequency of proteins in the current diet
     * @param currentConsumption object that holds users' parameters for the current diet
     */
    public PlanPicker(UserData currentConsumption){
        proteinList = new ArrayList<>();
        proteinList.add("Beef");
        proteinList.add("Pork");
        proteinList.add("Chicken");
        proteinList.add("Fish");
        proteinList.add("Eggs");
        proteinList.add("Beans");

        proteinFrequency = new ArrayList<>();
        proteinFrequency.add(currentConsumption.getUserFrequency("Beef"));
        proteinFrequency.add(currentConsumption.getUserFrequency("Pork"));
        proteinFrequency.add(currentConsumption.getUserFrequency("Chicken"));
        proteinFrequency.add(currentConsumption.getUserFrequency("Fish"));
        proteinFrequency.add(currentConsumption.getUserFrequency("Eggs"));
        proteinFrequency.add(currentConsumption.getUserFrequency("Beans"));
    };


    /**
     * Check if the user is vegetarian
     * @return boolean, true if user only eat eggs and beans
     */
    public boolean isVegetarian() {
        int indexFirstProtein = findFirstNonZeroProtein();

        if (indexFirstProtein >= 4) {
            return true;
        } else{
            return false;
        }
    }

    /**
     * Get the resources id (image, text and option name)
     * @return an ArrayList of integer that contains the resources id of the options based on user's previous input
     */
    public ArrayList<Integer> getResource() {
        int indexFirstProtein = findFirstNonZeroProtein();

        ArrayList<Integer> displayOptionList = new ArrayList<>();

        //If there are more than 3 options available, adding the second, third and forth protein as an option in the list
        if (indexFirstProtein < 3 ) {
            addResource(displayOptionList, proteinList.get(indexFirstProtein + 1));
            addResource(displayOptionList, proteinList.get(indexFirstProtein + 2));
            addResource(displayOptionList, proteinList.get(indexFirstProtein + 3));
        }
        // If not, only show two relevance ones
        else {
            addResource(displayOptionList, proteinList.get(indexFirstProtein + 1));
            addResource(displayOptionList, proteinList.get(indexFirstProtein + 2));
        }

        return displayOptionList;
    }

    /**
     * Calculate the new consumption based on the plant based diet (set all the protein to only beans)
     * @param suggestedConsumption object that holds users' parameters for the diet
     * @return a userdata object that contains all the information about the new diet
     */
    public UserData plantBased(UserData suggestedConsumption) {
        int totalProteinConsumption = 0;

        for (int index = 0; index < proteinFrequency.size(); index++) {
            totalProteinConsumption += proteinFrequency.get(index);
        }

        suggestedConsumption.setFoodFrequency("Beans", totalProteinConsumption);
        suggestedConsumption.setFoodFrequency("Beef", 0);
        suggestedConsumption.setFoodFrequency("Pork", 0);
        suggestedConsumption.setFoodFrequency("Chicken", 0);
        suggestedConsumption.setFoodFrequency("Fish", 0);
        suggestedConsumption.setFoodFrequency("Eggs", 0);

        return suggestedConsumption;

    }

    /**
     * Calculate the new consumption based on the meat eater diet (distribute the frequency of higher CO2 food to user's chosen protein)
     * @param suggestedConsumption object that holds users' parameters for the diet
     * @return a userdata object that contains all the information about the new diet
     */
    public UserData meatEater(UserData suggestedConsumption, int proteinChoice) {
        int frequencyCount = 0;

        for(int index = 0; index < proteinChoice; index++) {
            frequencyCount += proteinFrequency.get(index);
            suggestedConsumption.setFoodFrequency(proteinList.get(index),0);
        }

        suggestedConsumption.setFoodFrequency(proteinList.get(proteinChoice),frequencyCount + proteinFrequency.get(proteinChoice));

        return suggestedConsumption;
    }

    /**
     * Sort from the highest CO2 protein to lowest and find the first protein that is non-zero in frequency
     * @return index of the first non-zero protein
     */
    private int findFirstNonZeroProtein () {
        int index = 0;
        while (index < proteinFrequency.size() && proteinFrequency.get(index) == 0) {
            index++;
        }
        return index;
    }

    /**
     * Add the resources to the list of displays.
     * @param displayOptionList object that holds resources id and option name of the chosen protein
     * @param protein the name of the protein that would be added to the list
     */
    private void addResource (ArrayList<Integer> displayOptionList, String protein){
        if (protein == "Chicken"){
            displayOptionList.add(2);
            displayOptionList.add(R.drawable.chicken);
            displayOptionList.add(R.string.chicken);
        } else if (protein == "Pork") {
            displayOptionList.add(1);
            displayOptionList.add(R.drawable.pork);
            displayOptionList.add(R.string.pork);
        } else if (protein == "Fish") {
            displayOptionList.add(3);
            displayOptionList.add(R.drawable.fish);
            displayOptionList.add(R.string.fish);
        } else if (protein == "Eggs") {
            displayOptionList.add(4);
            displayOptionList.add(R.drawable.egg);
            displayOptionList.add(R.string.egg);
        } else {
            displayOptionList.add(5);
            displayOptionList.add(R.drawable.bean);
            displayOptionList.add(R.string.bean);
        }
    }

}
