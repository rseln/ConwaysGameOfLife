package javaCulminating;

//Rose Lin
//Mr.Radulovic 
//ICS3U 
//Culminating Project

import javafx.scene.canvas.GraphicsContext; 
import javafx.scene.paint.Color;

public class Grid {
	private int tileSize = 10;
	private int rowNum;
	private int colNum;
	private int height;
	private int width; 
	private int gridX, gridY;
	
	public Grid(int row, int col) {
		rowNum = row;
		colNum = col;
		height = tileSize * colNum; // total height of window
		width = tileSize * rowNum; // total width of window
	}

	// draws grid on graphics context
	public void drawGrid(GraphicsContext gc) {
		// draw the current state of your map here
		for (int y = 0; y < rowNum; y++) {
			for (int x = 0; x < colNum; x++) { // x and y represent coordinates in the 2d array
				gridX = x * tileSize; // y position of the individual squares
				gridY = y * tileSize; // x position of the individual squares

				gc.setFill(Color.WHITE);
				gc.fillRect(gridX, gridY, tileSize, tileSize);

				// strokes each rectangle black, which makes it look like a grid
				gc.setStroke(Color.BLACK);
				gc.setLineWidth(0.7);
				gc.strokeRect(gridX, gridY, tileSize, tileSize);

			}
		}
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public int getRowNum() {
		return this.rowNum;
	}
	
	public int getColNum() {
		return this.colNum;
	}

	public double getTileSize() {
		return this.tileSize;
	}
	

	}



	
	

