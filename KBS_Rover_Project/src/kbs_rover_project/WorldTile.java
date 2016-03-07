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
    private int col = 0;
    private int row = 0;
    
    
    //CONSTRUCTOR
    public WorldTile(TileType tt, int col, int row){
        this.myType = tt;
        this.col = col;
        this.row = row;
    }
    
    
    //sets tile attributes
    public void setWorldTile(TileType genType, int col, int row)
    {
        myType = genType;
        setCol(col);
        setRow(row);
        

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

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }
    
    //returns col coordinate of this tile
    public int getCol()
    {
        return col;
    }
    
    //returns row coordinate of this tile
    public int getRow()
    {
        return row;
    }
    
    @Override
    public boolean equals(Object obj){
        boolean is_equal = obj instanceof WorldTile;
        is_equal = is_equal && ((WorldTile) obj).getCol() == this.getCol();
        is_equal = is_equal && ((WorldTile) obj).getRow() == this.getRow();
        is_equal = is_equal && ((WorldTile) obj).getMyDifficulty() == this.getMyDifficulty();
        is_equal = is_equal && ((WorldTile) obj).getMyType() == this.getMyType();
        return is_equal;
    }
    
    @Override
    public int hashCode(){
        int hash = 1;
        hash = hash * 31 + this.myType.ordinal();
        hash = hash * 31 + this.getCol();
        hash = hash * 31 + this.getRow();
        return hash;
    }
}
