package zad2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUIApp extends Application {
	private static Service _service;

	Label labelInsertCountryName;
	Label labelInsertCityName;
	Label labelInsertCurrencyAcronym;
	Label labelWeather;

	TextField textFieldCountryName;
	TextField textFieldCityName;
	TextField textFieldCurrencyAcronym;

	Button buttonSearchForData;

	@Override
	public void start(Stage stage) {
		labelInsertCountryName = new Label("Please insert country name:");
		labelInsertCityName = new Label("Please insert city name:");
		labelInsertCurrencyAcronym = new Label("Please insert currency acronym:");
		labelWeather = new Label("Weather:");

		textFieldCountryName = new TextField(_service.getCountryName());
		textFieldCityName = new TextField("Warsaw");
		textFieldCurrencyAcronym = new TextField("USD");

		buttonSearchForData = new Button("Search for Data");
		buttonSearchForData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				_service.insertNewData(textFieldCountryName.getText(), textFieldCityName.getText(), textFieldCurrencyAcronym.getText());
				_service.runService();
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
//		gridPane.add(button1, 0, 2);
//		gridPane.add(button2, 1, 2);

		Scene scene = new Scene(gridPane);
		stage.setTitle("CSS Example");
		stage.setScene(scene);
		stage.show();
	}

	public static void launch(Class<GUIApp> class1, String[] args, Service s) {
		try {
			_service = s;
		} catch (Exception e) {
			e.printStackTrace();
		}

		launch(class1, args);
	}
}
