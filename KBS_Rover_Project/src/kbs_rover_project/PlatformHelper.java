package kbs_rover_project;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;


public class PlatformHelper {
    
    
    public static void run(Runnable treatment) {
        if(treatment == null) throw new IllegalArgumentException("The treatment to perform can not be null");
 
        if(Platform.isFxApplicationThread()) { 
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(e -> treatment.run());
            pause.play();
//            delay(1000);
        }
        else { 
            Platform.runLater(treatment);
        }

    }
}
