package javaCulminating;

//Rose Lin
//Mr.Radulovic 
//ICS3U 
//Culminating Project

import javafx.animation.AnimationTimer; 
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.input.*;
import javafx.stage.Stage;

public class GameOfLife {
	//Initializing global variables
	private Canvas canvas;
	private Grid grid;
	private GraphicsContext gc;
	private CellState cellState;
	private long oldTime;
	private double elapsedTime;
	private int tileSize;
	private double gridX, gridY;
	private boolean[][] startGrid; //starting 2d array determines drawn cells to be alive/dead
	private boolean[][] copyGrid; //copyGrid implements any changes (cell deaths + reproduction) onto another 2d array 
	private boolean running; //controls if the program updates/runs

	public void start(Stage primaryStage, int col, int row) {	
		running = false; //set running to false 

		cellState = new CellState(); // introduce cellState class
		
		grid = new Grid(col, row); // introduce Grid class
		tileSize = (int) grid.getTileSize(); //sets variable to given tile size in the grid class
		
		startGrid = new boolean[grid.getColNum()][grid.getRowNum()]; //initialize the startGrid
		copyGrid = new boolean[grid.getColNum()][grid.getRowNum()]; //initialize the copyGrid

		// create a canvas
		canvas = new Canvas(col * tileSize, row * tileSize);

		// get graphics context
		gc = canvas.getGraphicsContext2D();

		// add canvas to root
		Pane root = new Pane();
		root.getChildren().add(canvas);

		// initialize stage values
		Scene scene = new Scene(root, grid.getWidth(), grid.getHeight());

		// draws the grid onto the window
		grid.drawGrid(gc); //calls on the drawing method in grid class

		// create the window
		primaryStage.setTitle("Game of Life");
		primaryStage.setScene(scene);
		primaryStage.show();

		// setup a timer to be used for repeatedly redrawing the scene
		oldTime = System.nanoTime();
		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				double deltaT = (now - oldTime) / 1000000000.0;
				onUpdate(deltaT);
				oldTime = now;
			}
		};

		timer.start(); //starting the timer

		// Event Handler for enter key
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				//enter key controls if the program runs or stops 
				if (key.getCode() == KeyCode.ENTER && running == false) { //if not running 
					running = !running; //set running to true (starts algorithm in onUpdate)
				}
				else if (key.getCode() == KeyCode.ENTER && running == true) { //if already running
					running = !running; //sets running to false (pauses algorithm in onUpdate
				}
			}
		});

		// Mouse Event allows user to draw on the grid
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				//identifies the x and y of the NODE in the 2D array that has been clicked
				int x = (int) (event.getX() / tileSize); 
				int y = (int) (event.getY() / tileSize);
				startGrid[x][y] = true; //sets start grid to true (so draw can run)
				draw(); //calls the drawing method
			}
		});
	}

	public void draw() { //allows user to draw on the grid 

		//for loop identifies which square is being coloured
		for (int y = 0; y < grid.getRowNum(); y++) { //y value
			for (int x = 0; x < grid.getColNum(); x++) { //x value 

				//tileSize = grid.getTileSize();
				
				//determines actual coordinates of the tile 
				gridX = x * tileSize; //x coordinate
				gridY = y * tileSize; //y coordinate

				//first colour all the tiles white 
				gc.setStroke(Color.BLACK); 
				gc.setFill(Color.WHITE);
				
				if (startGrid[x][y]) { //if true 
					startGrid[x][y] = cellState.alive(); //set the tile in the 2d array to alive(true)
					gc.setFill(Color.BLACK); //set the tile as black 
				}
				//fill and colour the tile (identified by their actual coordinates)
				gc.fillRect(gridX, gridY, tileSize, tileSize);  
				gc.strokeRect(gridX, gridY, tileSize, tileSize);
			}
		}
	}

	private void algorithm() { //executes the game of life based on its rules & the current situation of the cells on the grid
		
		//for loop checks every single tile in the grid 
		for (int x = 0; x < startGrid.length; x++) { 
			for (int y = 0; y < startGrid[0].length; y++) {

				//checks the cell's surroundings for other live cells. Gets the number of neighbors
				int numNeighbour = 0; //initializes neighbour counter

				//checks if the tile is in bounds and if its neighboring tiles harbor a live cell 
				
				//checks bottom right corner of surrounding tiles for live cell  
				if (x < startGrid.length-1 && y < startGrid[0].length-1 && startGrid[x + 1][y + 1] == cellState.alive()) {
					numNeighbour += 1; //updates neighbour counter 
				}
				//checks top right corner of surrounding tiles for live cell
				if (x < startGrid.length-1 && y > 0 && startGrid[x + 1][y - 1] == cellState.alive()) {
					numNeighbour += 1;
				}
				//checks bottom left corner of surrounding tiles for live cell
				if (x > 0 && y < startGrid[0].length-1 && startGrid[x - 1][y + 1] == cellState.alive()) {
					numNeighbour += 1;
				}
				//checks top left corner of surrounding tiles for live cell
				if (x > 0 && y > 0 && startGrid[x - 1][y - 1] == cellState.alive()) {
					numNeighbour += 1;
				}
				//checks up for live cell
				if (y > 0 && startGrid[x][y - 1] == cellState.alive()) {
					numNeighbour += 1;
				}
				//checks down for live cell
				if (y < startGrid[0].length-1 && startGrid[x][y + 1] == cellState.alive()) {
					numNeighbour += 1;
				}
				//checks right for live cell
				if (x < startGrid.length-1 && startGrid[x + 1][y] == cellState.alive()) {
					numNeighbour += 1;
				}
				//checks left for live cell
				if (x > 0 && startGrid[x - 1][y] == cellState.alive()) {
					numNeighbour += 1;
				}
				//rules for Conway's game of life 
				
				//Any live cell with fewer than two live neighbours dies by underpopulation
				if ((numNeighbour < 2) && startGrid[x][y] == cellState.alive()) { 
					copyGrid[x][y] = cellState.dead();

                //Any live cell with more than three live neighbours dies by overpopulation
				} else if ((numNeighbour > 3) && startGrid[x][y] == cellState.alive()) { 
					copyGrid[x][y] = cellState.dead();

				//Any live cell with two or three live neighbours lives
				} else if ((numNeighbour == 2 || numNeighbour == 3) && startGrid[x][y] == cellState.alive()) {
					copyGrid[x][y] = cellState.alive();

			    //Any dead cell with exactly three live neighbours becomes a live cell by reproduction.
				} else if (startGrid[x][y] == cellState.dead() && numNeighbour == 3) {
					copyGrid[x][y] = cellState.alive();
		
				//Set everything else as dead
				} else {
					copyGrid[x][y] = cellState.dead();
				}
			}
		}
	}

	private void onUpdate(double deltaT) { //update runs animation
		elapsedTime += deltaT;

		if (elapsedTime > 0.5 && running) {
			copyGrid = new boolean[grid.getColNum()][grid.getRowNum()]; //reinitialize copyGrid
			algorithm(); //call algorithm
			elapsedTime = 0;
			startGrid = copyGrid; //merge startGrid (original) with copyGrid (modified grid)
			draw(); //call drawing method
		}

	}
}
