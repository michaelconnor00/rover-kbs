
/*
 * used to feed senser data to infernice engine
 * will move based on input
 */
package kbs_rover_project;

import java.util.Arrays;

/**
 *
 * @author johnathan Raine
 */
public class Robot {

    //private varables
    private WorldModel world;
    private WorldTile goal;
    private WorldTile current;
    private InferenceEngine logicUnit;

    //given world, start position and inital goal
    public Robot(WorldModel t,WorldTile s, WorldTile g) {
        world = t;
        current=s;
        goal = g;
        logicUnit = new InferenceEngine(goal, world.getBoardSize());
        
    }

    // getters and setters
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
        int dim = world.getBoardSize();
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

        int currCol = current.getCol();
        int currRow = current.getRow();
        
        int[][] coordOffsetTuples = {
            {0,1}, {1,0}, {0,-1}, {-1,0}
        };

        for (int j = 0; j < neighborScores.length; j++) {
            int col = currCol + coordOffsetTuples[j][0];
            int row = currRow + coordOffsetTuples[j][1];
            
            // getNextCoord will return -1 if out of bounds
            boolean coordsOutOfBounds = col >= world.getBoardSize() || 
                    row >= world.getBoardSize() ||
                    col < 0 || row < 0;
                    
            if (coordsOutOfBounds){
                // Out of Bounds, treat as blocking.
                neighborScores[j] = -0.1;
                tileOptions[j] = null; // really, null??
            } else {
                neighborScores[j] = logicUnit.getNextScore(
                        world.getTile(col, row));
                System.out.println("Score: " + col + ", " + row + " - " + neighborScores[j]);
                tileOptions[j] = world.getTile(col, row);
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
        
//        System.out.println(
//            "Choose: " + Arrays.toString(neighborScores) + ", " +
//            max + ", " + maxIndex
//        );
        
        // Make sure there was a score over 0.0
        if (max > 0.0){
            move(tileOptions[maxIndex]);
            logicUnit.addPathScore(tileOptions[maxIndex]);
        } // else, don't make a move.

    }

    /*
     * moves robot
     */
    private void move(WorldTile place) {
        if(place.getMyType().equals(TileType.CRUST_SAND))
        {
            logicUnit.updateSensorAction(TileType.CRUST_SAND, MoveAction.DIFFICULT);
        }
        if(place.getMyType().equals(TileType.ROCKS_SMALL))
        {
            logicUnit.updateSensorAction(TileType.ROCKS_SMALL, MoveAction.PASSABLE);
        }
        
        current = place;
    }
}