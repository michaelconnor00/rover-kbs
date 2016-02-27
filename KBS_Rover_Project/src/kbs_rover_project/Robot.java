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
    private InferenceEngine logicUnit;
    
    // constructers
    //given world
    public Robot (WorldModel t){
        terra=t;
        logicUnit=new InferenceEngine();
    }
    //given world and inital goal
    public Robot (WorldModel t,WorldTile g){
        terra=t;
        goal=g;
        logicUnit=new InferenceEngine();
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
        int[] scores = new int[4];
        WorldTile[] options = new WorldTile[4];
        int curx,cury;
        curx=current.getXCoord();
        cury=current.getYCoord();
        int count=0;
        
        if(terra.getTile(curx+1,cury)!=last){
                scores[count] = logicUnit.getNextAction(terra.getTile(curx+1,cury));
                options[count]=terra.getTile(curx+1,cury);
                count++;
        }else{
            scores[count]=-1;
            options[count]=last;
            count++;
        }
        if(terra.getTile(curx,cury+1)!=last){
                scores[count] = logicUnit.getNextAction(terra.getTile(curx,cury+1));
                options[count]=terra.getTile(curx,cury+1);
                count++;
        }else{
            scores[count]=-1;
            options[count]=last;
            count++;
        }
        if(terra.getTile(curx-1,cury)!=last){
                scores[count] = logicUnit.getNextAction(terra.getTile(curx-1,cury));
                options[count]=terra.getTile(curx-1,cury);
                count++;
        }else{
            scores[count]=-1;
            options[count]=last;
            count++;
        }
        if(terra.getTile(curx,cury-1)!=last){
                scores[count] = logicUnit.getNextAction(terra.getTile(curx,cury-1));
                options[count]=terra.getTile(curx,cury-1);
                count++;
        }else{
            scores[count]=-1;
            options[count]=last;
            count++;
        }
        int max=0;
        for(int i=0;i<4;i++){
            if(scores[i]>max){
                max=i;
            }  
        }
        move(options[max]);   
    }
    private void move(WorldTile place){
        last=current;
        current=place;
        
        
    }
    
    
    
}
