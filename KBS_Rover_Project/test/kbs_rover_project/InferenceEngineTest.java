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
    
    private static WorldTile wtGoal;
    private static WorldTile wtPassable;
    private static WorldTile wtBlocking;
    private static WorldTile wtDifficult;
    private static InferenceEngine testEngine;
    
    @BeforeClass
    public static void setUpClass() {
        wtGoal = new WorldTile(TileType.DIRT, 4);
        wtGoal.setXCoord(3);
        wtGoal.setYCoord(3);
        wtPassable = new WorldTile(TileType.DIRT, 4);
        wtPassable.setXCoord(1);
        wtPassable.setYCoord(1);
        wtBlocking = new WorldTile(TileType.ROCKS_LARGE, 4);
        wtBlocking.setXCoord(1);
        wtBlocking.setYCoord(1);
        wtDifficult = new WorldTile(TileType.ROCKS_SMALL, 4);
        wtDifficult.setXCoord(1);
        wtDifficult.setYCoord(1);
        testEngine = new InferenceEngine(wtPassable);
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
        int travelDirection = 0;
        InferenceEngine instance = null;
        double expResult = 100.0;
        double result = instance.getNextScore(wtPassable, travelDirection);
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
