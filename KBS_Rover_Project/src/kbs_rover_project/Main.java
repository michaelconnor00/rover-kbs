
package kbs_rover_project;

import java.util.ArrayList;
import javafx.application.Application;
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
    
    private Button buttonRestart;
    private VBox sideBar;
    private GridPane boardGrid;
    private int boardSize = 8;
    private int complexity = 1;
    ArrayList<TextField> newInputs = new ArrayList<>();
    
    @Override 
    public void start(Stage stage) {
        stage.setTitle("KBS Rover");
        
        HBox mainGroup = new HBox();
        mainGroup.setAlignment(Pos.TOP_LEFT);
        
        sideBar = addVBox();
        boardGrid = addGridPane(boardSize, 830.0);
        
        mainGroup.getChildren().add(sideBar);
        mainGroup.getChildren().add(boardGrid);
        
        Scene scene = new Scene(mainGroup);
        
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
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
        
        VBox boardSize = addInput("Board Size", "8", "size");
        VBox obsticalComplexity = addInput(
                "Obstical Complexity Factor", "1", "complex"
        );
        
        // Add more components here is desired.
        
        vbox.getChildren().addAll(buttonRestart, boardSize, obsticalComplexity
        );
        
        vbox.setAlignment(Pos.TOP_CENTER);
        
        return vbox;
    }
    
    public GridPane addGridPane(int size, double screenHeight) {
        // tiles are 128x128 actual size, based on a 8x8 board. 
        // For different size boards, scale image based on 128/9.5 * newSize
        double baseImageHeigth = screenHeight / (double) size;
        
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));

        ImageView newImageView;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newImageView = new ImageView();
                newImageView.setImage(new Image(
                        getClass().getResourceAsStream("resources/rover.png")
                ));
                newImageView.setFitHeight(baseImageHeigth);
                newImageView.setPreserveRatio(true);
                GridPane.setConstraints(newImageView, i, j);
                grid.getChildren().add(newImageView);
            }
        }

        return grid;
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    private void restartClick(){
        String value = null;
        
        //Loop through all nodes of the side bar, read text fields.
        for (TextField tf: newInputs){
//            System.out.println();
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
    }
    
}
