package compatibility_predictor;

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
    	float avgTeamStr, avgTeamEnd, avgTeamInt, difference, score;
    	long totalTeamStr=0, totalTeamEnd=0, totalTeamInt=0;
    	
    	//finds average of team's strength/endurance/intelligence
    	for(int i = 0; i < teamList.length; i++) {
    		totalTeamStr += teamList[i].getStrength();
    		totalTeamEnd += teamList[i].getEndruance();
    		totalTeamInt += teamList[i].getIntelligence();
    	}
    	avgTeamStr = totalTeamStr/teamList.length;
    	avgTeamEnd = totalTeamEnd/teamList.length;
    	avgTeamInt = totalTeamInt/teamList.length;
    	//System.out.println(avgTeamStr+": "+avgTeamEnd+": "+avgTeamInt);
    	
    	//computes score for applicants
    	for(int i = 0; i < applicantsList.length; i++) {
    			//float score = (applicantsList[i].getStrength() / avgTeamStr + 
    			//		applicantsList[i].getEndruance() / avgTeamEnd + 
    			//		applicantsList[i].getIntelligence() / avgTeamInt) / 10;;
    			difference = Math.abs(applicantsList[i].getStrength() - avgTeamStr) + 
    					Math.abs(applicantsList[i].getEndruance() - avgTeamEnd) +
    					Math.abs(applicantsList[i].getIntelligence() - avgTeamInt);
    			score = (10 - difference)/10 ;
    			applicantsList[i].setScore(score);
    			//System.out.println(applicantsList[i].getScore());
    	}
    }

    //function which reads data from a given input json file
    public static void readJson(String file, Employee[] applicantsList, Employee[] teamList) {
        String name = "";
        long strength, endurance, intelligence;
        Object obj = null;
        JSONParser parser = new JSONParser();
		try {
			obj = parser.parse(new FileReader(file));	
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("Error: file not found.");
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Error: io exception.");
		} catch (ParseException e) {
			//e.printStackTrace();
			System.out.println("Error: parse exception.");
		}
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray applicants = (JSONArray) jsonObject.get("applicants");
		JSONArray team = (JSONArray) jsonObject.get("team");
		
		//read applicants from json file
		for(int i = 0; i < applicants.size(); i++) {
			//System.out.println(i+1 + ". " + applicants.get(i));
			JSONObject person = (JSONObject) applicants.get(i);
			JSONObject attrib = (JSONObject) person.get("attributes");
			name = (String) person.get("name");
			strength = (long) attrib.get("strength");
			endurance = (long) attrib.get("endurance");
			intelligence = (long) attrib.get("intelligence");
			
			//creates new employee object and stores in applicantsList[]
			for(int j = 0; j < applicantsList.length; j++) {
				applicantsList[i] = new Employee(name, strength, endurance, intelligence);
			}
		}
		
		//read team members from json file
		for(int i = 0; i < team.size(); i++) {
			//System.out.println(i+1 + ". " + team.get(i));
			JSONObject person = (JSONObject) team.get(i);
			JSONObject attrib = (JSONObject) person.get("attributes");
			name = (String) person.get("name");
			strength = (long) attrib.get("strength");
			endurance = (long) attrib.get("endurance");
			intelligence = (long) attrib.get("intelligence");
			
			//creates new employee object and stores in teamList[]
			for(int j = 0; j < teamList.length; j++) {
				teamList[i] = new Employee(name, strength, endurance, intelligence);
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
    }
}