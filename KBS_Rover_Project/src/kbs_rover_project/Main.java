
package kbs_rover_project;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    
    private Stage primaryStage;
    private WorldModel currentWorld;
    private ImageView[][] gridImageViews;
    private PauseTransition imageTransition = new PauseTransition(Duration.seconds(1.0));
    private Button buttonRestart;
    private VBox sideBar;
    private GridPane boardGrid;
    private int boardSize = 8;
    private double screenHeight = 830.0;
    private double baseImageHeight;
    private int complexity = 1;
    private ArrayList<TextField> newInputs = new ArrayList<>();
    
    @Override 
    public void start(Stage stage){
        stage.setTitle("KBS Rover");
        
        Scene scene = addScene();
        
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        
        primaryStage = stage;
        
        // Run Rover Loop
        runLoop();
    }
    
    private Scene addScene(){
        HBox mainGroup = new HBox();
        mainGroup.setAlignment(Pos.TOP_LEFT);
        
        initWorld();
        
        sideBar = addVBox();
        boardGrid = addGridPane(boardSize, screenHeight);
        
        mainGroup.getChildren().add(sideBar);
        mainGroup.getChildren().add(boardGrid);
        
        return new Scene(mainGroup);
    }
    
    private void initWorld(){
        // Create the environment and randomize
        currentWorld = new WorldModel(boardSize, true);
        currentWorld.generateRandomWorld();
    }
    
    private void runLoop(){
        
        //Initialize Robot
        Robot rover = new Robot(
                currentWorld, currentWorld.getTile(0, 0), currentWorld.getTile(boardSize-1, boardSize-1)
        );
        
        // Save rover current tile
        WorldTile current = rover.getCurrentPlace();
        // Add Rover image to board
        setRover(current);
        
        WorldTile next;
        
//        while (rover.atGoal() == false){
            rover.chooseMove();
            next = rover.getCurrentPlace();
            moveRover(next, current);
            
            System.out.println(
                "Next: " + next.getXCoord() + ", " + next.getYCoord()
            );
            current = rover.getCurrentPlace();
//        }

    }
    
    private VBox addInput(String label, String defValue, String getID){
        VBox vbox = new VBox();
        vbox.setPrefWidth(150);
        vbox.setSpacing(7);   // Gap between nodes
        
        Label newLabel = new Label(label);
        newLabel.setWrapText(true);
        newLabel.setTextAlignment(TextAlignment.CENTER);
        
        TextField newTextField = new TextField(defValue);
        newTextField.setId(getID);
        newTextField.setAlignment(Pos.CENTER);
        
        newInputs.add(newTextField);

        vbox.getChildren().addAll(newLabel, newTextField);
        
        return vbox;
    }
    
    private VBox addVBox(){
        VBox vbox = new VBox();
        vbox.setPrefWidth(150);
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);   // Gap between nodes
        
        buttonRestart = new Button("Restart");
        buttonRestart.setOnAction(e -> restartClick());
        buttonRestart.setPrefSize(100, 20);
        
        VBox boardSizeInput = addInput("Board Size", "8", "size");
        VBox obsticalComplexity = addInput(
                "Obstical Complexity Factor", "1", "complex"
        );
        
        // Add more components here is desired.
        
        vbox.getChildren().addAll(
                buttonRestart, boardSizeInput, obsticalComplexity
        );
        
        vbox.setAlignment(Pos.TOP_CENTER);
        
        return vbox;
    }
    
    public GridPane addGridPane(int size, double screenHeight) {
        // tiles are 128x128 actual size, based on a 8x8 board. 
        // For different size boards, scale image based on 128/9.5 * newSize
        baseImageHeight = screenHeight / (double) size;
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));

        gridImageViews = new ImageView[size][size];
        String imageName;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                imageName = currentWorld.getTile(j, i).getMyType().getImageName();
                gridImageViews[i][j] = getImageView(imageName);
                gridImageViews[i][j].setImage(new Image(
                        getClass().getResourceAsStream(imageName)
                ));
                gridImageViews[i][j].setFitHeight(baseImageHeight);
                gridImageViews[i][j].setPreserveRatio(true);
                GridPane.setConstraints(gridImageViews[i][j], i, j);
                grid.getChildren().add(gridImageViews[i][j]);
            }
        }

        return grid;
    }
    
    private ImageView getImageView(String imageName){
        ImageView newImageView = new ImageView();
        newImageView.setImage(new Image(
            getClass().getResourceAsStream(imageName)
        ));
        newImageView.setFitHeight(baseImageHeight);
        newImageView.setPreserveRatio(true);
        return newImageView;
    }

    private void setRover(WorldTile loc){
        ImageView newImageView = getImageView("resources/rover.png");
//        gridImageViews[loc.getYCoord()][loc.getXCoord()] = getImageView("resources/rover.png");
//        boardGrid.requestLayout();
//        gridImageViews[loc.getYCoord()][loc.getXCoord()] = getImageView("resources/rover.png");
//        PauseTransition pause = new PauseTransition(Duration.seconds(1.0));
//        pause.setOnFinished(e -> setToGrid(boardGrid, loc, newImageView));
//        pause.play();
        PlatformHelper.run(() -> setToGrid(boardGrid, loc, newImageView));
    }
    
    private void setTile(WorldTile loc){
        ImageView newImageView = getImageView(loc.getMyType().getImageName());
        PlatformHelper.run(() -> setToGrid(boardGrid, loc, newImageView));
    }
    
    private void setToGrid(GridPane currentGrid, WorldTile loc, ImageView newImageView){
        GridPane.setConstraints(
            newImageView, 
            loc.getYCoord(), 
            loc.getXCoord()
        );
        currentGrid.getChildren().add(newImageView);
//        boardGrid.requestLayout();
    }
    
    private void moveRover(WorldTile current, WorldTile prev){
        setRover(current);
        setTile(prev);
    }
   
    public static void main(String[] args){
        Application.launch(args);
    }
    
    //////////////////////
    ////    EVENTS    ////
    //////////////////////
    
    private void restartClick(){
        String value = null;
        
        //Loop through all nodes of the side bar, read text fields.
        for (TextField tf: newInputs){
            String tfID = tf.getId();
            if (tfID != null && tfID.equals("size")){
                value = tf.getText();
                boardSize = Integer.parseInt(value);
            } else if (tfID != null && tfID.equals("complex")){
                value = tf.getText();
                complexity = Integer.parseInt(value);
            }
            System.out.println(value);
        }
        initWorld();
        Stage stage = new Stage();
        stage.setScene(addScene());
        stage.sizeToScene();
        stage.show();
        primaryStage.close();
        primaryStage = stage;
        runLoop();
    }
    
}
