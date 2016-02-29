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
        WorldModel world = new WorldModel(64);
        world.generateRandomWorld();
        world.startGUI();
        Robot rover = new Robot(world, world.getTile(0, 0), world.getTile(7, 7));
        
        //main execution loop
       // while(rover.g)
    }
    
}
