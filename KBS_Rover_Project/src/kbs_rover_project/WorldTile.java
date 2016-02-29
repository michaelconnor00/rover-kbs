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
    private SurfaceDifficulty myDifficulty;
    private int xCoord;
    private int yCoord;
    
    //holds inclination values. This will be randomly generated between 0 and 45
    //John, you can pick a value to be deemed 'difficult'. Maybe >30 for dirt,
    //and >20 for rocks?
    //private int myInclination;
    //Dev note: getting rid of inclination for now
    
    //CONSTRUCTOR
    public WorldTile()
    {
        myType = TileType.DIRT;
        //myInclination = 0;
        
    }
    
    
    //sets tile attributes
    public void setWorldTile(TileType genType, int coordinate)
    {
        myType = genType;
        xCoord = coordinate % 8;
        yCoord = coordinate / 8;
        //myInclination = inclination;
        switch(myType)
        {
            case DIRT:
                myDifficulty = SurfaceDifficulty.PASSABLE;
                break;
                
            case ROCKS_LARGE:
                    myDifficulty = SurfaceDifficulty.BLOCKING;
                break;
                
                case ROCKS_SMALL:
                    myDifficulty = SurfaceDifficulty.DIFFICULT;
                break;
                
                case HOME_BASE:
                    myDifficulty = SurfaceDifficulty.PASSABLE;
                break;
                
                case SAMPLE_LOCATION:
                    myDifficulty = SurfaceDifficulty.PASSABLE;
                break;
                
                case CHASM:
                    myDifficulty = SurfaceDifficulty.BLOCKING;
                break;
                
                case CRUST_SAND:
                    myDifficulty = SurfaceDifficulty.PASSABLE;
                break;
                
                default:
                    myDifficulty = SurfaceDifficulty.PASSABLE;
                break;
        }
    }
    
    //Returns the type of this tile
    public TileType getMyType()
    {
        return myType;
    }
    
    public boolean isPassable(){
        return this.myDifficulty == SurfaceDifficulty.PASSABLE;
    }
    
    public boolean isBlocking(){
        return this.myDifficulty == SurfaceDifficulty.BLOCKING;
    }
    
    public boolean isDifficult(){
        return this.myDifficulty == SurfaceDifficulty.DIFFICULT;
    }
           
    //returns the difficulty of this tile
    public SurfaceDifficulty getMyDifficulty()
    {
        return myDifficulty;
    }
    
    //returns x coordinate of this tile
    public int getXCoord()
    {
        return xCoord;
    }
         
    
    //returns y coordinate of this tile
    public int getYCoord()
    {
        return yCoord;
    }
    
    
//    @Override
//    public boolean equals(Object obj){
//        boolean is_equal = obj instanceof WorldTile;
//        is_equal = is_equal && ((WorldTile) obj).getXCoord() == this.getXCoord();
//        is_equal = is_equal && ((WorldTile) obj).getYCoord() == this.getYCoord();
//        is_equal = is_equal && ((WorldTile) obj).getMyDifficulty() == this.getMyDifficulty();
//        is_equal = is_equal && ((WorldTile) obj).getMyType() == this.getMyType();
//        return is_equal;
//    }
//    
//    @Override
//    public int hashCode(){
//        int hash = 1;
//        hash = hash * 31 + this.myType.ordinal();
//        hash = hash * 31 + this.myDifficulty.ordinal();
//        hash = hash * 31 + this.getXCoord();
//        hash = hash * 31 + this.getYCoord();
//        return hash;
//    }
}
