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
//    public Robot (WorldModel t){
//        terra=t;
//        logicUnit=new InferenceEngine();
//        
//    }
    //given world and inital goal
    public Robot(WorldModel t, WorldTile g) {
        terra = t;
        goal = g;
        logicUnit = new InferenceEngine(goal);

    }

    // geters and setrs
    public void setWorld(WorldModel t) {
        terra = t;
    }

    public void setGoal(WorldTile t) {
        goal = t;
        logicUnit = new InferenceEngine(goal);
    }

    public void setCurrentPlace(WorldTile t) {
        current = t;
    }

    public void setLastPlace(WorldTile t) {
        last = t;
    }

    public WorldTile getLastPlace() {
        return last;
    }

    public WorldTile getCurrentPlace() {
        return current;
    }

    public WorldTile getGoal() {
        return goal;
    }

    public WorldModel getWorld() {
        return terra;
    }

    /*
     * method picks next diretion to take by first quireing the inferince engine for the posible option scores.
     * As it quires for each score it adds the tile to the corisponding tile arry
     * It then takes the highest score returned from the inforince engine amd moves to that spot.
     * 
     * note: if two scores are tied for first it will take the first score it got back
     * 
     * scores is stored in this order [0,1,2,3]
     * 
     * scores are gathered in this order. but one of these is never asked because it is the last position
     *      |1|
     *    |2|c|0|
     *      |3|
     * 
     * last position always gets a score of -1
     */
    public void chooseMove() {
        //corasponding arrys for chosing the best path
        double[] scores = new double[4];
        WorldTile[] options = new WorldTile[4];

        int currentX, currentY;
        currentX = current.getXCoord();
        currentY = current.getYCoord();
        int count = 0;

        if (terra.getTile(currentX + 1, currentY) != last) {
            double move1 = logicUnit.getNextScore(terra.getTile(currentX + 1, currentY), 0);
            scores[count] = move1;
            options[count] = terra.getTile(currentX + 1, currentY);
            count++;
        } else {
            scores[count] = 1;
            options[count] = last;
            count++;
        }
        if (terra.getTile(currentX, currentY + 1) != last) {
            double move2 = logicUnit.getNextScore(terra.getTile(currentX, currentY + 1), 1);
            scores[count] = move2;
            options[count] = terra.getTile(currentX, currentY + 1);
            count++;
        } else {
            scores[count] = 1;
            options[count] = last;
            count++;
        }
        if (terra.getTile(currentX - 1, currentY) != last) {
            double move3 = logicUnit.getNextScore(terra.getTile(currentX - 1, currentY), 2);
            scores[count] = move3;
            options[count] = terra.getTile(currentX - 1, currentY);
            count++;
        } else {
            scores[count] = 1;
            options[count] = last;
            count++;
        }
        if (terra.getTile(currentX, currentY - 1) != last) {
            double move4 = logicUnit.getNextScore(terra.getTile(currentX, currentY - 1), 3);
            scores[count] = move4;
            options[count] = terra.getTile(currentX, currentY - 1);
            count++;
        } else {
            scores[count] = 1;
            options[count] = last;
            count++;
        }
        //chouses largest score tie goes to first seen
        int max = 0;
        for (int i = 0; i < 4; i++) {
            if (scores[i] > max) {
                max = i;
            }
        }
        move(options[max]);
        logicUnit.addPathScore(options[max], max);
    }

    /*
     * moves robot
     * if to
     */
    private void move(WorldTile place) {

        if (place.isBlocking()) {
            MoveAction move = MoveAction.BLOCKING;
//            logicUnit.

        } else if (place.isDifficult()) {
            MoveAction move = MoveAction.DIFFICULT;
            last = current;
            current = place;
        } else {
            MoveAction move = MoveAction.PASSABLE;
            last = current;
            current = place;
        }
    }

}
