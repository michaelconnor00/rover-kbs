package kbs_rover_project;

import java.util.Random;
import javax.swing.JLabel;

/**
 *
 * @author Nick Taylor
 * This class holds an array of WorldTiles, and is in charge of populating it
 * and altering it as needed. 
 */
public class WorldModel {
    
    //PRIVATE MEMBER VARIABLES
    private WorldTile[][] theWorld;
    Random RNG = new Random();
    private WorldGUI gui;
    private int worldSize;
    private int boardSize;
    
    
    //CONSTRUCTORS
    //constructor that sets world size with parameter
    public WorldModel(int boardSize, boolean doInit) {
        this.boardSize = boardSize;
        this.worldSize = boardSize*boardSize;
        if(doInit){
            this.initWorld(boardSize);
        }
        
    }
    
    //GETTERS & SETTERS

    public int getBoardSize() {
        return boardSize;
    }

    public int getWorldDim() {
        // NOTE: this only works because the world is SQUARE
        return boardSize;
    }
    
    /*
    Returns the array holding all WorldTiles
    */
    public WorldTile[][] getWorld()
    {
        return theWorld;
    }
    
    /*
    Allows for the setting of a different WorldTile[]
    */
    public void setWorld(WorldTile[][] newWorld)
    {
        theWorld = newWorld;
    }
    
    
    public void setTile(int col, int row, WorldTile newTile)
    {
        theWorld[col][row] = newTile;
    }
    
    
    /*
    Initializes world by populating tiles
    */
    public void initWorld(int boardSize) 
    {
       theWorld = new WorldTile[boardSize][boardSize];
       WorldTile currentTile;
       for(int row = 0; row < boardSize; row++)
       {
           for(int col=0;col < boardSize; col++)
           {
           currentTile = new WorldTile(TileType.DIRT, col, row);
           currentTile.setWorldTile(TileType.DIRT, col ,row);
           theWorld[row][col] = currentTile;
           }
       }
       
    }
    
  
   
  
    
    
    //Works only on 64 size worlds
    public void generateRandomWorld()
    {
        WorldTile[][] blankWorld = theWorld;
        
        int blankTiles = worldSize;
        
        boolean[] positions = new boolean[worldSize];
        
        for (int i = 0; i < worldSize; i++) 
        {
            positions[i] = true;
	}
        
        
        //Pick location for home base:
        //Design note: always top left
        int baseLocation = 0;
        blankWorld[baseLocation][baseLocation].setWorldTile(TileType.HOME_BASE, 0,0);
        blankTiles--;
        positions[baseLocation] = false;
        
        
        //pick location for one sample
        //Dev note: we can add more samples later, if we want. Figured starting
        //with 1 was good enough for now.
        //Design note: always bottom right
				
        int sampleLocation = boardSize-1;
        blankWorld[sampleLocation][sampleLocation].setWorldTile(TileType.SAMPLE_LOCATION, sampleLocation, sampleLocation);
        blankTiles--;
        positions[sampleLocation] = false;
        
        
        //world generation variables
        //Adjust numbers to change chances of spawning each obstacle
        int randomSmallRockNum = worldSize / 10;
        int randomLargeRockNum = worldSize / 10;
        int randomChasmNum = worldSize / 10;
        int randomCrustNum = worldSize / 10;
        
        //spawn small rocks
        int smallRockNum = RNG.nextInt(randomSmallRockNum);
        int smallRockLocation;
        for(int i = 0; i < smallRockNum; i++)
        {
            smallRockLocation = RNG.nextInt(worldSize);
            while (positions[smallRockLocation] == false) 
            {
                smallRockLocation = RNG.nextInt(worldSize);
            }
            int col = smallRockLocation % boardSize;
            int row = smallRockLocation / boardSize;
            blankWorld[col][row].setWorldTile(TileType.ROCKS_SMALL, col, row);
            blankTiles--;
            positions[smallRockLocation] = false;
        }
        
        //spawn large rocks
        int largeRockNum = RNG.nextInt(randomLargeRockNum);
        int largeRockLocation;
        for(int i = 0; i < largeRockNum; i++)
        {
            largeRockLocation = RNG.nextInt(worldSize);
            while (positions[largeRockLocation] == false) 
            {
                largeRockLocation = RNG.nextInt(worldSize);
            }
            int col = largeRockLocation % boardSize;
            int row = largeRockLocation / boardSize;
            blankWorld[col][row].setWorldTile(TileType.ROCKS_LARGE, col, row);
            blankTiles--;
            positions[largeRockLocation] = false;
        }
        
        //spawn chasms
        int chasmNum = RNG.nextInt(randomChasmNum);
        int chasmLocation;
        for(int i = 0; i < chasmNum; i++)
        {
            chasmLocation = RNG.nextInt(worldSize);
            while (positions[chasmLocation] == false) 
            {
                chasmLocation = RNG.nextInt(worldSize);
            }
            int col = chasmLocation % boardSize;
            int row = chasmLocation / boardSize;
            blankWorld[col][row].setWorldTile(TileType.CHASM, col, row);
            blankTiles--;
            positions[chasmLocation] = false;
        }
        
        //spawn crusty sand
        int crustNum = RNG.nextInt(randomCrustNum);
        int crustLocation;
        for(int i = 0; i < crustNum; i++)
        {
            crustLocation = RNG.nextInt(worldSize);
            while (positions[crustLocation] == false) 
            {
                crustLocation = RNG.nextInt(worldSize);
            }
            int col = crustLocation % boardSize;
            int row = crustLocation / boardSize;
            blankWorld[col][row].setWorldTile(TileType.CRUST_SAND, col, row);
            blankTiles--;
            positions[crustLocation] = false;
        }
        
        //fill rest of map with dirt at random inclinations
        
        for(int row = 0; row < boardSize; row++)
        {
            for(int col = 0; col < boardSize; col++)
            {
                if(blankWorld[row][col].getMyType() == TileType.DIRT)
                {
                    blankWorld[row][col].setWorldTile(TileType.DIRT, col, row);
                }
            
                if((row*col == 1) || (row*col == worldSize-2))
                {
                    blankWorld[row][col].setWorldTile(TileType.DIRT, col, row);
                }
            }
            
        }
        
        setWorld(blankWorld);
        
    }
    
