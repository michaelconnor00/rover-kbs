
/*
 * used to feed senser data to infernice engine
 * will move based on input
 */
package kbs_rover_project;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author johnathan Raine
 */
public class Robot {

    //private varables
    private WorldModel world;
    private WorldTile goal;
    private WorldTile current;
    private WorldTile last;
    private InferenceEngine logicUnit;

    //given world, start position and inital goal
    public Robot(WorldModel t,WorldTile s, WorldTile g) {
        world = t;
        current=s;
        last=s;
        goal = g;
        logicUnit = new InferenceEngine(goal);
        
    }

    // geters and setrs
    public void setWorld(WorldModel t) {
        world = t;
    }

    public void setGoal(WorldTile t) {
        goal = t;
        logicUnit.setGoal(t);
    }

    public void setCurrentPlace(WorldTile t) {
        current = t;
    }

    public void setLastPlace(WorldTile t) {
        last = t;
    }
    
    public WorldTile getLastPlace() {
        return last;
    }

    public WorldTile getCurrentPlace() {
        return current;
    }

    public WorldTile getGoal() {
        return goal;
    }

    public WorldModel getWorld() {
        return world;
    }
    
    public boolean atGoal(){
        return goal.equals(current);
    }
    
    private int getNextCoord(int curr, int offset){
        // NOTE: this is only applicable for SQUARE worlds!
        // return -1 if out of bounds
        int newCoord = curr + offset;
        int dim = world.getWorldDim();
        if (newCoord >= dim){
            return -1; // out of bounds
        } else {
            return newCoord;
        }
    }

    /*
     * method picks next direction to take by first querying the inference engine for the possible option scores.
     * As it quires for each score it adds the tile to the corresponding tile array
     * It then takes the highest score returned from the inference engine and moves to that spot.

     * 
     * note: if two scores are tied for first it will take the first score it got back
     * 
     * scores is stored in this order [0,1,2,3]
     * 
     * scores are gathered in this order. but one of these is never asked because it is the last position
     *      |1|
     *    |2|R|0|
     *      |3|
     * 
     * last position always gets a score of 0.01
     * of bourd positions get a score of -1.0
     */
    public void chooseMove(){
        //corasponding arrys for chosing the best path
        double[] neighborScores = new double[4];
        WorldTile[] tileOptions = new WorldTile[4];

        int currentX = current.getXCoord();
        int currentY = current.getYCoord();
        
        int[][] coordOffsetTuples = {
            {1,0}, {0,1}, {-1,0}, {0,-1}
        };

        for (int j = 0; j < neighborScores.length; j++) {
            int xCoord = getNextCoord(currentX, coordOffsetTuples[j][0]);
            int yCoord = getNextCoord(currentY, coordOffsetTuples[j][1]);
            
            // getNextCoord will return -1 if out of bounds
            boolean coordsOutOfBounds = xCoord < 0 || yCoord < 0;
                    
            if (coordsOutOfBounds){
                // Out of Bounds, treat as blocking.
                neighborScores[j] = -0.1;
                tileOptions[j] = null; // really, null??
            } else if (!world.getTile(xCoord, yCoord).equals(last)) {
                neighborScores[j] = logicUnit.getNextScore(
                        world.getTile(xCoord, yCoord), 0);
                tileOptions[j] = world.getTile(xCoord, yCoord);
            } else {
                // Previous location,
                // sufficiently low to reduce chance of going backwards
                neighborScores[j] = 0.1; 
                tileOptions[j] = last;
            }
        }
        
        //chooses largest score tie goes to first seen
        double max = 0;
        int maxIndex = 0;
        for (int i = 0; i < 4; i++) {
            if (neighborScores[i] > max && tileOptions[i] != null) {
                max = neighborScores[i];
                maxIndex = i;
            }
        }
        
        // Make sure there was a score over 0.0
        if (max > 0.0){
            move(tileOptions[maxIndex]);
            logicUnit.addPathScore(tileOptions[maxIndex], maxIndex);
        } // else, don't make a move.

    }

    /*
     * moves robot
     */
    private void move(WorldTile place) {
        last = current;
        current = place;
    }
}