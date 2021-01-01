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
	
<<<<<<< HEAD
	private final int DOUBLETILESIZE = 100;
	private Level[] levels = new Level[15];
	
	private final int xOffset = (Window.WIDTH - DOUBLETILESIZE*5 - 10)/2;
	private final int yOffset = (Window.HEIGHT - DOUBLETILESIZE*4 - 10)/2;
=======
	private final int DOUBLETILESIZE = 74;
	private Level[] levels = new Level[30];
	
	private final int xOffset = (Window.WIDTH - DOUBLETILESIZE*7)/2;
	private final int yOffset = (Window.HEIGHT - DOUBLETILESIZE*4)/2;
	private final int SPACE = 10;
	
//	private final int xOffset = (Window.WIDTH - DOUBLETILESIZE*4)/2;
//	private final int yOffset = (Window.HEIGHT - DOUBLETILESIZE*2)/2;
>>>>>>> origin/alex
	
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
			
		}, Assets.font30,new Color(0xFFDA77));
		
	}


	@Override
	public void update() {
		back.update();
	}


	@Override
	public void render(Graphics g) {
		back.render(g);
		int counter = 1;
		Color solvedColor = new Color(0x02A79F);
		Color unsolvedColor = new Color(0xC4C4C4);
		
		//background image
		g.drawImage(Assets.moon, -100, -50, null);
		g.drawImage(Assets.uranus, 0, window.HEIGHT/2+20, null);
		g.drawImage(Assets.jupiter, window.WIDTH/2+180, -100, null);
		g.drawImage(Assets.saturn, window.WIDTH/2+150, window.HEIGHT/2, null);
		
<<<<<<< HEAD
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				Rectangle bounds = new Rectangle(xOffset + j*DOUBLETILESIZE, yOffset + i*DOUBLETILESIZE, DOUBLETILESIZE, DOUBLETILESIZE);
				State.currentArrLevel = levels[counter - 1];
//				System.out.println("counter out "  + counter);
=======
		//header level
		g.setFont(Assets.fontTitle);
		Text.drawString(g, "Level", window.WIDTH/2, window.HEIGHT/9, true, new Color (0, 45, 42));
		
		for(int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				//space column and row
				int spaceX=j*70;
				int spaceY=i*20;
				
				//set font
				Rectangle bounds;
				g.setFont(Assets.font22);
				
				g.fillRoundRect(xOffset + j*DOUBLETILESIZE+spaceX, yOffset + i*DOUBLETILESIZE+spaceY , DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
				
				//bounds for hover check
				bounds = new Rectangle(xOffset + j*DOUBLETILESIZE+spaceX, yOffset + i*DOUBLETILESIZE+spaceY , DOUBLETILESIZE, DOUBLETILESIZE);
				//condition for hover, get gameplay state
>>>>>>> origin/alex
				if(bounds.contains(MouseManager.x, MouseManager.y)) {
					if(MouseManager.left && State.currentArrLevel.isSolved()) {
//						System.out.println("counter "  + counter);
						((GameState)window.getGameState()).setLevel(State.currentArrLevel);
						State.currentState = window.getGameState();
					}
<<<<<<< HEAD
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
=======
					if(levels[counter-1].isSolved()) {
						g.setColor(solvedColor.darker());
						g.fillRoundRect(xOffset + j*DOUBLETILESIZE+spaceX, yOffset + i*DOUBLETILESIZE+spaceY , DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
						Text.drawString(g, counter+"", xOffset + j*DOUBLETILESIZE+spaceX + DOUBLETILESIZE/2, yOffset + spaceY +i*DOUBLETILESIZE + DOUBLETILESIZE/2, true, Color.white.darker());
					}	
					else {
						g.setColor(unsolvedColor.darker());
						g.fillRoundRect(xOffset + j*DOUBLETILESIZE+spaceX, yOffset + i*DOUBLETILESIZE+spaceY , DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
						Text.drawString(g, "?", xOffset + j*DOUBLETILESIZE+spaceX + DOUBLETILESIZE/2, yOffset + spaceY +i*DOUBLETILESIZE + DOUBLETILESIZE/2, true, Color.white.darker());
					}	
				} else {
					if(levels[counter-1].isSolved()) {
						g.setColor(solvedColor);
						g.fillRoundRect(xOffset + j*DOUBLETILESIZE+spaceX, yOffset + i*DOUBLETILESIZE+spaceY , DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
						Text.drawString(g, counter+"", xOffset + j*DOUBLETILESIZE+spaceX + DOUBLETILESIZE/2, yOffset + spaceY + i*DOUBLETILESIZE + DOUBLETILESIZE/2, true, Color.white);
					}
					else {
						g.setColor(unsolvedColor.darker());
						g.fillRoundRect(xOffset + j*DOUBLETILESIZE+spaceX, yOffset + i*DOUBLETILESIZE+spaceY , DOUBLETILESIZE, DOUBLETILESIZE, 25, 25);
						Text.drawString(g, "?", xOffset + j*DOUBLETILESIZE+spaceX + DOUBLETILESIZE/2, yOffset + spaceY + i*DOUBLETILESIZE + DOUBLETILESIZE/2, true, Color.white);
					}	
>>>>>>> origin/alex
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
