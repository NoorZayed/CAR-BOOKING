package application;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

public class client {
	public static Stage ustage;
	public static Scene uscene;
//	Brand b = new Brand();

	// linked list --
	public static DoubleLinkedList list = new DoubleLinkedList();
	public brandNode current = list.getFirst();;
	// = list.getFirst();
	private TableView<Car> table;
	private static ObservableList<Car> carList;
	private ObservableList<String> brandList = FXCollections.observableArrayList();
	private ArrayList<Car> cart = new ArrayList();
	private FilteredList<Car> filteredData;
	private ComboBox<String> brandComboBox;

	private CheckBox greyCheckBox, blackCheckBox, whiteCheckBox, greenCheckBox, blueCheckBox, redCheckBox;
	private CheckBox year2000to2010CheckBox, year2010to2018CheckBox, year2018to2020CheckBox, year2020to2023CheckBox;
	private CheckBox c200CheckBox, c300CheckBox, xCheckBox, x3CheckBox, x5CheckBox, x6CheckBox, mustangCheckBox,
			rioCheckBox, optimaCheckBox;
	private TextField brandTextField;

	private int currentBrandIndex = 0;

	Stage userp() {
		ustage = new Stage(StageStyle.DECORATED);
		table = new TableView<>();
		carList = FXCollections.observableArrayList();
		brandList = FXCollections.observableArrayList();

		BorderPane root = new BorderPane();

		VBox filtersBox = createFiltersBox();

		// Top section - Brand selection

		brandTextField = new TextField();
		brandTextField.setEditable(false);
		brandTextField.setMinWidth(100);

		// create tap
		TabPane tpane = new TabPane();
		Tab tform = new Tab("Client page");
		Tab closeform = new Tab("Close");

		tpane.getTabs().addAll(tform, reportTab1(), closeform);

		TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
		modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

		TableColumn<Car, String> colorColumn = new TableColumn<>("Color");
		colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

		TableColumn<Car, Integer> yearColumn = new TableColumn<>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
		TableColumn<Car, Double> priceColumn = new TableColumn<>("price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

		// Add columns to the table
		table.getColumns().addAll(modelColumn, yearColumn, colorColumn, priceColumn);

		table.setPrefWidth(200); // Set the desired width of the table
		table.setPrefHeight(200); // Set the desired height of the table

		// set the tableview in tap
		tform.setContent(root);
		// Read data from file and populate the car list
		readFile();
		// table.setItems(filteredData);

		applyFilters();
		// prev button
		Button previousButton = new Button("Previous");
		previousButton.setOnAction(event -> {
			if (currentBrandIndex > 0) {
				currentBrandIndex--;
				setBrand(currentBrandIndex);
			}

		});
//next button
		Button nextButton = new Button("Next");
		nextButton.setOnAction(event -> {

			if (current != null && current.getNext() != null) {
				current = current.getNext();
				setBrand(brandList.indexOf(current.getB()));
			}

		});
		// add button
		Button add = new Button("Add to Cart");
		add.setOnAction(event -> {
//		private	ArrayList<Car> cart = new ArrayList();
			// Get the selected car from the table
			Car selectedCar = table.getSelectionModel().getSelectedItem();

			// Check if a car is selected
			if (selectedCar != null) {
				// Create a new stage for collecting customer information
				Stage cartStage = new Stage();
				cartStage.setTitle("Cart");

				// Create UI components for collecting customer information
				Label nameLabel = new Label("Name:");
				TextField nameT = new TextField();
				Label phoneLabel = new Label("Phone number:");
				TextField phone = new TextField();
				Button confirm = new Button("Confirm");

				// Handle the confirm button click event
				confirm.setOnAction(confirmEvent -> {
					String name = nameT.getText();
					String phoneNumber = phone.getText();
					String orderDate = LocalDate.now().toString();
					String orderStatus = "InProcess";

					// Create an Order object with the updated properties
					Order order = new Order(name, phoneNumber, selectedCar.getBrand(), selectedCar.getModel(),
							selectedCar.getYear(), selectedCar.getColor(), selectedCar.getPrice(), orderDate,
							orderStatus);

					cart.add(selectedCar);
					System.out.println(cart);
					System.out.println(order);
					// Save the order details to a file
					saveOrder(order);
					cartStage.close();

				});

				// Create a layout for the cart stage
				VBox cartLayout = new VBox(10);
				cartLayout.setPadding(new Insets(10));
				cartLayout.getChildren().addAll(nameLabel, nameT, phoneLabel, phone, confirm);

				// Set the layout in the cart stage
				cartStage.setScene(new Scene(cartLayout));
				cartStage.show();
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Input Error");
				alert.setHeaderText(null);
				alert.setContentText("Please select car.");
				alert.showAndWait();
			}
		});

		String selectedBrand = brandComboBox.getValue();
		for (Car car : carList) {
			if (car.getBrand().equals(selectedBrand)) {
				filteredData.add(car);
			}
		}
		table.setItems(filteredData);
		table.refresh();
		VBox brandBox = new VBox(10);

		brandBox.getChildren().addAll(previousButton, nextButton, add);
		root.setTop(brandTextField);
		root.setLeft(filtersBox);
		root.setRight(brandBox);

		root.setCenter(table);
		ScrollBar scrollBar = new ScrollBar();
		scrollBar.setOrientation(Orientation.VERTICAL);

		Scene uscene = new Scene(tpane, 1000, 800);
		ustage.setScene(uscene);
		ustage.setTitle("Client");
		ustage.show();

		return ustage;
	}

	// create combo box
	private ComboBox<String> createComboBox() {
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setConverter(new StringConverter<String>() {
			@Override
			public String toString(String object) {
				return object;
			}

			@Override
			public String fromString(String string) {
				return string;
			}
		});
		return comboBox;
	}

	// create check box method
	private CheckBox createCheckBox(String text) {
		CheckBox checkBox = new CheckBox(text);
		checkBox.setSelected(false);
		checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> applyFilters());
		return checkBox;

	}

