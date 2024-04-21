package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class AdminStage {
	public static Stage astage;
	public static Scene ascene;
	private static TableView<Car> carsTable = new TableView<>();
	private TableView<Order> procTable = new TableView<>();
	private TableView<Order> repTable = new TableView<>();
	private static TableView<Brand> brandTable = new TableView<>();
	public static DoubleLinkedList list = new DoubleLinkedList();
	public static SingleLinkedList clist = new SingleLinkedList();
	public brandNode current;
	private ObservableList<Car> carList = FXCollections.observableArrayList();
	private ObservableList<Brand> brandList = FXCollections.observableArrayList();
	// private TableView<Order> ordersTable;
	private Stack<Order> finishedStack = new Stack<>();
	private Queue processingQueue = new Queue(100);
	ObservableList<Order> orderList = FXCollections.observableArrayList();
	private int selectedRowIndex = -1;
	// private LinkedList<Car> carBrandsList;

	Stage admp() {
		astage = new Stage(StageStyle.DECORATED);

		TabPane tabPane = new TabPane();
		tabPane.getTabs().addAll(createCarsTab1(), createCarsTab2(), createbrandTab3(), createCarsTab4());

		Scene ascene = new Scene(tabPane, 900, 800);
		astage.setScene(ascene);
		astage.setTitle("Admin page");
		astage.show();

		return astage;
	}

	private Tab createCarsTab1() {
		BorderPane root = new BorderPane();

		// Create car brands table columns
		TableColumn<Order, String> cuNameColumn = new TableColumn<>("Customer Name");
		cuNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

		TableColumn<Order, String> cuMColumn = new TableColumn<>("Customer Mobile");
		cuMColumn.setCellValueFactory(new PropertyValueFactory<>("customerMobile"));

		TableColumn<Order, String> DColumn = new TableColumn<>("Order Date");
		DColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

		TableColumn<Order, String> StColumn = new TableColumn<>("Order Status");
		StColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

		// Create cars table columns
		TableColumn<Order, String> brandColumn = new TableColumn<>("Brand Name");
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

		TableColumn<Order, String> modelNameColumn = new TableColumn<>("Model Name");
		modelNameColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

		TableColumn<Order, String> colorColumn = new TableColumn<>("Color");
		colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

		TableColumn<Order, Integer> yearColumn = new TableColumn<>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

		TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

		// Create cars table

		procTable.getColumns().addAll(cuNameColumn, cuMColumn, DColumn, StColumn, brandColumn, modelNameColumn,
				colorColumn, yearColumn, priceColumn);

		// Create buttons for car brands
		Button accept = new Button("Accept");
		accept.setAlignment(Pos.CENTER);
		accept.setOnAction(event -> {
			// Get the selected order from the table
			selectFirstRow();
			Order selectedOrder = procTable.getSelectionModel().getSelectedItem();

			if (selectedOrder != null) {
				// Update the order status to "Finished"
				selectedOrder.setOrderStatus("Finished");

				// Push the finished order to the finished stack
				finishedStack.push(selectedOrder);

				// Remove the selected order from the table
				procTable.getItems().remove(selectedOrder);

				// Refresh the table to reflect the updated order status
				procTable.refresh();

				// Select the first row in the table
				selectFirstRow();
			}
		});

		Button later = new Button("Later");
		later.setOnAction(event -> {
			// Create an observable list to hold the orders
			ObservableList<Order> orderList = FXCollections.observableArrayList();
			selectFirstRow();
			// Get the selected order from the table
			Order selectedOrder = procTable.getSelectionModel().getSelectedItem();

			if (selectedOrder != null) {
				// Get the index of the selected row
				int selectedIndex = procTable.getSelectionModel().getSelectedIndex();

				if (selectedIndex == 0) {
					// If the first row is selected, move the selected order to the end of the queue
					processingQueue.deQueue(selectedOrder);
					processingQueue.enQueue(selectedOrder);
					// Remove the selected order from the table
					procTable.getItems().remove(selectedOrder);
					// Add the selected order to the order list
					orderList.add(selectedOrder);
					procTable.getItems().add(selectedOrder);
					// Reset the selected row index
					selectedRowIndex = -1;
				}

				// Refresh the table to reflect the updated order position
				procTable.refresh();

				// Select the first row in the table
				selectFirstRow();
			}
		});
		// Select the first row in the table
		procTable.getSelectionModel().select(0);
		// set table in center of tab
		root.setCenter(procTable);
		// make flow pane to add buttons
		FlowPane f = new FlowPane();
		// set the buttons in flow pane
		f.getChildren().addAll(accept, later);
		// set the flow pane in bottom of page
		root.setBottom(f);
		// populateTable(processingQueue);
		Tab carBrandsTab = new Tab("Accept Order", root);
		return carBrandsTab;
	}

	private Tab createCarsTab2() {
		BorderPane root = new BorderPane();

		// Create car brands table columns
		TableColumn<Order, String> cuNameColumn = new TableColumn<>("Customer Name");
		cuNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

		TableColumn<Order, String> cuMColumn = new TableColumn<>("Customer Mobile");
		cuMColumn.setCellValueFactory(new PropertyValueFactory<>("customerMobile"));

		TableColumn<Order, String> DColumn = new TableColumn<>("Order Date");
		DColumn.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

		TableColumn<Order, String> StColumn = new TableColumn<>("Order Status");
		StColumn.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));

		// Create cars table columns
		TableColumn<Order, String> brandColumn = new TableColumn<>("Brand Name");
		brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

		TableColumn<Order, String> modelNameColumn = new TableColumn<>("Model Name");
		modelNameColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

		TableColumn<Order, String> colorColumn = new TableColumn<>("Color");
		colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

		TableColumn<Order, Integer> yearColumn = new TableColumn<>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

		TableColumn<Order, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

		// Create cars table
		repTable.getColumns().addAll(cuNameColumn, cuMColumn, DColumn, StColumn, brandColumn, modelNameColumn,
				colorColumn, yearColumn, priceColumn);

		root.setCenter(repTable);

		// Retrieve orders from finishedStack and add them to the table
		while (!finishedStack.isEmpty()) {
			Order order = (Order) finishedStack.pop();
			repTable.getItems().add(order);
		}

		// Set the items of the table using the finishedStack

		Button refresh = new Button("Refresh");
		refresh.setOnAction(event -> {
			// Refresh the table and display the updated data from the finishedStack
			repTable.setItems(FXCollections.observableArrayList(finishedStack));
			repTable.refresh();
		});

		Button last = new Button("Last 10");
		last.setOnAction(event -> {
			int count = finishedStack.size();
			if (count >= 10) {
				int min = Math.min(count, 10);
				Stack<Order> temp = new Stack<>();

				// Retrieve the last 10 orders from the finishedStack and store them in temp
				// stack
				for (int i = 0; i < min; i++) {
					temp.push(finishedStack.pop());
				}

				// Clear the existing items in the table
				repTable.getItems().clear();

				// Add the orders to the table
				while (!temp.isEmpty()) {
					Order order = temp.pop();
					repTable.getItems().add(order);
				}
				repTable.setItems(FXCollections.observableArrayList(temp));

			} else {
				System.out.println("The number of orders in the stack is less than 10");
			}
		});

		FlowPane p = new FlowPane();
		p.getChildren().addAll(refresh, last);
		root.setBottom(p);

		Tab carBrandsTab = new Tab("Report", root);
		return carBrandsTab;
	}

	private Tab createCarsTab4() {
		BorderPane root = new BorderPane();

		// Create cars table columns
		TableColumn<Car, String> brandNameColumn = new TableColumn<>("Brand Name");
		brandNameColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

		TableColumn<Car, String> modelNameColumn = new TableColumn<>("Model Name");
		modelNameColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

		TableColumn<Car, String> colorColumn = new TableColumn<>("Color");
		colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

		TableColumn<Car, Integer> yearColumn = new TableColumn<>("Year");
		yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

		TableColumn<Car, Double> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

		// Create cars table
		carsTable.getColumns().addAll(brandNameColumn, modelNameColumn, colorColumn, yearColumn, priceColumn);

		Button refresh = new Button("Refresh");
		refresh.setOnAction(event -> {
			// Refresh the table and display the updated data from the carList
			carsTable.setItems(FXCollections.observableArrayList(carList));
			carsTable.refresh();
		});

		// Create buttons for cars
		Button addC = new Button("Add Car");
		addC.setOnAction(e -> {
			new addmy().addmp();
			addmy.addm.show();
		});

		Button deleteC = new Button("Delete Car");
		deleteC.setOnAction(e -> {
			// Remove selected cars from the TableView
			ObservableList<Car> selectedCars = AdminStage.getCarsTable().getSelectionModel().getSelectedItems();
			if (selectedCars != null && !selectedCars.isEmpty()) {
				showfoundAlert();
				AdminStage.getCarsTable().getItems().removeAll(selectedCars);
				AdminStage.getCarsTable().refresh();
				System.out.println("Car removed");
			} else {
				showBrandSelectionAlert();
			}
		});
//open the update stage car
		Button updateC = new Button("Update Car");
		updateC.setOnAction(e -> {
			new upmy().upm();
			upmy.upmg.show();
		});

		FlowPane f = new FlowPane();
		f.getChildren().addAll(addC, deleteC, updateC);

		root.setCenter(carsTable);
		root.setBottom(f);

		carsTable.setItems(carList);
		carsTable.refresh();

		Tab carBrandsTab = new Tab("Cars Edit", root);
		return carBrandsTab;
	}

	private Tab createbrandTab3() {

		BorderPane root = new BorderPane();
		// Create car brands table columns
		TableColumn<Brand, String> brandNameColumn = new TableColumn<>("Brand Name");
		brandNameColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

		// Create brand table

		brandTable.getColumns().add(brandNameColumn);

		// Create buttons for car brands
		Button addb = new Button("Add Brand");
		addb.setOnAction(e -> {
			new addloc().addp();
			addloc.addg.show();
		});

		Button deleteb = new Button("Delete Brand");
		deleteb.setOnAction(e -> {
			Brand bb = AdminStage.getBrandTable().getSelectionModel().getSelectedItem();
			System.out.println(AdminStage.list.found(bb));

			if (bb != null) {
				if (AdminStage.list.found(bb)) {
					AdminStage.list.remove(bb);
					System.out.println("Brand removed");
					AdminStage.getBrandTable().getItems().remove(bb);
				} else {
					showfoundAlert();
				}
			} else {
				showSelectionAlert();
			}
		});

		Button updateb = new Button("Update Brand");
		updateb.setOnAction(e -> {
			Brand locas = AdminStage.getBrandTable().getSelectionModel().getSelectedItem();

			if (locas != null) {
				new updateloc().upp();
				updateloc.upg.show();
			} else {
				showSelectionAlert();
			}
		});
		FlowPane f = new FlowPane();
		f.getChildren().addAll(addb, deleteb, updateb);
		root.setCenter(brandTable);
		root.setBottom(f);
		readFile();
		brandTable.setItems(brandList);
		brandTable.refresh();
		Tab BrandsTab = new Tab("Brands Edit", root);
		return BrandsTab;
	}

	void loadOrders() {
		// Create a file chooser dialog to select the data file
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Car Data File");
		File file = fileChooser.showOpenDialog(null);

		if (file != null) {
			try (Scanner scanner = new Scanner(file)) {
				ObservableList<Order> orderList = FXCollections.observableArrayList();

				// Read the data from the file line by line
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] orderData = line.split(",");

					// Check if the line contains the correct number of data fields
					if (orderData.length == 9) {
						// Extract the order details from the line
						String customerName = orderData[0];
						String customerMobile = orderData[1];
						String brand = orderData[2];
						String model = orderData[3];
						int year = Integer.parseInt(orderData[4]);
						String color = orderData[5];
						double price = Double.parseDouble(orderData[6]);
						String orderDate = orderData[7];
						String orderStatus = orderData[8];

						// Create an Order object with the extracted details
						Order order = new Order(customerName, customerMobile, brand, model, year, color, price,
								orderDate, orderStatus);

						if (orderStatus.equals("Finished")) {
							// Add the finished order to the finishedStack
							finishedStack.push(order);
							System.out.println(finishedStack);
						} else if (orderStatus.equals("InProcess")) {
							// Add the in-process order to the processingQueue and orderList
							processingQueue.enQueue(order);
							orderList.add(order);
							System.out.println(processingQueue);
							System.out.println(orderList);
						}
					}
				}

				System.out.println(finishedStack);
				System.out.println(processingQueue);

				// Set the items of procTable to the orderList and refresh the table
				procTable.setItems(orderList);
				procTable.refresh();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				showAlert("Error", "Invalid number format in the file.");
			}
		}
	}

	private void showSelectionAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("No Selection");
		alert.setHeaderText(null);
		alert.setContentText("Please select a brand .");
		alert.showAndWait();
	}

	private void showfoundAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Delete Selection");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure?.");
		alert.showAndWait();
	}

	private void showBrandSelectionAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("No Selection");
		alert.setHeaderText(null);
		alert.setContentText("Please select a car.");
		alert.showAndWait();
	}

	private static void showAlert(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static TableView<Car> getCarsTable() {
		return carsTable;
	}

	public static void setCarsTable(TableView<Car> carsTable) {
		AdminStage.carsTable = carsTable;
	}

	public static TableView<Brand> getBrandTable() {
		return brandTable;
	}

	public static void setBrandTable(TableView<Brand> brandTable) {
		AdminStage.brandTable = brandTable;
	}

	// ***************************************************************
	// -------------------------------------------------------------Add(first)---
	public class addmy {

		public static Stage addm;
		public static Scene addms;
		public SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yy");
		public String dd;

		// add window
		Stage addmp() {
			// create stage
			addm = new Stage(StageStyle.DECORATED);

			// stage UIF

			BorderPane root = new BorderPane();

			GridPane gp = new GridPane();
			Label namel = new Label("Brand");
			gp.add(namel, 0, 1);
			TextField b = new TextField();
			gp.add(b, 1, 1);

			Label agel = new Label("Model");
			gp.add(agel, 0, 2);
			TextField m = new TextField();
			gp.add(m, 1, 2);

			Label datel = new Label("year");
			gp.add(datel, 0, 3);
			TextField y = new TextField();
			gp.add(y, 1, 3);

			Label gl = new Label("Color");
			gp.add(gl, 3, 1);
			TextField co = new TextField();
			gp.add(co, 4, 1);

			Label sl = new Label("Price");
			gp.add(sl, 3, 2);
			TextField p = new TextField();
			gp.add(p, 4, 2);

			gp.setAlignment(Pos.CENTER);
			root.setCenter(gp);

			// ---------------------------------------------ADD widow (button) actions----

			Button addb = new Button("Add.");
			addb.setOnAction(event -> {

				String brandn = b.getText();
				String modeln = m.getText();
				int year = Integer.parseInt(y.getText());

				String color = co.getText();
				double price = Double.parseDouble(p.getText());
				Car car = new Car(brandn, modeln, year, color, price);

				if (!AdminStage.clist.found(car)) {
					AdminStage.clist.addLast(car);
					AdminStage.getCarsTable().getItems().add(car);
					AdminStage.getCarsTable().refresh();
					System.out.println("car added successfully");
					addm.close();
				} else {
					System.out.println("car already exists in the list");
				}

			});
			// -------------------------add widow(final)--------------

			addb.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
			addb.setPrefWidth(400);
			addb.setPrefHeight(100);

			root.setBottom(addb);

			// put pane in scene then scene in stage
			// layout the stage
			addms = new Scene(root, 450, 250);
			addms.getStylesheets().add("/com/javacodejunkie/stylesheet.css");
			// insert add scene to stage add
			addm.setScene(addms);
			addm.setTitle("Add screen");
			addm.initModality(Modality.APPLICATION_MODAL);
			return addm;

		}
	}
	// ----------------------------------------update widow(first)-----------------

	public class upmy {
		public static Stage upmg;
		public static Scene upms;
		ObservableList<Car> locas = AdminStage.getCarsTable().getSelectionModel().getSelectedItems();

		Stage upm() {
			// create stage
			upmg = new Stage(StageStyle.DECORATED);

			// stage UIF

			BorderPane root = new BorderPane();

			GridPane gp = new GridPane();
			Label namel = new Label("Brand");
			gp.add(namel, 0, 1);
			TextField b = new TextField();
			gp.add(b, 1, 1);

			Label agel = new Label("Model");
			gp.add(agel, 0, 2);
			TextField m = new TextField();
			gp.add(m, 1, 2);

			Label datel = new Label("year");
			gp.add(datel, 0, 3);
			TextField y = new TextField();
			gp.add(y, 1, 3);

			Label gl = new Label("Color");
			gp.add(gl, 3, 1);
			TextField co = new TextField();
			gp.add(co, 4, 1);

			Label sl = new Label("Price");
			gp.add(sl, 3, 2);
			TextField p = new TextField();
			gp.add(p, 4, 2);

			gp.setAlignment(Pos.CENTER);
			root.setCenter(gp);

			Button upb = new Button("Update.");
			// fill the text field

			// ----------------------------------------update widow(button actions)--
			upb.setOnAction(event -> {
				if (!locas.isEmpty()) {
					Car c = locas.get(0); // Assuming you only want to update the first selected Martyr
					c.setBrand(b.getText());
					c.setModel(m.getText());
					c.setYear(Integer.parseInt(y.getText()));
					c.setColor(co.getText());
					c.setPrice(Double.parseDouble(p.getText()));
					AdminStage.getCarsTable().refresh();
					upmg.close(); // Close the update window after updating
				} else {
					showBrandSelectionAlert();
				}

			});
			// ----------------------------------------update widow(final)-----------------
			upb.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
			upb.setPrefWidth(400);
			upb.setPrefHeight(100);

			root.setBottom(upb);

			// put pane in scene then scene in stage
			// layout the stage
			upms = new Scene(root, 450, 250);
			upms.getStylesheets().add("/com/javacodejunkie/stylesheet.css");
			// insert add scene to stage add
			upmg.setScene(upms);
			upmg.setTitle("Update screen");
			upmg.initModality(Modality.APPLICATION_MODAL);
			return upmg;

		}

		// ----------------------------------------update widow(done)-----------------

	}

	private void selectFirstRow() {
		if (!processingQueue.isEmpty()) {
			procTable.getSelectionModel().select(0);
			selectedRowIndex = 0;
		} else {
			procTable.getSelectionModel().clearSelection();
			selectedRowIndex = -1;
		}
	}

	// ------------------ for brand and car ----
	private void readFile() {

		File file = new File("/Users/noorzayed/Desktop/java/p2fx/src/cars.txt");

		if (file != null) {
			System.out.println("read file");
			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNextLine()) {
					brandNode current = list.getFirst();

					String line = scanner.nextLine();
					String[] parts = line.split(",");
					String brandName = parts[0].trim();
					String model = parts[1].trim();
					int year = Integer.parseInt(parts[2].trim());
					String color = parts[3].trim();
					double price = Double.parseDouble(parts[4].trim());

					boolean brandExists = list.founds(brandName);
					if (brandExists) {
						current = list.get(brandName);
						Car car = new Car(brandName, model, year, color, price);
						carList.add(car);
						current.list.addFirst(car);

					} else {
						list.addFirst(brandName);
						current = list.getFirst();
						Brand brand = new Brand(brandName);
						Car car = new Car(brandName, model, year, color, price);
						carList.add(car);
						current.list.addFirst(car);
						brandList.add(brand);

					}
				}

				// Update the cars table in the Cars Edit tab
				carsTable.setItems(carList);
				brandTable.setItems(brandList);
				// Set the items in the brand table

				System.out.println(carList);
				System.out.println(brandList);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		current = list.getFirst();
	}

}
