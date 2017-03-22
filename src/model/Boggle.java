package model;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by jouke on 21-3-2017.
 */
public class Boggle {
    Field[][] grid;
    int rows;
    int cols;
    Dictionary dictionary = new Dictionary(new File("src/wordlist.txt"));
    ArrayList<String> wordList = dictionary.getWordList();
    private HashSet<String> foundWords = new HashSet<>();


    public Boggle(int rowSize, int colSize){
        this.rows = rowSize;
        this.cols = colSize;

        grid = new Field[rowSize][colSize];
        for(int row = 0; row < rowSize; row++){
            for(int col = 0; col < colSize; col++){
                grid[row][col] = new Field();
            }
        }
        findNeighbors(rowSize, colSize);
    }

    public Boggle(int gridSize){
       this(gridSize, gridSize);
    }

    /**
     * Get the current grid array
     * @return Field[][]
     */
    public Field[][] getGrid(){
        return grid;
    }

    /**
     * Find all neighbors in the grid
     * Only run this once to find all neighbors
     * @todo maybe use a set? field never has a duplicate then, or reset the fields neighbor list after recalling this method
     * @param rowSize
     * @param colSize
     */
    public void findNeighbors(int rowSize, int colSize){
        for(int row = 0; row < rowSize; row++){
            for(int col = 0; col < colSize; col++){
                Field currentField = grid[row][col];

                //Top
                if(row-1 >= 0 && grid[row-1][col] != null){
                    currentField.addNeighbor(grid[row-1][col]);
                }
                //Right top
                if(row-1 >= 0 && col+1 < colSize && grid[row-1][col+1] != null){
                    currentField.addNeighbor(grid[row-1][col+1]);
                }
                //Left top
                if(row-1 >=0 && col-1 >=0 && grid[row-1][col-1] != null){
                    currentField.addNeighbor(grid[row-1][col-1]);
                }
                //Left
                if(col-1 >= 0 && grid[row][col-1] != null){
                    currentField.addNeighbor(grid[row][col-1]);
                }
                //Right
                if(col+1 < colSize && grid[row][col+1] != null){
                    currentField.addNeighbor(grid[row][col+1]);
                }
                //Under
                if(row+1 < rowSize && grid[row+1][col] != null){
                    currentField.addNeighbor(grid[row+1][col]);
                }
                //Right under
                if(row+1 < rowSize && col+1 < colSize && grid[row+1][col+1] !=null){
                    currentField.addNeighbor(grid[row+1][col+1]);
                }
                //Left under
                if(row+1 < rowSize && col-1 >= 0 && grid[row+1][col-1] != null){
                    currentField.addNeighbor(grid[row+1][col-1]);
                }
            }
        }
    }

    /**
     * Find all possible words in the current grid
     */
    public void solveGrid(){

        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                Field currentField = grid[row][col];

                ArrayList<Field> dissalowedFields = new ArrayList();
                getCombinations(currentField, currentField.getValue(), dissalowedFields);
            }
        }
    }

    /**
     * Debugging purposes
     */
    public void printFoundWords(){
        System.out.println("Found words:");
        for(String word : foundWords){
            System.out.println(word);
        }
    }

    /**
     * Get all combinations starting from a given field
     * Appends a currentString so it can be called recursively for all the fields neighbours
     * @param currentField The field you want to get the combinations from
     * @param stringCombination Can be empty when starting
     * @param disallowedFields Fields that are not allowed to be used for a combination, fields are also added to this list so a combination does not happen twice
     */
    public void getCombinations(Field currentField, String stringCombination, ArrayList disallowedFields){
        //As long as we check a combo this field can't be used again
        disallowedFields.add(currentField);

        if(wordList.contains(stringCombination)){
            foundWords.add(stringCombination);
        }

        for(Field neighbor: currentField.getNeighborList()){
            //Check if the currentfield can be used
            if(!disallowedFields.contains(neighbor)){
                String newStringCombination = stringCombination + neighbor.getValue();
                if(wordList.contains(newStringCombination)){
                    foundWords.add(newStringCombination);
                }
                getCombinations(neighbor, newStringCombination, disallowedFields);
            }
        }

        //Field can be used again for a new combination
        disallowedFields.remove(currentField);
    }

    /**
     * Debugging purposes
     */
    public void printGrid(){
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                System.out.print(grid[row][col].getValue() + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Debugging purposes
     */
    public void printNeighbors(){
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                Field currentField = grid[row][col];

                System.out.print(currentField.getValue() + " neighbors: ");
                for(Field neighbor: currentField.getNeighborList()){
                    System.out.print(neighbor.getValue() + ",");
                }
                System.out.println();
            }
        }
    }
}
