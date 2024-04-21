package application;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class updateloc {
	public static Stage upg;
	public static Scene ups;
	private static TextField old, newt;
	public static DoubleLinkedList list = new DoubleLinkedList();

//	LocationNode current = list.getFirst();

	public void start(Stage arg0) throws Exception {
		upg = upp();

	}

	// ADD stage
	Stage upp() {
		// create stage
		upg = new Stage(StageStyle.DECORATED);

		// stage UIF

		BorderPane root = new BorderPane();
		// name of file and chose it
		HBox hbox = new HBox(10);
		old = new TextField();
		Label to = new Label("to -->");
		newt = new TextField();

		hbox.getChildren().add(newt);
		hbox.setAlignment(Pos.CENTER);
		root.setTop(hbox);

		Button upb = new Button("Update.");

		upb.setOnAction(event -> {
			String newn = newt.getText();
			Brand current = AdminStage.getBrandTable().getSelectionModel().getSelectedItem();
			if (current != null) {
				current.setBrand(newt.getText());
				AdminStage.getBrandTable().refresh();
			} else {
				System.out.println("Error: Current location is null");
			}

		});

		upb.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		upb.setPrefWidth(400);
		upb.setPrefHeight(100);

		root.setCenter(upb);

		// put pane in scene then scene in stage
		// layout the stage
		ups = new Scene(root, 450, 250);
		ups.getStylesheets().add("/com/javacodejunkie/stylesheet.css");
		// insert add scene to stage add
		upg.setScene(ups);
		upg.setTitle("Update screen");
		upg.initModality(Modality.APPLICATION_MODAL);
		return upg;

	}

}
