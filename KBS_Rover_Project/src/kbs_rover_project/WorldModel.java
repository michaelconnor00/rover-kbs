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

    //world dimensions. Due to GUI form, world will always be square
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
    
    //sets a specific tile in the world array
    public void setTile(int col, int row, WorldTile newTile)
    {
        theWorld[col][row] = newTile;
    }
    
    
    /*
    Initializes world by populating tiles with dirt
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
    
  
   
  
    
    
    //Generates an initial random world, with the number of initial
    //terrain tiles being a function of the board size
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
        int randomSmallRockNum = worldSize / 4;
        int randomLargeRockNum = worldSize / 8;
        int randomChasmNum = worldSize / 8;
        int randomCrustNum = worldSize / 4;
        
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
        //Design note: always bottom right			
        int sampleLocation = boardSize-1;
        blankWorld[sampleLocation][sampleLocation].setWorldTile(TileType.SAMPLE_LOCATION, sampleLocation, sampleLocation);
        blankTiles--;
        positions[worldSize-1] = false;
        
        //spawn small rocks
        randomTiles(smallRockNum, TileType.ROCKS_SMALL, positions, blankWorld, blankTiles);
        blankTiles -= smallRockNum;
        
        //spawn large rocks
        randomTiles(largeRockNum, TileType.ROCKS_LARGE, positions, blankWorld, blankTiles);
        blankTiles -= largeRockNum;
        
        //spawn chasms
        randomTiles(chasmNum, TileType.CHASM, positions, blankWorld, blankTiles);
        blankTiles -= chasmNum;
        
        //spawn crusty sand
        randomTiles(crustNum, TileType.CRUST_SAND, positions, blankWorld, blankTiles);
        blankTiles -= crustNum;
        
        setWorld(blankWorld);
        
    }
    
    //sets 'numOfType' tiles of type newType to random positions on the board
    private void randomTiles(
            int numOfType, TileType newType, 
            boolean[] positions, WorldTile[][] blankWorld,
            int blankTilesRemaining
        )
    {
        if (numOfType > blankTilesRemaining){
            numOfType = blankTilesRemaining;
        }
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
            blankWorld[row][col].setWorldTile(newType, col, row);
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
            while(!theWorld[row][col].getMyType().equals(TileType.DIRT) || 
                    nextTile != 1)
            {
                nextTile = RNG.nextInt(theWorld.length-3);
            }
            
            theWorld[row][col].setWorldTile(TileType.ROCKS_LARGE, nextTile%boardSize, nextTile/boardSize);
        }
    }
    
    //returns a specific tile in the world via coordinates
    //takes an integer for both x and y coordinate
    public WorldTile getTile(int col, int row)
    {
        return theWorld[row][col];
    }
    
}

