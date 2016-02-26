/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs_rover_project;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Nick Taylor
 * This class holds an array of WorldTiles, and is in charge of populating it
 * and altering it as needed. 
 */
public class WorldModel {
    
    //PRIVATE MEMBER VARIABLES
    private WorldTile[] theWorld;
    Random RNG = new Random();
    
    //GETTERS & SETTERS
    
    /*
    Returns the array holding all WorldTiles
    */
    public WorldTile[] getWorld()
    {
        return theWorld;
    }
    
    /*
    Allows for the setting of a different WorldTile[]
    */
    public void setWorld(WorldTile[] newWorld)
    {
        theWorld = newWorld;
    }
    
    
    /*
    Initializes world by populating tiles
    */
    public void initWorld()
    {
       theWorld = new WorldTile[64];
    }
    
    
    public WorldTile[] generateRandomWorld(WorldTile[] blankWorld)
    {
        
        int worldSize = blankWorld.length;
        int blankTiles = worldSize;
        ArrayList<Integer> placementIndex = new ArrayList<Integer>(worldSize);
        
        //Fills ArrayList with all positions in the World array.
        for(int i = 0; i < worldSize; i++)
        {
            placementIndex.add(i);
        }
        
        //Pick location for home base:
        int baseLocation = RNG.nextInt(worldSize);
        blankWorld[baseLocation].setWorldTile(TileType.HOME_BASE, 0);
        blankTiles--;
        placementIndex.remove(baseLocation);
        
        
        //pick location for one sample
        //Dev note: we can add more samples later, if we want. Figured starting
        //with 1 was good enough for now.
        
        int sampleLocation = placementIndex.get(RNG.nextInt(placementIndex.size()));
        blankWorld[sampleLocation].setWorldTile(TileType.SAMPLE_LOCATION, 0);
        blankTiles--;
        placementIndex.remove(sampleLocation);
        
        
        return blankWorld;
        
    }
    
}
