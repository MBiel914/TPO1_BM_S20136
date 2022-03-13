/**
 *
 *  @author Bielecki Michał S20136
 *
 */

package zad2;

import java.io.StringWriter;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONObject;

public class Service {
	private static String _countryName;
	private static String _cityName;
	private static String _currencyAcronym;

	private static String host = "http://api.openweathermap.org/data/2.5/weather";
	// Headers for a request
	private static String x_rapidapi_host = "movie-database-imdb-alternative.p.rapidapi.com";
	private static String openweathermap_key = "8560aca6dea319ae8a3a0b8dbdd2464b";

	public Service(String countryName) {
		_countryName = countryName;
	}

	public void insertNewData(String countryName, String cityName, String currencyAcronym) {
		_countryName = countryName;
		_cityName = cityName;
		_currencyAcronym = currencyAcronym;

		System.out.println(_countryName + " " + _cityName + " " + _currencyAcronym);
	}

	public void runService() {
		getWeather(_cityName);
	}

	public String getCountryName() {
		return _countryName;
	}

	public static String getWeather(String cityName) {
		try {
			String apiUrl = host  + "?" + "q=London,uk" + "&appid=" + openweathermap_key;
			System.out.println(apiUrl.toString());
			HttpClient client = HttpClient.newHttpClient();
		    HttpRequest request = HttpRequest.newBuilder()
		          .uri(URI.create(apiUrl))
		          .build();

		    HttpResponse<String> response =
		          client.send(request, BodyHandlers.ofString());
		    JSONObject jsonObject = new JSONObject(response.body());
		    
		    System.out.println(jsonObject.toString());
		    System.out.println(jsonObject.getJSONObject("main").get("humidity").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public static double getRateFor(String currencyName) {
		return 0d;
	}

	public static double getNBPRate() {
		return 0d;
	}
}
