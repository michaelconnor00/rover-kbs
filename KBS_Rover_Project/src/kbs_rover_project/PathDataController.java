package kbs_rover_project;

import java.util.HashMap;

/**
 *
 * @author michaelconnor
 */
class PathDataController {
    
    private HashMap<Path, MoveAction> dataSource;
    
    public PathDataController(){
        this.dataSource = new HashMap();
        // TODO Init data for knowledge base
    }
    
    public MoveAction getAction(Path pathKey){
        return this.dataSource.get(pathKey);
    }
    
    public void putAction(Path pathKey, MoveAction actionValue){
        this.dataSource.put(pathKey, actionValue);
    }
}
