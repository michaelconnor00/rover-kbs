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
    private TileType myType = TileType.DIRT;
    private SurfaceDifficulty myDifficulty;
    private int xCoord = 0;
    private int yCoord = 0;
    private int boardSize = 8;
    
    //holds inclination values. This will be randomly generated between 0 and 45
    //John, you can pick a value to be deemed 'difficult'. Maybe >30 for dirt,
    //and >20 for rocks?
    //private int myInclination;
    //Dev note: getting rid of inclination for now
    
    //CONSTRUCTORS
    public WorldTile(){}
    
    public WorldTile(TileType tt, int boardSize){
        this.myType = tt;
        this.boardSize = boardSize;
    }
    
    
    //sets tile attributes
    public void setWorldTile(TileType genType, int xCoord, int yCoord)
    {
        myType = genType;
        setXCoord(xCoord);
        setYCoord(yCoord);
        

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

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
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
    
    @Override
    public boolean equals(Object obj){
        boolean is_equal = obj instanceof WorldTile;
        is_equal = is_equal && ((WorldTile) obj).getXCoord() == this.getXCoord();
        is_equal = is_equal && ((WorldTile) obj).getYCoord() == this.getYCoord();
        is_equal = is_equal && ((WorldTile) obj).getMyDifficulty() == this.getMyDifficulty();
        is_equal = is_equal && ((WorldTile) obj).getMyType() == this.getMyType();
        return is_equal;
    }
    
    @Override
    public int hashCode(){
        int hash = 1;
        hash = hash * 31 + this.myType.ordinal();
//        hash = hash * 31 + this.myDifficulty.ordinal();
        hash = hash * 31 + this.getXCoord();
        hash = hash * 31 + this.getYCoord();
        return hash;
    }
}
