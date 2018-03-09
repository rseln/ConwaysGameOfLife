package javaCulminating;

//Rose Lin
//Mr.Radulovic 
//ICS3U 
//Culminating Project

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.util.Random;

public class Main extends Application {
	Scene menuScene;
	private TextField gridInput;
	private TextField gridWidth;

	public static void main(String args[]) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {

		//introducing buttons, labels, and text boxes for the main menu
		
		Label label1 = new Label("Conway's Game of Life"); //title
		label1.setFont(new Font(35)); //set font size 
		
		Label label2 = new Label("Specify the dimensions of the grid"); 
		
		//instructions text
		Label label3 = new Label("Instructions: Draw the cells by clicking on the desired squares.\nPress enter to start and pause the animation.\nYou can redraw while the animation is paused."); //Prompts for user input
		label3.setFont(new Font(11)); 
		label3.setTextAlignment(TextAlignment.CENTER); //align text to center
		
		gridInput= new TextField(); //Allows user to input a value for the height of the grid
		gridInput.setPromptText("Input an integer"); //add prompt text in the text box
		gridInput.setMaxWidth(300); //set size of the text box
		
		Button btnRandom = new Button("Random"); //randomly generates grid size 
		btnRandom.setMaxWidth(100); //set button size
		
		Button btnStart = new Button("Start"); //button to start the game of life
		btnStart.setMaxWidth(100); //set button size
		
		Button btnExit = new Button("Exit"); //button to exit the program
		btnExit.setMaxWidth(100); //set button size
		
		// Event handler so buttons can be used  
		
		btnRandom.setOnAction(new EventHandler<ActionEvent>() { //event handler for random button 
			@Override
			public void handle(ActionEvent event) {
				Random random = new Random(); //initialize random 
				int randomNumber = random.nextInt(200);	// generate a random # between 1 and 200
				gridInput.setText(Integer.toString(randomNumber)); //set the user input to the random integer
				
			}
		});
		
		btnStart.setOnAction(new EventHandler<ActionEvent>() { //event handler for start button 
			@Override
			public void handle(ActionEvent event) {
				if (isInt(gridInput.getText())){ //if the inputs are integers
					GameOfLife gameOfLife = new GameOfLife(); //call game of life 
					gameOfLife.start(primaryStage, Integer.parseInt(gridInput.getText()), Integer.parseInt(gridInput.getText()));
				}
			}
		});
		
		btnExit.setOnAction(new EventHandler<ActionEvent>() { //event handler for exit button 
			@Override
			public void handle(ActionEvent event) {
				Platform.exit(); //exit the program
			}
		});
		
		//organizing the menu
		
		VBox layout = new VBox(10); // stacks the items in a column; spaced by 10 pixels
		// adds all visual elements; replaces root
		//layout.getChildren().addAll(label1, label2, label3, label4, label5, gridHeight, gridWidth, label6, btnRandom, btnStart, btnExit); 
		layout.getChildren().addAll(label1, label2, gridInput, label3, btnRandom, btnStart, btnExit); 
		layout.setAlignment(Pos.CENTER); //centers all the elements 
		menuScene = new Scene(layout, 500, 500); //sets window dimensions

		// create the window
		primaryStage.setScene(menuScene);
		primaryStage.show();

	}
	
	//validating user input as an integer
	public boolean isInt(String gridInput) {
		try { //try to convert the user input values to an integer
			int size = Integer.parseInt(gridInput);
			return true; 	
		}catch (NumberFormatException e) { //catch conversion error
			return false; //isInt returns as false if input cannot become an integer (meaning it contained letters, symbols or decimal places)
		}
	
	
	}

}
