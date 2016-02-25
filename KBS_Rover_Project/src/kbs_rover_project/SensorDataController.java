package kbs_rover_project;

import java.util.HashMap;

/**
 *
 * @author michaelconnor
 */
class SensorDataController {
    
    private HashMap<Sensor, Action> dataSource;
    
    public SensorDataController(){
        this.dataSource = new HashMap();
    }
    
    public Action getAction(Sensor pathKey){
        return this.dataSource.get(pathKey);
    }
    
    public void putAction(Sensor sensorKey, Action actionValue){
        this.dataSource.put(sensorKey, actionValue);
    }
}
