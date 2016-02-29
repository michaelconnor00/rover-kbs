/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs_rover_project;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author johnathan
 */
public class RobotTest {
    
    public RobotTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws InterruptedException {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp()throws InterruptedException {
        
        WorldModel grid3x3 = new WorldModel();
        
    }
    
    @After
    public void tearDown() {
    }


    
    @Test
    public void testOffBourdTiles()throws InterruptedException{
        System.out.println("test index out of bounds");
        WorldModel grid2x2 = new WorldModel();
        WorldTile start = new WorldTile();
        WorldTile rock = new WorldTile(TileType.ROCKS_LARGE);
        
        Robot instance = new Robot(grid2x2,start,null);
    }
    @Test
    public void testChooseMove() {
        System.out.println("chooseMove");
        Robot instance = null;
        instance.chooseMove();
        fail("The test case is a prototype.");
    }
}
