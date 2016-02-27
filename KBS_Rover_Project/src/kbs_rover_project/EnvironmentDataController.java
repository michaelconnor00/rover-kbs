package kbs_rover_project;

import java.util.HashMap;

/**
 *
 * @author michaelconnor
 */
class EnvironmentDataController {
    
    private HashMap<WorldTile, MoveAction> dataSource;
    
    public EnvironmentDataController(){
        this.dataSource = new HashMap();
        // TODO Init data for knowledge base
    }
    
    public Action getAction(WorldTile pathKey){
        return this.dataSource.get(pathKey);
    }
    
    public void putAction(WorldTile sensorKey, Action actionValue){
        this.dataSource.put(sensorKey, actionValue);
    }
}
