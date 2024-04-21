package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

public class User {
//	private static DoubleLinkedList brandList;
	public static DoubleLinkedList list;
	static brandNode current = list.getFirst();
	public static TableView<Car> table = new TableView<>();
	private ComboBox<Brand> brandComboBox = new ComboBox<>();
	private ObservableList<DoubleLinkedList> brands;
	static Label brandl = new Label("Brand:");
	//-------
	private FilteredList<Car> filteredData;
	private List<Car> carList = new ArrayList<>();
	private int currentBrandIndex = 0;
	//----------
	private CheckBox greyCheckBox, blackCheckBox, whiteCheckBox, greenCheckBox, blueCheckBox, redCheckBox;
	private CheckBox year2000to2010CheckBox, year2010to2018CheckBox, year2018to2020CheckBox, year2020to2023CheckBox;
	private CheckBox c200CheckBox, c300CheckBox, xCheckBox, x3CheckBox, x5CheckBox, x6CheckBox, mustangCheckBox,
			rioCheckBox, optimaCheckBox;
	private TextField brandTextField;
	public static Stage ustage;
	public static Scene uscene;

	Stage userp() {
		ustage = new Stage(StageStyle.DECORATED);
		SingleLinkedList singleLinkedList = new SingleLinkedList();
		BorderPane root = new BorderPane();
	//	VBox filtersBox = createFiltersBox();
		// Create Label for brand name
//		Label brandl = new Label("Brand:");
		TextField brandt = new TextField();
		brandt.setEditable(false);
		brandt.setMinWidth(200);

		// Center section - Car details table view
		TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
		modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

		TableColumn<Car, Integer> yearColumn = new TableColumn<>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

		TableColumn<Car, String> colorColumn = new TableColumn<>("Color");
		colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

		TableColumn<Car, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

		table.getColumns().addAll(modelColumn, yearColumn, colorColumn, priceColumn);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		// Read data from file and populate the car list
				readFile();

				// Set the initial brand in the brand text field and populate the table
	//			setBrand(currentBrandIndex);
		// Create buttons for navigation
		
		
//		brandComboBox.setMinWidth(200);
//		brandComboBox.setOnAction(event -> {
//			Brand selectedBrand = brandComboBox.getValue();
//			if (selectedBrand != null) {
//				// selectBrand(selectedBrand);
//			}
//		});
//		FlowPane bottomSection = new FlowPane();
//		bottomSection.setPadding(new Insets(10));
//		bottomSection.getChildren().addAll(prev, brandComboBox, next);
//
		// Create ListView for brand selection
		ListView<String> brandListView = new ListView<>();

		// Set event handlers for navigation buttons
		Button prev = new Button("<Previous");
		prev.setOnAction(event -> {
//		    String selectedBrand = brandListView.getSelectionModel().getSelectedItem();
//		    String prevBrand = singleLinkedList.getPreviousBrand(selectedBrand);
//		    if (prevBrand != null) {
//		        brandListView.getSelectionModel().select(prevBrand);
//		        updateBrandData(prevBrand);
//		    }
		});
		Button next = new Button("Next>");
		next.setOnAction(event -> {
			// String selectedBrand = brandListView.getSelectionModel().getSelectedItem();
			// String nextBrand = singleLinkedList.getNextBrand(selectedBrand);
//		    if (nextBrand != null) {
//		        brandListView.getSelectionModel().select(nextBrand);
//		        updateBrandData(nextBrand);
//		    }
		});
		FlowPane bottomSection = new FlowPane();
		bottomSection.setPadding(new Insets(10));
		bottomSection.getChildren().addAll(prev, brandComboBox, next);



		// Add the controls to your user stage layout
		root.setTop(new VBox(10, brandl, brandt));
		root.setCenter(table);
		root.setBottom(bottomSection);
		// Add vbox to your user stage layout appropriately
		Scene uscene = new Scene(root, 800, 600);
		ustage.setScene(uscene);
		ustage.setTitle("Client");
		ustage.show();

		return ustage;
	}

	private void readFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Car Data File");
		File file = fileChooser.showOpenDialog(null);

		if (file != null) {
			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] parts = line.split(",");
					if (parts.length >= 4) {
						String brand = parts[0].trim();
						String model = parts[1].trim();

						int year = Integer.parseInt(parts[2].trim());
						String color = parts[3].trim();
						double price = Double.parseDouble(parts[4].trim());
						list.add(new Car(model, year, color, price));
					}
				}
			}
			// displayBrands();
			catch (IOException e) {
				showAlert("Error", "Failed to read data from file.");
			} catch (NumberFormatException e) {
				showAlert("Error", "Invalid number format in the file.");
			}
		}

	}

	private static void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
