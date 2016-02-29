package kbs_rover_project;

/**
 * @author michaelconnor
 * Controller Class for Inference Engine and Knowledge base
 */
public class InferenceEngine {
    
    private EnvironmentDataController sensorData;
    
//    private PathDataController pathData;
    
    private final int goalXCoord;
    private final int goalYCoord;
    
    public InferenceEngine(WorldTile goalTile){
        this.sensorData = new EnvironmentDataController();
        this.initSensorData();
        this.goalXCoord = goalTile.getXCoord();
        this.goalYCoord = goalTile.getYCoord();
    }
    
    /**
     * Determines a score the given Environment. Score is calculated:
     *  MoveAction.score / distanceToGoal
     * @param environment: data from the sensors, type: WorldTile
     * @return 
     */
    public double getNextScore(WorldTile environment){
        MoveAction nextAction = this.sensorData.getAction(environment.getMyType());
        double nextScore = (double) nextAction.getScore() / this.distanceToGoal(
                environment.getXCoord(), environment.getYCoord()
        );
        
        return nextScore;
    }
    
    public void updateAction(WorldTile move, MoveAction newAction){
        this.sensorData.putAction(move.getMyType(), newAction);
    }
    
    private double distanceToGoal(int x, int y){
        return Math.sqrt(Math.pow((double) x, 2) + Math.pow((double) y, 2));
    }
    
    private void addSensorAction(WorldTile environment, MoveAction newAction){
        this.sensorData.putAction(environment.getMyType(), newAction);
    }
    
//    private void addPathscore(Path pathData, int score, Direction neighbor){
//        if(this.pathData.contains())
//        this.pathData.putScores(pathData, scoreArray);
//    }

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
