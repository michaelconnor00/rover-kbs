package kbs_rover_project;

/**
 * @author michaelconnor
 * Controller Class for Inference Engine and Knowledge base
 */
public class InferenceEngine {
    
    private EnvironmentDataController sensorData;
    
    private int goalCol;
    private int goalRow;
    private int boardSize;
    private int[][] historyMap;
    
    private final double previouslyVisitedFactor = 0.7;
    
    public InferenceEngine(WorldTile goalTile, int boardSize){
        this.initEngine();
        this.goalCol = goalTile.getCol();
        this.goalRow = goalTile.getRow();
        this.boardSize = boardSize;
        this.historyMap = new int[boardSize][boardSize];
    }
    
    private void initEngine(){
        this.sensorData = new EnvironmentDataController();
        this.initSensorData();
    }
    
    /**
     * Determines a score the given Environment. Score is calculated:
     *  MoveAction.score / distanceToGoal
     * @param environment: data from the sensors, type: WorldTile
     * @param travelDirection: the index the rover looking to move.
     * @return 
     */
    public double getNextScore(WorldTile environment, int travelDirection){
        MoveAction nextAction = this.sensorData.getAction(environment.getMyType());
        
        int col = environment.getCol();
        int row = environment.getRow();
        
        double nextScore = (double) nextAction.getScore() / this.distanceToGoal(
                col, row
        );
        
        if (historyMap[row][col] > 0){
            // Tile has been visited. reduce score to increase chance no to 
            // take the same previous path.
            System.out.println("History Scale: " + Math.pow(previouslyVisitedFactor, historyMap[row][col]));
            return nextScore * Math.pow(previouslyVisitedFactor, historyMap[row][col]);
        }
        
        return nextScore;
    }
    
    private double distanceToGoal(int col, int row){
        return Math.sqrt(
            Math.pow((double) (this.goalCol - col), 2) + 
            Math.pow((double) (this.goalRow - row), 2)
        );
    }
    
    //Updates knowledge base about TileTypes, allowing rover to learn about
    //true difficulty of some types
    public void updateSensorAction(TileType environment, MoveAction newAction){
        this.sensorData.putAction(environment, newAction);
    }
    
    public void setGoal(WorldTile newGoal){
        this.goalCol = newGoal.getCol();
        this.goalRow = newGoal.getRow();
    }
    
    /**
     * Function to add visit history to the path data. Adding implies 
     * the rover has chosen to move to that location. 
     * @param currTile, the tile the rover moved to.
     */
    public void addPathScore(WorldTile currTile){
        int col = currTile.getCol();
        int row = currTile.getRow();
        historyMap[row][col]++;
    }

    
    //Sets up initial knowledge about each TileType
    private void initSensorData() {
        this.sensorData.putAction(TileType.DIRT, MoveAction.PASSABLE);
        this.sensorData.putAction(TileType.CHASM, MoveAction.BLOCKING);
        this.sensorData.putAction(TileType.CRUST_SAND, MoveAction.PASSABLE);
        this.sensorData.putAction(TileType.HOME_BASE, MoveAction.PASSABLE);
        this.sensorData.putAction(TileType.ROCKS_LARGE, MoveAction.BLOCKING);
        this.sensorData.putAction(TileType.ROCKS_SMALL, MoveAction.DIFFICULT);
        this.sensorData.putAction(TileType.SAMPLE_LOCATION, MoveAction.PASSABLE);
    }
    
    
}