	private VBox createFiltersBox() {
		// Create all the necessary UI elements
		brandComboBox = createComboBox();
		greyCheckBox = createCheckBox("Grey");
		blackCheckBox = createCheckBox("Black");
		whiteCheckBox = createCheckBox("White");
		greenCheckBox = createCheckBox("Green");
		blueCheckBox = createCheckBox("Blue");
		redCheckBox = createCheckBox("Red");
		year2000to2010CheckBox = createCheckBox("2000 - 2010");
		year2010to2018CheckBox = createCheckBox("2010 - 2018");
		year2018to2020CheckBox = createCheckBox("2018 - 2020");
		year2020to2023CheckBox = createCheckBox("2020 - 2023");
		c200CheckBox = createCheckBox("C200");
		c300CheckBox = createCheckBox("C300");
		xCheckBox = createCheckBox("X");
		x3CheckBox = createCheckBox("X3");
		x5CheckBox = createCheckBox("X5");
		x6CheckBox = createCheckBox("X6");
		mustangCheckBox = createCheckBox("Mustang");
		rioCheckBox = createCheckBox("Rio");
		optimaCheckBox = createCheckBox("Optima");

		// Set the action listeners for the combo box and check boxes
		brandComboBox.setOnAction(e -> setBrand(brandComboBox.getSelectionModel().getSelectedIndex()));
		greyCheckBox.setOnAction(e -> applyFilters());
		blackCheckBox.setOnAction(e -> applyFilters());
		whiteCheckBox.setOnAction(e -> applyFilters());
		greenCheckBox.setOnAction(e -> applyFilters());
		blueCheckBox.setOnAction(e -> applyFilters());
		redCheckBox.setOnAction(e -> applyFilters());
		year2000to2010CheckBox.setOnAction(e -> applyFilters());
		year2010to2018CheckBox.setOnAction(e -> applyFilters());
		year2018to2020CheckBox.setOnAction(e -> applyFilters());
		year2020to2023CheckBox.setOnAction(e -> applyFilters());
		c200CheckBox.setOnAction(e -> applyFilters());
		c300CheckBox.setOnAction(e -> applyFilters());
		xCheckBox.setOnAction(e -> applyFilters());
		x3CheckBox.setOnAction(e -> applyFilters());
		x5CheckBox.setOnAction(e -> applyFilters());
		x6CheckBox.setOnAction(e -> applyFilters());
		mustangCheckBox.setOnAction(e -> applyFilters());
		rioCheckBox.setOnAction(e -> applyFilters());
		optimaCheckBox.setOnAction(e -> applyFilters());

		// Create the filters VBox and configure its properties
		VBox filtersBox = new VBox(10);
		filtersBox.setPadding(new Insets(10));
		filtersBox.getChildren().addAll(new Label("Filters"), brandComboBox, new Separator(), new Label("Color"),
				greyCheckBox, blackCheckBox, whiteCheckBox, greenCheckBox, blueCheckBox, redCheckBox, new Separator(),
				new Label("Year"), year2000to2010CheckBox, year2010to2018CheckBox, year2018to2020CheckBox,
				year2020to2023CheckBox, new Separator(), new Label("Model"), c200CheckBox, c300CheckBox, xCheckBox,
				x3CheckBox, x5CheckBox, x6CheckBox, mustangCheckBox, rioCheckBox, optimaCheckBox);

		return filtersBox;
	}

