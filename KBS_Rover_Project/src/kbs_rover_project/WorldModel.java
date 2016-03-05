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
    
    
    public void setTile(int xCoord, int yCoord, WorldTile newTile)
    {
        theWorld[xCoord][yCoord] = newTile;
    }
    
    
    /*
    Initializes world by populating tiles
    */
    public void initWorld(int boardSize) 
    {
       theWorld = new WorldTile[boardSize][boardSize];
       WorldTile currentTile;
       for(int i = 0; i < boardSize; i++)
       {
           for(int j=0;j < boardSize; j++)
           {
           currentTile = new WorldTile(TileType.DIRT, boardSize); // defaults to size 8
           currentTile.setWorldTile(TileType.DIRT, i,j);
           theWorld[i][j] = currentTile;
           }
       }
       
    }
    
  
   
  
    
    
    //Works only on 64 size worlds
    public void generateRandomWorld()
    {
        WorldTile[][] blankWorld = theWorld;
        int worldSize = blankWorld.length;
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
				
        int sampleLocation = worldSize-1;
        blankWorld[sampleLocation][sampleLocation].setWorldTile(TileType.SAMPLE_LOCATION, sampleLocation, sampleLocation);
        blankTiles--;
        positions[sampleLocation] = false;
        
        
        //world generation variables
        //Adjust numbers to change chances of spawning each obstacle
        int randomSmallRockNum = boardSize / 4;
        int randomLargeRockNum = boardSize / 4;
        int randomChasmNum = boardSize / 4;
        int randomCrustNum = boardSize / 4;
        
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
            int xCoord = smallRockLocation % boardSize;
            int yCoord = smallRockLocation / boardSize;
            blankWorld[xCoord][yCoord].setWorldTile(TileType.ROCKS_SMALL, xCoord, yCoord);
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
            int xCoord = largeRockLocation % boardSize;
            int yCoord = largeRockLocation / boardSize;
            blankWorld[xCoord][yCoord].setWorldTile(TileType.ROCKS_LARGE, xCoord, yCoord);
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
            int xCoord = chasmLocation % boardSize;
            int yCoord = chasmLocation / boardSize;
            blankWorld[xCoord][yCoord].setWorldTile(TileType.CHASM, xCoord, yCoord);
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
            int xCoord = crustLocation % boardSize;
            int yCoord = crustLocation / boardSize;
            blankWorld[xCoord][yCoord].setWorldTile(TileType.CRUST_SAND, xCoord, yCoord);
            blankTiles--;
            positions[crustLocation] = false;
        }
        
        //fill rest of map with dirt at random inclinations
        
        for(int i = 0; i < boardSize; i++)
        {
            for(int j = 0; j < boardSize; j++)
            {
                if(blankWorld[i][j].getMyType() == TileType.DIRT)
                {
                    blankWorld[i][j].setWorldTile(TileType.DIRT, i,j);
                }
            
                if((i*j == 1) || (i*j == worldSize-2))
                {
                    blankWorld[i][j].setWorldTile(TileType.DIRT, i,j);
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
        
        WorldTile[][] blankWorld = theWorld;
        int worldSize = blankWorld.length;
        int blankTiles = worldSize;
        
        boolean[] positions = new boolean[worldSize];
        
        for (int i = 0; i < worldSize; i++) 
        {
            positions[i] = true;
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
				
        int sampleLocation = worldSize-1;
        blankWorld[sampleLocation][sampleLocation].setWorldTile(TileType.SAMPLE_LOCATION, sampleLocation, sampleLocation);
        blankTiles--;
        positions[sampleLocation] = false;
        
        //spawn small rocks
        int smallRockLocation;
        for(int i = 0; i < smallRockNum; i++)
        {
            smallRockLocation = RNG.nextInt(worldSize);
            while (positions[smallRockLocation] == false) 
            {
                smallRockLocation = RNG.nextInt(worldSize);
            }
            int xCoord = smallRockLocation % boardSize;
            int yCoord = smallRockLocation / boardSize;
            blankWorld[xCoord][yCoord].setWorldTile(TileType.ROCKS_SMALL, xCoord, yCoord);
            blankTiles--;
            positions[smallRockLocation] = false;
        }
        
        //spawn large rocks
        int largeRockLocation;
        for(int i = 0; i < largeRockNum; i++)
        {
            largeRockLocation = RNG.nextInt(worldSize);
            while (positions[largeRockLocation] == false) 
            {
                largeRockLocation = RNG.nextInt(worldSize);
            }
            int xCoord = largeRockLocation % boardSize;
            int yCoord = largeRockLocation / boardSize;
            blankWorld[xCoord][yCoord].setWorldTile(TileType.ROCKS_LARGE, xCoord, yCoord);
            blankTiles--;
            positions[largeRockLocation] = false;
        }
        
        //spawn chasms
        int chasmLocation;
        for(int i = 0; i < chasmNum; i++)
        {
            chasmLocation = RNG.nextInt(worldSize);
            while (positions[chasmLocation] == false) 
            {
                chasmLocation = RNG.nextInt(worldSize);
            }
            int xCoord = chasmLocation % boardSize;
            int yCoord = chasmLocation / boardSize;
            blankWorld[xCoord][yCoord].setWorldTile(TileType.CHASM, xCoord, yCoord);
            blankTiles--;
            positions[chasmLocation] = false;
        }
        
        //spawn crusty sand
        int crustLocation;
        for(int i = 0; i < crustNum; i++)
        {
            crustLocation = RNG.nextInt(worldSize);
            while (positions[crustLocation] == false) 
            {
                crustLocation = RNG.nextInt(worldSize);
            }
            int xCoord = crustLocation % boardSize;
            int yCoord = crustLocation / boardSize;
            blankWorld[xCoord][yCoord].setWorldTile(TileType.CRUST_SAND, xCoord, yCoord);
            blankTiles--;
            positions[crustLocation] = false;
        }
        
        //fill rest of map with dirt at random inclinations
        
        for(int i = 0; i < boardSize; i++)
        {
            for(int j = 0; j < boardSize; j++)
            {
                if(blankWorld[i][j].getMyType() == TileType.DIRT)
                {
                    blankWorld[i][j].setWorldTile(TileType.DIRT, i,j);
                }
            
                if((i*j == 1) || (i*j == worldSize-2))
                {
                    blankWorld[i][j].setWorldTile(TileType.DIRT, i,j);
                }
            }
            
        }
        
        setWorld(blankWorld);
        
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
            int xCoord = nextTile % boardSize;
            int yCoord = nextTile / boardSize;
            //ensures the new rocks do not block the sample or home base
            while(!theWorld[xCoord][yCoord].getMyType().equals(TileType.DIRT) || 
                    nextTile != 1)
            {
                nextTile = RNG.nextInt(theWorld.length-3);
            }
            
            theWorld[xCoord][yCoord].setWorldTile(TileType.ROCKS_LARGE, nextTile%boardSize, nextTile/boardSize);
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
        int xCoord = tile.getXCoord();
        int yCoord = tile.getYCoord();
        
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

