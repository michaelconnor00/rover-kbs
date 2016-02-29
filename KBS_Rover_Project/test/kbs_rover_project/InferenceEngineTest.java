package kbs_rover_project;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michaelconnor
 */
public class InferenceEngineTest {
    
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
        testEngine = new InferenceEngine(wtGoal);
    }
    
    private double distToGoal(WorldTile currTile){
        int xdiff = wtGoal.getXCoord() - currTile.getXCoord();
        int ydiff = wtGoal.getYCoord() - currTile.getYCoord();
        return  Math.sqrt(Math.pow((double) xdiff, 2) + Math.pow((double) ydiff, 2));
    }

    @Test
    public void testGetNextScorePassable() {
        int travelDirection = 0;
        double expResult = 100.0 / distToGoal(wtPassable);
        double result = testEngine.getNextScore(wtPassable, travelDirection);
        assertEquals(expResult, result, 0.0);
        testEngine.addPathScore(wtPassable, travelDirection);
        expResult *= 0.5;
        result = testEngine.getNextScore(wtPassable, travelDirection);
        assertEquals(expResult, result, 0.0);
    }
    
    @Test
    public void testGetNextScoreDifficult() {
        int travelDirection = 0;
        double expResult = 50.0 / distToGoal(wtDifficult);
        double result = testEngine.getNextScore(wtDifficult, travelDirection);
        assertEquals(expResult, result, 0.0);
    }
    
    @Test
    public void testGetNextScoreBlocking() {
        int travelDirection = 0;
        double expResult = 0.0 / distToGoal(wtBlocking);
        double result = testEngine.getNextScore(wtBlocking, travelDirection);
        assertEquals(expResult, result, 0.0);
    }
    
}
