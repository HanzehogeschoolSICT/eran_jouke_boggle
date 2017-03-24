package model;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by jouke on 14-3-2017.
 */
public class ChooseFile {
    final JFileChooser fc;
    File file;

    /**
     * Init
     */
    public ChooseFile(){
        fc = new JFileChooser();
        fc.setDialogTitle("Choose word file");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new TextFilter());
        fc.setCurrentDirectory(new File("."));
    }

    /**
     * Manually set the file(trough code)
     */
    public void setFile(File f){
        file = f;
    }

    /*
     * Open a file
     */
    public void openFile(){
        int returnVal = fc.showOpenDialog(fc);

        if(returnVal == JFileChooser.CANCEL_OPTION){
            //@todo close application when canceled?
        }else if(returnVal == JFileChooser.APPROVE_OPTION){
            file = fc.getSelectedFile();
        }
    }

    /**
     * Convert a file to a array list containing words
     * Every newline is a new word
     * @return
     */
    public ArrayList<String> getChosenFileInList(){
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String currentLine = null;
            try {
                while ((currentLine = bufferedReader.readLine()) != null) {
                    list.add(currentLine);
                }
            }catch (IOException i){
                System.out.println(i.getStackTrace());
            }
        }catch (FileNotFoundException f){
            System.out.println(f.getStackTrace());
        }
        return list;
    }
}

/*
 * Source: http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
 */
class TextFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.txt)){
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

/**
 * Source: http://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
 */
class Utils {

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public final static String txt = "txt";

    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}