//	private VBox createFiltersBox() {
//		brandComboBox = createComboBox();
//		greyCheckBox = createCheckBox("Grey");
//		blackCheckBox = createCheckBox("Black");
//		whiteCheckBox = createCheckBox("White");
//		greenCheckBox = createCheckBox("Green");
//		blueCheckBox = createCheckBox("Blue");
//		redCheckBox = createCheckBox("Red");
//		year2000to2010CheckBox = createCheckBox("2000 - 2010");
//		year2010to2018CheckBox = createCheckBox("2010 - 2018");
//		year2018to2020CheckBox = createCheckBox("2018 - 2020");
//		year2020to2023CheckBox = createCheckBox("2020 - 2023");
//		c200CheckBox = createCheckBox("C200");
//		c300CheckBox = createCheckBox("C300");
//		xCheckBox = createCheckBox("X");
//		x3CheckBox = createCheckBox("X3");
//		x5CheckBox = createCheckBox("X5");
//		x6CheckBox = createCheckBox("X6");
//		mustangCheckBox = createCheckBox("Mustang");
//		rioCheckBox = createCheckBox("Rio");
//		optimaCheckBox = createCheckBox("Optima");

//		VBox filtersBox = new VBox(10);
//		filtersBox.setPadding(new Insets(10));
//		filtersBox.getChildren().addAll(new Label("Filters"), brandComboBox, new Separator(), new Label("Color"),
//				greyCheckBox, blackCheckBox, whiteCheckBox, greenCheckBox, blueCheckBox, redCheckBox, new Separator(),
//				new Label("Year"), year2000to2010CheckBox, year2010to2018CheckBox, year2018to2020CheckBox,
//				year2020to2023CheckBox, new Separator(), new Label("Model"), c200CheckBox, c300CheckBox, xCheckBox,
//				x3CheckBox, x5CheckBox, x6CheckBox, mustangCheckBox, rioCheckBox, optimaCheckBox);
//
//		return filtersBox;
//	}

	private ComboBox<String> createComboBox() {
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.getItems().addAll("All", "Mercedes", "BMW", "Ford", "Kia");
		comboBox.getSelectionModel().selectFirst();
//		comboBox.setConverter(new StringConverter<>() {
//			@Override
//			public String toString(String object) {
//				return object;
//			}
//
//			@Override
//			public String fromString(String string) {
//				return string;
//			}
//		});
//		comboBox.valueProperty().addListener((observable, oldValue, newValue) -> 
//		applyFilters());
//		return comboBox;
//	}
//	private CheckBox createCheckBox(String text) {
//		CheckBox checkBox = new CheckBox(text);
//		checkBox.selectedProperty().addListener((observable, oldValue, newValue) ->
//		applyFilters());
//		return checkBox;
		return comboBox;
	}
//	private void setBrand(int index) {
//		if (index >= 0 && index < carList.size()) {
//			Car car = carList.get(index);
//			brandComboBox.setValue(car.getBrand());
//			table.setItems(FXCollections.observableArrayList(car));
//			table.refresh();
//		}
//	}

//	private void applyFilters() {
//		String selectedBrand = brandComboBox.getValue();
//		String selectedColor = getSelectedColor();
//		int selectedYear = getSelectedYear();
//		String selectedModel = getSelectedModel();
//
//		filteredData.setPredicate(car -> {
//			boolean brandMatch = selectedBrand.equals("All") || car.getBrand().equals(selectedBrand);
//			boolean colorMatch = selectedColor.isEmpty() || car.getColor().equalsIgnoreCase(selectedColor);
//			boolean yearMatch = selectedYear == 0 || car.getYear() == selectedYear;
//			boolean modelMatch = selectedModel.isEmpty() || car.getModel().equalsIgnoreCase(selectedModel);
//
//			return brandMatch && colorMatch && yearMatch && modelMatch;
//		});
//	}

	private String getSelectedColor() {
		StringBuilder selectedColor = new StringBuilder();
		if (greyCheckBox.isSelected())
			selectedColor.append("Grey,");
		if (blackCheckBox.isSelected())
			selectedColor.append("Black,");
		if (whiteCheckBox.isSelected())
			selectedColor.append("White,");
		if (greenCheckBox.isSelected())
			selectedColor.append("Green,");
		if (blueCheckBox.isSelected())
			selectedColor.append("Blue,");
		if (redCheckBox.isSelected())
			selectedColor.append("Red,");
		return selectedColor.toString().replaceAll(",$", "");
	}

	private int getSelectedYear() {
		if (year2000to2010CheckBox.isSelected())
			return 2000;
		if (year2010to2018CheckBox.isSelected())
			return 2010;
		if (year2018to2020CheckBox.isSelected())
			return 2018;
		if (year2020to2023CheckBox.isSelected())
			return 2020;
		return 0;
	}

	private String getSelectedModel() {
		StringBuilder selectedModel = new StringBuilder();
		if (c200CheckBox.isSelected())
			selectedModel.append("C200,");
		if (c300CheckBox.isSelected())
			selectedModel.append("C300,");
		if (xCheckBox.isSelected())
			selectedModel.append("X,");
		if (x3CheckBox.isSelected())
			selectedModel.append("X3,");
		if (x5CheckBox.isSelected())
			selectedModel.append("X5,");
		if (x6CheckBox.isSelected())
			selectedModel.append("X6,");
		if (mustangCheckBox.isSelected())
			selectedModel.append("Mustang,");
		if (rioCheckBox.isSelected())
			selectedModel.append("Rio,");
		if (optimaCheckBox.isSelected())
			selectedModel.append("Optima,");
		return selectedModel.toString().replaceAll(",$", "");
	}

	
}
