/**
 *
 *  @author Bielecki Michał S20136
 *
 */

package zad2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class Service {
	private static String _countryName;
	private static String _countryCode;
	private static String _cityName;
	private static String _currencyAcronym;

	private static Map<String, String> countries = new HashMap<>();

	private static String host = "http://api.openweathermap.org/data/2.5/weather";
	private static String openweathermap_key = "8560aca6dea319ae8a3a0b8dbdd2464b";

	public Service(String countryName) {
		getListOfCountries();

		_countryName = countryName;
		_countryCode = countries.get(countryName);
	}

	private void getListOfCountries() {
		for (String isoCountries : Locale.getISOCountries()) {
			Locale locale = new Locale("", isoCountries);
			countries.put(locale.getDisplayCountry(), isoCountries);
		}
	}

	public void insertNewData(String countryName, String cityName, String currencyAcronym) {
		_countryName = countryName;
		_cityName = cityName;
		_currencyAcronym = currencyAcronym;
	}

	public String getWeatherField(String param) {
		try {
			JSONObject jsonObject = new JSONObject(getWeather(_cityName));
			return jsonObject.getJSONObject("main").get(param).toString() + " %";
		} catch (JSONException e) {
			return "";
		}
	}

	public String runGetWeather() {
		_countryCode = countries.get(_countryName);
		return getWeather(_cityName);
	}

	public String getCountryName() {
		return _countryName;
	}

	public static String getWeather(String cityName) {
		_cityName = cityName;

		JSONObject jsonObject = new JSONObject();
		try {
			String apiUrl = host + "?" + "q=" + _cityName + "," + _countryCode + "&appid=" + openweathermap_key;
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl)).build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			jsonObject = new JSONObject(response.body());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonObject.toString();
	}

	public static String runGetRateFor() {
		return Double.toString(getRateFor(_currencyAcronym));
	}

	public static double getRateFor(String currencyAcronym) {
		_currencyAcronym = currencyAcronym;

		String url_str = "https://api.exchangerate.host/convert?from=PLN&to=" + currencyAcronym;
		JSONObject jsonObject;

		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url_str)).build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			jsonObject = new JSONObject(response.body());

			String req_result = jsonObject.get("result").toString();
			return Double.parseDouble(req_result);
		} catch (IOException | JSONException | InterruptedException e) {
			return 0d;
		}
	}

	public static String getCountryCurrency() {
		Currency currency = Currency.getInstance(new Locale(_countryName, _countryCode));
		return currency.getCurrencyCode();
	}

	private static Matcher nbpCurrencyTableA() {
		Currency currency = Currency.getInstance(new Locale(_countryName, _countryCode));
		URL url;

		try {
			url = new URL("http://www.nbp.pl/kursy/kursya.html");
			Scanner sc = new Scanner(url.openStream());
			StringBuffer sb = new StringBuffer();
			while (sc.hasNext()) {
				sb.append(sc.next());
			}
			String result = sb.toString();

			Pattern pattern = Pattern.compile(currency.getCurrencyCode() + "<\\/td><tdclass=\"right\">.,....",
					Pattern.CASE_INSENSITIVE);

			return pattern.matcher(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Matcher nbpCurrencyTableB() {
		Currency currency = Currency.getInstance(new Locale(_countryName, _countryCode));
		URL url;

		try {
			url = new URL("http://www.nbp.pl/kursy/kursya.html");
			Scanner sc = new Scanner(url.openStream());
			StringBuffer sb = new StringBuffer();
			while (sc.hasNext()) {
				sb.append(sc.next());
			}
			String result = sb.toString();

			Pattern pattern = Pattern.compile(currency.getCurrencyCode() + "<\\/td><tdclass=\"right\">.,....",
					Pattern.CASE_INSENSITIVE);

			return pattern.matcher(result);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static double getNBPRate() {
		Currency currency = Currency.getInstance(new Locale(_countryName, _countryCode));

		try {
			Matcher matcher = nbpCurrencyTableA();
			boolean matchFound = matcher.find();
			if (matchFound) {
				return Double.parseDouble(matcher.group().substring(matcher.group().length() - 6).replace(',', '.'));
			} else {
				matcher = nbpCurrencyTableB();
				matchFound = matcher.find();

				if (matchFound) {
					return Double
							.parseDouble(matcher.group().substring(matcher.group().length() - 6).replace(',', '.'));
				} else {
					if (currency.getSymbol().equals("PLN"))
						return 1d;
					return 0d;
				}
			}
		} catch (Exception e) {
			return 0d;
		}
	}
}
