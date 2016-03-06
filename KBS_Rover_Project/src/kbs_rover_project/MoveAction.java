package kbs_rover_project;

/**
 * Class for defining actions for the rover to take
 * @author michaelconnor
 */
public enum MoveAction {
    DIFFICULT (500), 
    PASSABLE  (1000), 
    BLOCKING  (0);
    
    private final int score;
    
    MoveAction (int init_score){
        this.score = init_score;
    }
    
    int getScore(){
        return this.score;
    }
    
}

