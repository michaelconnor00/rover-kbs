package kbs_rover_project;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michaelconnor
 */
public class InferenceEngineTest {
    
//    public InferenceEngineTest() {
//    }
    
    @BeforeClass
    public static void setUpClass() {
        WorldTile wtBlocking = new WorldTile();
        wtBlocking.setWorldTile(TileType.DIRT, 0);
        
    }
    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }

    @Test
    public void testGetNextScore() {
        System.out.println("getNextScore");
        WorldTile environment = null;
        int travelDirection = 0;
        InferenceEngine instance = null;
        double expResult = 0.0;
        double result = instance.getNextScore(environment, travelDirection);
        assertEquals(expResult, result, 0.0);
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdateAction() {
        System.out.println("updateAction");
        WorldTile move = null;
        MoveAction newAction = null;
        InferenceEngine instance = null;
        instance.updateAction(move, newAction);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAddPathScore() {
        System.out.println("addPathScore");
        WorldTile currTile = null;
        int travelDirection = 0;
        InferenceEngine instance = null;
        instance.addPathScore(currTile, travelDirection);
        fail("The test case is a prototype.");
    }
    
}
