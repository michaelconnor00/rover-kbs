package kbs_rover_project;

import java.util.Map;
import java.util.EnumMap;

/**
 *
 * @author michaelconnor
 */
class EnvironmentDataController {
    
    private Map<TileType, MoveAction> dataSource;
    
    public EnvironmentDataController(){
        this.dataSource = new EnumMap<TileType, MoveAction>(TileType.class);
        // Load initial data 
        
    }
    
    public MoveAction getAction(TileType key){
        return this.dataSource.get(key);
    }
    
    public void putAction(TileType key, MoveAction actionValue){
        this.dataSource.put(key, actionValue);
    }
}
