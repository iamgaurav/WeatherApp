import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Weather {
	private String city;
	private String api = "73655885c3b1d211db2836f4ba8a3fb9";
	
	public Weather(String city) {
		this.city = city;
	}
	
	public StringBuffer getWeather() throws ClientProtocolException, IOException {
		String url_parse = "http://api.openweathermap.org/data/2.5/weather?q="+ city +"&APPID="+ api + "&type=accurate"; 
		 
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url_parse);
		
		HttpResponse response = client.execute(request);
		
		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		try {
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public double kevToCel(double value) {
		return value - 273.15f;
	}
	
}
