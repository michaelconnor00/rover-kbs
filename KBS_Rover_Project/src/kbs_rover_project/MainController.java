/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbs_rover_project;

/**
 *
 * @author Nick Taylor
 */
public class MainController {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        WorldModel world = new WorldModel(64, true);
        world.generateRandomWorld();
        world.startGUI();
        Robot rover = new Robot(world, world.getTile(0, 0), world.getTile(7, 7));
        world.updateRoverLocation(rover.getCurrentPlace());
        //main execution loop
        
        //going from start to sample location
        while(rover.atGoal() == false)
        {
            rover.chooseMove();
            world.updateRoverLocation(rover.getCurrentPlace());
            Thread.currentThread().sleep(1000);
            world.updateTileIcons();
            
            System.out.println(rover.getCurrentPlace().getXCoord() + ", " + rover.getCurrentPlace().getYCoord());
            
        }
        
        //set new goal as home base and cause rockslide
        rover.setGoal(world.getTile(0, 0));
        Thread.currentThread().sleep(1000);
        world.causeRockslide(6);
        world.updateTileIcons();
        //going from sample back to home
        while(rover.atGoal() == false)
        {
            rover.chooseMove();
            world.updateRoverLocation(rover.getCurrentPlace());
            Thread.currentThread().sleep(1000);
            world.updateTileIcons();
//             System.out.println(rover.getCurrentPlace().getXCoord() + ", " + rover.getCurrentPlace().getYCoord());
        }
    }
    
}
