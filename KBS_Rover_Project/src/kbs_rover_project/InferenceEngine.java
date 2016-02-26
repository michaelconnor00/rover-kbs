package kbs_rover_project;

/**
 * @author michaelconnor
 * Controller Class for Inference Engine and Knowledge base
 */
public class InferenceEngine {
    
    private SensorDataController sensorData;
    
    private PathDataController pathData;
    
    public InferenceEngine(){
        this.sensorData = new SensorDataController();
        this.pathData = new PathDataController();
    }
    
    public Action getNextAction(Sensor sensorData, Path pathData){
        Action nextSensorAction = this.sensorData.getAction(sensorData);
        Action nextPathAction = this.pathData.getAction(pathData);
        Action nextAction;
        if (true) {
            // determine which action to take.
            
        }
        return nextAction;
    }
    
    private void addSensorAction(Sensor sensorData, Action newAction){
        this.sensorData.putAction(sensorData, newAction);
    }
    
    private void addPathAction(Path pathData, Action newAction){
        this.pathData.putAction(pathData, newAction);
    }
    
    
}
