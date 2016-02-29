package kbs_rover_project;

import java.util.HashMap;

/**
 *
 * @author michaelconnor
 */
class PathDataController {
    
    private HashMap<Path, int[]> dataSource;
    
    public PathDataController(){
        this.dataSource = new HashMap();
        // TODO Init data for knowledge base
    }
    
    public int[] getAction(Path pathKey){
        return this.dataSource.get(pathKey);
    }
    
    /**
     * When putting an item, see if 
     * @param pathKey
     * @param score 
     */
    public void putScores(Path pathKey, int[] scoreArray){
        this.dataSource.put(pathKey, scoreArray);
    }
    
    
}
