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
    private WorldTile goal;
    private WorldTile current;
    private WorldTile last;
    
    
    // constructers
    //given world
    public Robot (WorldModel t){
        terra=t;
    }
    //given world and inital goal
    public Robot (WorldModel t,WorldTile g){
        terra=t;
        goal=g;
    }
    
    
    // geters and setrs
    public void setWorld(WorldModel t){
        terra=t;
    }
    public void setGoal(WorldTile t){
        goal=t;
    }
    public void setCurrentPlace(WorldTile t){
        current=t;
    }
    public void setLastPlace(WorldTile t){
        last=t;
    }
    public WorldTile getLastPlace(){
        return last;
    }
    public WorldTile getCurrentPlace(){
        return current;
    }
    public WorldTile getGoal(){
        return goal;
    }
    public WorldModel getWorld(){
        return terra;
    }
    
    
    //pick witch way to go
    public void chooseMove(){
        int sA,sB,sC;
        
        int curx,cury;
        curx=current.getXCoord();
        cury=current.getYCoord();
        
        if(terra.getTile(curx+1,cury)!=last){
            sA=Inferince
        }
        
        
    }
    
    
    
    
}
