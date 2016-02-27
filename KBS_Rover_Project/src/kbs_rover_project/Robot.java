/*
 * used to feed senser data to infernice engine
 * will move based on input
 */
package kbs_rover_project;

/**
 *
 * @author johnathan
 */
public class Robot {
    //private varables
    private WorldModel terra;
    
    // constructer 
    public Robot (WorldModel t){
        terra=t;
    }
    // geters and setrs
    public void setWorld(WorldModel t){
        terra=t;
    }
    public WorldModel getWorld(){
        return terra;
    }
    
    
    
    
}
