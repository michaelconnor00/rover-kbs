
package kbs_rover_project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    
    @Override 
    public void start(Stage stage) {
        stage.setTitle("KBS Rover");
        
        HBox mainGroup = new HBox();
        mainGroup.setAlignment(Pos.TOP_LEFT);
        
        mainGroup.getChildren().add(addVBox());
        mainGroup.getChildren().add(addGridPane(16, 830.0));
        
        Scene scene = new Scene(mainGroup);
        
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    
    private VBox addInput(String label, String defValue){
        VBox vbox = new VBox();
        vbox.setPrefWidth(150);
        vbox.setSpacing(7);   // Gap between nodes
        
        Label sizeLabel = new Label(label);
        sizeLabel.setWrapText(true);
        sizeLabel.setTextAlignment(TextAlignment.CENTER);
        TextField sizeTextField = new TextField(defValue);
        sizeTextField.setAlignment(Pos.CENTER);
        
        vbox.getChildren().addAll(sizeLabel, sizeTextField);
        
        return vbox;
    }
    
    private VBox addVBox(){
        VBox vbox = new VBox();
        vbox.setPrefWidth(150);
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);   // Gap between nodes
        
        Button buttonCurrent = new Button("Restart");
        buttonCurrent.setPrefSize(100, 20);
        
        VBox boardSize = addInput("Board Size", "8");
        VBox obsticalComplexity = addInput("Obstical Complexity Factor", "1");
        
        // Add more components here is desired.
        
        vbox.getChildren().addAll(
                buttonCurrent, boardSize, obsticalComplexity
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
                newImageView.setImage(new Image(getClass().getResourceAsStream("resources/rover.png")));
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
    
    
}
