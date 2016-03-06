/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs_rover_project;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author johnathan
 */
public class RobotTest {
    
    private static WorldModel grid2x2;
    private static WorldModel grid3x3;
    private static WorldTile start;
    private static WorldTile rock;
    private static WorldTile dirt;
    private static WorldTile goal;
    
    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        grid2x2 = new WorldModel(4, true);
        
        grid3x3 = new WorldModel(9, true);
        
        start = new WorldTile(TileType.DIRT, 2);
        rock = new WorldTile(TileType.ROCKS_LARGE, 2);
        dirt = new WorldTile(TileType.DIRT, 2);
        goal = new WorldTile(TileType.DIRT, 2);
        
        
    }
    
    @Before
    public void setUp() {
        grid2x2.setTile(0, 0, start);
        grid2x2.setTile(1, 0, rock);
        grid2x2.setTile(0, 1, dirt);
        grid2x2.setTile(1, 1, goal);
    }
 
    @Test
    public void testMove(){
        // Simple Inference decision test.
        
        Robot robo = new Robot(grid2x2, start, goal);
        
        robo.chooseMove();
        
        WorldTile nextTile = robo.getCurrentPlace();
        
        int expX = 0;
        int expY = 1;
        
        assertEquals(expX, nextTile.getCol());
        assertEquals(expY, nextTile.getRow());
        
    }
    
    @Test
    public void testMoveBlocking(){
        // Simple Inference decision test.
        
        // Box in robot
        grid2x2.setTile(0, 1, rock);
        
        Robot robo = new Robot(grid2x2, start, goal);
        
        robo.chooseMove();
        
        WorldTile nextTile = robo.getCurrentPlace();
        
        int expX = 0;
        int expY = 0;
        
        assertEquals(expX, nextTile.getCol());
        assertEquals(expY, nextTile.getRow());
        
    }
    
    @Test
    public void testMoveReturn(){
        // Simple Inference decision test.
        
        // Box in robot
        grid2x2.setTile(1, 1, rock);
        
        
        Robot robo = new Robot(grid2x2, start, goal);
        
        robo.chooseMove();
        robo.chooseMove();
        
        WorldTile nextTile = robo.getCurrentPlace();
        
        int expX = 0;
        int expY = 0;
        
        assertEquals(expX, nextTile.getCol());
        assertEquals(expY, nextTile.getRow());
        
    }
        
}
