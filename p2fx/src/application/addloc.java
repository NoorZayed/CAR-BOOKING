package application;

import java.io.File;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class addloc {
	public static Stage addg;
	public static Scene adds;
	private static TextField t;

	public void start(Stage arg0) throws Exception {
		addg = addp();

	}

	// ADD stage
	Stage addp() {
		// create stage
		addg = new Stage(StageStyle.DECORATED);

		// stage UIF

		BorderPane root = new BorderPane();
		// name of file and chose it
		HBox hbox = new HBox(10);
		t = new TextField();

		hbox.getChildren().add(t);
		hbox.setAlignment(Pos.CENTER);
		root.setTop(hbox);

		Button addb = new Button("Add");

		// addb.addEventHandler(ActionEvent.ACTION, new events());
		addb.setOnAction(event -> {
			String title = t.getText();
			Brand temp = new Brand(title);
			if (AdminStage.list.found(temp) == false) {
				System.out.println(AdminStage.list.found(temp));
				AdminStage.list.addLast(temp);
				System.out.println(((Brand) AdminStage.list.last.getB()).getBrand());
				AdminStage.getBrandTable().getItems().add((Brand) AdminStage.list.last.getB());
				AdminStage.getBrandTable().refresh();
			}
		});

		addb.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
		addb.setPrefWidth(400);
		addb.setPrefHeight(100);

		root.setCenter(addb);

		// put pane in scene then scene in stage
		// layout the stage
		adds = new Scene(root, 450, 250);
		adds.getStylesheets().add("/com/javacodejunkie/stylesheet.css");
		// insert add scene to stage add
		addg.setScene(adds);
		addg.setTitle("Add screen");
		addg.initModality(Modality.APPLICATION_MODAL);
		return addg;

	}

}
