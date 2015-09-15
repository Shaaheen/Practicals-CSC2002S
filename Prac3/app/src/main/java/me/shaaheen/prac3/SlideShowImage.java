package me.shaaheen.prac3;

/**
 * Created by Shaaheen on 9/10/2015.
 */
public class SlideShowImage {
    private String fileName;   //The name of the picture
    private int drawablePath;  //The backup loading path of the image
    private String description; //The description of the image
    private boolean whiteOrBlack; //The color of the text description to be displayed for that picture

    /*
        Constructor for all necessary variables of an image
     */
    public SlideShowImage(String fName,int drawable,String descrip,boolean white){
        this.fileName = fName;
        this.drawablePath = drawable;
        this.description = descrip;
        this.whiteOrBlack = white;
    }

    /*
        Gets color of text
     */
    public boolean white(){
        return whiteOrBlack;
    }

    public String getFileName(){
        return fileName;
    }

    /*
        Gets the description given to the image
     */
    public String getDescription(){
        return description;
    }

    /*
        Used incase internal storage path couldn't be found
     */
    public int getDrawablePath(){
        return drawablePath;
    }



}
