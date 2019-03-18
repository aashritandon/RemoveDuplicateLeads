package com.marketo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Main {

    private static Logger logger;

    public static void main(String[] args) throws IOException {
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader input = new BufferedReader(reader);

        System.out.println("Enter the path to your json file");
        String filePath = input.readLine();
        if(filePath.isEmpty()){
            System.out.println("Please enter a valid path");
            return;
        }
        String path = initializeLogging(filePath);
        LeadController lc = new LeadController();

        // Read the json file
        List<Lead> leadList = lc.readFile(filePath);
        if(!leadList.isEmpty()){
            List<Lead> leadListUniqueId = lc.filterList(leadList, true, logger); // filter list by ID
            List<Lead> leadListUniqueEmail = lc.filterList(leadListUniqueId, false,logger); // filter list by email
            lc.writeToFile(leadListUniqueEmail, path); // write list to file in JSON format
        }
        else{
            logger.info("No leads found");
            lc.writeToFile(new ArrayList<Lead>(), path);
        }
    }

    /*
    Initialize logging for the application
    Returns path, where output and log file would be placed
     */
    public static String initializeLogging(String filePath)throws IOException{
        String os = System.getProperty("os.name").toLowerCase();
        String path = "";
        if(os.indexOf("win") > 0)
            path = filePath.substring(0, filePath.lastIndexOf('\\')+1);
        else
            path = filePath.substring(0, filePath.lastIndexOf('/')+1);
        FileHandler handler = new FileHandler(path+"log.log", true);
        logger = Logger.getLogger("com.marketo");
        logger.addHandler(handler);
        return path;
    }
}
