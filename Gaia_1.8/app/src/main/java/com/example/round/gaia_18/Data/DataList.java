package com.example.round.gaia_18.Data;

import java.util.ArrayList;

/**
 * Created by Round on 2017-09-06.
 */

public class DataList {

    private static ArrayList<Flower> flowers = new ArrayList<>();
    private static ArrayList<Plant> plants = new ArrayList<>();
    private static ArrayList<OverlayPlant> overlayPlants = new ArrayList<>();

    public DataList(ArrayList<Flower> flowers) {
        this.flowers = flowers;
    }

    public ArrayList<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(ArrayList<Flower> flowers) {
        this.flowers = flowers;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
    }

    public void addPlant(Plant plant){
        this.plants.add(plant);
    }

    public ArrayList<OverlayPlant> getOverlayPlants() {
        return overlayPlants;
    }

    public void setOverlayPlants(ArrayList<OverlayPlant> overlayPlants) {
        DataList.overlayPlants = overlayPlants;
    }

    public void addOverlayPlant(OverlayPlant overlayPlant){
        overlayPlants.add(overlayPlant);
    }

    public void compareFlowers( ){
        for(int i =0 ; i< plants.size() ; i++){
            for(int j = 0; j <flowers.size() ; j++){
                if(plants.get(i).getPlantNo() == flowers.get(j).getFlowerNo()){
                    flowers.get(j).setBuyType(true);
                    flowers.get(j).setLevel(plants.get(i).getLevel());
                }
            }
        }
    }
}