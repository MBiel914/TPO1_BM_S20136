package zad2;

import javafx.application.Application;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class GUIApp extends Application {
	private static Service _service;
	private static String[] _args;
	
	Label labelInsertCountryName;
	Label labelInsertCityName;
	Label labelInsertCurrencyAcronym;
	Label labelWeather;
	Label labelWeatherFullPath;
	Label labelHumidity;
	Label labelCurrency;
	Label labelRateForCurrency;
	Label labelNBPRate;

	TextField textFieldCountryName;
	TextField textFieldCityName;
	TextField textFieldCurrencyAcronym;
	TextField textFieldWeatherFullPathData;
	TextField textFieldHumidityData;
	TextField textFieldRateForCurrencyData;
	TextField textFieldNBPRateData;

	Button buttonSearchForData;
	Button buttonOpenMyOwnBrowser;

	@Override
	public void start(Stage stage) {
		labelInsertCountryName = new Label("Please insert country name:");
		labelInsertCityName = new Label("Please insert city name:");
		labelInsertCurrencyAcronym = new Label("Please insert currency acronym:");
		labelWeather = new Label("Weather:");
		labelWeatherFullPath = new Label("Weather full path:");
		labelHumidity = new Label("Humidity:");
		labelCurrency = new Label("Currency:");
		labelRateForCurrency = new Label("Rate for 1 USD:");
		labelNBPRate = new Label("NBP rate for PLN:");

		textFieldCountryName = new TextField(_service.getCountryName());
		textFieldCityName = new TextField("Warsaw");
		textFieldCurrencyAcronym = new TextField("USD");
		textFieldWeatherFullPathData = new TextField(_service.runGetWeather());
		textFieldHumidityData = new TextField(_service.getWeatherField("humidity"));
		textFieldRateForCurrencyData = new TextField(_service.runGetRateFor());
		textFieldNBPRateData = new TextField(Double.toString(_service.getNBPRate()));
		
		buttonSearchForData = new Button("Search for Data");
		buttonSearchForData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (textFieldCurrencyAcronym.getText().isEmpty())
					textFieldCurrencyAcronym.setText("PLN");
				
				if (textFieldCountryName.getText().isEmpty())
					textFieldCountryName.setText("Poland");
				
				if (textFieldCityName.getText().isEmpty())
					textFieldCityName.setText("Warsaw");
				
				_service.insertNewData(textFieldCountryName.getText(), textFieldCityName.getText(),
						textFieldCurrencyAcronym.getText());

				textFieldWeatherFullPathData.setText(_service.runGetWeather());
				textFieldHumidityData.setText(_service.getWeatherField("humidity"));

				labelRateForCurrency.setText("Rate for 1 " + textFieldCurrencyAcronym.getText().toUpperCase() + ":");
				labelNBPRate.setText("NBP rate for " + _service.getCountryCurrency() + ":");

				textFieldRateForCurrencyData.setText(_service.runGetRateFor());
				textFieldNBPRateData.setText(Double.toString(_service.getNBPRate()));
			}
		});
		
		buttonOpenMyOwnBrowser = new Button("Open Wiki");
		buttonOpenMyOwnBrowser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
//				StackPane secondaryLayout = new StackPane();
//
//				Scene secondScene = new Scene(secondaryLayout, 230, 100);
//
//				Stage newWindow = new Stage();
//				newWindow.setTitle("Second Stage");
//				newWindow.setScene(secondScene);
//				
//				WebEngine webEngine = new WebEngine();
//			    webEngine.getLoadWorker().stateProperty()
//			        .addListener((obs, oldValue, newValue) -> {
//			          if (newValue == State.SUCCEEDED) {
//			            System.out.println("finished loading");
//			            org.w3c.dom.Document   xmlDom  = webEngine.getDocument();
//			            System.out.println(xmlDom);
//			          }
//			        });
//
//			    webEngine.load("https://en.wikipedia.org/wiki/" + textFieldCityName.getText());
//
//			    Group root = new Group();
//			    Scene scene = new Scene(root, 300, 250);
//
//			    newWindow.setScene(scene);
//			    newWindow.show();
			}
		});

		GridPane gridPane = new GridPane();

		gridPane.setMinSize(400, 200);

		gridPane.setPadding(new Insets(2, 2, 2, 2));

		gridPane.setVgap(5);
		gridPane.setHgap(5);

		gridPane.setAlignment(Pos.TOP_LEFT);

		gridPane.add(labelInsertCountryName, 0, 0);
		gridPane.add(labelInsertCityName, 0, 1);
		gridPane.add(labelInsertCurrencyAcronym, 0, 2);

		gridPane.add(textFieldCountryName, 1, 0);
		gridPane.add(textFieldCityName, 1, 1);
		gridPane.add(textFieldCurrencyAcronym, 1, 2);

		gridPane.add(buttonSearchForData, 1, 3);

		gridPane.add(labelWeather, 0, 4);
		gridPane.add(labelWeatherFullPath, 0, 5);
		gridPane.add(textFieldWeatherFullPathData, 1, 5);
		gridPane.add(labelHumidity, 0, 6);
		gridPane.add(textFieldHumidityData, 1, 6);

		gridPane.add(labelCurrency, 0, 7);
		gridPane.add(labelRateForCurrency, 0, 8);
		gridPane.add(textFieldRateForCurrencyData, 1, 8);
		gridPane.add(labelNBPRate, 0, 9);
		gridPane.add(textFieldNBPRateData, 1, 9);
		
		gridPane.add(buttonOpenMyOwnBrowser, 1, 10);

		Scene scene = new Scene(gridPane);
		stage.setTitle("CSS Example");
		stage.setScene(scene);
		stage.show();
	}

	public static void launch(Class<GUIApp> class1, String[] args, Service s) {
		try {
			_args = args;
			_service = s;
		} catch (Exception e) {
			e.printStackTrace();
		}

		launch(class1, args);
	}
}
