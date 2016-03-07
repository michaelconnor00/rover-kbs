/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs_rover_project;

/**
 *
 * @author Nick Taylor
 * This class is used to hold the enumerated values for each different
 * type of terrain that is represented by the WorldTiles.
 * Also holds the filepath for the GUI image
 */
public enum TileType {
    
    DIRT("resources/dirt.png"), 
    ROCKS_LARGE("resources/largeRock.png"), 
    ROCKS_SMALL("resources/smallRock.png"), 
    HOME_BASE("resources/homeBase.png"), 
    SAMPLE_LOCATION("resources/goal.png"), 
    CHASM("resources/chasm.png"), 
    CRUST_SAND("resources/crustSand.png");
    
    private String imageName;
    
    TileType(String filename){
        this.imageName = filename;
    }
    
    public String getImageName(){
        return this.imageName;
    }
    
}
