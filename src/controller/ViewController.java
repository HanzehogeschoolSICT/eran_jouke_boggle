package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import model.Boggle;
import model.ChooseFile;
import model.Field;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ViewController implements Initializable {
    @FXML private GridPane gridPane;
    private int gridSize = 4;
    @FXML private HBox hBox;
    @FXML private TextArea foundWordsField;
    private ArrayList<String> wordList;
    private ChooseFile chooseFile;

    Boggle boggle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        foundWordsField.setEditable(false);
        foundWordsField.setMouseTransparent(true);
        foundWordsField.setFocusTraversable(false);

        boggle = new Boggle(gridSize);

        //Fix hbox position
        gridPane.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)->{
            hBox.setLayoutX(gridPane.getLayoutX() + 16);
            hBox.setLayoutY(gridPane.getLayoutY() + gridPane.getHeight() + 16);

        });
        gridPane.heightProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)->{
            hBox.setLayoutX(gridPane.getLayoutX() + 16);
            hBox.setLayoutY(gridPane.getLayoutY() + gridPane.getHeight() + 16);
        });

        System.out.println("Loaded our main view");
        init(gridSize);
    }

    /**
     * Solve the boggle
     */
    public void solveBoggle() {
        //Clear textarea and set default text
        foundWordsField.clear();
        foundWordsField.appendText("Found words: \n");

        boggle.solveGrid();

        //Update textarea
        for(String foundWord : boggle.getFoundWords()) {
            this.foundWordsField.appendText(foundWord + "\n");
        }

    }

    public void initColumns(int columns){
        for(int i = 0; i < columns; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setHgrow(Priority.SOMETIMES); //resize to screen
            gridPane.getColumnConstraints().add(column);
        }
    }

    public void initRows(int rows){
        for(int i = 0; i < rows; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.SOMETIMES);
            gridPane.getRowConstraints().add(row);
        }
    }

    public void initBoard(int gridSize){

        Field[][] fields = boggle.getGrid();

        for(int x = 0; x < fields.length; x++) {
            for(int y = 0; y < fields[x].length; y++) {
                Pane pane = new Pane();
                pane.setStyle(
                        "-fx-background-color:white;" +
                                "-fx-border-color: black;" +
                                "-fx-border-width: 1 1 1 1;");

                Label label = new Label(fields[x][y].getValue());

                pane.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)->{
                    label.setLayoutX(20);
                    double lowestValue = Math.min(pane.getWidth(), pane.getHeight());
                    label.setFont(Font.font(lowestValue/1.5));
                });
                pane.heightProperty().addListener((observableValue, oldSceneWidth, newSceneWidth)->{
                    label.setLayoutX(20);
                    double lowestValue = Math.min(pane.getWidth(), pane.getHeight());
                    label.setFont(Font.font(lowestValue/1.5));
                });

                pane.getChildren().add(label);
                gridPane.add(pane, y, x);

            }
        }
    }

    /**
     * Initalize the GridPane
     * @param gridSize
     */
    public void init(int gridSize){
        initColumns(gridSize);
        initRows(gridSize);
        initBoard(gridSize);
    }
}
