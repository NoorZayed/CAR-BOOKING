package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Main extends Application {
	private DoubleLinkedList brandList;
	private Stage primaryStage;
	private Scene scene;
	private Scene cars;

	private static Button wel;

	@Override
	public void start(Stage primaryStage) {
		brandList = new DoubleLinkedList();
		this.primaryStage = primaryStage;

		// Create the scenes
		mainScene();
		carpage();

		primaryStage.setTitle("Car Agency System");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void mainScene() {

		StackPane root = new StackPane();
		VBox vbox = new VBox(5);
		// main image
		// Main image
		Image img = new Image("c1.png");
		ImageView imageView = new ImageView(img);
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(primaryStage.getWidth()); // Adjust the width to fit the screen
		imageView.setFitHeight(primaryStage.getHeight()); // Adjust the height to fit the screen

		// Two buttons
		wel = new Button("Start");
		wel.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		wel.setPrefWidth(100);
		wel.setPrefHeight(50);

		// Set action in button to change scene
		wel.setOnAction(event -> {
			primaryStage.setScene(cars);
		});

		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(wel);
		root.getChildren().addAll(imageView, vbox);

		scene = new Scene(root, Color.WHITE);
	}

	public void carpage() {
		try {
			StackPane root = new StackPane();

			// Main image
			Image img = new Image("done.jpeg");
			ImageView imageView = new ImageView(img);
			imageView.setPreserveRatio(true);
			imageView.setFitWidth(primaryStage.getWidth()); // Adjust the width to fit the screen
			imageView.setFitHeight(primaryStage.getHeight()); // Adjust the height to fit the screen

			HBox box = new HBox(20);

			Button user = new Button("Client");
			user.setOnAction(event -> {

				new client().userp();
				client.ustage.show();

			});
			user.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
			user.setPrefWidth(200);
			user.setPrefHeight(50);

			Button admin = new Button("Admin");
			admin.setOnAction(event -> {

				AdminStage adminStage = new AdminStage();
				adminStage.admp();
				adminStage.loadOrders();
				AdminStage.astage.show();

			});

			admin.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
			admin.setPrefWidth(200);
			admin.setPrefHeight(50);

			box.getChildren().addAll(admin, user);

			box.setAlignment(Pos.CENTER);
			root.getChildren().addAll(imageView, box);
			cars = new Scene(root, Color.WHITE);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}