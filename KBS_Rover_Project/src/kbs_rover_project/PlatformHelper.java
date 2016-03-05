package kbs_rover_project;

import javafx.application.Platform;



/**
 *
 * @author michaelconnor
 */
//public class GridUpdater implements Runnable {
//
//    public void run() {
//        GridPane.setConstraints(
//                newImageView, 
//                loc.getYCoord(), 
//                loc.getXCoord()
//        );
//        currentGrid.getChildren().add(newImageView);
////        boardGrid.requestLayout();
//    }
//
//    public static void main(String args[]) {
//        (new Thread(new HelloRunnable())).start();
//    }
//
//}

public class PlatformHelper {
 
    public static void run(Runnable treatment) {
        if(treatment == null) throw new IllegalArgumentException("The treatment to perform can not be null");
 
        if(Platform.isFxApplicationThread()) treatment.run();
        else Platform.runLater(treatment);
    }
}
