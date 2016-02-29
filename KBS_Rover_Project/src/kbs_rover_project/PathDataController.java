package kbs_rover_project;

import java.util.HashMap;

/**
 * Controller for keeping track of previously visited tiles.
 * @author michaelconnor
 */
class PathDataController {
    
    private HashMap<WorldTile, int[]> dataSource;
    
    public PathDataController(){
        this.dataSource = new HashMap();
    }
    
    public int[] getHistory(WorldTile key){
        if (this.dataSource.containsKey(key)){
            return this.dataSource.get(key);
        } else {
            return null;
        }
    }
    
    public void putScores(WorldTile key, int[] scoreArray){
        this.dataSource.put(key, scoreArray);
    }
    
    public boolean containsEnv(WorldTile key){
        return this.dataSource.containsKey(key);
    }
    
}
