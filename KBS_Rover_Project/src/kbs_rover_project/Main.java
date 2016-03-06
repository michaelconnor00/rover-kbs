package kbs_rover_project;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class Main extends Application {
    
    private Stage primaryStage;
    private WorldModel currentWorld;
    private Button buttonRestart;
    private Button buttonStep;
    private VBox sideBar;
    private GridPane boardGrid;
    private int boardSize = 8;
    private double screenHeight = 830.0;
    private double baseImageHeight;
    private Robot rover;
    private int complexity = 1;
    private ArrayList<TextField> newInputs = new ArrayList<>();
    
    @Override 
    public void start(Stage stage){
        stage.setTitle("KBS Rover");
        
        initWorld();
        
        Scene scene = addScene();
        
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        
        primaryStage = stage;
        
        //Initialize Robot
        rover = new Robot(
                currentWorld, 
                currentWorld.getTile(0, 0), 
                currentWorld.getTile(boardSize-1, boardSize-1)
        );
        
        // Save rover current tile
        // Add Rover image to board
        setRover(rover.getCurrentPlace());
    }
    
    private Scene addScene(){
        HBox mainGroup = new HBox();
        mainGroup.setAlignment(Pos.TOP_LEFT);
        
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
    
    private void initWorld(int smallRockNum, int largeRockNum, int chasmNum, 
            int crustNum){
        // Create the environment and randomize
        currentWorld = new WorldModel(boardSize, true);
        currentWorld.generateWorld(smallRockNum, largeRockNum, chasmNum,
                crustNum);
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
        
        buttonStep = new Button("Step");
        buttonStep.setOnAction(e -> step());
        buttonStep.setPrefSize(100, 20);
        
        buttonRestart = new Button("Restart");
        buttonRestart.setOnAction(e -> restartClick());
        buttonRestart.setPrefSize(100, 20);
        
        VBox boardSizeInput = addInput("Board Size", "8", "size");
        VBox smallRockSetter = addInput(
                "Number of Small Rocks", "1", "smallRockNum"
        );
        VBox largeRockSetter = addInput(
                "Number of Large Rocks", "1", "largeRockNum"
        );
        VBox chasmSetter = addInput(
                "Number of Chasms", "1", "chasmNum"
        );
        VBox crustSetter = addInput(
                "Number of Crusty Sand Tiles", "1", "crustNum"
        );
        
        
        vbox.getChildren().addAll(
                buttonStep,
                buttonRestart, 
                boardSizeInput,
                smallRockSetter, 
                largeRockSetter,
                chasmSetter, 
                crustSetter
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

        ImageView newImageView;
        String imageName;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                imageName = currentWorld.getTile(col, row).getMyType().getImageName();
                newImageView = getImageView(imageName);
                newImageView.setImage(new Image(
                        getClass().getResourceAsStream(imageName)
                ));
                newImageView.setFitHeight(baseImageHeight);
                newImageView.setPreserveRatio(true);
                GridPane.setConstraints(newImageView, row, col);
                grid.getChildren().add(newImageView);
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
        PlatformHelper.run(() -> setToGrid(boardGrid, loc, newImageView));
    }
    
    private void setTile(WorldTile loc){
        ImageView newImageView = getImageView(loc.getMyType().getImageName());
        PlatformHelper.run(() -> setToGrid(boardGrid, loc, newImageView));
    }
    
    private void setToGrid(GridPane currentGrid, WorldTile loc, ImageView newImageView){
        GridPane.setConstraints(
                newImageView, 
                loc.getRow(), 
                loc.getCol()
        );
        currentGrid.getChildren().add(newImageView);
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
        int smallRockNum = 1;
        int largeRockNum = 1;
        int chasmNum = 1;
        int crustNum = 1;
        
        //Loop through all nodes of the side bar, read text fields.
        for (TextField tf: newInputs){
            String tfID = tf.getId();
            if (tfID != null && tfID.equals("size")){
                value = tf.getText();
                boardSize = Integer.parseInt(value);
            } else if (tfID != null && tfID.equals("smallRockNum")){
                value = tf.getText();
                smallRockNum = Integer.parseInt(value);
            } 
            else if (tfID != null && tfID.equals("largeRockRockNum")){
                value = tf.getText();
                largeRockNum = Integer.parseInt(value);
            }
            else if (tfID != null && tfID.equals("chasmNum")){
                value = tf.getText();
                chasmNum = Integer.parseInt(value);
            }
            else if (tfID != null && tfID.equals("crustNum")){
                value = tf.getText();
                crustNum = Integer.parseInt(value);
            }
            System.out.println(value);
        }
        initWorld(smallRockNum, largeRockNum, chasmNum, crustNum);
        Stage stage = new Stage();
        stage.setScene(addScene());
        stage.sizeToScene();
        stage.show();
        primaryStage.close();
        primaryStage = stage;
        //Initialize Robot
        rover = new Robot(
                currentWorld, 
                currentWorld.getTile(0, 0), 
                currentWorld.getTile(boardSize-1, boardSize-1)
        );
        
        // Add Rover image to board
        setRover(rover.getCurrentPlace());
    }
    
    private void step(){
        WorldTile current;
        WorldTile next;
        
        current = rover.getCurrentPlace();
        
        if(rover.atGoal()){
            if (current.getMyType() == TileType.HOME_BASE) return;
            else
            {
                rover.setGoal(currentWorld.getTile(0, 0));
                currentWorld.causeRockslide(5);
            }
        }
        
        rover.chooseMove();
        
        next = rover.getCurrentPlace();

        moveRover(next, current);
        
        System.out.println(
            "Next: " + next.getCol() + ", " + next.getRow()
        );
    }
    
}
