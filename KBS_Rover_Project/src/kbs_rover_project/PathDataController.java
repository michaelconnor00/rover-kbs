package kbs_rover_project;

import java.util.HashMap;

/**
 *
 * @author michaelconnor
 */
class PathDataController {
    
    private HashMap<Path, Action> dataSource;
    
    public PathDataController(){
        this.dataSource = new HashMap();
    }
    
    public Action getAction(Path pathKey){
        return this.dataSource.get(pathKey);
    }
    
    public void putAction(Path pathKey, Action actionValue){
        this.dataSource.put(pathKey, actionValue);
    }
}
