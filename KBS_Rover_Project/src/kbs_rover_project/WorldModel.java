/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs_rover_project;

/**
 *
 * @author Nick Taylor
 * This class holds an array of WorldTiles, and is in charge of populating it
 * and altering it as needed. 
 */
public class WorldModel {
    
    //PRIVATE MEMBER VARIABLES
    private WorldTile[] theWorld;
    
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
        /**
         * Design thoughts: do we want the base and surrounding initial zone to always
         * be the same, then randomize from there?
         */
    }
    
}
