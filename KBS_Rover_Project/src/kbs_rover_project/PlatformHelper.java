package kbs_rover_project;

import javafx.application.Platform;


public class PlatformHelper {
 
    public static void run(Runnable treatment) {
        if(treatment == null) throw new IllegalArgumentException("The treatment to perform can not be null");
 
        if(Platform.isFxApplicationThread()) treatment.run();
        else Platform.runLater(treatment);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.err.println("Yo");
        }
    }
}
