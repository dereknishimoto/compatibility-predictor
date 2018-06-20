package aaa;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Program extends Employee {

	//functions which takes two Employee arrays and scores each applicant for compatibility
    public static void compareApplicants(Employee[] applicantsList, Employee[] teamList) {
    	float avgTeamStr, avgTeamEnd, avgTeamInt, avgTeamBrv, difference, score;
    	long totalTeamStr=0, totalTeamEnd=0, totalTeamInt=0, totalTeamBrv=0;
    	
    	//finds average of team's strength/endurance/intelligence/bravery
    	for(int i = 0; i < teamList.length; i++) {
    		totalTeamStr += teamList[i].getStrength();
    		totalTeamEnd += teamList[i].getEndruance();
    		totalTeamInt += teamList[i].getIntelligence();
    		totalTeamBrv += teamList[i].getBravery();
    	}
    	avgTeamStr = totalTeamStr/teamList.length;
    	avgTeamEnd = totalTeamEnd/teamList.length;
    	avgTeamInt = totalTeamInt/teamList.length;
    	avgTeamBrv = totalTeamBrv/teamList.length;
    	
    	//computes score for applicants
    	for(int i = 0; i < applicantsList.length; i++) {
    			difference = Math.abs(applicantsList[i].getStrength() - avgTeamStr) + 
    					Math.abs(applicantsList[i].getEndruance() - avgTeamEnd) +
    					Math.abs(applicantsList[i].getIntelligence() - avgTeamInt) +
    					Math.abs(applicantsList[i].getBravery() - avgTeamBrv);
    			score = (10 - difference/2)/10 ;
    			applicantsList[i].setScore(score);
    	}
    }

    //function which reads data from a given input json file
    public static void readJson(String file, Employee[] applicantsList, Employee[] teamList) {
        String name = "";
        long strength, endurance, intelligence, bravery;
        Object obj = null;
        JSONParser parser = new JSONParser();
		try {
			obj = parser.parse(new FileReader(file));	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray applicants = (JSONArray) jsonObject.get("applicants");
		JSONArray team = (JSONArray) jsonObject.get("team");
		
		//read applicants from json file
		for(int i = 0; i < applicants.size(); i++) {
			JSONObject person = (JSONObject) applicants.get(i);
			JSONObject attrib = (JSONObject) person.get("attributes");
			name = (String) person.get("name");
			strength = (long) attrib.get("strength");
			endurance = (long) attrib.get("endurance");
			intelligence = (long) attrib.get("intelligence");
			bravery = (long) attrib.get("bravery");
			
			//creates new employee object and stores in applicantsList[]
			for(int j = 0; j < applicantsList.length; j++) {
				applicantsList[i] = new Employee(name, strength, endurance, intelligence, bravery);
			}
		}
		
		//read team members from json file
		for(int i = 0; i < team.size(); i++) {
			JSONObject person = (JSONObject) team.get(i);
			JSONObject attrib = (JSONObject) person.get("attributes");
			name = (String) person.get("name");
			strength = (long) attrib.get("strength");
			endurance = (long) attrib.get("endurance");
			intelligence = (long) attrib.get("intelligence");
			bravery = (long) attrib.get("bravery");
			
			//creates new employee object and stores in teamList[]
			for(int j = 0; j < teamList.length; j++) {
				teamList[i] = new Employee(name, strength, endurance, intelligence, bravery);
			}
		}
    }
    
    //writes data from Employee[] to json file
    @SuppressWarnings("unchecked")
	public static void writeJson(String filename, Employee[] applicantsList) {
    	JSONObject employeeDetails = new JSONObject(), scoredApplicants = new JSONObject();
    	JSONArray applicants = new JSONArray();
    	
    	//creates json objects to input data
    	for(int i = 0; i < applicantsList.length; i++) {
    		employeeDetails = new JSONObject();
    		employeeDetails.put("score", applicantsList[i].getScore());
    		employeeDetails.put("name", applicantsList[i].getName());
    		applicants.add(employeeDetails);
    	}
    	scoredApplicants.put("scoredApplicants", applicants);

    	//create new output.json and write data
    	try {
    		Gson prettyPrint = new GsonBuilder().setPrettyPrinting().create();
    		String output = prettyPrint.toJson(scoredApplicants);
			FileWriter file = new FileWriter(filename);
			file.write(output);
			//scoredApplicants.writeJSONString(file);
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //main. reads data from json file, computes score, writes data to json file
    public static void main(String[] args) {
    	String inputFile = "employee.json", outputFile = "output.json";
    	Employee[] applicants = new Employee[3];
    	Employee[] team = new Employee[3];
        readJson(inputFile, applicants, team);
        compareApplicants(applicants, team);
        writeJson(outputFile, applicants);
        System.out.println("done");
    }
}