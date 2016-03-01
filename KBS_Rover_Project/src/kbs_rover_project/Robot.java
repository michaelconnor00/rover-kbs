/*
 * used to feed senser data to infernice engine
 * will move based on input
 */
package kbs_rover_project;

/**
 *
 * @author johnathan Raine
 */
public class Robot {

    //private varables
    private WorldModel terra;
    private WorldTile goal;
    private WorldTile current;
    private WorldTile last;
    private InferenceEngine logicUnit;

    //given world, start position and inital goal
    public Robot(WorldModel t,WorldTile s, WorldTile g) {
        terra = t;
        current=s;
        last=s;
        goal = g;
        logicUnit = new InferenceEngine(goal);
        
    }

    // geters and setrs
    public void setWorld(WorldModel t) {
        terra = t;
    }

    public void setGoal(WorldTile t) {
        goal = t;
        logicUnit.setGoal(t);
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
    public boolean atGoal(){
        return goal.equals(current);
    }

    /*
     * method picks next direction to take by first querying the inference engine for the possible option scores.
     * As it quires for each score it adds the tile to the corresponding tile array
     * It then takes the highest score returned from the inference engine and moves to that spot.

     * 
     * note: if two scores are tied for first it will take the first score it got back
     * 
     * scores is stored in this order [0,1,2,3]
     * 
     * scores are gathered in this order. but one of these is never asked because it is the last position
     *      |1|
     *    |2|R|0|
     *      |3|
     * 
     * last position always gets a score of 0.01
     * of bourd positions get a score of -1.0
     */
    public void chooseMove() {
        //corasponding arrys for chosing the best path
        double[] scores = new double[4];
        WorldTile[] options = new WorldTile[4];

        int currentX, currentY;
        currentX = current.getXCoord();
        currentY = current.getYCoord();
        int count = 0;

        //cheaking  first tile index of 0
        try {
            if (!terra.getTile(currentX + 1, currentY).equals(last) ) {
                double move1 = logicUnit.getNextScore(terra.getTile(currentX + 1, currentY), 0);
                scores[count] = move1;
                options[count] = terra.getTile(currentX + 1, currentY);
                count++;
            } else {
                scores[count] = 0.01;
                options[count] = last;
                count++;
            }
        } catch (IndexOutOfBoundsException e) {
            scores[count] = -1.0;
            options[count] = null;
            count++;
        }
        //cheaking second tile index of 1
        try {
            if (!terra.getTile(currentX, currentY + 1).equals(last)) {
                double move2 = logicUnit.getNextScore(terra.getTile(currentX, currentY + 1), 1);
                scores[count] = move2;
                options[count] = terra.getTile(currentX, currentY + 1);
                count++;
            } else {
                scores[count] = 0.01;
                options[count] = last;
                count++;
            }
        } catch (IndexOutOfBoundsException e) {
            scores[count] = -1.0;
            options[count] = null;
            count++;
        }
        //cheaking second tile index of 2
        try {
            if (!terra.getTile(currentX - 1, currentY).equals(last)) {
                double move3 = logicUnit.getNextScore(terra.getTile(currentX - 1, currentY), 2);
                scores[count] = move3;
                options[count] = terra.getTile(currentX - 1, currentY);
                count++;
            } else {
                scores[count] = 0.01;
                options[count] = last;
                count++;
            }
        } catch (IndexOutOfBoundsException e) {
            scores[count] = -1.0;
            options[count] = null;
            count++;
        }
        //cheaking second tile index of 3
        try {
            if (!terra.getTile(currentX, currentY - 1).equals(last)) {
                double move4 = logicUnit.getNextScore(terra.getTile(currentX, currentY - 1), 3);
                scores[count] = move4;
                options[count] = terra.getTile(currentX, currentY - 1);
                count++;
            } else {
                scores[count] = 0.01;
                options[count] = last;
                count++;
            }
        } catch (IndexOutOfBoundsException e) {
            scores[count] = -1.0;
            options[count] = null;
            count++;
        }
        //chooses largest score tie goes to first seen
        double max = 0;
        int maxIndex = 0;
        for (int i = 0; i < 4; i++) {
            if (scores[i] > max) {
                max = scores[i];
                maxIndex = i;
            }
        }
        move(options[maxIndex]);
        logicUnit.addPathScore(options[maxIndex], maxIndex);
    }

    /*
     * moves robot
     * 
     */
    private void move(WorldTile place) {

        last = current;
        current = place;

    }
}
