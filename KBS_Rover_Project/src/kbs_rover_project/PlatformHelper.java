package kbs_rover_project;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;


public class PlatformHelper {
 
    public static void run(Runnable treatment) {
        if(treatment == null) throw new IllegalArgumentException("The treatment to perform can not be null");
 
        if(Platform.isFxApplicationThread()) { 
            PauseTransition pause = new PauseTransition(Duration.seconds(1.0));
            pause.setOnFinished(e -> treatment.run());
            pause.play();
//            treatment.run();
            System.out.println("FxThread");
        }
        else { 
            Platform.runLater(treatment);
            System.out.println("NotFxThread");
        }
        
        Sleep pause = new Sleep();
        pause.start();
        
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            System.out.println("Yo");
//        }
    }
}
