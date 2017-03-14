package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class ViewController implements Initializable {
    @FXML private GridPane gridPane;
    private NumberBinding gridWidthHeight;
    private int gridSize = 3;
    @FXML private AnchorPane anchorPane;
    private ArrayList<String> currentWord;
    private ArrayList<Pane> selectedPanes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.currentWord = new ArrayList<>();
        this.selectedPanes = new ArrayList<>();

        System.out.println("Loaded our main view");
        initGridPane(gridSize);
    }

    /**
     * Get a random character from the alphabet
     * @return A random character from the alphabet
     */
    public char getRandomCharacter(){
        Random random = new Random();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        return alphabet.charAt(random.nextInt(alphabet.length()));
    }

    //We know that a pane in this case is always 'unique'
    public boolean isAlreadySelected(Pane currentPane){
        //Check if pane is already selected
        for(Pane p: selectedPanes){
            if(currentPane.equals(p)){
                return true;
            }
        }
        return false;
    }

    public void selectItem(Pane pane, Label label){
        //Color pane red when selected
        pane.setStyle(
                "-fx-border-color:black;"+
                "-fx-background-color:red;");
        //Add to selected collection
        selectedPanes.add(pane);
        currentWord.add(label.getText()); //@todo will bug when deleting in different sequence
    }

    public void deselectItem(Pane pane, Label label){
        //Color pane back to original colors
        pane.setStyle(
                "-fx-border-color:black;"+
                        "-fx-background-color:white;");
        //Add to selected collection
        selectedPanes.remove(pane);
        currentWord.remove(label.getText()); //@todo will bug when deleting in different sequence
    }

    /**
     * Initalize the GridPane
     * @param gridSize
     */
    public void initGridPane(int gridSize){
        int columns = gridSize;
        int rows = gridSize;

        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.SOMETIMES); //resize to screen
            gridPane.getColumnConstraints().add(column);
        }
        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(row);
        }

        for(int col=0; col<gridSize; col++){
            for(int row=0; row<gridSize; row++){
                Pane pane = new Pane();
                pane.setStyle(
                        "-fx-background-color:white;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 1 1 1 1;");

                //Position label in center of pane
                Label label = new Label(String.valueOf(getRandomCharacter()));
                label.layoutXProperty().bind(pane.widthProperty().subtract(pane.widthProperty().divide(2)));
                label.layoutYProperty().bind(pane.heightProperty().subtract(pane.heightProperty().divide(2)));



                pane.getChildren().add(label);

                //Add listeners to this pain
                //When this pain is clicked
                pane.setOnMouseClicked(e->{
//                    System.out.println("Row: "+ GridPane.getRowIndex(pane));
//                    System.out.println("column: "+ GridPane.getColumnIndex(pane));


                    //get label
                    for(Node node: pane.getChildren()){
                        if(node instanceof Label){
                            //Get the label content
                            Label currentLabel = (Label) node;
//                            System.out.println(currentLabel.getText());

                            //Select or deselect item
                            if(!isAlreadySelected(pane)){
                                selectItem(pane, currentLabel);
                            }else{
                                deselectItem(pane, currentLabel);
                            }
                        }
                    }

                    System.out.println(currentWord);
                });

                gridPane.add(pane,col,row);
            }
        }
    }
}
