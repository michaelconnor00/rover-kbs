package kbs_rover_project;

/**
 * @author michaelconnor
 * Controller Class for Inference Engine and Knowledge base
 */
public class InferenceEngine {
    
    private EnvironmentDataController sensorData;
    
    private PathDataController pathData;
    
    private final int goalXCoord;
    private final int goalYCoord;
    
    private final double previouslyVisitedFactor = 0.5;
    
    public InferenceEngine(WorldTile goalTile){
        this.sensorData = new EnvironmentDataController();
        this.pathData = new PathDataController();
        this.initSensorData();
        this.goalXCoord = goalTile.getXCoord();
        this.goalYCoord = goalTile.getYCoord();
    }
    
    /**
     * Determines a score the given Environment. Score is calculated:
     *  MoveAction.score / distanceToGoal
     * @param environment: data from the sensors, type: WorldTile
     * @param travelDirection: the index the rover looking to move.
     * @return 
     */
    public double getNextScore(WorldTile environment, int travelDirection){
        MoveAction nextAction = this.sensorData.getAction(environment.getMyType());
        
        int[] history = this.pathData.getHistory(environment);
        
        double nextScore = (double) nextAction.getScore() / this.distanceToGoal(
                environment.getXCoord(), environment.getYCoord()
        );
        
        if (history != null || history[travelDirection] == 1){
            // Tile has been visited. reduce score to increase chance no to 
            // take the same previous path.
            return nextScore * previouslyVisitedFactor;
            
        }
        // either history == null or the value is not 1 (not visited)
        return nextScore;
    }
    
    public void updateAction(WorldTile move, MoveAction newAction){
        this.sensorData.putAction(move.getMyType(), newAction);
    }
    
    private double distanceToGoal(int x, int y){
        return Math.sqrt(Math.pow((double) x, 2) + Math.pow((double) y, 2));
    }
    
    private void updateSensorAction(WorldTile environment, MoveAction newAction){
        this.sensorData.putAction(environment.getMyType(), newAction);
    }
    
    /**
     * Function to add visit history to the path data. Adding implies 
     * the rover has chosen to move to that location. Assign value of 
     * @param currTile
     * @param travelDirection: the index that dictates the direction 
     *  the rover will move.
     */
    public void addPathScore(WorldTile currTile, int travelDirection){
        if(this.pathData.containsEnv(currTile)){
            int[] tileHistory = this.pathData.getHistory(currTile);
            tileHistory[travelDirection] = 1;
            
        } else {
            int[] newTileHistory = new int[4];
            newTileHistory[travelDirection] = 1;
            this.pathData.putScores(currTile, newTileHistory);
        }
    }

    private void initSensorData() {
        this.sensorData.putAction(TileType.DIRT, MoveAction.PASSABLE);
        this.sensorData.putAction(TileType.CHASM, MoveAction.BLOCKING);
        this.sensorData.putAction(TileType.CRUST_SAND, MoveAction.PASSABLE);
        this.sensorData.putAction(TileType.HOME_BASE, MoveAction.PASSABLE);
        this.sensorData.putAction(TileType.ROCKS_LARGE, MoveAction.BLOCKING);
        this.sensorData.putAction(TileType.ROCKS_SMALL, MoveAction.DIFFICULT);
        this.sensorData.putAction(TileType.SAMPLE_LOCATION, MoveAction.PASSABLE);
    }
    
    
}