    /*
    Generates a world based on number of obstacles set in parameters
    */
    public void generateWorld(int smallRockNum, int largeRockNum, int chasmNum, int crustNum)
    {
        
        WorldTile[][] blankWorld = new WorldTile[boardSize][boardSize];
        int blankTiles = worldSize;
        
        boolean[] positions = new boolean[worldSize];
        
        for (int i = 0; i < worldSize; i++) 
        {
            positions[i] = true;
	}
        
        //refill map with dirt
        for(int row = 0; row < boardSize; row++)
        {
            for(int col = 0; col < boardSize; col++)
            {
                blankWorld[row][col] = new WorldTile(TileType.DIRT, col, row);
            }
        }
        
        
        //Pick location for home base:
        //Design note: always top left
        int baseLocation = 0;
        blankWorld[baseLocation][baseLocation].setWorldTile(TileType.HOME_BASE, 0, 0);
        blankTiles--;
        positions[baseLocation] = false;
        
        
        //pick location for one sample
        //Dev note: we can add more samples later, if we want. Figured starting
        //with 1 was good enough for now.
        //Design note: always bottom right			
        int sampleLocation = boardSize-1;
        blankWorld[sampleLocation][sampleLocation].setWorldTile(TileType.SAMPLE_LOCATION, sampleLocation, sampleLocation);
        blankTiles--;
        positions[worldSize-1] = false;
        
        //spawn small rocks
        randomTiles(smallRockNum, TileType.ROCKS_SMALL, positions, blankWorld);
        blankTiles -= smallRockNum;
        
        //spawn large rocks
        randomTiles(largeRockNum, TileType.ROCKS_LARGE, positions, blankWorld);
        blankTiles -= largeRockNum;
        
        //spawn chasms
        randomTiles(chasmNum, TileType.CHASM, positions, blankWorld);
        blankTiles -= chasmNum;
        
        //spawn crusty sand
        randomTiles(crustNum, TileType.CRUST_SAND, positions, blankWorld);
        blankTiles -= crustNum;
        
        setWorld(blankWorld);
        
    }
    
    private void randomTiles(
            int numOfType, TileType newType, 
            boolean[] positions, WorldTile[][] blankWorld
        )
    {
        //spawn crusty sand
        int ranLocation;
        for(int i = 0; i < numOfType; i++)
        {
            ranLocation = RNG.nextInt(worldSize);
            while (positions[ranLocation] == false) 
            {
                ranLocation = RNG.nextInt(worldSize);
            }
            int col = ranLocation % boardSize;
            int row = ranLocation / boardSize;
            blankWorld[col][row].setWorldTile(newType, col, row);
            positions[ranLocation] = false;
        }
    }
    
    
    /*
    causes a "rockslide" to occur, adding new large rock terrain 
    to add difficulty to a returning path
    */
    public void causeRockslide(int rocksToAdd)
    {
        for(int i = 0; i < rocksToAdd; i++)
        {
            int nextTile = RNG.nextInt(worldSize-3);
            int col = nextTile % boardSize;
            int row = nextTile / boardSize;
            //ensures the new rocks do not block the sample or home base
            while(!theWorld[col][row].getMyType().equals(TileType.DIRT) || 
                    nextTile != 1)
            {
                nextTile = RNG.nextInt(theWorld.length-3);
            }
            
            theWorld[col][row].setWorldTile(TileType.ROCKS_LARGE, nextTile%boardSize, nextTile/boardSize);
        }
    }
    
    
    /*
    @Override
    public String toString()
    {
        String worldString = "";
        TileType currentType;
        for(int i = 0; i < theWorld.length; i++)
        {
            if(i%8 == 0)
            {
                worldString = worldString + "\n";
            }
            currentType = theWorld[i].getMyType();
            switch(currentType)
            {
                case DIRT:
                    worldString = worldString + "[ ]";
                break;
                
                case ROCKS_LARGE:
                    worldString = worldString + "[L]";
                break;
                
                case ROCKS_SMALL:
                    worldString = worldString + "[S]";
                break;
                
                case HOME_BASE:
                    worldString = worldString + "[B]";
                break;
                
                case SAMPLE_LOCATION:
                    worldString = worldString + "[X]";
                break;
                
                case CHASM:
                    worldString = worldString + "[O]";
                break;
                
                case CRUST_SAND:
                    worldString = worldString + "[~]";
                break;
                
                default:
                    worldString = worldString + "[Q]";
                break;
                    
                   
                       
            }
        }
        
        return worldString;
    }
    */
    //returns a specific tile in the world via coordinates
    //takes an integer for both x and y coordinate
    public WorldTile getTile(int xCoord, int yCoord)
    {
        return theWorld[xCoord][yCoord];
    }
    
    //NOTE only works for board size 64, antiquated method
    public int getTilePosition(WorldTile tile)
    {
        int xCoord = tile.getCol();
        int yCoord = tile.getRow();
        
        int position = xCoord + (8 * yCoord);
        return position;
    }
    
    
    public int get1DPosition(int x, int y)
    {
        int xSize = (int)(Math.sqrt(theWorld.length));
        int position = x + (xSize * y);
        return position;
    }
    
}

