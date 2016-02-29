package kbs_rover_project;

import java.util.Map;
import java.util.EnumMap;

/**
 * Controller for Environment knowledge base.
 * @author michaelconnor
 */
class EnvironmentDataController {
    
    private Map<TileType, MoveAction> dataSource;
    
    public EnvironmentDataController(){
        this.dataSource = new EnumMap<TileType, MoveAction>(TileType.class);   
    }
    
    public MoveAction getAction(TileType key){
        if (this.dataSource.containsKey(key)){
            return this.dataSource.get(key);
        } else {
            return null;
        }
    }
    
    public void putAction(TileType key, MoveAction actionValue){
        this.dataSource.put(key, actionValue);
    }
    
    public boolean containsTile(TileType key){
        return this.dataSource.containsKey(key);
    }
}
