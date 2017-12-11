import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import opennlp.tools.namefind.*;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class VirtualAssistant {

	public static void main(String[] args) throws IOException, ParseException {
		boolean flag = true;
		while(flag) {
			//Loading the NER-person model 
			InputStream inputStreamTokenizer = new FileInputStream("src/model/en-token.bin");
		    TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer); 
			
		    TokenizerME tokenizer = new TokenizerME(tokenModel); 
		    Scanner keyword = new Scanner(System.in);
		    System.out.println("Enter the sentence");
		    String sentence = keyword.nextLine(); 
		    
		    
		    String tokens[] = tokenizer.tokenize(sentence); 	
		    
		   
		    InputStream inputStreamNameFinder = new FileInputStream("src/model/en-ner-location.bin");       
		    TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);
		       
		    NameFinderME nameFinder = new NameFinderME(model);       
		       
		    Span nameSpans[] = nameFinder.find(tokens);        
		    String city = "Toronto";
		    for(Span s: nameSpans)   {     
		         city = tokens[s.getStart()];
		         System.out.println(city);
		     }
		 
		    if(!city.isEmpty()) {
		    		System.out.println("This is the city: " + city);
		    		Weather weather = new Weather(city);
		    		
		    		StringBuffer result = weather.getWeather();
		    		// Parse Json data from the API
		    		JSONParser parser = new JSONParser();
		    		Object jsonObj = parser.parse(result.toString());
		    		JSONObject jsonObject = (JSONObject) jsonObj;
		    		
		    		// Pick out weather information
		    		JSONObject weather_info = (JSONObject) jsonObject.get("main");
		    		
		    		// Convert from kelvin to celsius
		    		Double temp = weather.kevToCel((Double) weather_info.get("temp"));
		    		Double temp_min = weather.kevToCel((Double) weather_info.get("temp_min")); 
		    		Double temp_max = weather.kevToCel((Double) weather_info.get("temp_max"));  
		    		// Display the Information
		    		System.out.printf("%.2f is the average temperature.\n%.2f is the lowest temperature and %.2f is the highest temperature", temp, temp_min, temp_max);
		    		System.out.println();
		    }
		    int close = keyword.nextInt();
		    if(close < 8) {
		    		flag = false;
		    }
	   
		}
	}

}
