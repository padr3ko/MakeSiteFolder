/*Author: Patrick Furbert
Date: March 13, 2018
ReadMe: This script creates a Folder Structure for an HTML5 website.
It includes folders for CSS, JS, and an Imagefolder - with script.js and
style.css linked. Also The production build of jQuery version 3.3.1
is included and linked. The script also opens sublime_text.exe(it should be in
windows system path variable to work). The jQuery File is linked manifest.mf
Class-Path: res/jquery-3.3.1.min.js
*/


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MakeSite {
    public static void main(String[] args){
        //strings
        String mainFolder, websiteName, indexName, imgFolderName, jsFolderName, scriptFileName,
                jqueryFileName, cssFolderName, styleSheetName, htmlFormat, scriptFormat;
        mainFolder = "";
        indexName = "index.html";
        jsFolderName = "js";
        imgFolderName = "img";
        scriptFileName = "script.js";
        jqueryFileName = "jquery-3.3.1.min.js";
        cssFolderName = "css";
        styleSheetName = "style.css";


        //if user provides arguments we change the website name to the first argument
        if(args.length  == 2){
            websiteName = args[0];
            mainFolder = args[1];

        }else{
        //default names website
            websiteName = "MySite";
        }

        //htmlFormat which will be inserted into index.html
        htmlFormat =  "<!DOCTYPE html>\n" +
                      "<html>\n\n" +
                      "\t<head>\n" +
                      "\t\t<title>" + websiteName + "</title>\n"+
                      "\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"css/" + styleSheetName + "\">\n" +
                      "\t\t<script type=\"text/javascript\" src=\"js/" + scriptFileName +"\"></script>\n"+
                      "\t\t<script type=\"text/javascript\" src=\"js/" + jqueryFileName +"\"></script>\n"+
                      "\t</head>\n\n" +
                      "\t<body>\n\n" +
                      "\t</body>\n" +
                      "</html>";

        //scriptFormat which will be inserted into the script.js(uses jQuery)
        scriptFormat = "$(document).ready(function(){\n\n//insert jQuery stuff here...\n\n" +

                "\t});";

        try {

            //**Note**
            //getting a reasource packed in a jar and pulling it
            //out as a file requires use of getReasourceAsStream.
            //Create the byte array and use inputStream.available()
            //to get the size. Read the buffer?(not sure if this is requierd
            //Then write the file via outputstream with the new FileOutPutStream
            //constructor and the file object as an argument
            InputStream inputStream = MakeSite.class.getResourceAsStream("res/" + jqueryFileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);

            //website folder structure will be created where the script is located
            //Files and folders for website
            File websiteFolder;

            if(!(args.length == 2)) {
                 websiteFolder = new File(websiteName);
            }
            else {
                 websiteFolder = new File(mainFolder + "/" + websiteName);
            }

            //create file/folder objects
            File imgF = new File(websiteFolder.getAbsolutePath() + "/" + imgFolderName);
            File jsF = new File(websiteFolder.getAbsolutePath() + "/" +jsFolderName);
            File csF = new File(websiteFolder.getAbsolutePath() + "/" +cssFolderName);
            File index = new File(websiteFolder.getAbsolutePath() + "/" +indexName);
            File script = new File(jsF.getAbsolutePath() + "/" +scriptFileName);
            File jQF = new File(jsF.getAbsolutePath() + "/" +jqueryFileName);
            File style = new File(csF.getAbsolutePath() + "/" +styleSheetName);


            //first create the baseFolder if it doesn't exist already
            if(!websiteFolder.exists()) {
                websiteFolder.mkdir();

                //create the remaining files/folders
                imgF.mkdir();
                jsF.mkdir();
                csF.mkdir();
                index.createNewFile();
                script.createNewFile();
                style.createNewFile();

                //copy the included jQueryFile into the jQF file object
                OutputStream outputStream = new FileOutputStream(jQF);
                outputStream.write(buffer);

                //create the jQuery.js file
                jQF.createNewFile();

                //Write the html to the index.html file and the jQuery to the scripts.js file
                Files.write(index.toPath(), htmlFormat.getBytes());
                Files.write(script.toPath(), scriptFormat.getBytes());



                //open the files in sublime text
               Process child = Runtime.getRuntime().exec("sublime_text.exe \"" +
                                                            index.getAbsolutePath() + "\" \"" +
                                                            script.getAbsolutePath() + "\" \"" +
                                                            style.getAbsolutePath()+"\"");




            }//end if statement


        }catch(Exception ex){
            ex.printStackTrace();

        }


    }
}