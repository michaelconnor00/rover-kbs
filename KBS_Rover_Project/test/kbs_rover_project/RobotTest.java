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
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testSetWorld() {
        System.out.println("setWorld");
        WorldModel t = null;
        Robot instance = null;
        instance.setWorld(t);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetGoal() {
        System.out.println("setGoal");
        WorldTile t = null;
        Robot instance = null;
        instance.setGoal(t);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetCurrentPlace() {
        System.out.println("setCurrentPlace");
        WorldTile t = null;
        Robot instance = null;
        instance.setCurrentPlace(t);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetLastPlace() {
        System.out.println("setLastPlace");
        WorldTile t = null;
        Robot instance = null;
        instance.setLastPlace(t);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetLastPlace() {
        System.out.println("getLastPlace");
        Robot instance = null;
        WorldTile expResult = null;
        WorldTile result = instance.getLastPlace();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetCurrentPlace() {
        System.out.println("getCurrentPlace");
        Robot instance = null;
        WorldTile expResult = null;
        WorldTile result = instance.getCurrentPlace();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetGoal() {
        System.out.println("getGoal");
        Robot instance = null;
        WorldTile expResult = null;
        WorldTile result = instance.getGoal();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetWorld() {
        System.out.println("getWorld");
        Robot instance = null;
        WorldModel expResult = null;
        WorldModel result = instance.getWorld();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testChooseMove() {
        System.out.println("chooseMove");
        Robot instance = null;
        instance.chooseMove();
        fail("The test case is a prototype.");
    }
}
