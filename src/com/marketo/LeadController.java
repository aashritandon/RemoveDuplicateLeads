package com.marketo;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class LeadController {

    /*
    @param path - path to the input file
    Reads file, parses into json array
    Json array is then parsed into List<Lead>
    Returns List<Lead>
     */
    public List<Lead> readFile(String path)throws FileNotFoundException {
        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject)jsonParser.parse(new FileReader(path));
        JsonArray jsonArr = jo.getAsJsonArray("leads");
        Gson gson = new Gson();

        Lead[] leads = gson.fromJson(jsonArr, Lead[].class);
        if(leads == null || leads.length == 0)
            return new ArrayList<Lead>(); //return empty list
        List<Lead> leadList = Arrays.asList(leads);
        return leadList;
    }

    /*
    @param leads - filtered list of leads
    @param path - path to the output file, uniqueLeads.json
    Parses leads into json array and writes to the file
     */
    public void writeToFile(List<Lead> leads, String path){
        path += "uniqueLeads.json";
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(leads);
        if(!element.isJsonArray())
            return;
        else{
            JsonArray jsonArray = element.getAsJsonArray();
            JsonObject jsonLeads = new JsonObject();
            jsonLeads.add("leads", jsonArray);
            //Write JSON file
            try (FileWriter file = new FileWriter(path)) {
                gson.toJson(jsonLeads, file);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    Given a list of leads, removes leads with duplicate ids/email, keeps the recently added lead and filters out the old lead
     */
    public List<Lead> filterList(List<Lead> leads, boolean byID, Logger logger){
        List<Lead> list = new ArrayList<>();
        if(leads == null || leads.isEmpty())
            return list; //return empty list
        /*
        HashMap would store (key, value) pair
        When filtering by id, key would be id
        when filtering by email, key would be email
         */
        HashMap<String, Lead> map = new HashMap<>();
        for(Lead lead: leads){
            if(byID){
                if(map.containsKey(lead.get_id())){
                    Lead oldLead = map.get(lead.get_id());
                    if(lead.isRecentThan(oldLead)){
                        logger.info("Duplicate ID found: " + lead.get_id());
                        logger.info("Old Lead: " + oldLead.toString());
                        logger.info("New Lead: " +lead.toString());
                        logger.info("Old Lead's Entry Date: " + oldLead.getEntryDate());
                        logger.info("New Lead's Entry Date: " + lead.getEntryDate());
                        map.put(lead.get_id(), lead); //replace old lead with new lead
                    }
                }
                else{
                    map.put(lead.get_id(),lead);
                }
            } else {
                if(map.containsKey(lead.getEmail())){
                    Lead oldLead = map.get(lead.getEmail());
                    if(lead.isRecentThan(oldLead)){
                        logger.info("Duplicate email found: " + lead.getEmail());
                        logger.info("Old Lead: " + oldLead.toString());
                        logger.info("New Lead: " +lead.toString());
                        logger.info("Old Lead's Entry Date: " + oldLead.getEntryDate());
                        logger.info("New Lead's Entry Date: " + lead.getEntryDate());
                        map.put(lead.getEmail(), lead); //replace old lead with new lead
                    }
                }
                else{
                    map.put(lead.getEmail(),lead);
                }
            }
        }
        // Iterate through the hashmap and put the values in the list
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            list.add((Lead)pair.getValue());
            it.remove();
        }
        return list;
    }
}
