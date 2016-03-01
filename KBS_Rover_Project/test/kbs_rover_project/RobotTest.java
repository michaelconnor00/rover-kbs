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
    
    private WorldModel grid2x2;
    private WorldModel grid3x3;
    
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
        grid2x2 = new WorldModel();
        grid3x3 = new WorldModel();
        
    }
    
    @After
    public void tearDown() {
    }


    
    @Test
    public void testOffBourdTiles()throws InterruptedException{
        System.out.println("test index out of bounds exeption handiling");
        grid2x2.initWorld(4);
        WorldTile start = new WorldTile(4);
        WorldTile rock = new WorldTile(4);
        WorldTile place = new WorldTile(4);
        WorldTile goal = new WorldTile(4);
        grid2x2.setTile(0, 0, start);
        grid2x2.setTile(1, 0, place);
        grid2x2.setTile(0, 1, place);
        grid2x2.setTile(1, 1, goal);
        Robot instance = new Robot(grid2x2,start,goal);
        System.out.println(instance.getCurrentPlace().getXCoord()+","+instance.getCurrentPlace().getYCoord());
        System.out.println(grid2x2.getTile(0,0).toString()+" grid 0,0");
        /*instance.chooseMove();
        System.out.println(instance.options.toString());
        
        if(instance.getCurrentPlace()==null){
            fail("should not choose off grid");
        }*/
        
    }
    
    
    @Test
    public void testMax() {
        int max = 0;
        double maxFound=0;
        double[] scores = {2.5,100.0,50.0,75.0};
        for (int i = 0; i < 4; i++) {
            if (scores[i] > maxFound) {
                maxFound=scores[i];
                max = i;
            }
        }
        if(max!=1){
           fail("expected value is 1"); 
        }
        
    }
        
    
}
