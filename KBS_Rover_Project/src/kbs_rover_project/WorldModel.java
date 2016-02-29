/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    
    //CONSTRUCTOR
    //sets world size to static 64
    public WorldModel() throws InterruptedException
    {
        initWorld(64);
    }
    
    //PRIVATE MEMBER VARIABLES
    private WorldTile[] theWorld;
    Random RNG = new Random();
    private WorldGUI gui;
    private Robot rover;
    
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
    public void initWorld(int worldSize) throws InterruptedException
    {
       theWorld = new WorldTile[worldSize];
       for(int i = 0; i < theWorld.length; i++)
       {
           theWorld[i] = new WorldTile();
       }
       
       generateRandomWorld();
       
       WorldGUI.runGUI();
       Thread.currentThread().sleep(1000);
       gui = WorldGUI.getGUI();
       updateTileIcons();
    }
    
    
    public void updateTileIcons()
    {
        JLabel[] tiles = gui.getTiles();
        TileType currentType;
        
        for(int i = 0; i < theWorld.length; i++)
        {
            currentType = theWorld[i].getMyType();
            
            switch(currentType)
            {
                case DIRT: gui.setTileIcon(tiles[i], currentType); break;
                case ROCKS_LARGE: gui.setTileIcon(tiles[i], currentType); break;
                case ROCKS_SMALL: gui.setTileIcon(tiles[i], currentType); break;
                case HOME_BASE: gui.setTileIcon(tiles[i], currentType); break;
                case SAMPLE_LOCATION: gui.setTileIcon(tiles[i], currentType); break;
                case CHASM: gui.setTileIcon(tiles[i], currentType); break;
                case CRUST_SAND: gui.setTileIcon(tiles[i], currentType); break;
            }
        }
    }
    
    
    public void updateRoverLocation()
    {
        JLabel[] tiles = gui.getTiles();
        int roverLocation = getTilePosition(rover.getCurrentPlace());
        gui.setRoverLocation(tiles[roverLocation]);
    }
    
    public void generateRandomWorld()
    {
        WorldTile[] blankWorld = theWorld;
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
        blankWorld[baseLocation].setWorldTile(TileType.HOME_BASE, baseLocation);
        blankTiles--;
        positions[baseLocation] = false;
        
        
        //pick location for one sample
        //Dev note: we can add more samples later, if we want. Figured starting
        //with 1 was good enough for now.
        //Design note: always bottom right
				
        int sampleLocation = 63;
        blankWorld[sampleLocation].setWorldTile(TileType.SAMPLE_LOCATION, sampleLocation);
        blankTiles--;
        positions[sampleLocation] = false;
        
        
        //world generation variables
        //Adjust numbers to change chances of spawning each obstacle
        int randomSmallRockNum = blankTiles / 4;
        int randomLargeRockNum = blankTiles / 4;
        int randomChasmNum = blankTiles / 8;
        int randomCrustNum = blankTiles / 4;
        
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
            blankWorld[smallRockLocation].setWorldTile(TileType.ROCKS_SMALL, smallRockLocation);
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
            blankWorld[largeRockLocation].setWorldTile(TileType.ROCKS_LARGE, largeRockLocation);
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
            blankWorld[chasmLocation].setWorldTile(TileType.CHASM, chasmLocation);
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
            blankWorld[crustLocation].setWorldTile(TileType.CRUST_SAND, crustLocation);
            blankTiles--;
            positions[crustLocation] = false;
        }
        
        //fill rest of map with dirt at random inclinations
        
        for(int i = 0; i < worldSize; i++)
        {
            if(blankWorld[i].getMyType() == TileType.DIRT)
            {
                blankWorld[i].setWorldTile(TileType.DIRT, i);
            }
            
            if((i == 1) || (i == 8) || (i == 55) || (i == 62))
            {
                blankWorld[i].setWorldTile(TileType.DIRT, i);
            }
            
        }
        
        setWorld(blankWorld);
        
    }
    
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
    
    //returns a specific tile in the world via coordinates
    //takes an integer for both x and y coordinate
    public WorldTile getTile(int xCoord, int yCoord)
    {
        int coordLocation = xCoord + (8 * yCoord);
        return theWorld[coordLocation];
    }
    
    public int getTilePosition(WorldTile tile)
    {
        int xCoord = tile.getXCoord();
        int yCoord = tile.getYCoord();
        
        int position = xCoord + (8 * yCoord);
        return position;
    }
    
}

