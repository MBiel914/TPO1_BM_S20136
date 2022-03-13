/**
 *
 *  @author Bielecki Michał S20136
 *
 */

package zad2;

import java.io.StringWriter;
import org.json.simple.JSONObject;

public class Service {
	private static String _countryName;
	private static String _cityName;
	private static String _currencyAcronym;

	private static String host = "api.openweathermap.org/data/2.5/weather";
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
			JSONObject obj = new JSONObject();

			obj.put("name", "foo");
			obj.put("num", new Integer(100));
			obj.put("balance", new Double(1000.21));
			obj.put("is_vip", new Boolean(true));

			StringWriter out = new StringWriter();
			obj.writeJSONString(out);

			String jsonText = out.toString();
			System.out.print(jsonText);
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
