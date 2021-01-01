package com.sophiego.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sophiego.gfx.Assets;
import com.sophiego.gfx.Text;
import com.sophiego.input.MouseManager;
import com.sophiego.main.Window;
import com.sophiego.sophie.Level;
import com.sophiego.ui.Button;
import com.sophiego.ui.Click;
import com.sophiego.states.State;

public class LevelSelectorState extends State{
	
	private final int DOUBLETILESIZE = 100;
	private Level[] levels = new Level[15];
	
	private final int xOffset = (Window.WIDTH - DOUBLETILESIZE*5 - 10)/2;
	private final int yOffset = (Window.HEIGHT - DOUBLETILESIZE*4 - 10)/2;
	
	private Button back;
	
	public LevelSelectorState(Window window) {
		super(window);
		
		for(int i = 0; i < levels.length; i++)
			levels[i] = loadLevel("/levels/" + i + ".txt");
		
		back = new Button("BACK", Window.WIDTH/2, Window.HEIGHT - 100, new Click() {
			
			@Override
			public void onClick() {
				State.currentState = window.getMenuState();
			}
			
		}, Assets.font30);
		
	}


	@Override
	public void update() {
		back.update();
	}


	@Override
	public void render(Graphics g) {
		back.render(g);
		int counter = 1;
		g.setFont(Assets.font30);
		
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				Rectangle bounds = new Rectangle(xOffset + j*DOUBLETILESIZE, yOffset + i*DOUBLETILESIZE, DOUBLETILESIZE, DOUBLETILESIZE);
				State.currentArrLevel = levels[counter - 1];
//				System.out.println("counter out "  + counter);
				if(bounds.contains(MouseManager.x, MouseManager.y)) {
					if(MouseManager.left && State.currentArrLevel.isSolved()) {
//						System.out.println("counter "  + counter);
						((GameState)window.getGameState()).setLevel(State.currentArrLevel);
						State.currentState = window.getGameState();
					}
					g.drawImage(Assets.outline2, bounds.x, bounds.y, null);
					if(State.currentArrLevel.isSolved()) {
						Text.drawString(g, counter+"", xOffset + j*DOUBLETILESIZE + DOUBLETILESIZE/2, yOffset + i*DOUBLETILESIZE + DOUBLETILESIZE/2, true, Color.RED);
					} else {
						Text.drawString(g, "?", xOffset + j*DOUBLETILESIZE + DOUBLETILESIZE/2, yOffset + i*DOUBLETILESIZE + DOUBLETILESIZE/2, true, Color.RED);
					}
				} else {
					g.drawImage(Assets.outline, bounds.x, bounds.y, null);
					if(State.currentArrLevel.isSolved())
						Text.drawString(g, counter+"", xOffset + j*DOUBLETILESIZE + DOUBLETILESIZE/2, yOffset + i*DOUBLETILESIZE + DOUBLETILESIZE/2, true, Color.GREEN);
					else
						Text.drawString(g, "?", xOffset + j*DOUBLETILESIZE + DOUBLETILESIZE/2, yOffset + i*DOUBLETILESIZE + DOUBLETILESIZE/2, true, Color.BLUE);
				}
				counter++;
			}
		}
		
	}

	private Level loadLevel(String path) {
		String file = loadFileAsString(path);
		String[] numbers = file.split("\\s+");
		
		int cols = parseInt(numbers[0]);
		int rows = parseInt(numbers[1]);
	
		int player_col = parseInt(numbers[2]);
		int player_row = parseInt(numbers[3]);
		
		int[][] maze = new int[rows][cols];
		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
				maze[row][col] = parseInt(numbers[(col + (row*cols)) + 4]);
		
		return new Level(maze, player_row, player_col, this);
	}

	private int parseInt(String num) {
		try {
			return Integer.parseInt(num);
		}catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}


	private String loadFileAsString(String path) {
		StringBuilder builder = new StringBuilder();
		try {
			InputStream in = LevelSelectorState.class.getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line;
			while((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
			br.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}


	public void showResult() {
		State.currentState = window.getResultState();
	}
	
	public void showGameOver() {
		State.currentState = window.getGameOverState();
	}
	
	public Level[] getLevels() {
		return levels;
	}

}
