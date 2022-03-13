/**
 *
 *  @author Bielecki Michał S20136
 *
 */

package zad2;

public class Main {
	public static void main(String[] args) {
		Service s = new Service("Poland");
		String weatherJson = s.getWeather("Warsaw");
		Double rate1 = s.getRateFor("USD");
		Double rate2 = s.getNBPRate();
		
		GUIApp.launch(GUIApp.class, args, s);
	}
}