	private void readFile() {
		// Create a file chooser dialog for selecting the car data file
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Car Data File");
		File file = fileChooser.showOpenDialog(null);

		if (file != null) {
			try (Scanner scanner = new Scanner(file)) {
				// Iterate over each line in the file
				while (scanner.hasNextLine()) {
					brandNode current = list.getFirst();

					// Read the line and split it into parts
					String line = scanner.nextLine();
					String[] parts = line.split(",");
					String brandName = parts[0].trim();
					String model = parts[1].trim();
					int year = Integer.parseInt(parts[2].trim());
					String color = parts[3].trim();
					double price = Double.parseDouble(parts[4].trim());

					// Check if the brand already exists in the list
					boolean flag = list.founds(parts[0]);

					if (flag == true) {
						// Brand already exists, get the current brand node
						current = list.get(parts[0]);

						// Create a new car and add it to the car list and brand node's list
						Car car = new Car(brandName, model, year, color, price);
						carList.add(car);
						current.list.addFirst(car);

						// Add the brand name to the brand list if it doesn't exist already
						if (!brandList.contains(brandName)) {
							brandList.add(brandName);
						}
					} else if (flag == false) {
						// Brand doesn't exist, create a new brand node and add it to the list
						list.addFirst(brandName);
						list.getFirst().getList().addFirst(new Car(brandName, model, year, color, price));

						// Add the brand name to the brand list if it doesn't exist already
						if (!brandList.contains(brandName)) {
							brandList.add(brandName);
						}
					}
				}

				// Set the brand combo box items and table items
				brandComboBox.setItems(brandList);
				table.setItems(carList);

				System.out.println(carList);
				System.out.println(brandList);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		// Set the text field with the brand name of the first brand node
		current = list.getFirst();
		brandTextField.setText((String) current.getB());
	}

	private void setBrand(int index) {
		// Check if the brand combo box is not empty
		if (!brandComboBox.getItems().isEmpty()) {
			System.out.println("not empty");

			// Check if the index is within the range of the brand combo box items
			if (index >= 0 && index < brandComboBox.getItems().size()) {
				// Get the brand at the specified index
				String brand = brandComboBox.getItems().get(index);

				// Set the brand text field and combo box selection to the selected brand
				brandTextField.setText(brand);
				brandComboBox.getSelectionModel().select(index);
				currentBrandIndex = index;
				System.out.println("get selected");

				// Apply filters based on the selected brand
				applyFilters();
				System.out.println("apply filters");

				// Clear the table items or initialize it if null
				if (table.getItems() == null) {
					table.setItems(FXCollections.observableArrayList());
				} else {
					// table.getItems().clear();
				}

				// Get the brand node from the list
				brandNode currentBrandNode = list.get(brand);

				// Populate the table with cars from the current brand node
				if (currentBrandNode != null) {
					carNode current = currentBrandNode.getList().getFirst();
					System.out.println("set current");

					// Iterate over each car in the current brand node
					for (int i = 0; i < currentBrandNode.getList().getCount(); i++) {
						System.out.println("loop");
						// Add the car to the table items
						table.getItems().add((Car) current.getCar());
						System.out.println("get item");
						current = current.getNext();
						System.out.println("next current");
					}
				}

				// Filter and set items based on the selected brand
				List<Car> filteredCars = new ArrayList<>();
				for (Car car : carList) {
					if (car.getBrand().equals(brand)) {
						filteredCars.add(car);
					}
				}
				table.setItems(FXCollections.observableArrayList(filteredCars));
				table.refresh();
				System.out.println("refreshed");
			}
		}
	}

	private void applyFilters() {
		// Get the selected brand from the brand combo box
		String selectedBrand = brandComboBox.getValue();

		// Create a list to store filtered cars
		List<Car> filteredCars = new ArrayList<>();

		// Iterate over each car in the car list
		for (Car car : carList) {
			// Check if the selected brand matches the car's brand, or if no brand is
			// selected
			if (selectedBrand == null || selectedBrand.isEmpty() || car.getBrand().equals(selectedBrand)) {
				filteredCars.add(car);
			}
		}

		// Create a filtered list with the filtered cars
		filteredData = new FilteredList<>(FXCollections.observableArrayList(filteredCars));

		// Set the predicate for the filtered data
		filteredData.setPredicate(car -> {
			// Check if the car's color matches the selected color checkboxes
			boolean colorMatch = (!greyCheckBox.isSelected() || car.getColor().equalsIgnoreCase("grey"))
					&& (!blackCheckBox.isSelected() || car.getColor().equalsIgnoreCase("black"))
					&& (!whiteCheckBox.isSelected() || car.getColor().equalsIgnoreCase("white"))
					&& (!greenCheckBox.isSelected() || car.getColor().equalsIgnoreCase("green"))
					&& (!blueCheckBox.isSelected() || car.getColor().equalsIgnoreCase("blue"))
					&& (!redCheckBox.isSelected() || car.getColor().equalsIgnoreCase("red"));

			// Check if the car's year matches the selected year checkboxes
			boolean yearMatch = (!year2000to2010CheckBox.isSelected()
					|| (car.getYear() >= 2000 && car.getYear() <= 2010))
					&& (!year2010to2018CheckBox.isSelected() || (car.getYear() >= 2010 && car.getYear() <= 2018))
					&& (!year2018to2020CheckBox.isSelected() || (car.getYear() >= 2018 && car.getYear() <= 2020))
					&& (!year2020to2023CheckBox.isSelected() || (car.getYear() >= 2020 && car.getYear() <= 2023));

			// Check if the car's model matches the selected model checkboxes
			boolean modelMatch = (!c200CheckBox.isSelected() || car.getModel().equalsIgnoreCase("C200"))
					&& (!c300CheckBox.isSelected() || car.getModel().equalsIgnoreCase("C300"))
					&& (!xCheckBox.isSelected() || car.getModel().equalsIgnoreCase("X"))
					&& (!x3CheckBox.isSelected() || car.getModel().equalsIgnoreCase("X3"))
					&& (!x5CheckBox.isSelected() || car.getModel().equalsIgnoreCase("X5"))
					&& (!x6CheckBox.isSelected() || car.getModel().equalsIgnoreCase("X6"))
					&& (!mustangCheckBox.isSelected() || car.getModel().equalsIgnoreCase("Mustang"))
					&& (!rioCheckBox.isSelected() || car.getModel().equalsIgnoreCase("Rio"))
					&& (!optimaCheckBox.isSelected() || car.getModel().equalsIgnoreCase("Optima"));

			// Return true if all the filter conditions are met for the car
			return colorMatch && yearMatch && modelMatch;
		});

		// Set the items of the table to the filtered data
		table.setItems(filteredData);

		// Refresh the table view
		table.refresh();
	}

	private void saveOrder(Order order) {
		String filePath = "/Users/noorzayed/Desktop/java/p2fx/src/orders.txt";

		try (FileWriter fileWriter = new FileWriter(filePath, true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
			System.out.println("find file");
			System.out.println(order + "orders");
			if (order.getCar() != null) {
				// Prepare the order details as a comma-separated string
				String orderLine = order.getCustomerName() + "," + order.getCustomerMobile() + ","
						+ order.getCar().getBrand() + "," + order.getCar().getModel() + "," + order.getCar().getYear()
						+ "," + order.getCar().getColor() + "," + order.getCar().getPrice() + "," + order.getOrderDate()
						+ "," + order.getOrderStatus();
				System.out.println(orderLine);

				// Write the order details to the file
				printWriter.println(orderLine);
				// Flush and close the writers
				printWriter.flush();

				System.out.println("Order saved successfully.");
			} else {
				System.out.println("order is null");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Tab reportTab1() {
		BorderPane root = new BorderPane();

		TableView table = new TableView<>();
		TableColumn brandc = new TableColumn("Brand :");
		brandc.setMinWidth(150);
		brandc.setCellValueFactory(new PropertyValueFactory<Info, String>("brand"));
		table.getColumns().add(brandc);
		TableColumn hprice = new TableColumn("Heigher price");
		hprice.setMinWidth(150);
		hprice.setCellValueFactory(new PropertyValueFactory<Info, Integer>("hprice"));
		table.getColumns().add(hprice);
		TableColumn lprice = new TableColumn("Lower price");
		lprice.setMinWidth(150);
		lprice.setCellValueFactory(new PropertyValueFactory<Info, String>("lprice"));
		table.getColumns().add(lprice);
		TableColumn hmodel = new TableColumn("Heigher Model");
		hmodel.setMinWidth(150);
		hmodel.setCellValueFactory(new PropertyValueFactory<Info, Character>("hmodel"));
		table.getColumns().add(hmodel);
		TableColumn lmodel = new TableColumn("Lower Model");
		lmodel.setMinWidth(150);
		lmodel.setCellValueFactory(new PropertyValueFactory<Info, String>("lmodel"));
		table.getColumns().add(lmodel);

		brandNode cur = list.getFirst();
		for (int i = 0; i < list.getCount(); i++) {
			// Info inf = new Info(cur);
			// table.getItems().add(inf);
			cur = cur.getNext();
		}
		table.refresh();

		root.setCenter(table);

		Tab reportTab = new Tab("Car Report", root);
		return reportTab;
	}
}
