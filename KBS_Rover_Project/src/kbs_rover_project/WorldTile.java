/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs_rover_project;

/**
 *
 * @author Nick Taylor
 * This class is the constructor of a WorldTile object, of which an array is used to
 * represent the world the rover traverses. Each WorldTile holds various 
 * attributes which are taken into consideration for difficulty cost estimation.
 */
public class WorldTile {
    
    //PRIVATE MEMBER VARIABLES
    private TileType myType;
    
    //holds inclination values. This will be randomly generated between 0 and 45
    //John, you can pick a value to be deemed 'difficult'. Maybe >30 for dirt,
    //and >20 for rocks?
    private int myInclination;
    
    //CONSTRUCTOR
    public WorldTile()
    {
        myType = TileType.DIRT;
        myInclination = 0;
    }
    
    
    //sets tile attributes
    public void setWorldTile(TileType genType, int inclination)
    {
        myType = genType;
        myInclination = inclination;
    }
    
    //Returns the type of this tile
    public TileType getMyType()
    {
        return myType;
    }
           
    
}
