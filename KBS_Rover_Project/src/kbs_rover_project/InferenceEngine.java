package kbs_rover_project;

/**
 * @author michaelconnor
 * Controller Class for Inference Engine and Knowledge base
 */
public class InferenceEngine {
    
    private EnvironmentDataController sensorData;
    
    private PathDataController pathData;
    
    public InferenceEngine(){
        this.sensorData = new EnvironmentDataController();
        this.pathData = new PathDataController();
    }
    
    /**
     * Determines a score 
     * @param environment: data from the sensors, type: WorldTile
     * @param pathData
     * @return 
     */
    public MoveAction getNextAction(WorldTile environment){
        MoveAction nextSensorAction = this.sensorData.getAction(environment);
        
//        MoveAction nextPathAction = this.pathData.getAction(pathData);
        MoveAction nextAction = null;
        if (true) {
            // determine which action to take.
            
        }
        return nextAction;
    }
    
    private void addSensorAction(WorldTile environment, MoveAction newAction){
        this.sensorData.putAction(environment, newAction);
    }
    
    private void addPathAction(Path pathData, MoveAction newAction){
        this.pathData.putAction(pathData, newAction);
    }
    
    
}
